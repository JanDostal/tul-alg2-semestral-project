
package app.models.data;

import java.util.Objects;

/**
 *
 * @author Admin
 */
public class PrimaryKey 
{
    private final int id;
    
    public PrimaryKey(int id) 
    {
        this.id = id;
    }
    
    public int getId() 
    {
        return id;
    }
    
    public @Override boolean equals(Object obj) 
    {        
        if (this == obj) 
        {
            return true;
        }
        
        if (obj == null || (obj instanceof PrimaryKey) == false) 
        {
           return false;
        }
        
        final PrimaryKey other = (PrimaryKey) obj;
                
        return id == other.id;
    }

    public @Override int hashCode() 
    {        
        return Objects.hash(id);
    }

    public @Override String toString() 
    {
        return String.format("PrimaryKey{id=%d}", id);
    }
}
