
package app.models.input;


/**
 *
 * @author jan.dostal
 */
public class TVEpisodeJSONInput extends MediaContentJSONInput
{
    private int orderInTVShowSeason;
        
    public TVEpisodeJSONInput(String runtime, String name,
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
