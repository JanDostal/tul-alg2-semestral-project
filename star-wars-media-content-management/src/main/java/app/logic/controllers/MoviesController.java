
package app.logic.controllers;

import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManager;
import app.models.data.Era;
import app.models.data.Movie;
import java.text.Collator;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.mail.EmailException;
import utils.emailsender.EmailSender;

/**
 *
 * @author Admin
 */
public class MoviesController 
{
    private static MoviesController movieController;
    
    private final DataContextAccessor dbContext;
    
    private final EmailSender emailSender;
    
    private final FileManager fileManager;
    
    private final Collator czechCollator = DataStore.loadCzechCollator();
    
    private final Comparator<Movie> BY_LONGEST_DURATION_MOVIE = (Movie m1, Movie m2) -> 
    {
        if (m1.getRuntime() == null && m2.getRuntime() == null) 
        {
            return 0;
        } 
        else if (m1.getRuntime() == null) 
        {
            return 1;
        } 
        else if (m2.getRuntime() == null) 
        {
            return -1;
        } 
        
        return m2.getRuntime().compareTo(m1.getRuntime());
    };

    private final Comparator<Movie> BY_NAME_ALPHABETICALLY_MOVIE = (Movie m1, Movie m2) -> 
            czechCollator.compare(m1.getName(), m2.getName());
    
    private final Comparator<Movie> BY_DATE_NEWEST_MOVIE = (Movie m1, Movie m2) -> 
    {
        if (m1.getReleaseDate() == null && m2.getReleaseDate() == null) 
        {
            return 0;
        } 
        else if (m1.getReleaseDate() == null) 
        {
            return 1;
        } 
        else if (m2.getReleaseDate() == null) 
        {
            return -1;
        }
        
        return m2.getReleaseDate().compareTo(m1.getReleaseDate());
    };
    
    private final Comparator<Movie> BY_DATE_OLDEST_MOVIE = (Movie m1, Movie m2) -> 
    {
        if (m1.getReleaseDate() == null && m2.getReleaseDate() == null) 
        {
            return 0;
        } 
        else if (m1.getReleaseDate() == null) 
        {
            return 1;
        } 
        else if (m2.getReleaseDate() == null) 
        {
            return -1;
        }
        
        return m1.getReleaseDate().compareTo(m2.getReleaseDate());
    };
    
    private final Comparator<Movie> BY_PERCENTAGE_RATING_HIGHEST_MOVIE = (Movie m1, Movie m2) -> 
    {        
        return m2.getPercentageRating() - m1.getPercentageRating();
    };
    
    private MoviesController(DataContextAccessor dbContext, EmailSender emailSender, FileManager fileManager) 
    {
        this.dbContext = dbContext;
        this.emailSender = emailSender;
        this.fileManager = fileManager;
    }
    
    public static MoviesController getInstance(DataContextAccessor dbContext, EmailSender emailSender, 
            FileManager fileManager) 
    {
        if (movieController == null) 
        {
            movieController = new MoviesController(dbContext, emailSender, fileManager);
        }
        
        return movieController;
    }
    
    //file method
    public void configureDataDirectory(String dataDirectoryPath) 
    {
        fileManager.setDataDirectory(dataDirectoryPath);
    }
    
    //email method
    public void sendUnwatchedOldestMoviesWithHyperlinks(String recipientEmailAddress) throws EmailException 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == false);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_OLDEST_MOVIE, filteredMovies);
        
        String subject = String.format("%s - Neshlédnuté filmy - Podle datumu uvedení", DataStore.loadAppName());
        
        StringBuilder message = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        message.append("<html>");
        message.append("<h1>Neshlédnuté filmy od nejstaršího datumu uvedení</h1>");
        message.append("<ul>");
        
        for (Movie m : filteredMovies) 
        {
            String durationText = m.getRuntime() == null ? null : String.format("%02d:%02d:%02d", 
                    m.getRuntime().toHours(), m.getRuntime().toMinutesPart(), 
                    m.getRuntime().toSecondsPart());
            
            message.append("<li>");
            message.append("<h2>");
            message.append(m.getName());
            message.append("</h2>");
            message.append("<p>");
            message.append(String.format("Datum vydání: %s", m.getReleaseDate().format(formatter)));
            message.append("</p>");
            message.append("<p>");
            message.append(String.format("Délka filmu: %s", durationText));
            message.append("</p>");
            message.append(String.format("<a href=\"%s\">", m.getHyperlinkForContentWatch()));
            message.append("Shlédnout");
            message.append("</a>");
            message.append("</li>");
        }
        message.append("</ul>");
        message.append("</html>");
        
        emailSender.sendEmail(recipientEmailAddress, subject, message);
    }
    
    //email method
    public void sendUnwatchedMoviesWithHyperlinksInChronologicalEras(String recipientEmailAddress) 
            throws EmailException 
    {
        LocalDate currentDate = getCurrentDate();
        List<Movie> filteredMovies;
        
        String subject = String.format("%s - Neshlédnuté filmy - Podle chronologických období", 
                DataStore.loadAppName());
        StringBuilder message = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        message.append("<html>");
        message.append("<h1>Neshlédnuté filmy od nejstaršího chronologického období</h1>");
        
        for (Era era : Era.values()) 
        {
            filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == false && m.getEra() == era);
            
            dbContext.getMoviesTable().sortBy(BY_NAME_ALPHABETICALLY_MOVIE, filteredMovies);
            
            message.append("<h2>");
            message.append(String.format("%s", era.getDisplayName()));
            message.append("</h2>");
            
            if (filteredMovies.isEmpty()) 
            {
                message.append("<br>");
                message.append("<br>");
                message.append("<br>");
            }
            else 
            {
                message.append("<ul>");
            
                for (Movie m : filteredMovies) 
                {
                    String durationText = m.getRuntime() == null ? null : String.format("%02d:%02d:%02d", 
                            m.getRuntime().toHours(), m.getRuntime().toMinutesPart(), 
                            m.getRuntime().toSecondsPart());
            
                    message.append("<li>");
                    message.append("<h3>");
                    message.append(m.getName());
                    message.append("</h3>");
                    message.append("<p>");
                    message.append(String.format("Datum vydání: %s", m.getReleaseDate().format(formatter)));
                    message.append("</p>");
                    message.append("<p>");
                    message.append(String.format("Délka filmu: %s", durationText));
                    message.append("</p>");
                    message.append(String.format("<a href=\"%s\">", m.getHyperlinkForContentWatch()));
                    message.append("Shlédnout");
                    message.append("</a>");
                    message.append("</li>");
                }
            
                message.append("</ul>");
            }
        }
        
        message.append("</html>");
        
        emailSender.sendEmail(recipientEmailAddress, subject, message);
    }
    
    //statistic method
    public Duration getTotalRuntimeOfAllMoviesByEra(Era era, boolean onlyWatched)
    {
        Duration duration = Duration.ZERO;
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        for (Movie m : filteredMovies) 
        {
            if (m.getRuntime() != null) 
            {
                duration = duration.plus(m.getRuntime());
            }
        }
                
        return duration;
    }
    
    //statistic method
    public Duration getAverageRuntimeOfAllMoviesByEra(Era era, boolean onlyWatched)
    {
        Duration duration = Duration.ZERO;
        long averageSeconds;
        int durationsCount = 0;
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        for (Movie m : filteredMovies) 
        {
            if (m.getRuntime() != null) 
            {
                durationsCount++;
                duration = duration.plus(m.getRuntime());
            }
        }
        
        averageSeconds = duration.toSeconds() / durationsCount;
              
        return Duration.ofSeconds(averageSeconds);
    }
    
    //statistic method
    public float getAverageRatingOfAllMoviesByEra(Era era)
    {
        float averageRating;
        long totalRating = 0;
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == true);
        
        for (Movie m : filteredMovies) 
        {
            totalRating += m.getPercentageRating();
        }
        
        averageRating = totalRating / (float) filteredMovies.size();
              
        return averageRating;
    }
    
    public List<Movie> getLongestMoviesByEra(Era era, boolean onlyWatched) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        dbContext.getMoviesTable().sortBy(BY_LONGEST_DURATION_MOVIE, filteredMovies);
        return filteredMovies;
    }
    
    public List<Movie> getMoviesByEraInAlphabeticalOrder(Era era, boolean onlyWatched) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        dbContext.getMoviesTable().sortBy(BY_NAME_ALPHABETICALLY_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    public List<Movie> getNewestMoviesByEra(Era era, boolean onlyWatched) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_NEWEST_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    public List<Movie> getFavoriteMoviesByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == true);
        
        dbContext.getMoviesTable().sortBy(BY_PERCENTAGE_RATING_HIGHEST_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    public List<Movie> getAnnouncedMovies(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && (m.getReleaseDate() == null || 
                        m.getReleaseDate().compareTo(currentDate) > 0));
        
        dbContext.getMoviesTable().sortBy(BY_NAME_ALPHABETICALLY_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    public int getAnnouncedMoviesCountByEra(Era era) 
    {
        List<Movie> filteredMovies = getAnnouncedMovies(era);
                
        return filteredMovies.size();
    }
    
    public int getMoviesCountByEra(Era era, boolean onlyWatched) 
    {        
        List<Movie> filteredMovies = getNewestMoviesByEra(era, onlyWatched);
                
        return filteredMovies.size();
    }
    
    public List<Movie> getFavoriteMoviesOfAllTime() 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && 
                m.getReleaseDate().compareTo(currentDate) <= 0 && 
                        m.getWasWatched() == true);
        
        dbContext.getMoviesTable().sortBy(BY_PERCENTAGE_RATING_HIGHEST_MOVIE, filteredMovies);
                
        return filteredMovies;
    }
    
    public List<Movie> getNewestMovies()
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && 
                m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_NEWEST_MOVIE, filteredMovies);
                
        return filteredMovies;
    }
    
    public boolean rateMovie(Movie existingMovie, int percentageRating)
    {
        Movie newData = new Movie(existingMovie.getPrimaryKey(), 
                    existingMovie.getRuntime(), 
                    existingMovie.getName(), 
                    percentageRating, 
                    true, 
                    existingMovie.getHyperlinkForContentWatch(),
                    existingMovie.getShortContentSummary(), 
                    existingMovie.getReleaseDate(), 
                    existingMovie.getEra());
        
        boolean wasDataChanged = dbContext.getMoviesTable().editBy(existingMovie.getPrimaryKey(), newData);
                
        return wasDataChanged;
    }
    
    public List<Movie> searchForMovie(String name) 
    {
        String normalizedName = Normalizer.normalize(name, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase();
        
        String regex = normalizedName;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("");
        
        List<Movie> foundMovies = dbContext.getMoviesTable().filterBy(movie -> 
        {
            String normalizedMovieName = Normalizer.normalize(movie.getName(), Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase();
            matcher.reset(normalizedMovieName);
            return matcher.find();
        });
        
        return foundMovies;
    }
        
    private static LocalDate getCurrentDate() 
    {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
