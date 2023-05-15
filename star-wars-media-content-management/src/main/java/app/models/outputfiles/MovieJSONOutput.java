
package app.models.outputfiles;

import java.time.Duration;
import java.time.LocalDate;

/**
 *
 * @author jan.dostal
 */
public class MovieJSONOutput
{
    private int id;
    
    private String runtime;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    private String releaseDate;
    
    private String era;
    
    public int getId() 
    {
        return id;
    }

    public String getRuntime() 
    {
        return runtime;
    }

    public String getName() 
    {
        return name;
    }

    public int getPercentageRating() 
    {
        return percentageRating;
    }

    public boolean getWasWatched() 
    {
        return wasWatched;
    }

    public String getHyperlinkForContentWatch() 
    {
        return hyperlinkForContentWatch;
    }

    public String getShortContentSummary() 
    {
        return shortContentSummary;
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
        return "MovieJSONOutput{id=" + id + ", runtime=" + 
                runtime + ", name=" + name + ", percentageRating=" +
                percentageRating + ", wasWatched=" + wasWatched +
                ", hyperlinkForContentWatch=" + hyperlinkForContentWatch + 
                ", releaseDate=" + releaseDate + ", era=" + era + "}";
    }
}
