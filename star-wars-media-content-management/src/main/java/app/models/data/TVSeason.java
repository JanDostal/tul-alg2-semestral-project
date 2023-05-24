
package app.models.data;

/**
 *
 * @author jan.dostal
 */
public class TVSeason extends DatabaseRecord
{    
    private int orderInTVShow;
    
    private int tvShowId;
        
    public TVSeason(int id, int orderInTVShow, int tvShowId) 
    {
        super(id);
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
        return "TVSeason{id=" + getId() + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowId=" + tvShowId + "}";
    }
}
