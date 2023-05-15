
package app.models.inputfiles;

/**
 *
 * @author jan.dostal
 */
public class TVShowJSONInput 
{   
    private String name;
    
    private String releaseDate;
    
    private String era;
        
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

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setReleaseDate(String releaseDate) 
    {
        this.releaseDate = releaseDate;
    }

    public void setEra(String era) 
    {
        this.era = era;
    }
    
    public @Override String toString() 
    {
        return "TVShowJSONInput{name=" + name + ", releaseDate=" + 
                releaseDate + ", era=" + era + "}";
    }
}
