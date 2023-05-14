
package app.models.input;


/**
 *
 * @author jan.dostal
 */
public class InputFileTVEpisode extends InputFileMediaContent
{
    private int orderInTVShowSeason;
    
    private int tvSeasonId;
    
    public InputFileTVEpisode(String runtime, String name,
            int percentageRating, boolean wasWatched, String hyperlinkForContentWatch,
            String shortContentSummary, int orderInTVShowSeason, int tvSeasonId) 
    {
        super(runtime, name, percentageRating, wasWatched, hyperlinkForContentWatch,
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
