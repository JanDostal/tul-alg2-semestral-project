package app.models.data;

import java.util.Objects;

/**
 * Represents a data model class for tv season
 * TVSeason class has access to primary key attribute from DatabaseRecord
 * TVSeason class can test for duplicity or equality between two tv season data instances  
 * @author jan.dostal
 */
public class TVSeason extends DatabaseRecord
{    
    private final int orderInTVShow;
    
    private final PrimaryKey tvShowForeignKey;
        
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
        return super.toString() + String.format(", orderInTVShow=%d, tvShowForeignKey=%s}", 
                orderInTVShow, tvShowForeignKey);
    }
}
