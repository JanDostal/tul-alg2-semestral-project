package app.models.input;

/**
 * Represents a input data model class for movie.
 * MovieInput class is used when parsing movies input data files.
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
    
    /**
     * Creates a new instance representing movie input data model
     * @param runtimeInSeconds represents movie runtime/duration in seconds (-infinity, positive number)
     * @param name represents movie name
     * @param percentageRating represents movie percentage rating (-infinity, 100)
     * @param hyperlinkForContentWatch represents URL hyperlink to movie for watching
     * @param shortContentSummary represents movie short summary of its content
     * @param releaseDateInEpochSeconds represents movie release date in epoch seconds (GMT/UTC) (-infinity, positive number)
     * @param eraCodeDesignation represents movie chosen chronological star wars era code designation
     */
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
    
    /**
     * @return movie instance runtime in seconds
     */
    public long getRuntimeInSeconds() 
    {
        return runtimeInSeconds;
    }
    
    /**
     * @return movie instance name
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * @return movie instance percentage rating
     */
    public int getPercentageRating() 
    {
        return percentageRating;
    }
    
    /**
     * @return movie instance URL hyperlink for content watch
     */
    public String getHyperlinkForContentWatch() 
    {
        return hyperlinkForContentWatch;
    }
    
    /**
     * @return movie instance short content summary
     */
    public String getShortContentSummary() 
    {
        return shortContentSummary;
    }
    
    /**
     * @return movie instance release date in epoch seconds (UTC/GMT)
     */
    public long getReleaseDateInEpochSeconds() 
    {
        return releaseDateInEpochSeconds;
    }
    
    /**
     * @return movie instance chosen star wars era code designation
     */
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
