package app.models.data;

import java.util.Objects;

/**
 * Represents a custom data type for primary key.
 * PrimaryKey class is used in data models to ensure they can be used as database records.
 * PrimaryKey class is used in IDataTable interface.
 * PrimaryKey class can test for duplicity or equality between two primary keys instances. 
 * @author jan.dostal
 */
public class PrimaryKey 
{
    private final int id;
    
    /**
     * Creates a new instance representing primary key as identificator in database
     * @param id represents primary key atomic attribute (greater or equal to 1)
     */
    public PrimaryKey(int id) 
    {
        this.id = id;
    }
    
    /**
     * @return primary key instance atomic attribute
     */
    public int getId() 
    {
        return id;
    }
    
    /**
     * Method tests this primary key instance with second instance for equality
     * (custom atomic attribute id testing for equality).
     * @param obj anonymous object for comparison
     * @return logical value stating if this instance equals second instance
     */
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
    
    /**
     * Method creates unique hash code from primary key instance attributes (id)
     * @return int value representing unique hash code
     */
    public @Override int hashCode() 
    {        
        return Objects.hash(id);
    }

    public @Override String toString() 
    {
        return String.format("PrimaryKey{id=%d}", id);
    }
}
