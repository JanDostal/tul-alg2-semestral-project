package tests.mainmethods;

import app.models.output.*;

/**
 * Represents a custom unit test class for testing Data output models of models output module.
 * @author jan.dostal
 */
public class OutputModelsTest 
{

    //testing main
    public static void main(String[] args) 
    {
        //movie test
        System.out.println();
        System.out.println("movie test");
        System.out.println();
        
        System.out.println("Movie record size: " + MovieOutput.MOVIE_RECORD_SIZE + " B");
        
        //tv episode test
        System.out.println();
        System.out.println("tv episode test");
        System.out.println();
        System.out.println("TV episode record size: " + TVEpisodeOutput.TV_EPISODE_RECORD_SIZE + " B");
        
        //tv season test
        System.out.println();
        System.out.println("tv season test");
        System.out.println();
        System.out.println("TV season record size: " + TVSeasonOutput.TV_SEASON_RECORD_SIZE + " B");
        
        //tv show test
        System.out.println();
        System.out.println("tv show test");
        System.out.println();
        System.out.println("TV show record size: " + TVShowOutput.TV_SHOW_RECORD_SIZE + " B");
        
        // null data attributes test
        System.out.println();
        System.out.println("null data attributes test");
        System.out.println();
        
        TVShowOutput show = new TVShowOutput(2, null, 2, "    ");
        System.out.println(show);
        
        TVEpisodeOutput episode = new TVEpisodeOutput(2, 3, "", 2, "   ", null, 2, 2);
        System.out.println(episode);
        
        MovieOutput movie = new MovieOutput(2, 3, null, 3, "    ", "", 2, null);
        System.out.println(movie);
    }
    
}
