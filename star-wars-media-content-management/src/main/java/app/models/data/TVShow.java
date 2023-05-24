
package app.models.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    public @Override String toString() 
    {
        return "TVShow{primaryKey=" + getPrimaryKey() + ", name=" + name + ", releaseDate=" + 
                releaseDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + ", era=" + era.toString() + "}";
    }
}
