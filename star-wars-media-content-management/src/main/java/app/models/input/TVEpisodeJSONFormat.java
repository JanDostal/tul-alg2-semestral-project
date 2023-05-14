
package app.models.input;


/**
 *
 * @author jan.dostal
 */
public class TVEpisodeJSONFormat extends MediaContentJSONFormat
{
    private int orderInTVShowSeason;
        
    public TVEpisodeJSONFormat(String runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason) 
    {
        super(runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
                shortContentSummary);
        this.orderInTVShowSeason = orderInTVShowSeason;
    }

    public int getOrderInTVShowSeason() 
    {
        return orderInTVShowSeason;
    }

    protected @Override String getSubclassInstanceDataAttributesValues() 
    {
        return ", orderInTVShowSeason=" + orderInTVShowSeason;
    }
}
