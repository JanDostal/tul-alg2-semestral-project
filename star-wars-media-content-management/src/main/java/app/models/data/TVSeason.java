
package app.models.data;

import java.util.Objects;

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
    
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode(), orderInTVShow, tvShowForeignKey);
    }

    public @Override boolean equals(Object obj) 
    {
        if (super.equals(obj) == false) 
        {
            return false;
        }
                
        final TVSeason other = (TVSeason) obj;
        
        if (orderInTVShow != other.orderInTVShow || 
                Objects.equals(tvShowForeignKey, other.tvShowForeignKey) == false) 
        {
            return false;
        }
        
        return true;
    }
    
    public @Override String toString() 
    {
        return "TVSeason{primaryKey=" + getPrimaryKey() + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowForeignKey=" + tvShowForeignKey + "}";
    }
}
