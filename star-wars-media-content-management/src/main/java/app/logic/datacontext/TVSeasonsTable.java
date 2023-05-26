
package app.logic.datacontext;

import app.models.data.PrimaryKey;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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


    public @Override TVSeason addFrom(TVSeason inputData) 
    {
        //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
        
        TVShow existingTVShow = tvShowsTable.getBy(inputData.getTVShowForeignKey());
            
        if (existingTVShow == null) 
        {
            //exception
        }
                
        PrimaryKey newPrimaryKey = generatePrimaryKey();
        
        List<TVSeason> tvSeasonWithDuplicateData = filterBy(season -> season.equals(inputData));
        
        if (tvSeasonWithDuplicateData.isEmpty() == false) 
        {
            //exception
        }
        
        TVSeason newData = new TVSeason(newPrimaryKey, inputData.getOrderInTVShow(), 
                inputData.getTVShowForeignKey());
        tvSeasonsData.add(newData);
        
        return newData;
    }

    public @Override void loadFrom(TVSeason outputData) 
    {
        //porovnavani spravnosti vystupnich dat (pozdeji exceptions)
        
        TVSeason tvSeasonWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (tvSeasonWithDuplicateKey != null) 
        {
            //exception
        }
        
        TVShow existingTVShow = tvShowsTable.getBy(outputData.getTVShowForeignKey());
            
        if (existingTVShow == null) 
        {
            //exception
        }
        
        List<TVSeason> tvSeasonWithDuplicateData = filterBy(season -> season.equals(outputData));
        
        if (tvSeasonWithDuplicateData.isEmpty() == false) 
        {
            //exception
        }
        
        tvSeasonsData.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) 
    {
        TVSeason foundTVSeason = getBy(primaryKey);
        
        if (foundTVSeason == null) 
        {
            //exception
        }
        
        tvSeasonsData.remove(foundTVSeason);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, TVSeason editedExistingData) 
    {
        TVSeason foundTVSeason = getBy(primaryKey);
        
        if (foundTVSeason == null) 
        {
            //exception
        }
                
        boolean areDataSame = foundTVSeason.equals(editedExistingData);
        
        if (areDataSame == false) 
        {
            //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
            TVShow existingTVShow = tvShowsTable.getBy(editedExistingData.getTVShowForeignKey());
            
            if (existingTVShow == null) 
            {
                //exception
            }

            TVSeason newData = new TVSeason(primaryKey, editedExistingData.getOrderInTVShow(), 
                    editedExistingData.getTVShowForeignKey());
            
            tvSeasonsData.remove(foundTVSeason);
            tvSeasonsData.add(newData);
        }
        
        return areDataSame;
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
