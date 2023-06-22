
package app.models.input;

/**
 * Represents a input data model class for tv show.
 * TVShowInput class is used when parsing tv show input data files.
 * @author jan.dostal
 */
public class TVShowInput 
{
    private final String name;
    
    private final long releaseDateInEpochSeconds;
    
    private final String eraCodeDesignation;
    
    /**
     * Creates a new instance representing TV show input data model
     * @param name represents tv show name
     * @param releaseDateInEpochSeconds represents tv show release date in epoch seconds (UTC/GMT) (-infinity, positive number)
     * @param eraCodeDesignation represents tv show era code designation
     */
    public TVShowInput(String name, long releaseDateInEpochSeconds, String eraCodeDesignation) 
    {
        this.name = name;
        this.eraCodeDesignation = eraCodeDesignation;
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
    }
    
    /**
     * @return tv show instance name
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * @return tv show instance release date in epoch seconds (UTC/GMT)
     */
    public long getReleaseDateInEpochSeconds() 
    {
        return releaseDateInEpochSeconds;
    }
    
    /**
     * @return tv show instance era code designation
     */
    public String getEraCodeDesignation() 
    {
        return eraCodeDesignation;
    }
    
    public @Override String toString() 
    {
        return String.format("TVShowInput{name=%s, releaseDateInEpochSeconds=%d, "
                + "eraCodeDesignation=%s}", name, releaseDateInEpochSeconds, eraCodeDesignation);
    }
}
