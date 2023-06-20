package app.models.data;

import java.time.Duration;
import java.util.Objects;

/**
 * Represents a data model class for tv episode
 * TVEpisode class inherits from MediaContent class, so it can be grouped with other classes which inherit from MediaContent
 * Also TVEpisode class has access to primary key attribute from MediaContent
 * TVEpisode class can test for duplicity or equality between two tv episodes data instances  
 * @author jan.dostal
 */
public class TVEpisode extends MediaContent
{
    private final int orderInTVShowSeason;
    
    private final PrimaryKey tvSeasonForeignKey;
    
    public TVEpisode(PrimaryKey primaryKey, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason, PrimaryKey tvSeasonForeignKey) 
    {
        super(primaryKey, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.orderInTVShowSeason = orderInTVShowSeason;
        this.tvSeasonForeignKey = tvSeasonForeignKey;
    }

    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
    }
    
    public PrimaryKey getTVSeasonForeignKey() 
    {
        return tvSeasonForeignKey;
    }
    
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode(), getHyperlinkForContentWatch(), 
                getShortContentSummary(), orderInTVShowSeason, tvSeasonForeignKey);
    }

    public @Override boolean equals(Object obj) 
    {
        if (super.equals(obj) == false) 
        {
            return false;
        }
        
        final TVEpisode other = (TVEpisode) obj;
        
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
        
        if (orderInTVShowSeason != other.orderInTVShowSeason || 
                Objects.equals(tvSeasonForeignKey, other.tvSeasonForeignKey) == false) 
        {
            return false;
        }
        
        return true;
    }
    
    public @Override String toString() 
    {
        return super.toString() + 
                String.format(", orderInTVShowSeason=%d, tvSeasonForeignKey=%s}", 
                        orderInTVShowSeason, tvSeasonForeignKey);
    }
}
