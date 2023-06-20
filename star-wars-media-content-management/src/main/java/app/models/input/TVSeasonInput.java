
package app.models.input;

/**
 * Represents a input data model class for tv season
 * TVSeasonInput class is used when parsing tv season input data files
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
