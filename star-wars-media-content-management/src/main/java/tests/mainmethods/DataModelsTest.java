
package tests.mainmethods;

import app.models.data.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jan.dostal
 */
public class DataModelsTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //movie test
        System.out.println();
        System.out.println("movie test");
        System.out.println();
        
        MediaContent filmA = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        Movie filmB = new Movie(new PrimaryKey(3), Duration.ofMinutes(50), "filmB", 
                60, true, "https://www.example02.com", "Velmi špatný film", 
                LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
                
        System.out.println(filmA);
        System.out.println("filmB.releaseDate: " + filmB.getReleaseDate());
        System.out.println("filmB.era: " + filmB.getEra());
        System.out.println(filmB);
        
        //era test
        System.out.println();
        System.out.println("era test");
        System.out.println();
        
        System.out.println("defaultName: " + Era.FALL_OF_THE_JEDI);
        System.out.println("displayName: " + Era.FALL_OF_THE_JEDI.getDisplayName());
        
        //tvShow test
        System.out.println();
        System.out.println("tvShow test");
        System.out.println();
        
        TVShow show = new TVShow(new PrimaryKey(1), "show", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        System.out.println(show);
        
        //tvSeason test
        System.out.println();
        System.out.println("tvSeason test");
        System.out.println();
        
        TVSeason season = new TVSeason(new PrimaryKey(1), 2, show.getPrimaryKey());
        System.out.println(season);
        
        //tvEpisode test
        System.out.println();
        System.out.println("tvEpisode test");
        System.out.println();
        
        MediaContent episodeA = new TVEpisode(new PrimaryKey(2), Duration.ofMinutes(45), "episodeA", 
                50, false, "https://www.example01.com", "Velmi krásná epizoda", 
                4, season.getTVShowForeignKey());
        
        TVEpisode episodeB = new TVEpisode(new PrimaryKey(3), Duration.ofMinutes(50), "episodeB", 
                60, true, "https://www.example02.com", "Velmi špatná epizoda", 3, season.getTVShowForeignKey());
                
        System.out.println(episodeA);
        System.out.println("episodeB.orderIntTVShowSeason: " + episodeB.getOrderInTVShowSeason());
        System.out.println("episodeB.tvSeasonForeignKey: " + episodeB.getTVSeasonForeignKey());
        System.out.println(episodeB);
        
        //null data attributes test
        System.out.println();
        System.out.println("null data attributes test");
        System.out.println();
        
        TVShow showNull = new TVShow(null, null, null, null);
        System.out.println(showNull);
        
        MediaContent episodeNull = new TVEpisode(null, null, null, 2, true, null, null, 2, null);
        System.out.println(episodeNull);
        
        Movie movieNull = new Movie(null, null, null, 2, true, null, null, null, null);
        System.out.println(movieNull);
        
        //tvShow equality test
        System.out.println();
        System.out.println("tvShow equality test");
        System.out.println();
        
        //same reference
        TVShow show01 = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        
        TVShow show02 = show01;
        
        System.out.println("is same reference: show01 == show02: " + show01.equals(show02));
        
        //instances originating from same class
        Movie movie01 = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        System.out.println("are instances from same class: show01 == movie01: " + show01.equals(movie01));
        
        
        //same data
        TVShow show03 = new TVShow(new PrimaryKey(10), "show2", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        
        System.out.println("are same data: show01 == show03: " + show01.equals(show03));
        
        //comparison if method decides origin of instances from data type or instances themselves
        TVShow show04 = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        DatabaseRecord show05 = show04;
        
        if (show01.equals(show05) == true) 
        {
            System.out.println("Instances show01 and show05 are compared by instances");
        }
        else 
        {
            System.out.println("Instances show01 and show05 are compared by data types");
        }        
    }
}
