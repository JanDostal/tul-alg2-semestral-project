
package tests.mainmethods;

import app.models.data.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jan.dostal
 */
public class DataModelsTest 
{

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
        System.out.println("description (mělo by se umět vypsat víceřádkově): \n" +
                Era.FALL_OF_THE_JEDI.getDescription());
        
        System.out.println("era list (nejstarší DAWN_OF_THE_JEDI, nejnovější NEW_JEDI_ORDER)");
        
        for (Era era : Era.values()) 
        {
            System.out.println(era);
        }
        
        //tvShow test
        System.out.println();
        System.out.println("tvShow test");
        System.out.println();
        
        TVShow show = new TVShow(new PrimaryKey(1), "show", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
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
        
        TVSeason tvSeasonNull = new TVSeason(null, 5, null);
        System.out.println(tvSeasonNull);

        
        //tvShow equality test
        System.out.println();
        System.out.println("tvShow equality test");
        System.out.println();
        
        //same reference
        TVShow show01_test = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
        
        TVShow show02_test = show01_test;
        
        System.out.println("is same reference (should be true): show01_test == show02_test: " 
                + show01_test.equals(show02_test));
        
        //instances originating from same class
        Movie movie01_test = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        System.out.println("are instances from same class (should be false): show01_test == movie01_test: " 
                + show01_test.equals(movie01_test));
        
        
        //same data
        TVShow show03_test = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.RISE_OF_THE_FIRST_ORDER);
        
        System.out.println("are same data (should be true): show01_test == show03_test: " 
                + show01_test.equals(show03_test));
        
        //comparison if method decides origin of instances from data type or instances themselves
        TVShow show04_test = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
        DatabaseRecord show05_test = show04_test;
        
        System.out.println("Instances show01_test and show05_test are compared by instances (should be true): " 
                + show01_test.equals(show05_test));

        //PrimaryKey equality test
        
        System.out.println();
        System.out.println("PrimaryKey equality test");
        System.out.println();
        
        //same reference
        PrimaryKey primaryKey01_test = new PrimaryKey(1);
        
        PrimaryKey primaryKey02_test = primaryKey01_test;
        
        System.out.println("is same reference (should be true): primaryKey01_test == primaryKey02_test: " 
                + primaryKey01_test.equals(primaryKey02_test));
        
        //instances originating from same class
        Movie movie02_test = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        System.out.println("are instances from same class (should be false): primaryKey01_test == movie02_test: " 
                + primaryKey01_test.equals(movie02_test));
        
        
        //same data
        PrimaryKey primaryKey03_test = new PrimaryKey(1);
        
        System.out.println("are same data (should be true): primaryKey01_test == primaryKey03_test: " 
                + primaryKey01_test.equals(primaryKey03_test));
        
        //tvSeason equality test
        System.out.println();
        System.out.println("tvSeason equality test");
        System.out.println();
        
        //same reference
        TVShow referenceTVShow_test = new TVShow(new PrimaryKey(10), "show1", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
        TVSeason season01_test = new TVSeason(new PrimaryKey(222), 2, referenceTVShow_test.getPrimaryKey());
        
        TVSeason season02_test = season01_test;
        
        System.out.println("is same reference (should be true): season01_test == season02_test: " + 
                season01_test.equals(season02_test));
        
        //instances originating from same class
       
        System.out.println("are instances from same class (should be false): season01_test == referenceTVShow_test: " 
                + season01_test.equals(referenceTVShow_test));
        
        //same data
        TVSeason season03_test = new TVSeason(new PrimaryKey(222), 2, referenceTVShow_test.getPrimaryKey());
        
        System.out.println("are same data (should be true): season01_test == season03_test: " 
                + season01_test.equals(season03_test));
        
        //tvEpisode equality test
        System.out.println();
        System.out.println("tvEpisode equality test");
        System.out.println();
        
        //same reference
        TVSeason referenceTVSeason_test = new TVSeason(new PrimaryKey(10), 2, referenceTVShow_test.getPrimaryKey());
        TVEpisode episode01_test = new TVEpisode(new PrimaryKey(2), Duration.ofMinutes(45), "episodeA", 
                50, false, null, null, 
                4, referenceTVSeason_test.getTVShowForeignKey());
        
        TVEpisode episode02_test = episode01_test;
        
        System.out.println("is same reference (should be true): episode01_test == episode02_test: " 
                + episode01_test.equals(episode02_test));
        
        //instances originating from same class
        Movie movie03_test = new Movie(new PrimaryKey(2), Duration.ofMinutes(45), "filmA", 
                50, false, "https://www.example01.com", "Velmi krásný film", 
                LocalDate.parse("2023-05-11", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
        
        System.out.println("are instances from same class (should be false): episode01_test == movie03_test: " 
                + episode01_test.equals(movie03_test));
        
        //same data
        TVEpisode episode03_test = new TVEpisode(new PrimaryKey(2), Duration.ofMinutes(45), "episodeA", 
                50, false, "https://www.example01.com", "Ahoj3", 
                4, referenceTVSeason_test.getTVShowForeignKey());
        TVEpisode episode04_test = new TVEpisode(new PrimaryKey(2), Duration.ofMinutes(45), "episodeA", 
                50, false, "https://www.example01.com", "Ahoj2", 
                4, referenceTVSeason_test.getTVShowForeignKey());
        TVEpisode episode05_test = new TVEpisode(new PrimaryKey(2), Duration.ofMinutes(45), "episodeA", 
                50, false, "https://www.example02.com", "Ahoj3", 
                4, referenceTVSeason_test.getTVShowForeignKey());
        TVEpisode episode06_test = new TVEpisode(new PrimaryKey(2), Duration.ofMinutes(45), "episodeA", 
                50, false, null, null, 
                4, referenceTVSeason_test.getTVShowForeignKey());
        
        System.out.println("are same data by hyperlink (should be true): episode03_test == episode04_test: " +
                episode03_test.equals(episode04_test));
        
        System.out.println("are same data by content (should be true): episode03_test == episode05_test: " +
                episode03_test.equals(episode05_test));
        
        System.out.println("are same data by order in season and seasonId (should be true): "
                + "episode03_test == episode06_test: " +
                episode03_test.equals(episode06_test));
        
        //comparison if method decides origin of instances from data type or instances themselves
        TVEpisode episode07_test = new TVEpisode(new PrimaryKey(2), Duration.ofMinutes(45), "episodeA", 
                50, false, null, null, 
                4, referenceTVSeason_test.getTVShowForeignKey());
        MediaContent episode08_test = episode07_test;
        
        System.out.println("Instances episode01_test and episode08_test are compared by instances (should be true): " 
                + episode01_test.equals(episode08_test));
        
        //movie equality test
        System.out.println();
        System.out.println("movie equality test");
        System.out.println();
        
        //same reference
        Movie movie04_test = new Movie(new PrimaryKey(3), Duration.ofMinutes(50), "filmB", 
                60, true, null, null, 
                LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        
        Movie movie05_test = movie04_test;
        
        System.out.println("is same reference (should be true): movie04_test == movie05_test: " + 
                movie04_test.equals(movie05_test));
        
        //instances originating from same class
        TVEpisode episode09_test = new TVEpisode(new PrimaryKey(3), Duration.ofMinutes(50), "episodeA", 
                50, false, null, null, 
                4, referenceTVSeason_test.getTVShowForeignKey());
        
        System.out.println("are instances from same class (should be false): movie04_test == episode09_test: " 
                + movie04_test.equals(episode09_test));
        
        //same data
        Movie movie06_test = new Movie(new PrimaryKey(3), Duration.ofMinutes(50), "filmB", 
                60, true, "https://www.example01.com", "Ahoj1", 
                LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        Movie movie07_test = new Movie(new PrimaryKey(3), Duration.ofMinutes(50), "filmB", 
                60, true,  "https://www.example01.com", "Ahoj2", 
                LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        Movie movie08_test = new Movie(new PrimaryKey(3), Duration.ofMinutes(50), "filmB", 
                60, true, "https://www.example02.com", "Ahoj1", 
                LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        Movie movie09_test = new Movie(new PrimaryKey(3), Duration.ofMinutes(50), "filmB", 
                60, true, null, null, 
                LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
        
        System.out.println("are same data by hyperlink (should be true): movie06_test == movie07_test: " +
                movie06_test.equals(movie07_test));
        
        System.out.println("are same data by content (should be true): movie06_test == movie08_test: " +
                movie06_test.equals(movie08_test));
        
        System.out.println("are same data by release and name (should be true): movie06_test == movie09_test: " +
                movie06_test.equals(movie09_test));
    }
}
