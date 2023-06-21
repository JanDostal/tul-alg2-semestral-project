package app.models.input;

/**
 * Represents a input data model class for tv episode.
 * TVEpisodeInput class is used when parsing tv episodes input data files.
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
    
    /**
     * Creates a new instance representing TV episode input data model
     * @param runtimeInSeconds represents TV episode runtime/duration in seconds (positive number)
     * @param name represents TV episode name
     * @param percentageRating represents TV episode percentage rating (-infinity, positive number)
     * @param hyperlinkForContentWatch represents URL hyperlink to TV episode for watching
     * @param shortContentSummary represents TV episode short summary of its content
     * @param orderInTVShowSeason represents TV episode order in context of parent TV season (should be 1 or greater)
     */
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
    
    /**
     * @return tv episode instance runtime in seconds
     */
    public long getRuntimeInSeconds() 
    {
        return runtimeInSeconds;
    }
    
    /**
     * @return tv episode instance name
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * @return tv episode instance percentage rating
     */
    public int getPercentageRating() 
    {
        return percentageRating;
    }
    
    /**
     * @return tv episode instance URL hyperlink for content watch
     */
    public String getHyperlinkForContentWatch() 
    {
        return hyperlinkForContentWatch;
    }
    
    /**
     * @return tv episode instance short content summary
     */
    public String getShortContentSummary() 
    {
        return shortContentSummary;
    }
    
    /**
     * @return tv episode instance order in context of parent TV season
     */
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
