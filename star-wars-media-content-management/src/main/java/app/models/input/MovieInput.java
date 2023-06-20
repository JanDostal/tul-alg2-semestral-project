package app.models.input;

/**
 * Represents a input data model class for movie
 * MovieInput class is used when parsing movies input data files
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
    
    private final String eraCodeDesignation;
        
    public MovieInput(long runtimeInSeconds, String name, 
            int percentageRating, String hyperlinkForContentWatch,
            String shortContentSummary, long releaseDateInEpochSeconds, String eraCodeDesignation) 
    {
        this.runtimeInSeconds = runtimeInSeconds;
        this.name = name;
        this.percentageRating = percentageRating;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch;
        this.shortContentSummary = shortContentSummary;
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
        this.eraCodeDesignation = eraCodeDesignation;
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
    
    public String getEraCodeDesignation() 
    {
        return eraCodeDesignation;
    }
    
    public @Override String toString() 
    {
        return String.format("MovieInput{runtimeInSeconds=%d, name=%s, "
                + "percentageRating=%d, hyperlinkForContentWatch=%s, "
                + "releaseDateInEpochSeconds=%d, eraCodeDesignation=%s}", runtimeInSeconds, name,
                percentageRating, hyperlinkForContentWatch,
                releaseDateInEpochSeconds, eraCodeDesignation);
    }
}
