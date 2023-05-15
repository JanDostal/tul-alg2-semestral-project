
package app.models.outputfiles;

/**
 *
 * @author jan.dostal
 */
public class TVEpisodeJSONOutput
{
    private int id;
    
    private String runtime;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    private int orderInTVShowSeason;
    
    private int tvSeasonId;
    
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

    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
    }
    
    public int getTVSeasonId() 
    {
        return tvSeasonId;
    }

    public void setId(int id) 
    {
        this.id = id;
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

    public void setTvSeasonId(int tvSeasonId) 
    {
        this.tvSeasonId = tvSeasonId;
    }
    
    public @Override String toString() 
    {   
        return "TVEpisodeJSONOutput{id=" + id + ", runtime=" + 
                runtime + ", name=" + name + ", percentageRating=" +
                percentageRating + ", wasWatched=" + wasWatched +
                ", hyperlinkForContentWatch=" + hyperlinkForContentWatch + 
                ", orderInTVShowSeason=" + orderInTVShowSeason + ", tvSeasonId=" +
                tvSeasonId + "}";
    }
}
