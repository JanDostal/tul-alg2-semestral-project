
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVShowJSONInput 
{   
    private String name;
    
    private String releaseDate;
    
    private String era;
        
    public TVShowJSONInput(String name, String releaseDate, String era) 
    {
        this.name = name;
        this.releaseDate = releaseDate;
        this.era = era;
    }

    public String getName() 
    {
        return name;
    }

    public String getReleaseDate() 
    {
        return releaseDate;
    }

    public String getEra() 
    {
        return era;
    }
    
    public @Override String toString() 
    {
        return "TVShowJSONInput{name=" + name + ", releaseDate=" + 
                releaseDate + ", era=" + era + "}";
    }
}
