
package app.logic.datastore;

import app.models.data.Era;
import java.text.Collator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a datastore, which accesses static data like files names, informations about app etc.
 * @author jan.dostal
 */
public final class DataStore 
{
    private DataStore()
    {
    }
    
    private static final String appName = "Star Wars Media Content Management";
    
    private static final String appCreator = "honzaswtor@gmail.com";
    
    private static final String dataDirectoryName = "data";
    
    
    private static final String textInputMoviesFilename = "input_movies.txt";
    
    private static final String textInputTVShowsFilename = "input_tvShows.txt";
    
    private static final String textInputTVSeasonsFilename = "input_tvSeasons.txt";
    
    private static final String textInputTVEpisodesFilename = "input_tvEpisodes.txt";
    
    
    private static final String binaryInputMoviesFilename = "input_movies.bin";
    
    private static final String binaryInputTVShowsFilename = "input_tvShows.bin";
    
    private static final String binaryInputTVSeasonsFilename = "input_tvSeasons.bin";
    
    private static final String binaryInputTVEpisodesFilename = "input_tvEpisodes.bin";
    
    
    private static final String textOutputMoviesFilename = "output_movies.txt";
    
    private static final String textOutputTVShowsFilename = "output_tvShows.txt";
    
    private static final String textOutputTVSeasonsFilename = "output_tvSeasons.txt";
    
    private static final String textOutputTVEpisodesFilename = "output_tvEpisodes.txt";
    
    
    private static final String binaryOutputMoviesFilename = "output_movies.bin";
    
    private static final String binaryOutputTVShowsFilename = "output_tvShows.bin";
    
    private static final String binaryOutputTVSeasonsFilename = "output_tvSeasons.bin";
    
    private static final String binaryOutputTVEpisodesFilename = "output_tvEpisodes.bin";
    
    
    private static final Collator czechCollator = Collator.getInstance(new Locale("cs", "CZ"));
    
    private static final Map<String, String> erasDescriptions;
    static 
    {
        erasDescriptions = new HashMap<>();
        
        erasDescriptions.put(Era.DAWN_OF_THE_JEDI.toString(), 
                """
                Nejstarší období, odehrává se před obdobím Staré republiky.
    
                Pro upřesnění se toto období odehrává dávno před údalostmi 
                počítačové hry Star Wars: Knights of the Old Republic a 
                popisuje počátky Síly, Jediů a Sithů.
                """        
        );
        
        erasDescriptions.put(Era.THE_OLD_REPUBLIC.toString(), 
                """
                Období odehrávající se po období Úsvitu Jediů a 
                před obdobím Vrcholné republiky.
    
                Pro upřesnění se toto období odehrává během 
                počítačových her Star Wars: Knights of the Old Republic,
                Star Wars: Knights of the Old Republic II: The Sith Lords a
                Star Wars: The Old Republic a popisuje události jako Mandaloriánské války,
                Jedijská občanská válka atd.
                """      
        );
        
        erasDescriptions.put(Era.THE_HIGH_REPUBLIC.toString(), 
                """
                Období odehrávající se po období Staré republiky a před obdobím Pádu Jediů.
    
                Pro upřesnění se toto období odehrává přímo před údálosti filmu
                Skrytá hrozba, tedy před Prequelovou 
                trilogií (filmy Skrytá hrozba, Klony útočí, Pomsta Sithů) a popisuje
                události, kdy Jediové a republika byli na vrcholu své moci. Tyto
                události jsou popsány v knížkách.
                """    
        );
        
        erasDescriptions.put(Era.FALL_OF_THE_JEDI.toString(), 
                """
                Období odehrávající se po období Vrcholné republiky a 
                před obdobím Vlády impéria.
    
                Pro upřesnění toto období popisuje události během Prequelové
                trilogie (filmy Skrytá hrozba, Klony útočí, Pomsta Sithů) a popisuje to,
                jak Jediové padli a jak se zrodil Darth Vader.
                """   
        );
        
        erasDescriptions.put(Era.REIGN_OF_THE_EMPIRE.toString(), 
                """
                Období odehrávající se po období Pádů Jediů a před obdobím Věku povstání.
    
                Pro upřesnění se toto období odehrává po událostech filmu Pomsta sithů, tedy
                po prequelové trilogii (filmy Skrytá hrozba, Klony útočí, Pomsta Sithů) a
                končí událostmi filmu Rogue One: Star Wars Story, tedy před Originální
                trilogií (filmy Nová naděje, Impérium vrací úder, Návrat Jediů). 
                Období popisuje lovení Jediů impériém a formování povstání.
                """  
        );
        
        erasDescriptions.put(Era.AGE_OF_REBELLION.toString(), 
                """
                Období odehrávající se po období Vlády impéria a před obdobím Nové republiky.
    
                Pro upřesnění toto období popisuje události během Originální 
                trilogie (filmy Nová naděje, Impérium vrací úder, Návrat Jediů). 
                Období začíná ihned po údalostech filmu Rogue One: Star Wars Story a 
                popisuje boj povstání proti impériu.
                """
        );
        
        erasDescriptions.put(Era.THE_NEW_REPUBLIC.toString(), 
                """
                Období odehrávající se po období Věku povstání a před 
                obdobím Vzestupu Prvního řádu.
    
                Pro upřesnění se toto období odehrává po údalostech Originální 
                trilogie (filmy Nová naděje, Impérium vrací úder, Návrat Jediů) a
                zároveň před údalostmi Sequelové trilogie (filmy Síla se probouzí, 
                Poslední z Jediů, Vzestup Skywalkera).
                Období začíná ihned po údalostech filmu Návrat Jediů a popisuje 
                stav galaxie a jejích obyvatel po pádu impéria a taky okolnosti, které
                umožní vzestup Prvního řádu. Období je více popsáno např. seriálem
                Mandalorian.
                """
        );
        
        erasDescriptions.put(Era.RISE_OF_THE_FIRST_ORDER.toString(), 
                """
                Období odehrávající se po období Nové republiky a 
                před obdobím Nového řádu Jedi.
    
                Pro upřesnění toto období popisuje události během Sequelové 
                trilogie (filmy Síla se probouzí, Poslední z Jediů, Vzestup Skywalkera). 
                Období popisuje vládu Prvního řádu a sleduje osudy postav z Originální
                trilogie.
                """
        );
        
        erasDescriptions.put(Era.NEW_JEDI_ORDER.toString(),
                """
                Nejnovější období odehrávající se po období Vzestupu Prvního řádu.
    
                Pro upřesnění toto období popisuje události po Sequelové 
                trilogie (filmy Síla se probouzí, Poslední z Jediů, Vzestup Skywalkera). 
                Období bude popisovat, jak se Rey po konci filmu Vzestup Skywalkera snaží
                obnovit Jedijský řád.
                """
        );
    }
    
    public static String loadEraDescription(String era) 
    {
        return erasDescriptions.get(era);
    }
    
    public static Collator loadCzechCollator() 
    {
        return czechCollator;
    }
    
    public static String getAppName() 
    {
        return appName;
    }

    public static String getTextInputMoviesFilename() 
    {
        return textInputMoviesFilename;
    }

    public static String getTextInputTVShowsFilename() 
    {
        return textInputTVShowsFilename;
    }

    public static String getTextInputTVSeasonsFilename() 
    {
        return textInputTVSeasonsFilename;
    }

    public static String getTextInputTVEpisodesFilename() 
    {
        return textInputTVEpisodesFilename;
    }

    public static String getBinaryInputMoviesFilename() 
    {
        return binaryInputMoviesFilename;
    }

    public static String getBinaryInputTVShowsFilename() {
        return binaryInputTVShowsFilename;
    }

    public static String getBinaryInputTVSeasonsFilename() 
    {
        return binaryInputTVSeasonsFilename;
    }

    public static String getBinaryInputTVEpisodesFilename() 
    {
        return binaryInputTVEpisodesFilename;
    }

    public static String getTextOutputMoviesFilename() 
    {
        return textOutputMoviesFilename;
    }

    public static String getTextOutputTVSeasonsFilename() 
    {
        return textOutputTVSeasonsFilename;
    }

    public static String getTextOutputTVEpisodesFilename() 
    {
        return textOutputTVEpisodesFilename;
    }

    public static String getBinaryOutputMoviesFilename() 
    {
        return binaryOutputMoviesFilename;
    }

    public static String getBinaryOutputTVShowsFilename() 
    {
        return binaryOutputTVShowsFilename;
    }

    public static String getBinaryOutputTVSeasonsFilename() 
    {
        return binaryOutputTVSeasonsFilename;
    }

    public static String getBinaryOutputTVEpisodesFilename() 
    {
        return binaryOutputTVEpisodesFilename;
    }
    
    public static String getTextOutputTVShowsFilename() 
    {
        return textOutputTVShowsFilename;
    }
    
    public static String getDataDirectoryName() 
    {
        return dataDirectoryName;
    }
    
    public static String getAppCreator() 
    {
        return appCreator;
    }
}
