
package app.logic.datacontext;

import app.models.data.Movie;
import app.models.data.TVEpisode;
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
    
    private IDataTable<TVEpisode> tvEpisodesTable;
    
    private IDataTable<Movie> moviesTable;
    
    private DataContextAccessor() 
    {
        this.tvSeasonsTable = TVSeasonsTable.getInstance(this);
        this.tvShowsTable = TVShowsTable.getInstance(this);
        this.tvEpisodesTable = TVEpisodesTable.getInstance(this); 
        this.moviesTable = MoviesTable.getInstance(this);
    }

    public IDataTable<TVSeason> getTVSeasonsTable() 
    {
        return tvSeasonsTable;
    }

    public IDataTable<TVShow> getTVShowsTable() 
    {
        return tvShowsTable;
    }
    
    public IDataTable<TVEpisode> getTVEpisodesTable() 
    {
        return tvEpisodesTable;
    }
    
    public IDataTable<Movie> getMoviesTable() 
    {
        return moviesTable;
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
