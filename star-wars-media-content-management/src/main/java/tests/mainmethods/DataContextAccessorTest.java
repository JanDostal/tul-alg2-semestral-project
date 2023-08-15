package tests.mainmethods;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.exceptions.DatabaseException;
import utils.interfaces.IDataTable;

/**
 * Represents a custom unit test class for testing data context access layer service with data tables of data context module.
 * @author jan.dostal
 */
public class DataContextAccessorTest 
{
    //testing main
    public static void main(String[] args) 
    {
        try 
        {
            DataContextAccessor dbContext = DataContextAccessor.getInstance();
            IDataTable<Movie> moviesTable = dbContext.getMoviesTable();
            IDataTable<TVEpisode> episodesTable = dbContext.getTVEpisodesTable();
            IDataTable<TVSeason> seasonsTable = dbContext.getTVSeasonsTable();
            IDataTable<TVShow> showsTable = dbContext.getTVShowsTable();

            //addFrom method
            Movie movieAdd = new Movie(null, Duration.ofMinutes(45), "Star Wars: The New Dawn",
                    75, true, "https://www.example01.com", "very pretty movie",
                    LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);

            moviesTable.addFrom(movieAdd);

            List<Movie> movies = moviesTable.getAll();

            TVShow showAdd = new TVShow(null, "Clone Wars", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE);

            showsTable.addFrom(showAdd);

            List<TVShow> shows = showsTable.getAll();

            TVSeason seasonAdd = new TVSeason(null, 1, shows.get(0).getPrimaryKey());

            seasonsTable.addFrom(seasonAdd);

            List<TVSeason> seasons = seasonsTable.getAll();

            TVEpisode episodeAdd = new TVEpisode(null, Duration.ofMinutes(45), "Twin suns",
                    0, false, "https://www.example02.com", "very pretty episode",
                    1, seasons.get(0).getPrimaryKey());

            episodesTable.addFrom(episodeAdd);

            List<TVEpisode> episodes = episodesTable.getAll();

            //getBy method
            System.out.println("getBy method");
            System.out.println();

            System.out.println(moviesTable.getBy(movies.get(0).getPrimaryKey()));
            System.out.println(showsTable.getBy(shows.get(0).getPrimaryKey()));
            System.out.println(seasonsTable.getBy(seasons.get(0).getPrimaryKey()));
            System.out.println(episodesTable.getBy(episodes.get(0).getPrimaryKey()));

            //getAll method
            System.out.println();
            System.out.println("getAll method");
            System.out.println();

            System.out.println("State of data tables after using addFrom:");
            System.out.println();
            System.out.println("Movies:");
            System.out.println();

            for (Movie m : movies) 
            {
                System.out.println(m);
            }

            System.out.println();
            System.out.println("TV shows:");
            System.out.println();

            for (TVShow s : shows) 
            {
                System.out.println(s);
            }

            System.out.println();
            System.out.println("TV seasons:");
            System.out.println();

            for (TVSeason s : seasons) 
            {
                System.out.println(s);
            }

            System.out.println();
            System.out.println("TV episodes:");
            System.out.println();

            for (TVEpisode e : episodes) 
            {
                System.out.println(e);
            }

            //deleteBy method
            System.out.println("deleteBy method");
            System.out.println();

            episodesTable.deleteBy(episodes.get(0).getPrimaryKey());
            episodes = episodesTable.getAll();
            seasonsTable.deleteBy(seasons.get(0).getPrimaryKey());
            seasons = seasonsTable.getAll();
            showsTable.deleteBy(shows.get(0).getPrimaryKey());
            shows = showsTable.getAll();
            moviesTable.deleteBy(movies.get(0).getPrimaryKey());
            movies = moviesTable.getAll();

            System.out.println("State of data tables after using deleteBy:");
            System.out.println();
            System.out.println("Movies:");
            System.out.println();

            for (Movie m : movies) 
            {
                System.out.println(m);
            }

            System.out.println();
            System.out.println("TV shows:");
            System.out.println();

            for (TVShow s : shows) 
            {
                System.out.println(s);
            }

            System.out.println();
            System.out.println("TV seasons:");
            System.out.println();

            for (TVSeason s : seasons) 
            {
                System.out.println(s);
            }

            System.out.println();
            System.out.println("TV episodes:");
            System.out.println();

            for (TVEpisode e : episodes) 
            {
                System.out.println(e);
            }

            //loadFrom method
            System.out.println();
            System.out.println("loadFrom method");
            System.out.println();

            showsTable.loadFrom(new TVShow(new PrimaryKey(23), "Clone Wars", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE));

            seasonsTable.loadFrom(new TVSeason(new PrimaryKey(232), 2, new PrimaryKey(23)));
            episodesTable.loadFrom(new TVEpisode(new PrimaryKey(232), Duration.ofMinutes(45), "Star Wars: The New Dawn",
                    75, true, "https://www.example01.com", "very pretty movie",
                    2, new PrimaryKey(232)));

            moviesTable.loadFrom(new Movie(new PrimaryKey(21313), Duration.ofMinutes(45), "Star Wars: The New Dawn",
                    75, true, "https://www.example01.com", "very pretty movie",
                    LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI));

            episodes = episodesTable.getAll();
            seasons = seasonsTable.getAll();
            shows = showsTable.getAll();
            movies = moviesTable.getAll();

            System.out.println("State of data tables after using loadFrom:");
            System.out.println();
            System.out.println("Movies:");
            System.out.println();

            for (Movie m : movies) 
            {
                System.out.println(m);
            }

            System.out.println();
            System.out.println("TV shows:");
            System.out.println();

            for (TVShow s : shows) 
            {
                System.out.println(s);
            }

            System.out.println();
            System.out.println("TV seasons:");
            System.out.println();

            for (TVSeason s : seasons) 
            {
                System.out.println(s);
            }

            System.out.println();
            System.out.println("TV episodes:");
            System.out.println();

            for (TVEpisode e : episodes) 
            {
                System.out.println(e);
            }

            //editBy method
            System.out.println();
            System.out.println("editBy method");
            System.out.println();

            boolean wasDataChanged = showsTable.editBy(shows.get(0).getPrimaryKey(), 
                    new TVShow(null, "Rebels", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE));

            showAdd = showsTable.getBy(shows.get(0).getPrimaryKey());
            System.out.println("Data change occured:" + wasDataChanged);
            System.out.println(showAdd);

            //filterBy method
            System.out.println();
            System.out.println("filterBy method");
            System.out.println();

            showsTable.addFrom(new TVShow(null, "Hello", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.RISE_OF_THE_FIRST_ORDER));

            //without filter
            System.out.println();
            System.out.println("without filter");
            System.out.println();

            shows = showsTable.getAll();
            for (TVShow s : shows) 
            {
                System.out.println(s);
            }

            //with filter
            System.out.println();
            System.out.println("with filter");
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

            //without sorting
            System.out.println();
            System.out.println("without sorting");
            System.out.println();

            shows = showsTable.getAll();
            for (TVShow s : shows) 
            {
                System.out.println(s);
            }

            //with sorting by name
            System.out.println();
            System.out.println("with sorting by name");
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

            //without sorting
            System.out.println();
            System.out.println("without sorting");
            System.out.println();

            for (TVShow s : shows) 
            {
                System.out.println(s);
            }

            //with sorting by primary key
            System.out.println();
            System.out.println("with sorting by primaryKey");
            System.out.println();

            showsTable.sortByPrimaryKey(shows);
            for (TVShow s : shows) 
            {
                System.out.println(s);
            }

            //with sorting by primary key with null values
            System.out.println();
            System.out.println("with sorting by primary key with null values");
            System.out.println();

            List<TVShow> test = new ArrayList<>();

            test.add(new TVShow(null, "Clone Wars", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE));
            test.add(new TVShow(new PrimaryKey(5), "Clone Wars", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE));

            test.add(new TVShow(null, "Clone Wars", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE));
            test.add(new TVShow(new PrimaryKey(3), "Clone Wars", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.REIGN_OF_THE_EMPIRE));

            Collections.sort(test);
            for (TVShow s : test) 
            {
                System.out.println(s);
            }
        }
        catch (DatabaseException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
