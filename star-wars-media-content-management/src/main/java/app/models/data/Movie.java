package app.models.data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a data model class for movie
 * Movie class inherits from MediaContent class, so it can be grouped with other classes which inherit from MediaContent
 * Also movies class has access to primary key attribute from MediaContent
 * Movie class can test for duplicity or equality between two movies data instances  
 * @author jan.dostal
 */
public class Movie extends MediaContent
{
    private final LocalDate releaseDate;
    
    private final Era era;
    
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
    
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode(), getHyperlinkForContentWatch(), 
                getShortContentSummary(), getName(), releaseDate);
    }

    public @Override boolean equals(Object obj) 
    {
        if (super.equals(obj) == false) 
        {
            return false;
        }
        
        final Movie other = (Movie) obj;
        
        if (getHyperlinkForContentWatch() != null && 
                Objects.equals(getHyperlinkForContentWatch(), other.getHyperlinkForContentWatch()) == true) 
        {
            return true;
        }
        
        if (getShortContentSummary() != null && Objects.equals(getShortContentSummary(), 
                other.getShortContentSummary()) == true) 
        {
            return true;
        }
        
        if (Objects.equals(getName(), other.getName()) == false ||
                Objects.equals(releaseDate, other.releaseDate) == false) 
        {
            return false;
        }
        
        return true;
    }
    
    public @Override String toString() 
    {
        String releaseDateText = releaseDate == null ? null : 
                releaseDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String eraText = era == null ? null : era.toString();
        
        return super.toString() + 
                String.format(", releaseDate=%s, era=%s}", releaseDateText, eraText);
    }
}
