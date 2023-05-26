
package app.models.data;

import java.util.Objects;

/**
 *
 * @author Admin
 */
public abstract class DatabaseRecord implements Comparable<DatabaseRecord>
{
    private PrimaryKey primaryKey;
    
    protected DatabaseRecord(PrimaryKey primaryKey) 
    {
        this.primaryKey = primaryKey;
    }
    
    public PrimaryKey getPrimaryKey() 
    {
        return primaryKey;
    }
    
    public @Override boolean equals(Object obj) 
    {
        boolean result = true;
        
        if (this == obj) 
        {
            result = true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) 
        {
           result = false;
        }
        
        return result;
    }

    public @Override int hashCode() 
    {
        return super.hashCode();
    }

    public @Override int compareTo(DatabaseRecord o) 
    {
        return this.primaryKey.getId() - o.primaryKey.getId();
    }
    
    public @Override String toString() 
    {
        return String.format("%s{primaryKey=%s", this.getClass().getSimpleName(), 
                primaryKey);
    }
}
