
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class MovieInput
{    
    private long runtimeInSeconds;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    private long releaseDateInEpochSeconds;
    
    private String era;
        
    public MovieInput(long runtimeInSeconds, String name, 
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, long releaseDateInEpochSeconds, String era) 
    {
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name;
        this.percentageRating = percentageRating;
        this.wasWatched = wasWatched;
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
                + "percentageRating=%d, wasWatched=%s, hyperlinkForContentWatch=%s, "
                + "releaseDateInEpochSeconds=%d, era=%s}", runtimeInSeconds, name,
                percentageRating, wasWatched, hyperlinkForContentWatch,
                releaseDateInEpochSeconds, era);
    }
}
