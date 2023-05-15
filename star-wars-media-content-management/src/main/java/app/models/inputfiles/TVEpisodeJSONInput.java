
package app.models.inputfiles;


/**
 *
 * @author jan.dostal
 */
public class TVEpisodeJSONInput
{
    private String runtime;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    private int orderInTVShowSeason;
            
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

    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
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

    public void setOrderInTVShowSeason(int orderInTVShowSeason) 
    {
        this.orderInTVShowSeason = orderInTVShowSeason;
    }
    
    public @Override String toString() 
    {       
        return "TVEpisodeJSONInput{runtime=" + runtime + ", name=" + name + 
                ", percentageRating=" + percentageRating + ", wasWatched=" + wasWatched +
                ", hyperlinkForContentWatch=" + hyperlinkForContentWatch + 
                ", orderInTVShowSeason=" + orderInTVShowSeason + "}";
    }
}
