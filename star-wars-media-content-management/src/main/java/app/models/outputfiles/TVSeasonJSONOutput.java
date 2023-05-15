
package app.models.outputfiles;

/**
 *
 * @author Admin
 */
public class TVSeasonJSONOutput 
{
    private int id;
    
    private int orderInTVShow;
    
    private int tvShowId;
            
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

    public void setId(int id) 
    {
        this.id = id;
    }

    public void setOrderInTVShow(int orderInTVShow) 
    {
        this.orderInTVShow = orderInTVShow;
    }

    public void setTvShowId(int tvShowId) 
    {
        this.tvShowId = tvShowId;
    }
    
    public @Override String toString() 
    {
        return "TVSeasonJSONOutput{id=" + id + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowId=" + tvShowId + "}";
    }
}
