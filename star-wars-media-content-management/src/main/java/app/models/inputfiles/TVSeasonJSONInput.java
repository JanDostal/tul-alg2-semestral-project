
package app.models.inputfiles;

/**
 *
 * @author jan.dostal
 */
public class TVSeasonJSONInput 
{    
    private int orderInTVShow;
            
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }

    public void setOrderInTVShow(int orderInTVShow) 
    {
        this.orderInTVShow = orderInTVShow;
    }
    
    public @Override String toString() 
    {
        return "TVSeasonJSONInput{orderInTVShow=" + orderInTVShow + "}";
    }
}
