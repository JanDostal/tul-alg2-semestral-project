
package tests.mainmethods;

import app.logic.controllers.MoviesController;
import app.logic.datacontext.DataContextAccessor;
import app.logic.filemanager.FileManager;
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
import utils.emailsender.EmailSender;
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
        EmailSender emailSender = EmailSender.getInstance();
        IDataTable<Movie> moviesTable = dbContext.getMoviesTable();
        FileManager fileManager = FileManager.getInstance();
        
        MoviesController controller = MoviesController.getInstance(dbContext, emailSender, fileManager);
        
        Movie movieA = new Movie(new PrimaryKey(3), null, "movieA", 
                60, true, "https://www.example01.com", "A", 
                LocalDate.parse("2023-05-16", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        
        Movie movieB = new Movie(new PrimaryKey(4), Duration.ofMinutes(200), "Hvězdička", 
                40, true, "https://www.example02.com", "B", 
                LocalDate.parse("2023-05-15", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        Movie movieC = new Movie(new PrimaryKey(5), null, "movieC", 
                60, false, null, null, null, Era.FALL_OF_THE_JEDI);
        
        Movie movieD = new Movie(new PrimaryKey(8), Duration.ofMinutes(300), "movieD", 
                60, false, null, null, 
                LocalDate.parse("2025-05-14", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        
        Movie movieE = new Movie(new PrimaryKey(10), Duration.ofMinutes(500), "Bitva", 
                80, true, null, null, 
                LocalDate.parse("2023-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        Movie movieF = new Movie(new PrimaryKey(11), null, "movieF", 
                2, false, null, null, 
                LocalDate.parse("2025-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        Movie movieG = new Movie(new PrimaryKey(12), null, "movieG", 
                2, false, null, null, 
                null, Era.FALL_OF_THE_JEDI);
        
        moviesTable.loadFrom(movieA);
        moviesTable.loadFrom(movieB);
        moviesTable.loadFrom(movieC);
        moviesTable.loadFrom(movieD);
        moviesTable.loadFrom(movieE);
        moviesTable.loadFrom(movieF);
        moviesTable.loadFrom(movieG);
        
        System.out.println();
        System.out.println("Kontrolni vypis:");
        System.out.println();
        
        List<Movie> movies = moviesTable.getAll();
        
        for (Movie s : movies) 
        {
            System.out.println(s);
        }

        //getLongestMoviesByEra method
        List<Movie> getLongestMoviesByEra_result = controller.getLongestMoviesByEra(Era.FALL_OF_THE_JEDI, true);
        
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
                controller.getMoviesByEraInAlphabeticalOrder(Era.FALL_OF_THE_JEDI, true);
                
        for (Movie m : getMoviesByEraInAlphabeticalOrder_result) 
        {
            System.out.println(m);
        }
        
        //getNewestMoviesByEra method
        System.out.println();
        System.out.println("getNewestMoviesByEra method:");
        System.out.println();
        List<Movie> getNewestMoviesByEra_result = 
                controller.getNewestMoviesByEra(Era.FALL_OF_THE_JEDI, true);
        
        
        for (Movie m : getNewestMoviesByEra_result) 
        {
            System.out.println(m);
        }
        
        //getFavoriteMoviesByEra method
        System.out.println();
        System.out.println("getFavoriteMoviesByEra method:");
        System.out.println();
        List<Movie> getFavoriteMoviesByEra_result = 
                controller.getFavoriteMoviesByEra(Era.FALL_OF_THE_JEDI);
        
        
        for (Movie m : getFavoriteMoviesByEra_result) 
        {
            System.out.println(m);
        }
        
        //getAnnouncedMovies method
        System.out.println();
        System.out.println("getAnnouncedMovies method:");
        System.out.println();
        List<Movie> getAnnouncedMovies_result = 
                controller.getAnnouncedMovies(Era.FALL_OF_THE_JEDI);
        
        
        for (Movie m : getAnnouncedMovies_result) 
        {
            System.out.println(m);
        }
        
        //getAnnouncedMoviesCountByEra method
        System.out.println();
        System.out.println("getAnnouncedMoviesCountByEra method:");
        System.out.println();
        int getAnnouncedMoviesCountByEra_result = 
                controller.getAnnouncedMoviesCountByEra(Era.FALL_OF_THE_JEDI);
        
        System.out.println("počet: " + getAnnouncedMoviesCountByEra_result);
        
        //getMoviesCountByEra method
        System.out.println();
        System.out.println("getMoviesCountByEra method:");
        System.out.println();
        int getMoviesCountByEra_result = 
                controller.getMoviesCountByEra(Era.FALL_OF_THE_JEDI, true);
        
        System.out.println("počet: " + getMoviesCountByEra_result);
        
        //getFavoriteMoviesOfAllTime method
        System.out.println();
        System.out.println("getFavoriteMoviesOfAllTime method:");
        System.out.println();
        List<Movie> getFavoriteMoviesOfAllTime_result = 
                controller.getFavoriteMoviesOfAllTime();
        
        for (Movie m : getFavoriteMoviesOfAllTime_result) 
        {
            System.out.println(m);
        }
        
        //getNewestMovies method
        System.out.println();
        System.out.println("getNewestMovies method:");
        System.out.println();
        List<Movie> getNewestMovies_result = 
                controller.getNewestMovies();
        
        for (Movie m : getNewestMovies_result) 
        {
            System.out.println(m);
        }
        
        //rateMovie method
        System.out.println();
        System.out.println("rateMovie method:");
        System.out.println();
        
        Movie movieEdit = new Movie(new PrimaryKey(5), null, "movieEdit", 
                60, false, null, null, 
                LocalDate.parse("2021-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        boolean wasDataChanged = 
                controller.rateMovie(movieEdit, 60);
        
        System.out.println("Po rateMovie pouziti:");
        System.out.println("zmena dat:" + wasDataChanged);
        movieEdit = dbContext.getMoviesTable().getBy(movieEdit.getPrimaryKey());
        System.out.println(movieEdit);
        
        wasDataChanged = 
                controller.rateMovie(movieEdit, 60);
        System.out.println("zmena dat po druhe se stejnym ohodnocenim: " + wasDataChanged);
        
        
        //searchForMovie method
        System.out.println();
        System.out.println("searchForMovie method:");
        System.out.println();
        
        Movie movieSearch_01 = new Movie(new PrimaryKey(5), Duration.ofMinutes(50), "Ola ÁHójé Jé", 
                60, false, null, null, 
                LocalDate.parse("2021-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        movieSearch_01 = dbContext.getMoviesTable().addFrom(movieSearch_01);
        
        List<Movie> searchForMovie_result = 
                controller.searchForMovie("ahóje jě");
        
        for (Movie m : searchForMovie_result)
        {
            System.out.println(m);
        }
        
        //getTotalRuntimeOfAllMoviesByEra method
        System.out.println();
        System.out.println("getTotalRuntimeOfAllMoviesByEra method:");
        System.out.println();
        Duration getTotalRuntimeOfAllMoviesByEra_result = 
                controller.getTotalRuntimeOfAllMoviesByEra(Era.FALL_OF_THE_JEDI, true);
        
        System.out.println("Doba v minutach: " + getTotalRuntimeOfAllMoviesByEra_result.toMinutes());
        
        //getAverageRuntimeOfAllMoviesByEra method
        System.out.println();
        System.out.println("getAverageRuntimeOfAllMoviesByEra method:");
        System.out.println();
        Duration getAverageRuntimeOfAllMoviesByEra_result = 
                controller.getAverageRuntimeOfAllMoviesByEra(Era.FALL_OF_THE_JEDI, true);
        
        System.out.println("Doba v minutach: " + getAverageRuntimeOfAllMoviesByEra_result.toMinutes());
        
        //getAverageRatingOfAllMoviesByEra method
        System.out.println();
        System.out.println("getAverageRatingOfAllMoviesByEra method:");
        System.out.println();
        float getAverageRatingOfAllMoviesByEra_result = 
                controller.getAverageRatingOfAllMoviesByEra(Era.FALL_OF_THE_JEDI);
        
        System.out.println("Prumerne hodnoceni v procentech: " + getAverageRatingOfAllMoviesByEra_result);
    }
}
