
package app.logic.datastore;

import app.models.data.Era;
import java.text.Collator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Admin
 */
public final class DataStore 
{
    private DataStore()
    {
    }
    
    private static Collator czechCollator = Collator.getInstance(new Locale("cs", "CZ"));
    
    private static Map<String, String> erasDescriptions;
    static 
    {
        erasDescriptions = new HashMap<>();
        
        erasDescriptions.put("DAWN_OF_THE_JEDI", 
                """
                Nejstarší období, odehrává se před obdobím Staré republiky.
    
                Pro upřesnění se toto období odehrává dávno před údalostmi 
                počítačové hry Star Wars: Knights of the Old Republic a 
                popisuje počátky Síly, Jediů a Sithů.
                """        
        );
        
        erasDescriptions.put("THE_OLD_REPUBLIC", 
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
        
        erasDescriptions.put("THE_HIGH_REPUBLIC", 
                """
                Období odehrávající se po období Staré republiky a před obdobím Pádu Jediů.
    
                Pro upřesnění se toto období odehrává přímo před údálosti filmu
                Skrytá hrozba, tedy před Prequelovou 
                trilogií (filmy Skrytá hrozba, Klony útočí, Pomsta Sithů) a popisuje
                události, kdy Jediové a republika byli na vrcholu své moci. Tyto
                události jsou popsány v knížkách.
                """    
        );
        
        erasDescriptions.put("FALL_OF_THE_JEDI", 
                """
                Období odehrávající se po období Vrcholné republiky a 
                před obdobím Vlády impéria.
    
                Pro upřesnění toto období popisuje události během Prequelové
                trilogie (filmy Skrytá hrozba, Klony útočí, Pomsta Sithů) a popisuje to,
                jak Jediové padli a jak se zrodil Darth Vader.
                """   
        );
        
        erasDescriptions.put("REIGN_OF_THE_EMPIRE", 
                """
                Období odehrávající se po období Pádů Jediů a před obdobím Věku povstání.
    
                Pro upřesnění se toto období odehrává po událostech filmu Pomsta sithů, tedy
                po prequelové trilogii (filmy Skrytá hrozba, Klony útočí, Pomsta Sithů) a
                končí událostmi filmu Rogue One: Star Wars Story, tedy před Originální
                trilogií (filmy Nová naděje, Impérium vrací úder, Návrat Jediů). 
                Období popisuje lovení Jediů impériém a formování povstání.
                """  
        );
        
        erasDescriptions.put("AGE_OF_THE_REBELLION", 
                """
                Období odehrávající se po období Vlády impéria a před obdobím Nové republiky.
    
                Pro upřesnění toto období popisuje události během Originální 
                trilogie (filmy Nová naděje, Impérium vrací úder, Návrat Jediů). 
                Období začíná ihned po údalostech filmu Rogue One: Star Wars Story a 
                popisuje boj povstání proti impériu.
                """
        );
        
        erasDescriptions.put("THE_NEW_REPUBLIC", 
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
        
        erasDescriptions.put("RISE_OF_THE_FIRST_ORDER", 
                """
                Období odehrávající se po období Nové republiky a 
                před obdobím Nového řádu Jedi.
    
                Pro upřesnění toto období popisuje události během Sequelové 
                trilogie (filmy Síla se probouzí, Poslední z Jediů, Vzestup Skywalkera). 
                Období popisuje vládu Prvního řádu a sleduje osudy postav z Originální
                trilogie.
                """
        );
        
        erasDescriptions.put("NEW_JEDI_ORDER",
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
}
