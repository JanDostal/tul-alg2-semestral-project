
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVShowInput 
{
    private String name;
    
    private Long releaseDateInEpochSeconds;
    
    private String era;
    
    public TVShowInput(String name, Long releaseDateInEpochSeconds, String era) 
    {
        this.name = name;
        this.era = era;
        this.releaseDateInEpochSeconds = releaseDateInEpochSeconds;
    }
        
    public String getName() 
    {
        return name;
    }

    public Long getReleaseDateInEpochSeconds() 
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
