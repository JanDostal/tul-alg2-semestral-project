
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVEpisodeInput
{
    private final long runtimeInSeconds;
    
    private final String name;
    
    private final int percentageRating;
    
    private final String hyperlinkForContentWatch;
    
    private final String shortContentSummary;
    
    private final int orderInTVShowSeason;
    
    public TVEpisodeInput(long runtimeInSeconds, String name, 
            int percentageRating, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason) 
    {
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name;
        this.percentageRating = percentageRating;
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
                + "percentageRating=%d, hyperlinkForContentWatch=%s, "
                + "orderInTVShowSeason=%d}", runtimeInSeconds, name,
                percentageRating, hyperlinkForContentWatch, 
                orderInTVShowSeason);
    }
}
