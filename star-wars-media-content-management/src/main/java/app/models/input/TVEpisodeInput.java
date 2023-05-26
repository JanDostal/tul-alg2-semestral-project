
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVEpisodeInput
{
    private long runtimeInSeconds;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    private int orderInTVShowSeason;
    
    public TVEpisodeInput(long runtimeInSeconds, String name, 
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason) 
    {
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name;
        this.percentageRating = percentageRating;
        this.wasWatched = wasWatched;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch;
        this.shortContentSummary = shortContentSummary;
        this.orderInTVShowSeason = orderInTVShowSeason;
    }
            
    public long getRuntimeInSeconds() 
    {
        return runtimeInSeconds;
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
    
    public @Override String toString() 
    {
        return String.format("TVEpisodeInput{runtimeInSeconds=%d, name=%s, "
                + "percentageRating=%d, wasWatched=%s, hyperlinkForContentWatch=%s, "
                + "orderInTVShowSeason=%d}", runtimeInSeconds, name,
                percentageRating, wasWatched, hyperlinkForContentWatch, 
                orderInTVShowSeason);
    }
}
