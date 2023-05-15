
package app.models.output;

/**
 *
 * @author jan.dostal
 */
public class TVEpisodeJSONOutputFormat extends MediaContentJSONOutputFormat
{
    private int orderInTVShowSeason;
    
    private int tvSeasonId;
    
    public TVEpisodeJSONOutputFormat(int id, String runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason, int tvSeasonId) 
    {
        super(id, runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.orderInTVShowSeason = orderInTVShowSeason;
        this.tvSeasonId = tvSeasonId;
    }

    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
    }
    
    public int getTVSeasonId() 
    {
        return tvSeasonId;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", orderInTVShowSeason=" + orderInTVShowSeason + ", tvSeasonId=" +
                tvSeasonId;
    }
}
