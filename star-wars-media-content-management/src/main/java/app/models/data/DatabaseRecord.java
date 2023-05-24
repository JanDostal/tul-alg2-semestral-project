
package app.models.data;

/**
 *
 * @author Admin
 */
public abstract class DatabaseRecord 
{
    private int id;
    
    protected DatabaseRecord(int id) 
    {
        this.id = id;
    }
    
    public int getId() 
    {
        return id;
    }
}
