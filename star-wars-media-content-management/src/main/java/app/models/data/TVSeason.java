package app.models.data;

import java.util.Objects;

/**
 * Represents a data model class for tv season.
 * TVSeason class has access to primary key attribute from DatabaseRecord.
 * TVSeason class can test for duplicity or equality between two tv season data instances. 
 * @author jan.dostal
 */
public class TVSeason extends DatabaseRecord
{    
    private final int orderInTVShow;
    
    private final PrimaryKey tvShowForeignKey;
    
    /**
     * Creates a new instance representing TV season data model
     * @param primaryKey represents instance of primary key as identificator in database 
     * (calling constructor from DatabaseRecord).
     * @param tvShowForeignKey represents tv season parent TV show primary key
     * @param orderInTVShow represents tv season order in context of parent TV show (greater or equal to 1)
     */
    public TVSeason(PrimaryKey primaryKey, int orderInTVShow, PrimaryKey tvShowForeignKey) 
    {
        super(primaryKey);
        this.orderInTVShow = orderInTVShow;
        this.tvShowForeignKey = tvShowForeignKey;
    }
    
    /**
     * @return tv season instance order in context of parent TV show
     */
    public int getOrderInTVShow() 
    {
        return orderInTVShow;
    }
    
    /**
     * @return tv season instance parent TV show primary key
     */
    public PrimaryKey getTVShowForeignKey() 
    {
        return tvShowForeignKey;
    }
    
    /**
     * Method creates unique hash code from tv season instance attributes
     * (method calls hashCode method from DatabaseRecord).
     * @return int value representing unique hash code
     */
    public @Override int hashCode() 
    {                
        return Objects.hash(super.hashCode(), orderInTVShow, tvShowForeignKey);
    }
    
    
    /**
     * Method tests this tv season instance with second instance for equality
     * (custom data duplicity testing, method calls equals method from DatabaseRecord).
     * @param obj anonymous object for comparison
     * @return logical value stating if this instance equals second instance
     */
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
