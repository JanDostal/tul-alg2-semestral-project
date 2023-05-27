
package app.logic.controllers;

import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.Movie;
import java.text.Collator;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

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
    
    public List<Movie> getLongestMoviesByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getMoviesTable().sortBy(BY_LONGEST_DURATION_MOVIE, filteredMovies);
        return filteredMovies;
    }
    
    public List<Movie> getMoviesByEraInAlphabeticalOrder(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getMoviesTable().sortBy(BY_NAME_ALPHABETICALLY_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    public List<Movie> getNewestMoviesByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<Movie> filteredMovies = dbContext.getMoviesTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getMoviesTable().sortBy(BY_DATE_NEWEST_MOVIE, filteredMovies);
        
        return filteredMovies;
    }
    
    private static LocalDate getCurrentDate() 
    {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
