
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVSeasonJSONFormat 
{    
    private int orderInTVShow;
    
    private int tvShowId;
        
    public TVSeasonJSONFormat(int orderInTVShow, int tvShowId) 
    {
        this.orderInTVShow = orderInTVShow;
        this.tvShowId = tvShowId;
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
        return "TVSeasonJSONFormat{orderInTVShow=" + orderInTVShow + 
                ", tvShowId=" + tvShowId + "}";
    }
}
