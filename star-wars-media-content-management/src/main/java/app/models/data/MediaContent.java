
package app.models.data;

import java.time.Duration;
import java.util.Objects;

/**
 *
 * @author jan.dostal
 */
public abstract class MediaContent extends DatabaseRecord
{    
    private Duration runtime;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    protected MediaContent(PrimaryKey primaryKey, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary) 
    {
        super(primaryKey);
        this.runtime = runtime;
        this.name = name;
        this.percentageRating = percentageRating;
        this.wasWatched = wasWatched;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch;
        this.shortContentSummary = shortContentSummary;
    }
    
    public Duration getRuntime() 
    {
        return runtime;
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
            
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode());
    }

    public @Override boolean equals(Object obj) 
    {
        return super.equals(obj);
    }
    
    public @Override String toString() 
    {
        String runtimeText = runtime == null ? null : String.format("%02d:%02d:%02d", runtime.toHours(), 
                runtime.toMinutesPart(), runtime.toSecondsPart());
        
        return super.toString() + 
                String.format(", runtime=%s, name=%s, percentageRating=%d, wasWatched=%s, hyperlinkForContentWatch=%s", 
                        runtimeText, name, percentageRating, wasWatched, hyperlinkForContentWatch);
    }
}
