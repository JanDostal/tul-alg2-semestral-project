
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVSeasonJSONInput 
{    
    private int orderInTVShow;
        
    public TVSeasonJSONInput(int orderInTVShow) 
    {
        this.orderInTVShow = orderInTVShow;
    }
    
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    public @Override String toString() 
    {
        return "TVSeasonJSONInput{orderInTVShow=" + orderInTVShow + "}";
    }
}
