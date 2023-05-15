
package app.models.output;

import java.time.Duration;
import java.time.LocalDate;

/**
 *
 * @author jan.dostal
 */
public class MovieJSONOutputFormat extends MediaContentJSONOutputFormat
{
    private String releaseDate;
    
    private String era;
    
    public MovieJSONOutputFormat(int id, String runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, String releaseDate, String era) 
    {
        super(id, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.releaseDate = releaseDate;
        this.era = era;
    }
    
    public LocalDate getReleaseDate() 
    {
        return releaseDate;
    }
    
    public Era getEra() 
    {
        return era;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", releaseDate=" + releaseDate.toString() + ", era=" + era.toString();
    }
}
