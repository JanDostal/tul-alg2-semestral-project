
package app.logic.datacontext;

import app.models.data.PrimaryKey;
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
public class TVShowsTable implements IDataTable<TVShow>
{
    private static TVShowsTable tvShowsTable;
    
    private List<TVShow> tvShows;
    
    private Random primaryKeysGenerator;
    
    private TVShowsTable() 
    {
        tvShows = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    public static TVShowsTable getInstance() 
    {
        if (tvShowsTable == null) 
        {
            tvShowsTable = new TVShowsTable();
        }
        
        return tvShowsTable;
    }
    
    public @Override TVShow addFrom(TVShow inputData) 
    {
        //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
                
        PrimaryKey newPrimaryKey = generatePrimaryKey();
        
        List<TVShow> tvShowWithDuplicateData = filterBy(show -> inputData.equals(show));
        
        if (tvShowWithDuplicateData != null) 
        {
            //exception
        }
        
        TVShow newData = new TVShow(newPrimaryKey, inputData);
        tvShows.add(newData);
        
        return newData;
    }

    public @Override void loadFrom(TVShow outputData) 
    {
        //porovnavani spravnosti vystupnich dat (pozdeji exceptions)
        
        TVShow tvShowWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (tvShowWithDuplicateKey != null) 
        {
            //exception
        }
        
        List<TVShow> tvShowWithDuplicateData = filterBy(show -> outputData.equals(show));
        
        if (tvShowWithDuplicateData != null) 
        {
            //exception
        }
        
        tvShows.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) 
    {
        TVShow foundTVShow = getBy(primaryKey);
        
        if (foundTVShow == null) 
        {
            //exception
        }
        
        tvShows.remove(foundTVShow);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, TVShow inputData) 
    {
        TVShow foundTVShow = getBy(primaryKey);
        
        //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
        
        boolean areDataSame = foundTVShow.equals(inputData);
        
        if (areDataSame == false) 
        {
            TVShow newData = new TVShow(primaryKey, inputData);
            
            tvShows.remove(foundTVShow);
            tvShows.add(newData);
        }
        
        return areDataSame;
    }

    public @Override TVShow getBy(PrimaryKey primaryKey) 
    {
        Optional<TVShow> tvShow = tvShows.stream().filter(show -> show.getPrimaryKey().getId() 
                == primaryKey.getId()).findFirst();
        
        if (tvShow.isEmpty()) 
        {
            return null;
        }
        
        return tvShow.get();
    }

    public @Override List<TVShow> getAll() 
    {
        List<TVShow> tvShowsCopy = new ArrayList<>(tvShows);
        return tvShowsCopy;
    }

    public @Override List<TVShow> filterBy(Predicate<TVShow> condition) 
    {
        return tvShows.stream().filter(condition).collect(Collectors.toList());
    }

    public @Override void sortBy(Comparator<TVShow> comparator, List<TVShow> sourceList) 
    {
        Collections.sort(sourceList, comparator);
    }
    
    private PrimaryKey generatePrimaryKey() 
    {
        boolean isSame = true;
        PrimaryKey generatedPrimaryKey = null;
        
        do 
        {
            int id = primaryKeysGenerator.nextInt(Integer.MAX_VALUE) + 1;
            generatedPrimaryKey = new PrimaryKey(id);
            
            TVShow tvShowWithDuplicateKey = getBy(generatedPrimaryKey);  

            if (tvShowWithDuplicateKey == null)
            {
                isSame = false;
            }
        }
        while(isSame);
        
        return generatedPrimaryKey;
    }
}
