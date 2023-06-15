
package app.logic.datacontext;

import app.models.data.DatabaseRecord;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.util.Random;
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
    }
    
    public static DataContextAccessor getInstance() 
    {
        if (dataContextAccessor == null) 
        {
            dataContextAccessor = new DataContextAccessor();
            
            dataContextAccessor.initializeDataContextAccessor();
        }
        
        return dataContextAccessor;
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

    private void initializeDataContextAccessor() 
    {
        moviesTable = MoviesTable.getInstance(this);
        tvSeasonsTable = TVSeasonsTable.getInstance(this);
        tvShowsTable = TVShowsTable.getInstance(this);
        tvEpisodesTable = TVEpisodesTable.getInstance(this);
    }
    
    protected PrimaryKey generatePrimaryKey(IDataTable dataTable, Random dataTablePrimaryKeysGenerator) 
    {
        boolean isSame = true;
        PrimaryKey generatedPrimaryKey = null;
        
        do 
        {
            int id = dataTablePrimaryKeysGenerator.nextInt(Integer.MAX_VALUE) + 1;
            generatedPrimaryKey = new PrimaryKey(id);
            
            DatabaseRecord dataWithDuplicateKey = dataTable.getBy(generatedPrimaryKey);  

            if (dataWithDuplicateKey == null)
            {
                isSame = false;
            }
        }
        while(isSame);
        
        return generatedPrimaryKey;
    }
}
