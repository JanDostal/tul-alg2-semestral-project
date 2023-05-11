
package app.models.data;

import java.time.Duration;

/**
 *
 * @author jan.dostal
 */
public class TVEpisode extends MediaContent
{
    private int orderInTvShowSeason;
    
    private int seasonId;
    
    public TVEpisode(int id, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTvShowSeason, int seasonId) 
    {
        super(id, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.orderInTvShowSeason = orderInTvShowSeason;
        this.seasonId = seasonId;
    }

    public int getOrderInTvShowSeason() 
    {
        return orderInTvShowSeason;
    }
    
    public int getSeasonId() 
    {
        return seasonId;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", orderInTvShowSeason=" + orderInTvShowSeason + ", seasonId=" +
                seasonId;
    }
}
