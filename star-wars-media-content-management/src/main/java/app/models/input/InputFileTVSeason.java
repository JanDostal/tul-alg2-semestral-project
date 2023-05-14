
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class InputFileTVSeason 
{    
    private int orderInTVShow;
    
    private int tvShowId;
        
    public InputFileTVSeason(int orderInTVShow, int tvShowId) 
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
        return "InputFileTVSeason{orderInTVShow=" + orderInTVShow + 
                ", tvShowId=" + tvShowId + "}";
    }
}
