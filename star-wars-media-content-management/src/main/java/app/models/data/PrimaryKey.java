
package app.models.data;

/**
 *
 * @author Admin
 */
public class PrimaryKey 
{
    private int id;
    
    public PrimaryKey(int id) 
    {
        this.id = id;
    }
    
    public int getId() 
    {
        return id;
    }

    public @Override String toString() 
    {
        return "PrimaryKey{" + "id=" + id + '}';
    }
}
