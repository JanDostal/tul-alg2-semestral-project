package app.models.data;


/**
 * Represents an abstract class for classification of data as database records
 * DatabaseRecord class is used in IDataTable interface to ensure the data has primary key data type attribute
 * DatabaseRecord class also also offers sorting by primary keys
 * @author jan.dostal
 */
public abstract class DatabaseRecord implements Comparable<DatabaseRecord>
{
    private final PrimaryKey primaryKey;
    
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
        if (this.primaryKey == null) 
        {
            if (o == null || o.primaryKey == null) 
            {
                return 0;
            }
            else 
            {
                return 1;
            }
        }
        
        if (o == null || o.primaryKey == null) 
        {
            return -1;
        }
        
        return this.primaryKey.getId() - o.primaryKey.getId();
    }
    
    public @Override String toString() 
    {
        return String.format("%s{primaryKey=%s", this.getClass().getSimpleName(), 
                primaryKey);
    }
}
