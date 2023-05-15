
package app.models.outputfiles;

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

    public void setId(int id) 
    {
        this.id = id;
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
        return "TVShowJSONOutput{id=" + id + ", name=" + name + ", releaseDate=" + 
                releaseDate + ", era=" + era + "}";
    }
}
