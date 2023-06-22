package app.models.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a data model class for tv show.
 * TVShow class has access to primary key attribute from DatabaseRecord.
 * TVShow class can test for duplicity or equality between two tv show data instances.
 * @author jan.dostal
 */
public class TVShow extends DatabaseRecord
{    
    private final String name;
    
    private final LocalDate releaseDate;
    
    private final Era era;
    
    /**
     * Creates a new instance representing TV show data model
     * @param primaryKey represents instance of primary key as identificator in database 
     * (calling constructor from DatabaseRecord).
     * @param name represents tv show name (not null)
     * @param releaseDate represents tv show release date (can be null)
     * (release date of first TV episode/episodes).
     * @param era represents tv show chosen chronological star wars era (not null)
     */
    public TVShow(PrimaryKey primaryKey, String name, LocalDate releaseDate, Era era) 
    {
        super(primaryKey);
        this.name = name;
        this.releaseDate = releaseDate;
        this.era = era;
    }
    
    /**
     * @return tv show instance name
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * @return tv show instance release date (release of first TV episode/episodes)
     */
    public LocalDate getReleaseDate() 
    {
        return releaseDate;
    }
    
    /**
     * @return tv show instance chosen Star Wars chronological era
     */
    public Era getEra() 
    {
        return era;
    }
    
    /**
     * Method creates unique hash code from tv show instance attributes
     * (method calls hashCode method from DatabaseRecord).
     * @return int value representing unique hash code
     */
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode(), name, releaseDate);
    }
    
    /**
     * Method tests this tv show instance with second instance for equality
     * (custom data duplicity testing, method calls equals method from DatabaseRecord).
     * @param obj anonymous object for comparison
     * @return logical value stating if this instance equals second instance
     */
    public @Override boolean equals(Object obj) 
    {
        if (super.equals(obj) == false) 
        {
            return false;
        }
                
        final TVShow other = (TVShow) obj;
        
        if (Objects.equals(name, other.name) == false ||
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
        
        return super.toString() + String.format(", name=%s, releaseDate=%s, era=%s}", 
                name, releaseDateText, eraText);
    }
}
