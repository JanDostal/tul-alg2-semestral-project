
package app.models.output;

/**
 *
 * @author Admin
 */
public class TVShowJSONOutput 
{
    private int id;
    
    private String name;
    
    private String releaseDate;
    
    private String era;
        
    public TVShowJSONOutput(int id, String name, String releaseDate, String era) 
    {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.era = era;
    }

    public int getId() 
    {
        return id;
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
        return "TVShowJSONOutput{id=" + id + ", name=" + name + ", releaseDate=" + 
                releaseDate + ", era=" + era + "}";
    }
}
