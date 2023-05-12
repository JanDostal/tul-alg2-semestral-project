
package app.models.data;

/**
 *
 * @author jan.dostal
 */
public class TVSeasonB 
{
    private int id;
    
    private int orderInTVShow;
    
    private int tvShowId;
        
    public TVSeasonB(int id, int orderInTVShow, int tvShowId) 
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
        return "TVSeason{id=" + id + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowId=" + tvShowId + "}";
    }
}
