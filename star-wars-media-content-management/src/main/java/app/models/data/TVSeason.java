
package app.models.data;

/**
 *
 * @author jan.dostal
 */
public class TVSeason extends DatabaseRecord
{    
    private int orderInTVShow;
    
    private PrimaryKey tvShowForeignKey;
        
    public TVSeason(PrimaryKey primaryKey, int orderInTVShow, PrimaryKey tvShowForeignKey) 
    {
        super(primaryKey);
        this.orderInTVShow = orderInTVShow;
        this.tvShowForeignKey = tvShowForeignKey;
    }
        
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    public PrimaryKey getTVShowForeignKey() 
    {
        return tvShowForeignKey;
    }
    
    public @Override String toString() 
    {
        return "TVSeason{primaryKey=" + getPrimaryKey() + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowForeignKey=" + tvShowForeignKey + "}";
    }
}
