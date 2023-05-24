
package app.models.data;

import java.time.Duration;

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

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", orderInTVShowSeason=" + orderInTVShowSeason + ", tvSeasonForeignKey=" +
                tvSeasonForeignKey;
    }
}
