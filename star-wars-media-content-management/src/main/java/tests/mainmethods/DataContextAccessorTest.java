
package tests.mainmethods;

import app.logic.datacontext.DataContextAccessor;
import app.models.data.Era;
import app.models.data.Movie;
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
public class DataContextAccessorTest 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        DataContextAccessor dbContext = DataContextAccessor.getInstance();
        IDataTable<Movie> moviesTable = dbContext.getMoviesTable();
        IDataTable<TVEpisode> episodesTable = dbContext.getTVEpisodesTable();
        IDataTable<TVSeason> seasonsTable = dbContext.getTVSeasonsTable();
        IDataTable<TVShow> showsTable = dbContext.getTVShowsTable();
        
        //addFrom method
        
        Movie movieAdd = new Movie(null, Duration.ofMinutes(45), "Star Wars: The New Dawn", 
                75, true, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
       
        movieAdd = moviesTable.addFrom(movieAdd);
        
        TVShow showAdd = new TVShow(null, "Clone Wars", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE);
        
        showAdd = showsTable.addFrom(showAdd);
        
        TVSeason seasonAdd = new TVSeason(null, 1, showAdd.getPrimaryKey());
        
        seasonAdd = seasonsTable.addFrom(seasonAdd);
        
        TVEpisode episodeAdd = new TVEpisode(null, Duration.ofMinutes(45), "Twin suns", 
                0, false, "https://www.example02.com", "Velmi krásná epizoda", 
                1, seasonAdd.getPrimaryKey());
        
        episodeAdd = episodesTable.addFrom(episodeAdd);
        
        //getBy method
        System.out.println("getBy method");
        System.out.println();
        
        System.out.println(moviesTable.getBy(movieAdd.getPrimaryKey()));
        System.out.println(showsTable.getBy(showAdd.getPrimaryKey()));
        System.out.println(seasonsTable.getBy(seasonAdd.getPrimaryKey()));
        System.out.println(episodesTable.getBy(episodeAdd.getPrimaryKey()));
        
        
        //getAll method
        System.out.println();
        System.out.println("getAll method");
        System.out.println();
        
        List<Movie> movies = moviesTable.getAll();
        List<TVEpisode> episodes = episodesTable.getAll();
        List<TVSeason> seasons = seasonsTable.getAll();
        List<TVShow> shows = showsTable.getAll();
        
        System.out.println("Stav tabulek po použití addFrom:");
        System.out.println();
        System.out.println("Filmy:");
        System.out.println();
        
        for (Movie m : movies) 
        {
            System.out.println(m);
        }
        
        System.out.println();
        System.out.println("Seriály:");
        System.out.println();
        
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        System.out.println();
        System.out.println("Sezóny:");
        System.out.println();
        
        for (TVSeason s : seasons) 
        {
            System.out.println(s);
        }
        
        System.out.println();
        System.out.println("Epizody:");
        System.out.println();
        
        for (TVEpisode e : episodes) 
        {
            System.out.println(e);
        }
        
        //deleteBy method
        System.out.println("deleteBy method");
        System.out.println();
        
        episodesTable.deleteBy(episodeAdd.getPrimaryKey());
        episodes = episodesTable.getAll();
        seasonsTable.deleteBy(seasonAdd.getPrimaryKey());
        seasons = seasonsTable.getAll();
        showsTable.deleteBy(showAdd.getPrimaryKey());
        shows = showsTable.getAll();
        moviesTable.deleteBy(movieAdd.getPrimaryKey());
        movies = moviesTable.getAll();
        
        System.out.println("Stav tabulek po použití deleteBy:");
        System.out.println();
        System.out.println("Filmy:");
        System.out.println();
        
        for (Movie m : movies) 
        {
            System.out.println(m);
        }
        
        System.out.println();
        System.out.println("Seriály:");
        System.out.println();
        
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        System.out.println();
        System.out.println("Sezóny:");
        System.out.println();
        
        for (TVSeason s : seasons) 
        {
            System.out.println(s);
        }
        
        System.out.println();
        System.out.println("Epizody:");
        System.out.println();
        
        for (TVEpisode e : episodes) 
        {
            System.out.println(e);
        }
        
        //loadFrom method
        System.out.println();
        System.out.println("loadFrom method");
        System.out.println();
        
        showsTable.loadFrom(showAdd);
        seasonsTable.loadFrom(seasonAdd);
        episodesTable.loadFrom(episodeAdd);
        moviesTable.loadFrom(movieAdd);
        
        episodes = episodesTable.getAll();
        seasons = seasonsTable.getAll();
        shows = showsTable.getAll();
        movies = moviesTable.getAll();
        
        System.out.println("Stav tabulek po použití loadFrom:");
        System.out.println();
        System.out.println("Filmy:");
        System.out.println();
        
        for (Movie m : movies) 
        {
            System.out.println(m);
        }
        
        System.out.println();
        System.out.println("Seriály:");
        System.out.println();
        
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        System.out.println();
        System.out.println("Sezóny:");
        System.out.println();
        
        for (TVSeason s : seasons) 
        {
            System.out.println(s);
        }
        
        System.out.println();
        System.out.println("Epizody:");
        System.out.println();
        
        for (TVEpisode e : episodes) 
        {
            System.out.println(e);
        }
        
        //editBy method
        System.out.println();
        System.out.println("editBy method");
        System.out.println();
        
        showsTable.editBy(showAdd.getPrimaryKey(), new TVShow(null, "Rebels", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE));
        
        showAdd = showsTable.getBy(showAdd.getPrimaryKey());
        
        System.out.println(showAdd);
        
        //filterBy method
        System.out.println();
        System.out.println("filterBy method");
        System.out.println();
        
        showsTable.addFrom(new TVShow(null, "Ahoj", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.RISE_OF_THE_FIRST_ORDER));
        
        //bez filtru
        System.out.println();
        System.out.println("bez filtru");
        System.out.println();
        
        shows = showsTable.getAll();
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        //s filtrem
        System.out.println();
        System.out.println("s filtrem");
        System.out.println();
        
        shows = showsTable.filterBy(o -> o.getEra() == Era.RISE_OF_THE_FIRST_ORDER);
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        //sortBy method
        System.out.println();
        System.out.println("sortBy method");
        System.out.println();
        
        //bez řazení
        System.out.println();
        System.out.println("bez řazení");
        System.out.println();
        
        shows = showsTable.getAll();
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        //s řazením podle jména
        System.out.println();
        System.out.println("s řazením podle jména");
        System.out.println();
        
        showsTable.sortBy((TVShow s1, TVShow s2) -> s1.getName().compareTo(s2.getName()), shows);
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        //sortByPrimaryKey method
        System.out.println();
        System.out.println("sortByPrimaryKey method");
        System.out.println();
        
        //bez řazení
        System.out.println();
        System.out.println("bez řazení");
        System.out.println();
        
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        //s řazením podle primary key
        System.out.println();
        System.out.println("s řazením podle primaryKey");
        System.out.println();
        
        showsTable.sortByPrimaryKey(shows);
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
    }
}
