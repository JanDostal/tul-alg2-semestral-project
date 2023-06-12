
package app.models.input;

/**
 *
 * @author jan.dostal
 */
public class TVSeasonInput 
{    
    private final int orderInTVShow;
    
    public TVSeasonInput(int orderInTVShow) 
    {
        this.orderInTVShow = orderInTVShow;
    }
            
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    public @Override String toString() 
    {
        return String.format("TVSeasonInput{orderInTVShow=%d}", orderInTVShow);
    }
}
