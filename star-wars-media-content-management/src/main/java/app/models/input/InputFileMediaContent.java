
package app.models.input;

import java.time.Duration;

/**
 *
 * @author jan.dostal
 */
public abstract class InputFileMediaContent 
{    
    private String runtime;
    
    private String name;
    
    private int percentageRating;
    
    private boolean wasWatched;
    
    private String hyperlinkForContentWatch;
    
    private String shortContentSummary;
    
    protected InputFileMediaContent(String runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary) 
    {
        this.runtime = runtime;
        this.name = name;
        this.percentageRating = percentageRating;
        this.wasWatched = wasWatched;
        this.hyperlinkForContentWatch = hyperlinkForContentWatch;
        this.shortContentSummary = shortContentSummary;
    }
    
    public String getRuntime() 
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
        return getSubclassInstanceTypeName() + "{runtime=" + 
                runtime + ", name=" + name + ", percentageRating=" +
                percentageRating + ", wasWatched=" + wasWatched +
                ", hyperlinkForContentWatch=" + hyperlinkForContentWatch + 
                getSubclassInstanceDataAttributesValues() + "}";
    }
}
