
package app.models.input;

/**
 * Represents a input data model class for tv season.
 * TVSeasonInput class is used when parsing tv season input data files.
 * @author jan.dostal
 */
public class TVSeasonInput 
{    
    private final int orderInTVShow;
    
    /**
     * Creates a new instance representing TV season input data model
     * @param orderInTVShow represents TV season order in context of parent TV show (should be 1 or greater)
     */
    public TVSeasonInput(int orderInTVShow) 
    {
        this.orderInTVShow = orderInTVShow;
    }
    
    /**
     * @return tv season instance order in context of parent TV season
     */
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    public @Override String toString() 
    {
        return String.format("TVSeasonInput{orderInTVShow=%d}", orderInTVShow);
    }
}
