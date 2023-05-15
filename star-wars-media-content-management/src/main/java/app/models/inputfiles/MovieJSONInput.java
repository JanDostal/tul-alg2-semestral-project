
package app.models.inputfiles;

/**
 *
 * @author jan.dostal
 */
public class MovieJSONInput
{
    private String runtime;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    private String releaseDate;
    
    private String era;
        
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

    public void setRuntime(String runtime) 
    {
        this.runtime = runtime;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setPercentageRating(int percentageRating) 
    {
        this.percentageRating = percentageRating;
    }

    public void setWasWatched(boolean wasWatched) 
    {
        this.wasWatched = wasWatched;
    }

    public void setHyperlinkForContentWatch(String hyperlinkForContentWatch) 
    {
        this.hyperlinkForContentWatch = hyperlinkForContentWatch;
    }

    public void setShortContentSummary(String shortContentSummary) 
    {
        this.shortContentSummary = shortContentSummary;
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
        return "MovieJSONInput{runtime=" + runtime + ", name=" + name + 
                ", percentageRating=" + percentageRating + ", wasWatched=" +
                wasWatched + ", hyperlinkForContentWatch=" + hyperlinkForContentWatch + 
                ", releaseDate=" + releaseDate + ", era=" + era + "}";
    }
}
