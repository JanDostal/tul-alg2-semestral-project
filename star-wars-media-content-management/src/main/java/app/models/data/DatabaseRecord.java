package app.models.data;

/**
 * Represents an abstract class for classification of data as database records.
 * DatabaseRecord class is used in IDataTable interface to ensure the data has 
 * primary key data type attribute.
 * DatabaseRecord class also also offers sorting by primary keys through Comparable interface.
 * @author jan.dostal
 */
public abstract class DatabaseRecord implements Comparable<DatabaseRecord>
{
    private final PrimaryKey primaryKey;
    
    /**
     * Creates a new instance representing database record (using constructor in subclass)
     * @param primaryKey represents instance of primary key as identificator in database
     */
    protected DatabaseRecord(PrimaryKey primaryKey) 
    {
        this.primaryKey = primaryKey;
    }
    
    /**
     * @return instance of primary key
     */
    public PrimaryKey getPrimaryKey() 
    {
        return primaryKey;
    }
    
    /**
     * Method compares current database record instance with another database record instance
     * (null checking, same class checking) to test if 
     * they are equal (using equals method in subclass).
     * @param obj represents anonymous object which is tested for equality
     * @return logical value indicating if compared objects are equal or not
     */
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
    
    /**
     * Method creates unique hash code from instance attributes 
     * (using hashCode method in subclass).
     * @return int value representing unique hash code
     */
    public @Override int hashCode() 
    {
        return super.hashCode();
    }
    
    /**
     * Method compares current database record instance with 
     * another database record instance
     * to sort database records by primary key ascendingly (interface Comparable).
     * @param o second DatabaseRecord instance for comparison
     * @return int value indicating if first databaseRecord instance primary key
     * is greater or equal than primary key of second instance.
     */
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
