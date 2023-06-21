package app.models.data;

import java.time.Duration;
import java.util.Objects;

/**
 * Represents a data model class for tv episode.
 * TVEpisode class inherits from MediaContent class, so it can be grouped with other classes which inherit from MediaContent.
 * Also TVEpisode class has access to primary key attribute from MediaContent.
 * TVEpisode class can test for duplicity or equality between two tv episodes data instances.
 * @author jan.dostal
 */
public class TVEpisode extends MediaContent
{
    private final int orderInTVShowSeason;
    
    private final PrimaryKey tvSeasonForeignKey;
    
    /**
     * Creates a new instance representing TV episode data model
     * @param primaryKey represents instance of primary key as identificator in database 
     * (calling constructor from MediaContent).
     * @param runtime represents TV episode runtime/duration (calling constructor from MediaContent) (not null)
     * @param name represents TV episode name (calling constructor from MediaContent) (can be null)
     * @param percentageRating represents TV episode percentage rating (-infinity, 100) (calling constructor from MediaContent)
     * @param wasWatched represents status of TV episode, if it is watched/unwatched (calling constructor from MediaContent)
     * @param hyperlinkForContentWatch represents URL hyperlink to TV episode for watching (calling constructor from MediaContent) (can be null)
     * @param shortContentSummary represents TV episode short summary of its content (calling constructor from MediaContent) (can be null)
     * @param orderInTVShowSeason represents TV episode order in context of parent TV season (greater or equal to 1)
     * @param tvSeasonForeignKey represents parent TV season primary key
     */
    public TVEpisode(PrimaryKey primaryKey, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason, PrimaryKey tvSeasonForeignKey) 
    {
        super(primaryKey, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.orderInTVShowSeason = orderInTVShowSeason;
        this.tvSeasonForeignKey = tvSeasonForeignKey;
    }
    
    /**
     * @return tv episode instance order in context of parent TV season
     */
    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
    }
    
    /**
     * @return tv episode instance parent tv season primary key
     */
    public PrimaryKey getTVSeasonForeignKey() 
    {
        return tvSeasonForeignKey;
    }
    
    /**
     * Method creates unique hash code from tv episode instance attributes
     * (method calls hashCode method from MediaContent).
     * @return int value representing unique hash code
     */
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode(), getHyperlinkForContentWatch(), 
                getShortContentSummary(), orderInTVShowSeason, tvSeasonForeignKey);
    }
    
     /**
     * Method tests this tv episode instance with second instance for equality
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
