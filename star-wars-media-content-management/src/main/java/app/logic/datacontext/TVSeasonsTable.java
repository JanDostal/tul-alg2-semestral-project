
package app.logic.datacontext;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import utils.exceptions.DatabaseException;
import utils.interfaces.IDataTable;

/**
 * Represents a tv seasons data table, which offers basic CRUD operations.
 * TV seasons data table works with tv season data model and implements IDataTable interface.
 * TV seasons data table is made available 
 * through accessor and can communicate with other data tables through accessor
 * or use common methods of accessor.
 * @author jan.dostal
 */
public class TVSeasonsTable implements IDataTable<TVSeason>
{
    private static IDataTable<TVSeason> tvSeasonsTable;
    
    private final List<TVSeason> tvSeasonsData;
        
    private final DataContextAccessor dbContext;
    
    private final Random primaryKeysGenerator;
    
    /**
     * Creates singleton instance of TVSeasonsTable.
     * Uses dependency injection to inject data context service.
     * @param dbContext Singleton instance of data context accessor. 
     * Can be used for using common methods from {@link DataContextAccessor} class or
     * communicating with other data tables.
     */
    private TVSeasonsTable(DataContextAccessor dbContext) 
    {
        this.dbContext = dbContext;
        tvSeasonsData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @param dbContext singleton instance of data context accessor.
     * Can be used for using common methods from {@link DataContextAccessor} class or
     * communicating with other data tables.
     * @return singleton instance of TVSeasonsTable as interface
     */
    protected static IDataTable<TVSeason> getInstance(DataContextAccessor dbContext) 
    {
        if (tvSeasonsTable == null) 
        {
            tvSeasonsTable = new TVSeasonsTable(dbContext);
        }
        
        return tvSeasonsTable;
    }    

    public @Override void addFrom(TVSeason inputData) throws DatabaseException
    {
        if (inputData == null) 
        {
            throw new DatabaseException("Data přidané sezóny seriálu jsou prázdná");
        }
           
        if (inputData.getOrderInTVShow() <= 0) 
        {
            throw new DatabaseException("Pořadí přidané sezóny seriálu musí být větší než nula");
        }
        
        if (inputData.getTVShowForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor seriálu pro přidanou sezónu musí být větší než nula");
        }
        
        TVShow existingTVShow = dbContext.getTVShowsTable().getBy(inputData.getTVShowForeignKey());
            
        if (existingTVShow == null) 
        {
            throw new DatabaseException("Identifikátor seriálu pro přidanou sezónu neodkazuje na žádný seriál");
        }
                
        List<TVSeason> tvSeasonWithDuplicateData = filterBy(season -> season.equals(inputData));
        
        if (tvSeasonWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data přidané sezóny seriálu jsou duplicitní");
        }
        
        PrimaryKey newPrimaryKey = dbContext.generatePrimaryKey(this, primaryKeysGenerator);
        
        TVSeason newData = new TVSeason(newPrimaryKey, inputData.getOrderInTVShow(), 
                inputData.getTVShowForeignKey());
        tvSeasonsData.add(newData);        
    }

    public @Override void loadFrom(TVSeason inputOutputData) throws DatabaseException
    {
        if (inputOutputData == null) 
        {
            throw new DatabaseException("Existující data sezóny seriálu jsou prázdná");
        }
        
        if (inputOutputData.getPrimaryKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor " + inputOutputData.getPrimaryKey().getId() +
                    " existující sezóny seriálu musí být větší než nula");
        }
        
        if (inputOutputData.getOrderInTVShow() <= 0) 
        {
            throw new DatabaseException("Pořadí existující sezóny seriálu s identifikátorem " + inputOutputData.getPrimaryKey().getId() + 
                    " musí být větší než nula");
        }
        
        if (inputOutputData.getTVShowForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor seriálu pro existující sezónu s identifikátorem "
                    + inputOutputData.getPrimaryKey().getId() + " musí být větší než nula");
        }
        
        TVSeason tvSeasonWithDuplicateKey = getBy(inputOutputData.getPrimaryKey());
        
        if (tvSeasonWithDuplicateKey != null) 
        {
            throw new DatabaseException("Identifikátor " + inputOutputData.getPrimaryKey().getId() 
                    + " existující sezóny seriálu je duplicitní");
        }
        
        TVShow existingTVShow = dbContext.getTVShowsTable().getBy(inputOutputData.getTVShowForeignKey());
            
        if (existingTVShow == null) 
        {
            throw new DatabaseException("Identifikátor seriálu pro existující sezónu s identifikátorem "
                    + inputOutputData.getPrimaryKey().getId() + " neodkazuje na žádný seriál");
        }
        
        List<TVSeason> tvSeasonWithDuplicateData = filterBy(season -> season.equals(inputOutputData));
        
        if (tvSeasonWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data existující sezóny seriálu s identifikátorem " + inputOutputData.getPrimaryKey().getId() 
                    + " jsou duplicitní");
        }
        
        tvSeasonsData.add(inputOutputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) throws DatabaseException
    {
        TVSeason foundTVSeason = getBy(primaryKey);
        
        if (foundTVSeason == null)
        {
            throw new DatabaseException("Sezóna seriálu vybraná pro smazání nebyla nalezena");
        }
        
        List<TVEpisode> seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e -> 
                e.getTVSeasonForeignKey().equals(primaryKey));

        for (TVEpisode o : seasonEpisodes) 
        {
            try 
            {
                dbContext.getTVEpisodesTable().deleteBy(o.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }
        
        tvSeasonsData.remove(foundTVSeason);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, TVSeason editedExistingData) throws DatabaseException
    {
        TVSeason foundTVSeason = getBy(primaryKey);
        
        if (foundTVSeason == null) 
        {
            throw new DatabaseException("Sezóna seriálu vybraná pro editaci nebyla nalezena");
        }
        
        if (editedExistingData == null) 
        {
            throw new DatabaseException("Nová data editované sezóny seriálu jsou prázdná");
        }
        
        if (editedExistingData.getOrderInTVShow() <= 0) 
        {
            throw new DatabaseException("Pořadí editované sezóny seriálu musí být větší než nula");
        }
        
        if (editedExistingData.getTVShowForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor seriálu pro editovanou sezónu musí být větší než nula");
        }
                
        boolean wasDataChanged = false;
        
        if (Objects.equals(foundTVSeason.getTVShowForeignKey(), editedExistingData.getTVShowForeignKey()) == false || 
            foundTVSeason.getOrderInTVShow() != editedExistingData.getOrderInTVShow())
        {
            wasDataChanged = true;
        }
        
        if (wasDataChanged == true) 
        {
            TVShow existingTVShow = dbContext.getTVShowsTable().getBy(editedExistingData.getTVShowForeignKey());
            
            if (existingTVShow == null) 
            {
                throw new DatabaseException("Upravený identifikátor seriálu pro "
                        + "editovanou sezónu neodkazuje na žádný seriál");
            }
            
            List<TVSeason> seasonWithDuplicateData = filterBy(season -> 
                    season.getPrimaryKey().equals(foundTVSeason.getPrimaryKey()) == false 
                            && season.equals(editedExistingData));
            
            if (seasonWithDuplicateData.isEmpty() == false) 
            {
                throw new DatabaseException("Upravená data editované sezóny jsou duplicitní");
            }
            
            TVSeason newData = new TVSeason(primaryKey, editedExistingData.getOrderInTVShow(), 
                    editedExistingData.getTVShowForeignKey());
            
            tvSeasonsData.remove(foundTVSeason);
            tvSeasonsData.add(newData);
        }
        
        return wasDataChanged;
    }

    public @Override TVSeason getBy(PrimaryKey primaryKey) 
    {
        Optional<TVSeason> tvSeason = tvSeasonsData.stream().filter(season -> 
                season.getPrimaryKey().equals(primaryKey)).findFirst();
        
        if (tvSeason.isEmpty()) 
        {
            return null;
        }
        
        return tvSeason.get();
    }

    public @Override List<TVSeason> getAll() 
    {
        List<TVSeason> tvSeasonsCopy = new ArrayList<>(tvSeasonsData);
        return tvSeasonsCopy;
    }

    public @Override List<TVSeason> filterBy(Predicate<TVSeason> condition) 
    {
        return tvSeasonsData.stream().filter(condition).collect(Collectors.toList());
    }

    public @Override void sortBy(Comparator<TVSeason> comparator, List<TVSeason> sourceList) 
    {
        Collections.sort(sourceList, comparator);
    }
    
    public @Override void sortByPrimaryKey(List<TVSeason> sourceList) 
    {
        Collections.sort(sourceList);
    }
    
    public @Override void clearData() 
    {
        tvSeasonsData.clear();
    }
}
