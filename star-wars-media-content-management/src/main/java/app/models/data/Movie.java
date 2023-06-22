package app.models.data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a data model class for movie.
 * Movie class inherits from MediaContent class, so it can be grouped with other 
 * classes which inherit from MediaContent.
 * Also movies class has access to primary key attribute from MediaContent.
 * Movie class can test for duplicity or equality between two movies data instances. 
 * @author jan.dostal
 */
public class Movie extends MediaContent
{
    private final LocalDate releaseDate;
    
    private final Era era;
    
    /**
     * Creates a new instance representing Movie data model
     * @param primaryKey represents instance of primary key as identificator in database 
     * (calling constructor from MediaContent).
     * @param runtime represents movie runtime/duration (calling constructor from MediaContent) (can be null)
     * @param name represents movie name (calling constructor from MediaContent) (not null)
     * @param percentageRating represents movie percentage rating (-infinity, 100) (calling constructor from MediaContent)
     * @param wasWatched represents status of movie, if it is watched/unwatched (calling constructor from MediaContent)
     * @param hyperlinkForContentWatch represents URL hyperlink to movie for watching (calling constructor from MediaContent) (can be null)
     * @param shortContentSummary represents movie short summary of its content (calling constructor from MediaContent) (can be null)
     * @param releaseDate represents movie release date (can be null)
     * @param era represents movie chosen chronological star wars era (not null)
     */
    public Movie(PrimaryKey primaryKey, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, LocalDate releaseDate, Era era) 
    {
        super(primaryKey, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.releaseDate = releaseDate;
        this.era = era;
    }
    
    /**
     * @return movie instance release date
     */
    public LocalDate getReleaseDate() 
    {
        return releaseDate;
    }
    
    /**
     * @return movie instance star wars chronological era
     */
    public Era getEra() 
    {
        return era;
    }
    
    /**
     * Method creates unique hash code from movie instance attributes
     * (method calls hashCode method from MediaContent).
     * @return int value representing unique hash code
     */
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode(), getHyperlinkForContentWatch(), 
                getShortContentSummary(), getName(), releaseDate);
    }
    
    
    /**
     * Method tests this movie instance with second instance for equality
     * (custom data duplicity testing, method calls equals method from MediaContent).
     * @param obj anonymous object for comparison
     * @return logical value stating if this instance equals second instance
     */
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
