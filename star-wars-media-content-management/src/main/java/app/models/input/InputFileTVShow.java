
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class InputFileTVShow 
{   
    private String name;
    
    private String releaseDate;
    
    private String era;
        
    public InputFileTVShow(int id, String name, String releaseDate, String era) 
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
        return "InputFileTVShow{name=" + name + ", releaseDate=" + 
                releaseDate + ", era=" + era + "}";
    }
}
