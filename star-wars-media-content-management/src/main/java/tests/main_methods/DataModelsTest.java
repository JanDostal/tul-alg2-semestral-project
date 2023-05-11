
package tests.main_methods;

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
        System.out.println("Movie test");
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
    }
    
    
    
}
