package app.logic.controllers;

import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.input.MovieInput;
import app.models.inputoutput.MovieInputOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Collator;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.mail.EmailException;
import utils.emailsender.EmailSender;
import utils.exceptions.DataConversionException;
import utils.exceptions.DatabaseException;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;
import utils.helpers.MovieDataConverter;
import utils.interfaces.IDataFileManager;

/**
 * Represents a movies controller for acting as business logic for application.
 * Movies controller works with movie data type.
 * Movies controller uses services like file manager, email service and database access layer.
 * @author jan.dostal
 */
public class MoviesController 
{
    private static MoviesController movieController;
    
    private final DataContextAccessor dbContext;
    
    private final EmailSender emailSender;
    
    private final FileManagerAccessor fileManagerAccessor;
    
    private final Collator czechCollator = DataStore.loadCzechCollator();
    
    /**
     * Compares two movies by their runtime attribute and sorts them from longest.
     * Also if runtime attribute is null, still continues comparison.
     * @return int value indicating if first movie runtime is greater or equal than second movie runtime
     */
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
    
    /**
     * Compares two movies by their name attribute and sorts them alphabetically.
     * @return int value indicating if first movie name is alphabetically greater or equal than second movie name
     */
    private final Comparator<Movie> BY_NAME_ALPHABETICALLY_MOVIE = (Movie m1, Movie m2) -> 
            czechCollator.compare(m1.getName(), m2.getName());
    
    
    /**
     * Compares two movies by their date attribute and sorts them from newest.
     * Also if date attribute is null, still continues comparison.
     * @return int value indicating if first movie date is newer or equal than second movie date
     */
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
    
    /**
     * Compares two movies by their date attribute and sorts them from oldest.
     * Also if date attribute is null, still continues comparison.
     * @return int value indicating if first movie date is newer or equal than second movie date
     */
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
    
    /**
     * Compares two movies by their percentage rating attribute and sorts them from highest rating.
     * @return int value indicating if first movie rating is greater or equal than second movie rating
     */
    private final Comparator<Movie> BY_PERCENTAGE_RATING_HIGHEST_MOVIE = (Movie m1, Movie m2) -> 
    {        
        return m2.getPercentageRating() - m1.getPercentageRating();
    };
    
    /**
     * Creates singleton instance of MoviesController.
     * Uses dependency injection to inject data context, email sender and file manager services.
     * @param dbContext singleton instance of data context accessor 
     * @param emailSender singleton instance of email sender 
     * @param fileManagerAccessor singleton instance of file manager accessor 
     */
    private MoviesController(DataContextAccessor dbContext, EmailSender emailSender, 
            FileManagerAccessor fileManagerAccessor) 
    {
        this.dbContext = dbContext;
        this.emailSender = emailSender;
        this.fileManagerAccessor = fileManagerAccessor;
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @param dbContext singleton instance of data context accessor 
     * @param emailSender singleton instance of email sender 
     * @param fileManagerAccessor singleton instance of file manager accessor 
     * @return singleton instance of MoviesController class
     */
    public static MoviesController getInstance(DataContextAccessor dbContext, EmailSender emailSender, 
            FileManagerAccessor fileManagerAccessor) 
    {
        if (movieController == null) 
        {
            movieController = new MoviesController(dbContext, emailSender, fileManagerAccessor);
        }
        
        return movieController;
    }
    
    /**
     * Represents an email method for sending e-mail with HTML encoded unwatched movies with hyperlinks, sorted from oldest.
     * @param recipientEmailAddress entered recipient e-mail address from user
     * @throws org.apache.commons.mail.EmailException if recipientEmailAddress is invalid or network error occures
     */
    public void sendUnwatchedOldestMoviesWithHyperlinksByEmail(String recipientEmailAddress) throws EmailException 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == false);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_OLDEST_MOVIE, filteredMovies);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        String subject = String.format("%s –⁠ Nezhlédnuté filmy –⁠ Seřazené podle data uvedení", DataStore.getAppName());
        
        StringBuilder message = new StringBuilder();
        String durationText;
        String hyperlinkText;
        
        message.append("<html>");
        message.append("<h1>Nezhlédnuté filmy seřazené od nejstaršího datumu uvedení</h1>");
        
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
                durationText = m.getRuntime() == null ? "<span style=\"color:red\">Neznámá</span>" : 
                        String.format("%02d:%02d:%02d", m.getRuntime().toHours(), m.getRuntime().toMinutesPart(),
                        m.getRuntime().toSecondsPart());
                              
                hyperlinkText = m.getHyperlinkForContentWatch() == null ? "<span style=\"color:red\">Neuveden</span>" : 
                                String.format("<a href=\"%s\">Zhlédnout</a>", m.getHyperlinkForContentWatch());

                message.append("<li>");
                message.append("<h2>");
                message.append(String.format("Film %s", m.getName()));
                message.append("</h2>");
                message.append("<p>");
                message.append(String.format("Datum vydání: %s", m.getReleaseDate().format(dateFormatter)));
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
    
    /**
     * Represents an email method for sending e-mail with HTML encoded unwatched movies with hyperlinks, categorized
     * into chronological eras.
     * @param recipientEmailAddress entered recipient e-mail address from user
     * @throws org.apache.commons.mail.EmailException if recipientEmailAddress is invalid or network error occures
     */
    public void sendUnwatchedMoviesWithHyperlinksInChronologicalErasByEmail(String recipientEmailAddress) throws EmailException 
    {
        LocalDate currentDate = getCurrentDate();
        List<Movie> filteredMovies;
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        String subject = String.format("%s –⁠ Nezhlédnuté filmy –⁠ Seřazené podle chronologických období", 
                DataStore.getAppName());
        
        StringBuilder message = new StringBuilder();
        String durationText;
        String hyperlinkText;
        
        message.append("<html>");
        message.append("<h1>Nezhlédnuté filmy seřazené od nejstaršího chronologického období</h1>");
        
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
                    durationText = m.getRuntime() == null ? "<span style=\"color:red\">Neznámá</span>" : 
                            String.format("%02d:%02d:%02d", m.getRuntime().toHoursPart(), m.getRuntime().toMinutesPart(), 
                            m.getRuntime().toSecondsPart());
                    
                    hyperlinkText = m.getHyperlinkForContentWatch() == null ? "<span style=\"color:red\">Neuveden</span>" : 
                                String.format("<a href=\"%s\">Zhlédnout</a>", m.getHyperlinkForContentWatch());
            
                    message.append("<li>");
                    message.append("<h3>");
                    message.append(String.format("Film %s", m.getName()));
                    message.append("</h3>");
                    message.append("<p>");
                    message.append(String.format("Datum vydání: %s", m.getReleaseDate().format(dateFormatter)));
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
    
    /**
     * Represents a statistic method for calculating total runtime of unwatched/watched
     * movies in selected era.
     * @param era chosen era in which to operate
     * @param onlyWatched selects if movies filtered will be unwatched or watched
     * @return total runtime of all watched/unwatched movies by era
     */
    public Duration getTotalRuntimeOfAllReleasedMoviesByEra(Era era, boolean onlyWatched)
    {
        Duration totalDuration = Duration.ZERO;
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 && 
                        m.getWasWatched() == onlyWatched && m.getRuntime() != null);
        
        for (Movie m : filteredMovies) 
        {
            totalDuration = totalDuration.plus(m.getRuntime());
        }
                        
        return totalDuration;
    }
    
    /**
     * Represents a statistic method for calculating average runtime of unwatched/watched
     * movies in selected era.
     * @param era chosen era in which to operate
     * @param onlyWatched selects if movies filtered will be unwatched or watched
     * @return average runtime of all unwatched/watched movies by era
     */
    public Duration getAverageRuntimeOfAllReleasedMoviesByEra(Era era, boolean onlyWatched)
    {
        long averageSeconds;
        
        Duration totalRuntimeOfAllReleasedMoviesByEra = getTotalRuntimeOfAllReleasedMoviesByEra(era, onlyWatched);
        
        int durationsCount = getReleasedMoviesWithRuntimeSetCountByEra(era, onlyWatched);
        
        if (durationsCount == 0) 
        {
            averageSeconds = 0;
        }
        else 
        {
            averageSeconds = totalRuntimeOfAllReleasedMoviesByEra.toSeconds() / durationsCount;
        }
        
        Duration averageDuration = Duration.ofSeconds(averageSeconds);
                      
        return averageDuration;
    }
    
    /**
     * Represents a statistic method for calculating average percentage rating (0 - 100) of watched
     * movies in selected era.
     * @param era chosen era in which to operate
     * @return float value indicating calculated average rating in percents 
     */
    public float getAverageRatingOfAllReleasedMoviesByEra(Era era)
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
        
        if (filteredMovies.isEmpty() == true) 
        {
            averageRating = 0;
        }
        else 
        {
            averageRating = totalRating / (float) filteredMovies.size();
        }
                      
        return averageRating;
    }
    
    /**
     * Represents a statistic method for calculating announced movies count
     * in selected era.
     * @param era chosen era in which to operate
     * @return int value indicating total count of announced movies
     */
    public int getAnnouncedMoviesCountByEra(Era era) 
    {
        List<Movie> filteredMovies = getAnnouncedMoviesInAlphabeticalOrderByEra(era);
                
        return filteredMovies.size();
    }
    
    /**
     * Represents a statistic method for calculating a total count of unwatched/watched 
     * movies, which have runtime attribute set (not null) in selected era.
     * @param era chosen era in which to operate
     * @param onlyWatched selects if movies filtered will be unwatched or watched
     * @return int value indicating total count of watched/unwatched movies with runtime
     * attribute set
     */
    public int getReleasedMoviesWithRuntimeSetCountByEra(Era era, boolean onlyWatched) 
    {
        LocalDate currentDate = getCurrentDate();
                
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 && 
                        m.getWasWatched() == onlyWatched && m.getRuntime() != null);
                
        return filteredMovies.size();
    }
    
    /**
     * Represents a statistic method for calculating a total count of unwatched/watched 
     * movies in selected era.
     * @param era chosen era in which to operate
     * @param onlyWatched selects if movies filtered will be unwatched or watched
     * @return int value indicating total count of watched/unwatched movies
     */
    public int getReleasedMoviesCountByEra(Era era, boolean onlyWatched) 
    {        
        List<Movie> filteredMovies = getReleasedNewestMoviesByEra(era, onlyWatched);
                
        return filteredMovies.size();
    }
    
    /**
     * Represents a method for getting watched/unwatched longest movies in selected era
     * @param era chosen era in which to operate.
     * @param onlyWatched selects if movies filtered will be unwatched or watched
     * @return list of filtered and sorted unwatched/watched movies
     */
    public List<Movie> getReleasedLongestMoviesByEra(Era era, boolean onlyWatched) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        dbContext.getMoviesTable().sortBy(BY_LONGEST_DURATION_MOVIE, filteredMovies);
        return filteredMovies;
    }
    
    /**
     * Represents a method for getting watched/unwatched movies, sorted in alphabetical order,
     * in selected era.
     * @param era chosen era in which to operate
     * @param onlyWatched selects if movies filtered will be unwatched or watched
     * @return list of filtered and sorted unwatched/watched movies
     */
    public List<Movie> getReleasedMoviesInAlphabeticalOrderByEra(Era era, boolean onlyWatched) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        dbContext.getMoviesTable().sortBy(BY_NAME_ALPHABETICALLY_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    /**
     * Represents a method for getting watched/unwatched newest movies
     * in selected era.
     * @param era chosen era in which to operate
     * @param onlyWatched selects if movies filtered will be unwatched or watched
     * @return list of filtered and sorted unwatched/watched movies
     */
    public List<Movie> getReleasedNewestMoviesByEra(Era era, boolean onlyWatched) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == onlyWatched);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_NEWEST_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    /**
     * Represents a method for getting watched favorite movies
     * in selected era.
     * @param era chosen era in which to operate
     * @return list of filtered and sorted released movies
     */
    public List<Movie> getReleasedFavoriteMoviesByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0 
                        && m.getWasWatched() == true);
        
        dbContext.getMoviesTable().sortBy(BY_PERCENTAGE_RATING_HIGHEST_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    /**
     * Represents a method for getting announced movies, sorted in alphabetical order,
     * in selected era.
     * @param era chosen era in which to operate
     * @return list of filtered and sorted announced movies
     */
    public List<Movie> getAnnouncedMoviesInAlphabeticalOrderByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && (m.getReleaseDate() == null || 
                        m.getReleaseDate().compareTo(currentDate) > 0));
        
        dbContext.getMoviesTable().sortBy(BY_NAME_ALPHABETICALLY_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    /**
     * Represents a method for getting watched favorite movies from all eras.
     * @return list of filtered and sorted watched movies
     */
    public List<Movie> getReleasedFavoriteMoviesOfAllTime() 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && 
                m.getReleaseDate().compareTo(currentDate) <= 0 && 
                        m.getWasWatched() == true);
        
        dbContext.getMoviesTable().sortBy(BY_PERCENTAGE_RATING_HIGHEST_MOVIE, filteredMovies);
                
        return filteredMovies;
    }
    
    /**
     * Represents a method for getting released newest movies from all eras.
     * @return list of filtered and sorted released movies
     */
    public List<Movie> getReleasedNewestMovies()
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getReleaseDate() != null && 
                m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_NEWEST_MOVIE, filteredMovies);
                
        return filteredMovies;
    }
    
    /**
     * Represents a method for rating a selected movie with new percentage rating.
     * @param existingMovie movie data model instance which rating wants to be changed
     * @param percentageRating percentage rating in range 0 - 100 indicating likability of movie
     * @return logical value indicating if percentage rating changed or remained unchanged
     * @throws utils.exceptions.DatabaseException if percentage rating value is invalid
     * @throws java.io.IOException if updating movies input/output data files with new data failes
     * @throws IllegalArgumentException if percentageRating is negative number
     */
    public boolean rateMovie(Movie existingMovie, int percentageRating) throws DatabaseException, IOException
    {
        updateMoviesInputOutputFilesWithExistingData();
        
        if (percentageRating < 0) 
        {
            throw new IllegalArgumentException("Procentuální ohodnocení filmu nesmí být záporné");
        }
        
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
        
        if (wasDataChanged == true) 
        {
            updateMoviesInputOutputFilesWithNewChanges();
        }
                
        return wasDataChanged;
    }
    
    /**
     * Represents a method for searching in movies data table by movie name.
     * Searching uses regular expression to achieve it.
     * @param name queried movie name entered from user
     * @return list of zero to N movies which meet best with queried movie name
     * @throws IllegalArgumentException if entered movie name is empty
     */
    public List<Movie> searchForMovie(String name) 
    {
        if (name.isEmpty() || name.isBlank()) 
        {
            throw new IllegalArgumentException("Hledaný název filmu nemůže být prázdný");
        }
        
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
    
    /**
     * Represents a method for getting chosen movies file (binary or text, input or input/output) content.
     * @param fileName name of the chosen file (not file path)
     * @return stringbuilder which contains file content as string
     * @throws java.io.IOException when reading from chosen file fails
     * @throws java.io.FileNotFoundException when chosen file does not exist
     * @throws utils.exceptions.FileEmptyException when chosen file content is empty
     */
    public StringBuilder getChosenMoviesFileContent(String fileName) throws IOException, FileNotFoundException, FileEmptyException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryInputOutputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getBinaryInputOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputOutputMoviesFilename())) 
        {
            content = fileManagerAccessor.getMoviesFileManager().getTextInputOutputFileContent();
        }
        
        return content;
    }
    
    /**
     * Represents a method for parsing movies input/output data from binary or text file
     * @param fromBinary selects if input/output file will be binary or text
     * @throws java.io.IOException when reading from movies input/output file fails
     * @throws utils.exceptions.FileParsingException when parsing from input/output file fails because of corrupted data
     * @throws utils.exceptions.DataConversionException when parsed input/output data cannot be converted to database model data
     * @throws utils.exceptions.DatabaseException when database model data have invalid data, duplicity etc.
     */
    public void loadAllInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException, 
            DataConversionException, DatabaseException, Exception 
    {
        try 
        {
            List<MovieInputOutput> inputOutputMovies = fileManagerAccessor.getMoviesFileManager().
                    loadInputOutputDataFrom(fromBinary);
        
            Movie convertedInputOutputMovie;
        
            for (MovieInputOutput m : inputOutputMovies) 
            {
                convertedInputOutputMovie = MovieDataConverter.convertToDataFrom(m);
                dbContext.getMoviesTable().loadFrom(convertedInputOutputMovie);
            }
        }
        catch (Exception ex) 
        {
            dbContext.getMoviesTable().clearData();
            dbContext.getTVShowsTable().clearData();
            dbContext.getTVSeasonsTable().clearData();
            dbContext.getTVEpisodesTable().clearData();
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * Represents a method for parsing movies input data from binary or text file
     * @param fromBinary selects if input file will be binary or text
     * @return stringbuilder which contains message log informing about occured errors and parsed movies
     * @throws java.io.IOException when reading from movies input file fails or when updating movies input/output files with new data fails
     * @throws java.io.FileNotFoundException when input file does not exist
     * @throws utils.exceptions.FileEmptyException when input file is empty
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public StringBuilder addMoviesFrom(boolean fromBinary) throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
    {
        updateMoviesInputOutputFilesWithExistingData();
        
        Map<Integer, MovieInput> inputMovies = fileManagerAccessor.getMoviesFileManager().loadInputDataFrom(fromBinary);
        
        StringBuilder message = new StringBuilder();
        StringBuilder moviesErrorMessages = new StringBuilder();
   
        Movie convertedInputMovie;
        int errorCounter = 0;

        for (Map.Entry<Integer, MovieInput> inputMovie : inputMovies.entrySet()) 
        {
            try 
            {
                convertedInputMovie = MovieDataConverter.convertToDataFrom(inputMovie.getValue());
                dbContext.getMoviesTable().addFrom(convertedInputMovie);

            } 
            catch (DatabaseException | DataConversionException e) 
            {
                errorCounter++;
                moviesErrorMessages.append(String.format("Chybový stav filmu s pořadím %d v souboru %s: %s", 
                        inputMovie.getKey(), fromBinary == true ? DataStore.getBinaryInputMoviesFilename() : 
                                DataStore.getTextInputMoviesFilename(), e.getMessage())).append("\n");
            }
        }

        int successfullyUploadedMoviesCount = inputMovies.size() - errorCounter;
        message.append(String.format("Celkově se podařilo nahrát %d filmů do databáze a naopak se nepodařilo nahrát %d filmů",
                successfullyUploadedMoviesCount, errorCounter)).append("\n");
        message.append(moviesErrorMessages);

        updateMoviesInputOutputFilesWithNewChanges();

        return message;
    }
    
    /**
     * Represents a method for deleting chosen data model movie by its primary key
     * @param moviePrimaryKey represents a movie identificator in database
     * @throws java.io.IOException when updating movies input/output files with new data fails
     * @throws utils.exceptions.DatabaseException when chosen movie does not exist
     */
    public void deleteMovieBy(PrimaryKey moviePrimaryKey) throws IOException, DatabaseException
    {
        updateMoviesInputOutputFilesWithExistingData();

        dbContext.getMoviesTable().deleteBy(moviePrimaryKey);

        updateMoviesInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for deleting chosen list of movies
     * @param chosenMovies represents a list of movies originating from database
     * @throws java.io.IOException when updating movies input/output files with new data fails
     */
    public void deleteMovies(List<Movie> chosenMovies) throws IOException
    {       
        updateMoviesInputOutputFilesWithExistingData();
                
        for (Movie m : chosenMovies) 
        {
            try 
            {
                dbContext.getMoviesTable().deleteBy(m.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }

        updateMoviesInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for editing chosen movie by its primary key and using movies input file
     * @param existingMoviePrimaryKey represents an existing movie identificator in database
     * @param fromBinary selects if parsing of new data for existing movie will be from text or binary input file
     * @return logical value indicating if existing movie data was changed or remained same
     * @throws java.io.IOException if reading from movies input file fails or when updating movies input/output files with new data fails
     * @throws java.io.FileNotFoundException if input file is not found
     * @throws utils.exceptions.FileEmptyException if input file is empty
     * @throws utils.exceptions.DataConversionException if movie input data cannot be converted to movie database data model
     * @throws utils.exceptions.DatabaseException if movie database data are invalid, duplicity etc.
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public boolean editMovieBy(PrimaryKey existingMoviePrimaryKey, boolean fromBinary) throws IOException, FileNotFoundException, 
            FileEmptyException, DataConversionException, DatabaseException, FileParsingException 
    {
        updateMoviesInputOutputFilesWithExistingData();
        
        Map<Integer, MovieInput> editedMovie = fileManagerAccessor.getMoviesFileManager().loadInputDataFrom(fromBinary);
        
        String filename = fromBinary == true ? DataStore.getBinaryInputMoviesFilename() : DataStore.getTextInputMoviesFilename();
        
        if (editedMovie.size() > 1 || editedMovie.get(1) == null) 
        {
            throw new FileParsingException("Soubor " + 
                    filename + " musí obsahovat právě jeden film vybraný pro editaci");
        }
        
        Movie convertedInputMovie = MovieDataConverter.convertToDataFrom(editedMovie.get(1));

        boolean wasDataChanged = dbContext.getMoviesTable().editBy(existingMoviePrimaryKey, convertedInputMovie);
        
        if (wasDataChanged == true) 
        {
            updateMoviesInputOutputFilesWithNewChanges();
        }
        
        return wasDataChanged;
    }
    
    /**
     * Represents a method for saving current movies table state into input/output files
     * @throws java.io.IOException if saving movies table state into input/output files fails
     */
    private void updateMoviesInputOutputFilesWithExistingData() throws IOException 
    {
        List<Movie> currentMovies = dbContext.getMoviesTable().getAll();
        dbContext.getMoviesTable().sortByPrimaryKey(currentMovies);
        
        List<MovieInputOutput> inputOutputMovies = new ArrayList<>();
        MovieInputOutput inputOutputMovie;
        
        for (Movie m : currentMovies) 
        {
            inputOutputMovie = MovieDataConverter.convertToInputOutputDataFrom(m);
            inputOutputMovies.add(inputOutputMovie);
        }
        
        fileManagerAccessor.getMoviesFileManager().saveInputOutputDataIntoFiles(inputOutputMovies);
    }
    
    /**
     * Represents a method for saving updated movies table state into input/output files.
     * <p>
     * The correct usage of this method is to 
     * call {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean) 
     * transferBetweenInputOutputDataAndCopyFiles} method to
     * backup input/output files. Then call {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method to try to save input/output data.
     * <p>
     * If calling {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails, then transfer input/output data from copies back into
     * input/output files by {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean)
     * transferBetweenInputOutputDataAndCopyFiles} and load them back into database.
     * <p>
     * After all of it, call {@link IDataFileManager#tryDeleteInputOutputDataFilesCopies() 
     * tryDeleteInputOutputDataFilesCopies} method regardless if calling 
     * {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails or not
     * @throws java.io.IOException if saving movies table updated state into input/output files fails
     */
    private void updateMoviesInputOutputFilesWithNewChanges() throws IOException
    {
        List<Movie> currentMovies = dbContext.getMoviesTable().getAll();
        dbContext.getMoviesTable().sortByPrimaryKey(currentMovies);
        
        List<MovieInputOutput> inputOutputMovies = new ArrayList<>();
        MovieInputOutput inputOutputMovie;

        for (Movie m : currentMovies) 
        {
            inputOutputMovie = MovieDataConverter.convertToInputOutputDataFrom(m);
            inputOutputMovies.add(inputOutputMovie);
        }
        
        fileManagerAccessor.getMoviesFileManager().transferBetweenInputOutputDataAndCopyFiles(false);

        try 
        {
            fileManagerAccessor.getMoviesFileManager().saveInputOutputDataIntoFiles(inputOutputMovies);
        } 
        catch (IOException e) 
        {
            fileManagerAccessor.getMoviesFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            try 
            {
                inputOutputMovies = fileManagerAccessor.getMoviesFileManager().loadInputOutputDataFrom(true);
            }
            catch (IOException | FileParsingException f) 
            {
                fileManagerAccessor.getMoviesFileManager().tryDeleteInputOutputDataFilesCopies();
                throw new IOException(f.getMessage());
            }
                       
            Movie convertedInputOutputMovie;
            
            dbContext.getMoviesTable().clearData();
            
            try 
            {
                for (MovieInputOutput m : inputOutputMovies) 
                {
                    convertedInputOutputMovie = MovieDataConverter.convertToDataFrom(m);
                    dbContext.getMoviesTable().loadFrom(convertedInputOutputMovie);
                } 
            }
            catch (DataConversionException | DatabaseException g) 
            {
                for (Movie m : currentMovies) 
                {
                    try 
                    {
                        dbContext.getMoviesTable().loadFrom(m);
                    }
                    catch (DatabaseException h) 
                    {
                    }
                }
            }
        } 
        finally 
        {
            fileManagerAccessor.getMoviesFileManager().tryDeleteInputOutputDataFilesCopies();
        }
    }
    
    /**
     * Represents a method for getting current date (present date as LocalDate).
     * Timezone is determined from operating system running application
     * @return instance of LocalDate, representing present date
     */
    public static LocalDate getCurrentDate() 
    {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
