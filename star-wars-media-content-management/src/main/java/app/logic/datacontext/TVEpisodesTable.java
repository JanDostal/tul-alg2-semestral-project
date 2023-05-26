
package app.logic.datacontext;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
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
public final class TVEpisodesTable implements IDataTable<TVEpisode>
{
    private static IDataTable<TVEpisode> tvEpisodesTable;
    
    private List<TVEpisode> tvEpisodesData;
    
    private Random primaryKeysGenerator;
    
    private IDataTable<TVSeason> tvSeasonsTable;
    
    private TVEpisodesTable() 
    {
        tvSeasonsTable = TVSeasonsTable.getInstance();
        tvEpisodesData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    protected static IDataTable<TVEpisode> getInstance() 
    {
        if (tvEpisodesTable == null) 
        {
            tvEpisodesTable = new TVEpisodesTable();
        }
        
        return tvEpisodesTable;
    }
    
    @Override
    public TVEpisode addFrom(TVEpisode inputData) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void loadFrom(TVEpisode outputData) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteBy(PrimaryKey primaryKey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean editBy(PrimaryKey primaryKey, TVEpisode editedExistingData) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public @Override TVEpisode getBy(PrimaryKey primaryKey) 
    {
        Optional<TVEpisode> tvEpisode = tvEpisodesData.stream().filter(episode -> 
                episode.getPrimaryKey().equals(primaryKey)).findFirst();
        
        if (tvEpisode.isEmpty()) 
        {
            return null;
        }
        
        return tvEpisode.get();
    }

    public @Override List<TVEpisode> getAll() 
    {
        List<TVEpisode> tvEpisodesCopy = new ArrayList<>(tvEpisodesData);
        return tvEpisodesCopy;
    }

    public @Override List<TVEpisode> filterBy(Predicate<TVEpisode> condition) 
    {
        return tvEpisodesData.stream().filter(condition).collect(Collectors.toList());
    }

    public @Override void sortBy(Comparator<TVEpisode> comparator, List<TVEpisode> sourceList) 
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
            
            TVEpisode tvEpisodeWithDuplicateKey = getBy(generatedPrimaryKey);  

            if (tvEpisodeWithDuplicateKey == null)
            {
                isSame = false;
            }
        }
        while(isSame);
        
        return generatedPrimaryKey;
    }
}
