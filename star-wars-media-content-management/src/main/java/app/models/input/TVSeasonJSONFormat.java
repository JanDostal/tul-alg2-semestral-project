
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVSeasonJSONFormat 
{    
    private int orderInTVShow;
        
    public TVSeasonJSONFormat(int orderInTVShow) 
    {
        this.orderInTVShow = orderInTVShow;
    }
    
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    public @Override String toString() 
    {
        return "TVSeasonJSONFormat{orderInTVShow=" + orderInTVShow + "}";
    }
}
