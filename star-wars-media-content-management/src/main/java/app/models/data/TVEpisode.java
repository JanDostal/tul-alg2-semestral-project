
package app.models.data;

import java.time.Duration;
import java.util.Objects;

/**
 *
 * @author jan.dostal
 */
public class TVEpisode extends MediaContent
{
    private int orderInTVShowSeason;
    
    private PrimaryKey tvSeasonForeignKey;
    
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

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", orderInTVShowSeason=" + orderInTVShowSeason + ", tvSeasonForeignKey=" +
                tvSeasonForeignKey;
    }
}
