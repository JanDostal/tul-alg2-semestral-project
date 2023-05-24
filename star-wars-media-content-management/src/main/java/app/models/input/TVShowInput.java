
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVShowInput 
{
    private String name;
    
    private long releaseDateInEpochSeconds;
    
    private String era;
    
    public TVShowInput(String name, long releaseDateInEpochSeconds, String era) 
    {
        this.name = name;
        this.era = era;
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

    public String getEra() 
    {
        return era;
    }
    
    public @Override String toString() 
    {
        return "TVShowInput{name=" + name + ", releaseDateInEpochSeconds=" + 
                releaseDateInEpochSeconds + ", era=" + era + "}";
    }
}
