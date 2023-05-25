
package app.logic.datacontext;

import app.models.data.TVSeason;
import app.models.data.TVShow;
import utils.interfaces.IDataTable;

/**
 *
 * @author jan.dostal
 */
public class DataContextAccessor 
{
    private static DataContextAccessor dataContextAccessor;
    
    private IDataTable<TVSeason> tvSeasonsTable;
    
    private IDataTable<TVShow> tvShowsTable;
    
    private DataContextAccessor() 
    {
        this.tvSeasonsTable = TVSeasonsTable.getInstance();
        this.tvShowsTable = TVShowsTable.getInstance();
    }

    public IDataTable<TVSeason> getTvSeasonsTable() 
    {
        return tvSeasonsTable;
    }

    public IDataTable<TVShow> getTvShowsTable() 
    {
        return tvShowsTable;
    }
    
    public static DataContextAccessor getInstance() 
    {
        if (dataContextAccessor == null) 
        {
            dataContextAccessor = new DataContextAccessor();
        }
        
        return dataContextAccessor;
    }
}
