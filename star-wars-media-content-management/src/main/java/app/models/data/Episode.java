
package app.models.data;

import java.time.Duration;

/**
 *
 * @author jan.dostal
 */
public class Episode extends MediaContent
{
    private int orderInTvShowSeason;
    
    public Episode(int id, Duration runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTvShowSeason) 
    {
        super(id, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.orderInTvShowSeason = orderInTvShowSeason;
    }

    public int getOrderInTvShowSeason() 
    {
        return orderInTvShowSeason;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", orderInTvShowSeason=" + orderInTvShowSeason;
    }
}
