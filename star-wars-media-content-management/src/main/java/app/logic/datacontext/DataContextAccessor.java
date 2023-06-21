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
 * Represents a database access layer service for accessing and manipulating data in data tables.
 * Database context accessor data tables are made available through this accessor.
 * @author jan.dostal
 */
public class DataContextAccessor 
{
    private static DataContextAccessor dataContextAccessor;
    
    private IDataTable<TVSeason> tvSeasonsTable;
    
    private IDataTable<TVShow> tvShowsTable;
    
    private IDataTable<TVEpisode> tvEpisodesTable;
    
    private IDataTable<Movie> moviesTable;
    
    /**
     * Creates singleton instance of DataContextAccessor.
     */
    private DataContextAccessor() 
    {
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @return singleton instance of DataContextAccessor class
     */
    public static DataContextAccessor getInstance() 
    {
        if (dataContextAccessor == null) 
        {
            dataContextAccessor = new DataContextAccessor();
            
            dataContextAccessor.initializeDataContextAccessor();
        }
        
        return dataContextAccessor;
    }
    
    /**
     * @return tv seasons data table instance as interface
     */
    public IDataTable<TVSeason> getTVSeasonsTable() 
    {
        return tvSeasonsTable;
    }

    /**
     * @return tv shows data table instance as interface
     */
    public IDataTable<TVShow> getTVShowsTable() 
    {
        return tvShowsTable;
    }
    
    /**
     * @return tv episodes data table instance as interface
     */
    public IDataTable<TVEpisode> getTVEpisodesTable() 
    {
        return tvEpisodesTable;
    }
    
    /**
     * @return movies data table instance as interface
     */
    public IDataTable<Movie> getMoviesTable() 
    {
        return moviesTable;
    }
    
    /**
     * Initializes data context accessor after instance creation, 
     * specifically loads data tables singleton instances
     */
    private void initializeDataContextAccessor() 
    {
        moviesTable = MoviesTable.getInstance(this);
        tvSeasonsTable = TVSeasonsTable.getInstance(this);
        tvShowsTable = TVShowsTable.getInstance(this);
        tvEpisodesTable = TVEpisodesTable.getInstance(this);
    }
    
    /**
     * Generates new random unique (unique in data table) 
     * primary key for selected data table (creating new database record).
     * @param dataTable represents a data table as interface, which wants to generate primary key
     * @param dataTablePrimaryKeysGenerator random seed generator for generating primary key, 
     * which originates from selected data table.
     * @return new unique primary key for chosen data table (creating new database record)
     */
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
