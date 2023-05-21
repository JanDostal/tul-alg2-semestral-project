
package app.models.output;

/**
 *
 * @author Admin
 */
public class TVSeasonOutput 
{
    public static final int ATTRIBUTE_ID_BYTES = Integer.BYTES;
    public static final int ATTRIBUTE_ORDERTVSHOW_BYTES = Integer.BYTES;
    public static final int ATTRIBUTE_TVSHOWID_BYTES = Integer.BYTES;
    
    public static final int TV_SEASON_RECORD_SIZE = ATTRIBUTE_ID_BYTES 
            + ATTRIBUTE_ORDERTVSHOW_BYTES + ATTRIBUTE_TVSHOWID_BYTES;
    
    private int id;
    
    private int orderInTVShow;
    
    private int tvShowId;
    
    public TVSeasonOutput(int id, int orderInTVShow, int tvShowId) 
    {
        this.id = id;
        this.orderInTVShow = orderInTVShow;
        this.tvShowId = tvShowId;
    }
            
    public int getId() 
    {
        return id;
    }
    
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    public int getTVShowId() 
    {
        return tvShowId;
    }
    
    public @Override String toString() 
    {
        return "TVSeasonOutput{id=" + id + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowId=" + tvShowId + "}";    
    }
}
