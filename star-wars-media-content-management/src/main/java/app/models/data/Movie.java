
package app.models.data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jan.dostal
 */
public class Movie extends MediaContent
{
    private LocalDate releaseDate;
    
    private Era era;
    
    public Movie(PrimaryKey primaryKey, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, LocalDate releaseDate, Era era) 
    {
        super(primaryKey, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
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
        return ", releaseDate=" + releaseDate.format(DateTimeFormatter.ISO_LOCAL_DATE) 
                + ", era=" + era.toString();
    }
}
