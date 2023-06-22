package app.models.data;

import java.time.Duration;
import java.util.Objects;

/**
 * Represents an abstract class for grouping data, which represents direct media content (movies, TV episodes).
 * MediaContent class is specified as database record, so it has primary key attribute.
 * @author jan.dostal
 */
public abstract class MediaContent extends DatabaseRecord
{    
    private final Duration runtime;
    
    private final String name;
    
    private final int percentageRating;
    
    private final boolean wasWatched;
    
    private final String hyperlinkForContentWatch;
    
    private final String shortContentSummary;
    
    /**
     * Creates a new instance representing Media Content (using constructor in subclass)
     * @param primaryKey represents instance of primary key as identificator in database 
     * (calling constructor from DatabaseRecord).
     * @param runtime represents media content runtime/duration
     * @param name represents media content name
     * @param percentageRating represents media content percentage rating
     * @param wasWatched represents status of media content, if it is watched/unwatched
     * @param hyperlinkForContentWatch represents URL hyperlink to media content for watching
     * @param shortContentSummary represents media content short summary of its content
     */
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
    
    /**
     * @return media content instance runtime
     */
    public Duration getRuntime() 
    {
        return runtime;
    }
    
    /**
     * @return media content instance name
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * @return media content instance percentage rating
     */
    public int getPercentageRating() 
    {
        return percentageRating;
    }
    
    /**
     * @return media content instance watched status (watched/unwatched)
     */
    public boolean getWasWatched() 
    {
        return wasWatched;
    }
    
    /**
     * @return media content instance URL hyperlink for watching media content
     */
    public String getHyperlinkForContentWatch() 
    {
        return hyperlinkForContentWatch;
    }
    
    /**
     * @return media content instance short content summary
     */
    public String getShortContentSummary() 
    {
        return shortContentSummary;
    }

    /**
     * Method calls hashCode method from DatabaseRecord
     * (using hashCode method in subclass).
     * @return int value representing unique hash code
     */
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode());
    }
    
    /**
     * Method calls equals method from DatabaseRecord (calling equals method from subclass)
     * @param obj anonymous object instance
     * @return status if instance is equal to second instance
     */
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
