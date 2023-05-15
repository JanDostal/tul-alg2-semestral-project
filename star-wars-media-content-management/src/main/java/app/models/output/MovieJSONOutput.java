
package app.models.output;

import java.time.Duration;
import java.time.LocalDate;

/**
 *
 * @author jan.dostal
 */
public class MovieJSONOutput extends MediaContentJSONOutput
{
    private String releaseDate;
    
    private String era;
    
    public MovieJSONOutput(int id, String runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, String releaseDate, String era) 
    {
        super(id, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.releaseDate = releaseDate;
        this.era = era;
    }
    
    public String getReleaseDate() 
    {
        return releaseDate;
    }
    
    public String getEra() 
    {
        return era;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", releaseDate=" + releaseDate + ", era=" + era;
    }
}
