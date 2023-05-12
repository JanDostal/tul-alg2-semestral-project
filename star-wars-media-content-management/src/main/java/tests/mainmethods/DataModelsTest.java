
package tests.mainmethods;

import app.models.data.*;
import java.time.Duration;
import java.time.LocalDate;

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
        
        MediaContent filmA = new Movie(2, Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11"), Era.FALL_OF_THE_JEDI);
        
        Movie filmB = new Movie(3, Duration.ofMinutes(50), "filmB", 
                60, true, "https://www.example02.com", "Velmi špatný film", 
                LocalDate.parse("2023-05-13"), Era.THE_NEW_REPUBLIC);
                
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
        
        TVShow show = new TVShow(1, "show", LocalDate.parse("2023-05-20"), Era.AGE_OF_THE_REBELLION);
        System.out.println(show);
        
        //tvSeason test
        System.out.println();
        System.out.println("tvSeason test");
        System.out.println();
        
        TVSeason season = new TVSeason(1, 2, show.getId());
        System.out.println(season);
        
        //tvEpisode test
        System.out.println();
        System.out.println("tvEpisode test");
        System.out.println();
        
        MediaContent episodeA = new TVEpisode(2, Duration.ofMinutes(45), "episodeA", 
                50, false, "https://www.example01.com", "Velmi krásná epizoda", 
                4, season.getId());
        
        TVEpisode episodeB = new TVEpisode(3, Duration.ofMinutes(50), "episodeB", 
                60, true, "https://www.example02.com", "Velmi špatná epizoda", 3, season.getId());
                
        System.out.println(episodeA);
        System.out.println("episodeB.orderIntTVShowSeason: " + episodeB.getOrderInTVShowSeason());
        System.out.println("episodeB.tvSeasonId: " + episodeB.getTVSeasonId());
        System.out.println(episodeB);
    }
}
