
package app.models.data;

import java.time.Duration;

/**
 *
 * @author jan.dostal
 */
public abstract class MediaContent 
{
    private int id;
    
    private Duration runtime;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    protected MediaContent(int id, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary) 
    {
        this.id = id;
        this.runtime = runtime;
        this.name = name;
        this.percentageRating = percentageRating;
        this.wasWatched = wasWatched;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch;
        this.shortContentSummary = shortContentSummary;
    }

    public int getId() 
    {
        return id;
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
    
    protected String getSubclassInstanceTypeName() 
    {
        return this.getClass().getSimpleName();
    }
    
    protected abstract String getSubclassInstanceDataAttributesValues();
    
    public @Override String toString() 
    {
        String runtimeText = String.format("%02d:%02d:%02d", runtime.toHours(), 
                runtime.toMinutesPart(), runtime.toSecondsPart());
        
        return getSubclassInstanceTypeName() + "{id=" + id + ", runtime=" + 
                runtimeText + ", name=" + name + ", percentageRating=" +
                percentageRating + ", wasWatched=" + wasWatched +
                ", hyperlinkForContentWatch=" + hyperlinkForContentWatch + 
                getSubclassInstanceDataAttributesValues() + "}";
    }
}
