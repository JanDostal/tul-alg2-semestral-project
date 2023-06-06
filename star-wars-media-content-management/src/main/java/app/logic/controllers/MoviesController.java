
package app.logic.controllers;

import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import app.models.input.MovieInput;
import app.models.output.MovieOutput;
import app.models.output.TVEpisodeOutput;
import app.models.output.TVSeasonOutput;
import app.models.output.TVShowOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Collator;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.mail.EmailException;
import utils.emailsender.EmailSender;
import utils.exceptions.DatabaseException;
import utils.helpers.MovieDataConverter;
import utils.helpers.TVEpisodeDataConverter;
import utils.helpers.TVSeasonDataConverter;
import utils.helpers.TVShowDataConverter;

/**
 *
 * @author Admin
 */
public class MoviesController 
{
    private static MoviesController movieController;
    
    private final DataContextAccessor dbContext;
    
    private final EmailSender emailSender;
    
    private final FileManagerAccessor fileManagerAccessor;
    
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
    
    private MoviesController(DataContextAccessor dbContext, EmailSender emailSender, 
            FileManagerAccessor fileManagerAccessor) 
    {
        this.dbContext = dbContext;
        this.emailSender = emailSender;
        this.fileManagerAccessor = fileManagerAccessor;
    }
    
    public static MoviesController getInstance(DataContextAccessor dbContext, EmailSender emailSender, 
            FileManagerAccessor fileManagerAccessor) 
    {
        if (movieController == null) 
        {
            movieController = new MoviesController(dbContext, emailSender, fileManagerAccessor);
        }
        
        return movieController;
    }
    
    //email method
    public void sendUnwatchedOldestMoviesWithHyperlinksByEmail(String recipientEmailAddress) throws EmailException 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == false);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_OLDEST_MOVIE, filteredMovies);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        String subject = String.format("%s - Nezhlédnuté filmy - Podle datumu uvedení", DataStore.getAppName());
        
        StringBuilder message = new StringBuilder();
        String durationText;
        String hyperlinkText;
        
        message.append("<html>");
        message.append("<h1>Nezhlédnuté filmy od nejstaršího datumu uvedení</h1>");
        
        if(filteredMovies.isEmpty()) 
        {
            message.append("<br>");
            message.append("<p style=\"color:red\">Žádné filmy</p>");
            message.append("<br>");
        }
        else 
        {
            message.append("<ul>");

            for (Movie m : filteredMovies) 
            {
                durationText = m.getRuntime() == null ? "<span style=\"color:red\">Není známa</span>" : 
                        String.format("%dh %dm %ds", m.getRuntime().toHours(), m.getRuntime().toMinutesPart(),
                        m.getRuntime().toSecondsPart());
                
                hyperlinkText = m.getHyperlinkForContentWatch() == null ? "<span style=\"color:red\">Neuveden</span>" : 
                                String.format("<a href=\"%s\">Zhlédnout</a>", m.getHyperlinkForContentWatch());

                message.append("<li>");
                message.append("<h2>");
                message.append(String.format("Film %s", m.getName()));
                message.append("</h2>");
                message.append("<p>");
                message.append(String.format("Datum vydání: %s", m.getReleaseDate().format(formatter)));
                message.append("</p>");
                message.append("<p>");
                message.append(String.format("Délka filmu: %s", durationText));
                message.append("</p>");
                message.append("<p>");
                message.append(String.format("Odkaz ke zhlédnutí: %s", hyperlinkText));
                message.append("</p>");
                message.append("</li>");
            }

            message.append("</ul>");
        }
        
        message.append("</html>");
        
        emailSender.sendEmail(recipientEmailAddress, subject, message);
    }
    
    //email method
    public void sendUnwatchedMoviesWithHyperlinksInChronologicalErasByEmail(String recipientEmailAddress) throws EmailException 
    {
        LocalDate currentDate = getCurrentDate();
        List<Movie> filteredMovies;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        String subject = String.format("%s - Nezhlédnuté filmy - Podle chronologických období", 
                DataStore.getAppName());
        
        StringBuilder message = new StringBuilder();
        String durationText;
        String hyperlinkText;
        
        message.append("<html>");
        message.append("<h1>Nezhlédnuté filmy od nejstaršího chronologického období</h1>");
        
        for (Era era : Era.values()) 
        {
            filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == false && m.getEra() == era);
            
            dbContext.getMoviesTable().sortBy(BY_NAME_ALPHABETICALLY_MOVIE, filteredMovies);
            
            message.append("<h2>");
            message.append(String.format("Období %s", era.getDisplayName()));
            message.append("</h2>");
            
            if (filteredMovies.isEmpty()) 
            {
                message.append("<br>");
                message.append("<p style=\"color:red\">Žádné filmy</p>");
                message.append("<br>");
            }
            else 
            {
                message.append("<ul>");
            
                for (Movie m : filteredMovies) 
                {
                    durationText = m.getRuntime() == null ? "<span style=\"color:red\">Není známa</span>" : 
                            String.format("%dh %dm %ds", m.getRuntime().toHoursPart(), m.getRuntime().toMinutesPart(), 
                            m.getRuntime().toSecondsPart());
                    
                    hyperlinkText = m.getHyperlinkForContentWatch() == null ? "<span style=\"color:red\">Neuveden</span>" : 
                                String.format("<a href=\"%s\">Zhlédnout</a>", m.getHyperlinkForContentWatch());
            
                    message.append("<li>");
                    message.append("<h3>");
                    message.append(String.format("Film %s", m.getName()));
                    message.append("</h3>");
                    message.append("<p>");
                    message.append(String.format("Datum vydání: %s", m.getReleaseDate().format(formatter)));
                    message.append("</p>");
                    message.append("<p>");
                    message.append(String.format("Délka filmu: %s", durationText));
                    message.append("</p>");
                    message.append("<p>");
                    message.append(String.format("Odkaz ke zhlédnutí: %s", hyperlinkText));
                    message.append("</p>");
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
    
    public boolean rateMovie(Movie existingMovie, int percentageRating) throws DatabaseException
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
    
    public StringBuilder getMoviesChosenFileContent(String fileName) throws IOException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryOutputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getBinaryOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextOutputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getTextOutputFileContent();
        }
        
        return content;
    }
    
    public Movie getMovieDetail(PrimaryKey chosenMoviePrimaryKey) 
    {
        Movie foundMovie = dbContext.getMoviesTable().getBy(chosenMoviePrimaryKey);
        
        return foundMovie;
    }
    
    public void loadAllOutputDataFrom(boolean fromBinary) throws IOException 
    {
        fileManagerAccessor.getMoviesFileManager().tryCreateDataOutputFiles();
        fileManagerAccessor.getTVShowsFileManager().tryCreateDataOutputFiles();
        fileManagerAccessor.getTVSeasonsFileManager().tryCreateDataOutputFiles();
        fileManagerAccessor.getTVEpisodesFileManager().tryCreateDataOutputFiles();
        
        //vyhazovani vyjimky v kazde z nasledujicich metod
        List<MovieOutput> outputMovies = fileManagerAccessor.getMoviesFileManager().
                loadOutputDataFrom(fromBinary);
        List<TVShowOutput> outputTVShows = fileManagerAccessor.getTVShowsFileManager().
                loadOutputDataFrom(fromBinary);
        List<TVSeasonOutput> outputTVSeasons = fileManagerAccessor.getTVSeasonsFileManager().
                loadOutputDataFrom(fromBinary);
        List<TVEpisodeOutput> outputTVEpisodes = fileManagerAccessor.getTVEpisodesFileManager().
                loadOutputDataFrom(fromBinary);
        
        Movie convertedOutputMovie;
        TVShow convertedOutputTVShow;
        TVSeason convertedOutputTVSeason;
        TVEpisode convertedOutputTVEpisode;
                    
        for (MovieOutput m : outputMovies) 
        {
            convertedOutputMovie = MovieDataConverter.convertToDataFrom(m);
            dbContext.getMoviesTable().loadFrom(convertedOutputMovie);
        }
        
        for (TVShowOutput m : outputTVShows) 
        {
            convertedOutputTVShow = TVShowDataConverter.convertToDataFrom(m);
            dbContext.getTVShowsTable().loadFrom(convertedOutputTVShow);
        }
        
        for (TVSeasonOutput m : outputTVSeasons) 
        {
            convertedOutputTVSeason = TVSeasonDataConverter.convertToDataFrom(m);
            dbContext.getTVSeasonsTable().loadFrom(convertedOutputTVSeason);
        }
        
        for (TVEpisodeOutput m : outputTVEpisodes) 
        {
            convertedOutputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(m);
            dbContext.getTVEpisodesTable().loadFrom(convertedOutputTVEpisode);
        }
    }
    
    public int addMoviesFrom(boolean fromBinary) throws IOException, FileNotFoundException 
    {
        updateMoviesOutputFilesWithExistingData();
        
        List<MovieInput> inputMovies = fileManagerAccessor.getMoviesFileManager().loadInputDataFrom(fromBinary);
        
        if (inputMovies.isEmpty()) 
        {
            return 0;
        }
        else 
        {
            Movie convertedInputMovie;
            
            for (MovieInput inputMovie : inputMovies) 
            {
                convertedInputMovie = MovieDataConverter.convertToDataFrom(inputMovie);
                dbContext.getMoviesTable().addFrom(convertedInputMovie);
            }

            fileManagerAccessor.getMoviesFileManager().transferBetweenOutputDataAndCopyFiles(false);
                  
            updateMoviesOutputFilesWithNewChanges();
        }
        
        //pocet nahranych filmu
        return 5;
    }
    
    public void deleteMovieBy(PrimaryKey moviePrimaryKey) throws IOException
    {
        updateMoviesOutputFilesWithExistingData();

        fileManagerAccessor.getMoviesFileManager().transferBetweenOutputDataAndCopyFiles(false);

        dbContext.getMoviesTable().deleteBy(moviePrimaryKey);

        updateMoviesOutputFilesWithNewChanges();
    }
    
    public void deleteMovies(List<Movie> chosenMovies) throws IOException
    {       
        updateMoviesOutputFilesWithExistingData();
        
        fileManagerAccessor.getMoviesFileManager().transferBetweenOutputDataAndCopyFiles(false);
        
        for (Movie m : chosenMovies) 
        {
            dbContext.getMoviesTable().deleteBy(m.getPrimaryKey());
        }

        updateMoviesOutputFilesWithNewChanges();
    }
    
    public boolean editMovieBy(PrimaryKey existingMoviePrimaryKey, boolean fromBinary) throws IOException 
    {
        updateMoviesOutputFilesWithExistingData();
        
        List<MovieInput> editedMovie = fileManagerAccessor.getMoviesFileManager().loadInputDataFrom(fromBinary);
                
        if (editedMovie.isEmpty()) 
        {
            //exception
        }
        
        Movie convertedInputMovie = MovieDataConverter.convertToDataFrom(editedMovie.get(0));

        fileManagerAccessor.getMoviesFileManager().transferBetweenOutputDataAndCopyFiles(false);

        boolean wasDataChanged = dbContext.getMoviesTable().editBy(existingMoviePrimaryKey, convertedInputMovie);

        updateMoviesOutputFilesWithNewChanges();

        return wasDataChanged;
    }
        
    private void updateMoviesOutputFilesWithExistingData() throws IOException 
    {
        List<Movie> currentMovies = dbContext.getMoviesTable().getAll();
        dbContext.getMoviesTable().sortByPrimaryKey(currentMovies);
        
        List<MovieOutput> outputMovies = new ArrayList<>();
        MovieOutput outputMovie;
        
        for (Movie m : currentMovies) 
        {
            outputMovie = MovieDataConverter.convertToOutputDataFrom(m);
            outputMovies.add(outputMovie);
        }
        
        fileManagerAccessor.getMoviesFileManager().saveOutputDataIntoFiles(outputMovies);
    }
    
    private void updateMoviesOutputFilesWithNewChanges() throws IOException 
    {
        List<Movie> currentMovies = dbContext.getMoviesTable().getAll();
        dbContext.getMoviesTable().sortByPrimaryKey(currentMovies);
        
        List<MovieOutput> outputMovies = new ArrayList<>();
        MovieOutput outputMovie;

        for (Movie m : currentMovies) 
        {
            outputMovie = MovieDataConverter.convertToOutputDataFrom(m);
            outputMovies.add(outputMovie);
        }

        try 
        {
            fileManagerAccessor.getMoviesFileManager().saveOutputDataIntoFiles(outputMovies);
        } 
        catch (IOException e) 
        {
            fileManagerAccessor.getMoviesFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            outputMovies = fileManagerAccessor.getMoviesFileManager().loadOutputDataFrom(true);
                       
            Movie convertedOutputMovie;
            
            dbContext.getMoviesTable().clearData();
            
            for (MovieOutput m : outputMovies) 
            {
                convertedOutputMovie = MovieDataConverter.convertToDataFrom(m);
                dbContext.getMoviesTable().loadFrom(convertedOutputMovie);
            }            
        } 
        finally 
        {
            fileManagerAccessor.getMoviesFileManager().tryDeleteDataOutputFilesCopies();
        }
    }
        
    private static LocalDate getCurrentDate() 
    {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
