package app.models.data;

import java.util.Objects;

/**
 * Represents a custom data type for primary key
 * PrimaryKey class is used in data models to ensure they can be used as database records
 * PrimaryKey class is used in IDataTable interface
 * PrimaryKey class can test for duplicity or equality between two primary keys instances  
 * @author jan.dostal
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
