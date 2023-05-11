
package app.models.data;

import java.util.List;

/**
 *
 * @author jan.dostal
 */
public class Season 
{
    private int id;
    
    private int orderInTvShow;
        
    public Season(int id, int orderInTvShow) 
    {
        this.id = id;
        this.orderInTvShow = orderInTvShow;
    }
    
    public int getId() 
    {
        return id;
    }
    
    public int getOrderInTvShow() 
    {
        return orderInTvShow;
    }
    
    public @Override String toString() 
    {
        return "Season{id=" + id + ", orderInTvShow=" + orderInTvShow + "}";
    }
}
