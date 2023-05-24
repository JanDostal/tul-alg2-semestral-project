
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
        
    public TVShow(int id, String name, LocalDate releaseDate, Era era) 
    {
        super(id);
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
        return "TVShow{id=" + getId() + ", name=" + name + ", releaseDate=" + 
                releaseDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + ", era=" + era.toString() + "}";
    }
}
