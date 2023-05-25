
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
        System.out.println("filmB.releaseDate (should be 2023-05-13): " + filmB.getReleaseDate());
        System.out.println("filmB.era (should be THE_NEW_REPUBLIC): " + filmB.getEra());
        System.out.println(filmB);
        
        //era test
        System.out.println();
        System.out.println("era test");
        System.out.println();
        
        System.out.println("defaultName (should be FALL_OF_THE_JEDI): " + Era.FALL_OF_THE_JEDI);
        System.out.println("displayName (should be Pád Jediů): " + Era.FALL_OF_THE_JEDI.getDisplayName());
        
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
        System.out.println("episodeB.orderIntTVShowSeason (should be 3): " + episodeB.getOrderInTVShowSeason());
        System.out.println("episodeB.tvSeasonForeignKey (should be PrimaryKey{id=1}): " + episodeB.getTVSeasonForeignKey());
        System.out.println(episodeB);
        
        //null data attributes test
        System.out.println();
        System.out.println("null data attributes test");
        System.out.println();
        
        TVShow showNull = new TVShow(null, "   ", null, null);
        System.out.println(showNull);
        
        MediaContent episodeNull = new TVEpisode(null, null, "   ", 2, true, "", null, 2, null);
        System.out.println(episodeNull);
        
        Movie movieNull = new Movie(null, null, null, 2, true, "", "  ", null, null);
        System.out.println(movieNull);
        
        //tvShow equality test
        System.out.println();
        System.out.println("tvShow equality test");
        System.out.println();
        
        //same reference
        TVShow show01 = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        
        TVShow show02 = show01;
        
        System.out.println("is same reference (should be true): show01 == show02: " + show01.equals(show02));
        
        //instances originating from same class
        Movie movie01 = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        System.out.println("are instances from same class (should be false): show01 == movie01: " + show01.equals(movie01));
        
        
        //same data
        TVShow show03 = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        
        System.out.println("are same data (should be true): show01 == show03: " + show01.equals(show03));
        
        //comparison if method decides origin of instances from data type or instances themselves
        TVShow show04 = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        DatabaseRecord show05 = show04;
        
        System.out.println("Instances show01 and show05 are compared by instances (should be true): " + show01.equals(show05));

        //PrimaryKey equality test
        
        System.out.println();
        System.out.println("PrimaryKey equality test");
        System.out.println();
        
        //same reference
        PrimaryKey k1 = new PrimaryKey(1);
        
        PrimaryKey k2 = k1;
        
        System.out.println("is same reference (should be true): k1 == k2: " + k1.equals(k2));
        
        //instances originating from same class
        Movie m1 = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        System.out.println("are instances from same class (should be false): k1 == m1: " + k1.equals(m1));
        
        
        //same data
        PrimaryKey k3 = new PrimaryKey(1);
        
        System.out.println("are same data (should be true): k1 == k3: " + k1.equals(k3));
        
        //tvSeason equality test
        System.out.println();
        System.out.println("tvSeason equality test");
        System.out.println();
        
        //same reference
        TVShow referenceTVShow = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        TVSeason season01 = new TVSeason(new PrimaryKey(222), 2, referenceTVShow.getPrimaryKey());
        
        TVSeason season02 = season01;
        
        System.out.println("is same reference (should be true): season01 == season02: " + season01.equals(season02));
        
        //instances originating from same class
       
        System.out.println("are instances from same class (should be false): season01 == referenceTVShow: " 
                + season01.equals(referenceTVShow));
        
        //same data
        TVSeason season03 = new TVSeason(new PrimaryKey(222), 2, referenceTVShow.getPrimaryKey());
        
        System.out.println("are same data (should be true): season01 == season03: " + season01.equals(season03));
    }
}
