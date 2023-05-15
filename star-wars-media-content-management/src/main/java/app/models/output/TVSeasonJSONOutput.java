
package app.models.output;

/**
 *
 * @author Admin
 */
public class TVSeasonJSONOutput 
{
    private int id;
    
    private int orderInTVShow;
    
    private int tvShowId;
        
    public TVSeasonJSONOutput(int id, int orderInTVShow, int tvShowId) 
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
        return "TVSeasonJSONOutput{id=" + id + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowId=" + tvShowId + "}";
    }
}
