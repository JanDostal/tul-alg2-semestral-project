
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class MovieInput
{    
    private final long runtimeInSeconds;
    
    private final String name;
    
    private final int percentageRating;
    
    private final String hyperlinkForContentWatch;
    
    private final String shortContentSummary;
    
    private final long releaseDateInEpochSeconds;
    
    private final String era;
        
    public MovieInput(long runtimeInSeconds, String name, 
            int percentageRating, String hyperlinkForContentWatch,
            String shortContentSummary, long releaseDateInEpochSeconds, String era) 
    {
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name;
        this.percentageRating = percentageRating;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch;
        this.shortContentSummary = shortContentSummary;
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
        this.era = era;
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
    
    public long getReleaseDateInEpochSeconds() 
    {
        return releaseDateInEpochSeconds;
    }
    
    public String getEra() 
    {
        return era;
    }
    
    public @Override String toString() 
    {
        return String.format("MovieInput{runtimeInSeconds=%d, name=%s, "
                + "percentageRating=%d, hyperlinkForContentWatch=%s, "
                + "releaseDateInEpochSeconds=%d, era=%s}", runtimeInSeconds, name,
                percentageRating, hyperlinkForContentWatch,
                releaseDateInEpochSeconds, era);
    }
}
