
package app.models.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author jan.dostal
 */
public class TVShow extends DatabaseRecord
{    
    private String name;
    
    private LocalDate releaseDate;
    
    private Era era;
        
    public TVShow(PrimaryKey primaryKey, String name, LocalDate releaseDate, Era era) 
    {
        super(primaryKey);
        this.name = name;
        this.releaseDate = releaseDate;
        this.era = era;
    }
    
    public String getName() 
    {
        return name;
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
        return Objects.hash(super.hashCode(), name, releaseDate);
    }

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
        
        return "TVShow{primaryKey=" + getPrimaryKey() + ", name=" + 
                name + ", releaseDate=" + releaseDateText + ", era=" + eraText + "}";
    }
}
