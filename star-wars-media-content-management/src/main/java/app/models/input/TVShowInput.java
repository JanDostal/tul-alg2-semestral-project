
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVShowInput 
{
    private final String name;
    
    private final long releaseDateInEpochSeconds;
    
    private final String eraCodeDesignation;
    
    public TVShowInput(String name, long releaseDateInEpochSeconds, String eraCodeDesignation) 
    {
        this.name = name;
        this.eraCodeDesignation = eraCodeDesignation;
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
    }
        
    public String getName() 
    {
        return name;
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
        return String.format("TVShowInput{name=%s, releaseDateInEpochSeconds=%d, "
                + "eraCodeDesignation=%s}", name, releaseDateInEpochSeconds, eraCodeDesignation);
    }
}
