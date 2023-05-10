
package app.models.data;

import java.time.Duration;
import java.time.LocalDate;

/**
 *
 * @author jan.dostal
 */
public class Movie extends StarWarsMediaContent
{
    private LocalDate releaseDate;
    
    public Movie(int id, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, LocalDate releaseDate) 
    {
        super(id, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.releaseDate = releaseDate  ;
    }
    
    public LocalDate getReleaseDate() 
    {
        return releaseDate;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", releaseDate=" + releaseDate;
    }
}
