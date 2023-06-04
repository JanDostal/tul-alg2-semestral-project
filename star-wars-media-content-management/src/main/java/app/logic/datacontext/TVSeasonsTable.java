
package app.logic.datacontext;

import app.models.data.PrimaryKey;
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
 *
 * @author jan.dostal
 */
public class TVSeasonsTable implements IDataTable<TVSeason>
{
    private static IDataTable<TVSeason> tvSeasonsTable;
    
    private List<TVSeason> tvSeasonsData;
    
    private Random primaryKeysGenerator;
    
    private IDataTable<TVShow> tvShowsTable;
    
    private TVSeasonsTable() 
    {
        tvShowsTable = TVShowsTable.getInstance();
        tvSeasonsData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    protected static IDataTable<TVSeason> getInstance() 
    {
        if (tvSeasonsTable == null) 
        {
            tvSeasonsTable = new TVSeasonsTable();
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
        
        TVShow existingTVShow = tvShowsTable.getBy(inputData.getTVShowForeignKey());
            
        if (existingTVShow == null) 
        {
            throw new DatabaseException("Identifikátor seriálu pro přidanou sezónu neodkazuje na žádný seriál");
        }
                
        PrimaryKey newPrimaryKey = generatePrimaryKey();
        
        List<TVSeason> tvSeasonWithDuplicateData = filterBy(season -> season.equals(inputData));
        
        if (tvSeasonWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data přidané sezóny seriálu jsou duplicitní");
        }
        
        TVSeason newData = new TVSeason(newPrimaryKey, inputData.getOrderInTVShow(), 
                inputData.getTVShowForeignKey());
        tvSeasonsData.add(newData);        
    }

    public @Override void loadFrom(TVSeason outputData) throws DatabaseException
    {
        if (outputData == null) 
        {
            throw new DatabaseException("Existující data sezóny seriálu jsou prázdná");
        }
        
        if (outputData.getPrimaryKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor existující sezóny seriálu musí být větší než nula");
        }
        
        if (outputData.getOrderInTVShow() <= 0) 
        {
            throw new DatabaseException("Pořadí existující sezóny seriálu musí být větší než nula");
        }
        
        if (outputData.getTVShowForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor seriálu pro existující sezónu musí být větší než nula");
        }
        
        TVSeason tvSeasonWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (tvSeasonWithDuplicateKey != null) 
        {
            throw new DatabaseException("Identifikátor existující sezóny seriálu je duplicitní");
        }
        
        TVShow existingTVShow = tvShowsTable.getBy(outputData.getTVShowForeignKey());
            
        if (existingTVShow == null) 
        {
            throw new DatabaseException("Identifikátor seriálu pro existující sezónu neodkazuje na žádný seriál");
        }
        
        List<TVSeason> tvSeasonWithDuplicateData = filterBy(season -> season.equals(outputData));
        
        if (tvSeasonWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data existující sezóny seriálu jsou duplicitní");
        }
        
        tvSeasonsData.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) throws DatabaseException
    {
        TVSeason foundTVSeason = getBy(primaryKey);
        
        if (foundTVSeason == null)
        {
            throw new DatabaseException("Sezóna seriálu vybraná pro smazání nebyla nalezena");
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
            TVShow existingTVShow = tvShowsTable.getBy(editedExistingData.getTVShowForeignKey());
            
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
    
    private PrimaryKey generatePrimaryKey() 
    {
        boolean isSame = true;
        PrimaryKey generatedPrimaryKey = null;
        
        do 
        {
            int id = primaryKeysGenerator.nextInt(Integer.MAX_VALUE) + 1;
            generatedPrimaryKey = new PrimaryKey(id);
            
            TVSeason tvSeasonWithDuplicateKey = getBy(generatedPrimaryKey);  

            if (tvSeasonWithDuplicateKey == null)
            {
                isSame = false;
            }
        }
        while(isSame);
        
        return generatedPrimaryKey;
    }
}
