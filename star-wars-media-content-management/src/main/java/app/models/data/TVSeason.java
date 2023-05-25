
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
        return Objects.hash(super.hashCode(), name, releaseDate, era);
    }

    public @Override boolean equals(Object obj) 
    {
        if (super.equals(obj) == false) 
        {
            return false;
        }
                
        final TVSeason other = (TVSeason) obj;
        
        if (orderInTVShow != other.orderInTVShow ||
                Objects.equals(releaseDate, other.releaseDate) == false) 
        {
            return false;
        }
        
        return Objects.equals(era, other.era);
    }
    
    public @Override String toString() 
    {
        return "TVSeason{primaryKey=" + getPrimaryKey() + ", orderInTVShow=" + orderInTVShow + 
                ", tvShowForeignKey=" + tvShowForeignKey + "}";
    }
}
