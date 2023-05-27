
package tests.mainmethods;

import app.logic.controllers.MoviesController;
import app.logic.datacontext.DataContextAccessor;
import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utils.interfaces.IDataTable;

/**
 *
 * @author Admin
 */
public class MoviesControllerTest 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        DataContextAccessor dbContext = DataContextAccessor.getInstance();
        IDataTable<Movie> moviesTable = dbContext.getMoviesTable();
        
        MoviesController controller = MoviesController.getInstance(dbContext);
        
        Movie movieA = new Movie(new PrimaryKey(3), null, "movieA", 
                60, true, "https://www.example01.com", "A", 
                LocalDate.parse("2023-05-16", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        
        Movie movieB = new Movie(new PrimaryKey(4), Duration.ofMinutes(200), "Hvězdička", 
                60, true, "https://www.example02.com", "B", 
                LocalDate.parse("2023-05-15", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        Movie movieC = new Movie(new PrimaryKey(5), null, "movieC", 
                60, true, "https://www.example03.com", "C", null, Era.FALL_OF_THE_JEDI);
        
        Movie movieD = new Movie(new PrimaryKey(8), Duration.ofMinutes(300), "movieD", 
                60, true, "https://www.example04.com", "D", 
                LocalDate.parse("2025-05-14", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        
        Movie movieE = new Movie(new PrimaryKey(10), Duration.ofMinutes(500), "Bitva", 
                60, true, null, null, 
                LocalDate.parse("2023-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        moviesTable.loadFrom(movieA);
        moviesTable.loadFrom(movieB);
        moviesTable.loadFrom(movieC);
        moviesTable.loadFrom(movieD);
        moviesTable.loadFrom(movieE);
        
        System.out.println();
        System.out.println("Kontrolni vypis:");
        System.out.println();
        
        List<Movie> movies = moviesTable.getAll();
        
        for (Movie s : movies) 
        {
            System.out.println(s);
        }

        //getLongestMoviesByEra method
        List<Movie> getLongestMoviesByEra_result = controller.getLongestMoviesByEra(Era.FALL_OF_THE_JEDI);
        
        System.out.println();
        System.out.println("getLongestMoviesByEra method:");
        System.out.println();
        
        for (Movie m : getLongestMoviesByEra_result) 
        {
            System.out.println(m);
        }
        
        //getMoviesByEraInAlphabeticalOrder method
        System.out.println();
        System.out.println("getMoviesByEraInAlphabeticalOrder method:");
        System.out.println();
        List<Movie> getMoviesByEraInAlphabeticalOrder_result = 
                controller.getMoviesByEraInAlphabeticalOrder(Era.FALL_OF_THE_JEDI);
                
        for (Movie m : getMoviesByEraInAlphabeticalOrder_result) 
        {
            System.out.println(m);
        }
        
        //getNewestMoviesByEra method
        System.out.println();
        System.out.println("getNewestMoviesByEra method:");
        System.out.println();
        List<Movie> getNewestMoviesByEra_result = 
                controller.getNewestMoviesByEra(Era.FALL_OF_THE_JEDI);
        
        
        for (Movie m : getNewestMoviesByEra_result) 
        {
            System.out.println(m);
        }
    }
}
