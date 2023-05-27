
package app.logic.controllers;

import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.Movie;
import java.text.Collator;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class MoviesController 
{
    private static MoviesController movieController;
    
    private final DataContextAccessor dbContext;
    
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
    
    private final Comparator<Movie> BY_PERCENTAGE_RATING_HIGHEST_MOVIE = (Movie m1, Movie m2) -> 
    {        
        return m2.getPercentageRating() - m1.getPercentageRating();
    };
    
    private MoviesController(DataContextAccessor dbContext) 
    {
        this.dbContext = dbContext;
    }
    
    public static MoviesController getInstance(DataContextAccessor dbContext) 
    {
        if (movieController == null) 
        {
            movieController = new MoviesController(dbContext);
        }
        
        return movieController;
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
