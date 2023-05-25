
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
public final class TVSeasonsTable implements IDataTable<TVSeason>
{
    private static TVSeasonsTable tvSeasonsTable;
    
    private List<TVSeason> tvSeasonsData;
    
    private Random primaryKeysGenerator;
    
    private IDataTable<TVShow> tvShowsTable;
    
    private TVSeasonsTable() 
    {
        tvShowsTable = TVShowsTable.getInstance();
        tvSeasonsData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    protected static TVSeasonsTable getInstance() 
    {
        if (tvSeasonsTable == null) 
        {
            tvSeasonsTable = new TVSeasonsTable();
        }
        
        return tvSeasonsTable;
    }    

    @Override
    public TVSeason addFrom(TVSeason inputData) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void loadFrom(TVSeason outputData) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteBy(PrimaryKey primaryKey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean editBy(PrimaryKey primaryKey, TVSeason editedExistingData) 
    {
        TVSeason foundTVSeason = getBy(primaryKey);
                
        boolean areDataSame = foundTVSeason.equals(editedExistingData);
        
        if (areDataSame == false) 
        {
            //porovnavani spravnosti vstupnich dat (pozdeji exceptions)

            TVShow newData = new TVShow(primaryKey, inputData);
            
            tvShowsData.remove(foundTVShow);
            tvShowsData.add(newData);
        }
        
        return areDataSame;
    }

    @Override
    public TVSeason getBy(PrimaryKey primaryKey) 
    {
        Optional<TVSeason> tvSeason = tvSeasonsData.stream().filter(season -> 
                primaryKey.equals(season.getPrimaryKey())).findFirst();
        
        if (tvSeason.isEmpty()) 
        {
            return null;
        }
        
        return tvSeason.get();
    }

    @Override
    public List<TVSeason> getAll() 
    {
        List<TVSeason> tvSeasonsCopy = new ArrayList<>(tvSeasonsData);
        return tvSeasonsCopy;
    }

    @Override
    public List<TVSeason> filterBy(Predicate<TVSeason> condition) 
    {
        return tvSeasonsData.stream().filter(condition).collect(Collectors.toList());
    }

    @Override
    public void sortBy(Comparator<TVSeason> comparator, List<TVSeason> sourceList) 
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
