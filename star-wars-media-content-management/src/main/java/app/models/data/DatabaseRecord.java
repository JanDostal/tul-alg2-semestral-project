
package app.models.data;

/**
 *
 * @author Admin
 */
public abstract class DatabaseRecord 
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
}
