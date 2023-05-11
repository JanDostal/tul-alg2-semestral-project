
package app.models.data;

/**
 *
 * @author jan.dostal
 */
public class TVSeason 
{
    private int id;
    
    private int orderInTvShow;
        
    public TVSeason(int id, int orderInTvShow) 
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
