package tests.mainmethods;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utils.emailsender.EmailSender;
import utils.exceptions.DataConversionException;
import utils.exceptions.DatabaseException;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;
import utils.interfaces.IDataTable;

/**
 * Represents a custom unit test class for testing Movies controller of controllers module.
 * @author jan.dostal
 */
public class MoviesControllerTest 
{
    //testing main
    public static void main(String[] args) 
    {
//        DataContextAccessor dbContext = DataContextAccessor.getInstance();
//        EmailSender emailSender = EmailSender.getInstance();
//        IDataTable<Movie> moviesTable = dbContext.getMoviesTable();
//        FileManagerAccessor fileManager = FileManagerAccessor.getInstance();
//        
//        try 
//        {
//            FileManagerAccessor.setDataDirectory(DataStore.getDataDirectoryName());
//        }
//        catch (IllegalArgumentException | IllegalStateException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        MoviesController controller = MoviesController.getInstance(dbContext, emailSender, fileManager);
//        TVEpisodesController controllerEpisodes = TVEpisodesController.getInstance(dbContext, emailSender, fileManager);
//        
//        Movie movieA = new Movie(new PrimaryKey(3), null, "movieA", 
//                60, true, "https://www.example01.com", "A", 
//                LocalDate.parse("2023-05-16", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
//        
//        Movie movieB = new Movie(new PrimaryKey(4), Duration.ofMinutes(200), "Star", 
//                40, true, "https://www.example02.com", "B", 
//                LocalDate.parse("2023-05-15", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//        
//        Movie movieC = new Movie(new PrimaryKey(5), null, "movieC", 
//                60, false, null, null, null, Era.FALL_OF_THE_JEDI);
//        
//        Movie movieD = new Movie(new PrimaryKey(8), Duration.ofMinutes(300), "movieD", 
//                60, false, null, null, 
//                LocalDate.parse("2025-05-14", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//        
//        
//        Movie movieE = new Movie(new PrimaryKey(10), Duration.ofMinutes(500), "Battle", 
//                80, true, null, null, 
//                LocalDate.parse("2023-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//        
//        Movie movieF = new Movie(new PrimaryKey(11), null, "movieF", 
//                2, false, null, null, 
//                LocalDate.parse("2025-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//        
//        Movie movieG = new Movie(new PrimaryKey(12), null, "movieG", 
//                2, false, null, null, 
//                null, Era.FALL_OF_THE_JEDI);
//        
//        try 
//        {
//            moviesTable.loadFrom(movieA);
//            moviesTable.loadFrom(movieB);
//            moviesTable.loadFrom(movieC);
//            moviesTable.loadFrom(movieD);
//            moviesTable.loadFrom(movieE);
//            moviesTable.loadFrom(movieF);
//            moviesTable.loadFrom(movieG);
//        }
//        catch (DatabaseException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//
//        
//        System.out.println();
//        System.out.println("Printed results for checking correctness:");
//        System.out.println();
//        
//        List<Movie> movies = moviesTable.getAll();
//        
//        for (Movie s : movies) 
//        {
//            System.out.println(s);
//        }
//
//        //getReleasedLongestMoviesByEra method
//        List<Movie> getLongestMoviesByEra_result = controller.getReleasedLongestMoviesByEra(Era.FALL_OF_THE_JEDI, true);
//        
//        System.out.println();
//        System.out.println("getReleasedLongestMoviesByEra method:");
//        System.out.println();
//        
//        for (Movie m : getLongestMoviesByEra_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //getReleasedMoviesInAlphabeticalOrderByEra method
//        System.out.println();
//        System.out.println("getReleasedMoviesInAlphabeticalOrderByEra method:");
//        System.out.println();
//        List<Movie> getMoviesByEraInAlphabeticalOrder_result = 
//                controller.getReleasedMoviesInAlphabeticalOrderByEra(Era.FALL_OF_THE_JEDI, true);
//                
//        for (Movie m : getMoviesByEraInAlphabeticalOrder_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //getReleasedNewestMoviesByEra method
//        System.out.println();
//        System.out.println("getReleasedNewestMoviesByEra method:");
//        System.out.println();
//        List<Movie> getNewestMoviesByEra_result = 
//                controller.getReleasedNewestMoviesByEra(Era.FALL_OF_THE_JEDI, true);
//        
//        
//        for (Movie m : getNewestMoviesByEra_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //getReleasedFavoriteMoviesByEra method
//        System.out.println();
//        System.out.println("getReleasedFavoriteMoviesByEra method:");
//        System.out.println();
//        List<Movie> getFavoriteMoviesByEra_result = 
//                controller.getReleasedFavoriteMoviesByEra(Era.FALL_OF_THE_JEDI);
//        
//        
//        for (Movie m : getFavoriteMoviesByEra_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //getAnnouncedMoviesInAlphabeticalOrderByEra method
//        System.out.println();
//        System.out.println("getAnnouncedMoviesInAlphabeticalOrderByEra method:");
//        System.out.println();
//        List<Movie> getAnnouncedMovies_result = 
//                controller.getAnnouncedMoviesInAlphabeticalOrderByEra(Era.FALL_OF_THE_JEDI);
//        
//        for (Movie m : getAnnouncedMovies_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //getAnnouncedMoviesCountByEra method
//        System.out.println();
//        System.out.println("getAnnouncedMoviesCountByEra method:");
//        System.out.println();
//        int getAnnouncedMoviesCountByEra_result = 
//                controller.getAnnouncedMoviesCountByEra(Era.FALL_OF_THE_JEDI);
//        
//        System.out.println("count: " + getAnnouncedMoviesCountByEra_result);
//        
//        //getReleasedMoviesCountByEra method
//        System.out.println();
//        System.out.println("getReleasedMoviesCountByEra method:");
//        System.out.println();
//        int getMoviesCountByEra_result = 
//                controller.getReleasedMoviesCountByEra(Era.FALL_OF_THE_JEDI, true);
//        
//        System.out.println("count: " + getMoviesCountByEra_result);
//        
//        //getReleasedFavoriteMoviesOfAllTime method
//        System.out.println();
//        System.out.println("getReleasedFavoriteMoviesOfAllTime method:");
//        System.out.println();
//        List<Movie> getFavoriteMoviesOfAllTime_result = 
//                controller.getReleasedFavoriteMoviesOfAllTime();
//        
//        for (Movie m : getFavoriteMoviesOfAllTime_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //getReleasedNewestMovies method
//        System.out.println();
//        System.out.println("getReleasedNewestMovies method:");
//        System.out.println();
//        List<Movie> getNewestMovies_result = 
//                controller.getReleasedNewestMovies();
//        
//        for (Movie m : getNewestMovies_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //rateMovie method
//        System.out.println();
//        System.out.println("rateMovie method:");
//        System.out.println();
//        
//        Movie movieEdit = new Movie(new PrimaryKey(5), null, "movieEdit", 
//                -1, false, null, null, 
//                LocalDate.parse("2021-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//        
//        try 
//        {
//            boolean wasDataChanged = controller.rateMovie(movieEdit, 60);
//             
//            System.out.println("State after rateMovie method usage:");
//            System.out.println("data change occured:" + wasDataChanged);
//            movieEdit = dbContext.getMoviesTable().getBy(movieEdit.getPrimaryKey());
//            System.out.println(movieEdit);
//            
//            wasDataChanged = controller.rateMovie(movieEdit, 60);
//            System.out.println("data change occured (same movie rating, second attempt): " + wasDataChanged);
//        }
//        catch (DatabaseException | IOException | IllegalArgumentException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //searchForMovie method
//        System.out.println();
//        System.out.println("searchForMovie method:");
//        System.out.println();
//        
//        Movie movieSearch_01 = new Movie(new PrimaryKey(5), Duration.ofMinutes(50), "Ola ÁHójé Jé", 
//                60, false, null, null, 
//                LocalDate.parse("2021-05-20", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//        
//        try 
//        {
//            dbContext.getMoviesTable().addFrom(movieSearch_01);
//        }
//        catch (DatabaseException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        List<Movie> searchForMovie_result = 
//                controller.searchForMovie("ahóje jě");
//        
//        for (Movie m : searchForMovie_result)
//        {
//            System.out.println(m);
//        }
//        
//        //getTotalRuntimeOfAllReleasedMoviesByEra method
//        System.out.println();
//        System.out.println("getTotalRuntimeOfAllReleasedMoviesByEra method:");
//        System.out.println();
//        Duration getTotalRuntimeOfAllMoviesByEra_result = 
//                controller.getTotalRuntimeOfAllReleasedMoviesByEra(Era.FALL_OF_THE_JEDI, true);
//        
//        int watchedMoviesRuntimesCount_total = controller.getReleasedMoviesWithRuntimeSetCountByEra(Era.FALL_OF_THE_JEDI, true);
//        
//        System.out.println("Total runtime in minutes: " + getTotalRuntimeOfAllMoviesByEra_result.toMinutes());
//        System.out.println("Count of watched movies with entered runtime: " + 
//                watchedMoviesRuntimesCount_total);
//        
//        //getAverageRuntimeOfAllReleasedMoviesByEra method
//        System.out.println();
//        System.out.println("getAverageRuntimeOfAllReleasedMoviesByEra method:");
//        System.out.println();
//        Duration getAverageRuntimeOfAllMoviesByEra_result = 
//                controller.getAverageRuntimeOfAllReleasedMoviesByEra(Era.FALL_OF_THE_JEDI, true);
//        
//        int watchedMoviesRuntimesCount_average = controller.getReleasedMoviesWithRuntimeSetCountByEra(Era.FALL_OF_THE_JEDI, true);
//                       
//        System.out.println("Average runtime in minutes: " + getAverageRuntimeOfAllMoviesByEra_result.toMinutes());
//        System.out.println("Count of watched movies with entered runtime: " + 
//                watchedMoviesRuntimesCount_average);
//        
//        //getAverageRatingOfAllReleasedMoviesByEra method
//        System.out.println();
//        System.out.println("getAverageRatingOfAllReleasedMoviesByEra method:");
//        System.out.println();
//        float getAverageRatingOfAllMoviesByEra_result = 
//                controller.getAverageRatingOfAllReleasedMoviesByEra(Era.FALL_OF_THE_JEDI);
//        
//        int watchedMoviesCount_average = controller.getReleasedMoviesCountByEra(Era.FALL_OF_THE_JEDI, true);
//        
//        System.out.println("Average rating in percents: " + getAverageRatingOfAllMoviesByEra_result);
//        System.out.println("Count of watched movies: " + 
//                watchedMoviesCount_average);
//        
//        //addMoviesFrom
//        System.out.println();
//        System.out.println("addMoviesFrom method (text):");
//        System.out.println();
//        
//        try 
//        {
//            StringBuilder message = controller.addMoviesFrom(false);
//            
//            List<Movie> moviesList = moviesTable.getAll();
//            
//            System.out.println(message.toString());
//            
//            for (Movie m : moviesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException l) 
//        {
//            System.out.println(l.getMessage());
//        }
//        catch (IOException | FileEmptyException | FileParsingException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //deleteMovieBy
//        System.out.println();
//        System.out.println("deleteMovieBy method:");
//        System.out.println();
//        
//        try 
//        {
//            controller.deleteMovieBy(movieSearch_01.getPrimaryKey());
//            
//            List<Movie> moviesList = moviesTable.getAll();
//            
//            for (Movie m : moviesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (IOException | DatabaseException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //deleteMovies
//        System.out.println();
//        System.out.println("deleteMovies method:");
//        System.out.println();
//        
//        try 
//        {
//            List<Movie> moviesList = moviesTable.getAll();
//            
//            controller.deleteMovies(moviesList);
//            
//            moviesList = moviesTable.getAll();
//            
//            for (Movie m : moviesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (IOException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //editMovieBy
//        System.out.println();
//        System.out.println("editMovieBy method:");
//        System.out.println();
//        
//        try 
//        {
//            Movie movieForEdit = new Movie(new PrimaryKey(2312123), null, "movieOla", 
//                2, false, null, null, 
//                null, Era.THE_OLD_REPUBLIC);
//            
//            System.out.println(movieForEdit);
//        
//            moviesTable.loadFrom(movieForEdit);
//            
//            boolean wasDataChangedForEdit = controller.editMovieBy(movieForEdit.getPrimaryKey(), false);
//            
//            List<Movie> moviesList = moviesTable.getAll();
//            
//            System.out.println(wasDataChangedForEdit);
//            
//            for (Movie m : moviesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException o) 
//        {
//            System.out.println(o.getMessage());
//        }
//        catch (IOException | DatabaseException | FileEmptyException | FileParsingException | DataConversionException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //loadAllInputOutputDataFrom
//        System.out.println();
//        System.out.println("loadAllInputOutputDataFrom method (both methods from TVEpisodesController and MoviesController):");
//        System.out.println();
//        
//        try 
//        {
//            dbContext.getMoviesTable().clearData();
//            dbContext.getTVEpisodesTable().clearData();
//            dbContext.getTVSeasonsTable().clearData();
//            dbContext.getTVShowsTable().clearData();
//
//            controller.loadAllInputOutputDataFrom(false);
//            controllerEpisodes.loadAllInputOutputDataFrom(false);
//
//            List<Movie> moviesAfterLoad = dbContext.getMoviesTable().getAll();
//            List<TVEpisode> tvEpisodesAfterLoad = dbContext.getTVEpisodesTable().getAll();
//            List<TVSeason> tvSeasonsAfterLoad = dbContext.getTVSeasonsTable().getAll();
//            List<TVShow> tvShowsAfterLoad = dbContext.getTVShowsTable().getAll();
//
//            for (Movie m : moviesAfterLoad)
//            {
//                System.out.println(m);
//            }
//
//            for (TVEpisode m : tvEpisodesAfterLoad) 
//            {
//                System.out.println(m);
//            }
//
//            for (TVSeason m : tvSeasonsAfterLoad) 
//            {
//                System.out.println(m);
//            }
//
//            for (TVShow m : tvShowsAfterLoad) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (Exception e) 
//        {
//            System.out.println(e.getMessage());
//        }
    }
}
