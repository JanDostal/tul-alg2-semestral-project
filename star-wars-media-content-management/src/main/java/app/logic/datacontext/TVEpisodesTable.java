
package app.logic.datacontext;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
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
    
    public @Override TVEpisode addFrom(TVEpisode inputData) 
    {
        //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
        
        TVSeason existingTVSeason = tvSeasonsTable.getBy(inputData.getTVSeasonForeignKey());
            
        if (existingTVSeason == null) 
        {
            //exception
        }
                
        PrimaryKey newPrimaryKey = generatePrimaryKey();
        
        List<TVEpisode> tvEpisodeWithDuplicateData = filterBy(episode -> episode.equals(inputData));
        
        if (tvEpisodeWithDuplicateData.isEmpty() == false) 
        {
            //exception
        }
        
        TVEpisode newData = new TVEpisode(newPrimaryKey, inputData.getRuntime(), 
                inputData.getName(), inputData.getPercentageRating(), 
                inputData.getWasWatched(), 
                inputData.getHyperlinkForContentWatch(),
                inputData.getShortContentSummary(),
                inputData.getOrderInTVShowSeason(),
                inputData.getTVSeasonForeignKey());
        
        tvEpisodesData.add(newData);
        
        return newData;
    }

    public @Override void loadFrom(TVEpisode outputData) 
    {
        //porovnavani spravnosti vystupnich dat (pozdeji exceptions)
        
        TVEpisode tvEpisodeWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (tvEpisodeWithDuplicateKey != null) 
        {
            //exception
        }
        
        TVSeason existingTVSeason = tvSeasonsTable.getBy(outputData.getTVSeasonForeignKey());
            
        if (existingTVSeason == null) 
        {
            //exception
        }
        
        List<TVEpisode> tvEpisodeWithDuplicateData = filterBy(episode -> episode.equals(outputData));
        
        if (tvEpisodeWithDuplicateData.isEmpty() == false) 
        {
            //exception
        }
        
        tvEpisodesData.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) 
    {
        TVEpisode foundTVEpisode = getBy(primaryKey);
        
        if (foundTVEpisode == null) 
        {
            //exception
        }
        
        tvEpisodesData.remove(foundTVEpisode);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, TVEpisode editedExistingData) 
    {
        TVEpisode foundTVEpisode = getBy(primaryKey);
        
        if (foundTVEpisode == null) 
        {
            //exception
        }
                
        boolean areDataSame = foundTVEpisode.equals(editedExistingData);
        
        if (areDataSame == false) 
        {
            //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
            TVSeason existingTVSeason = tvSeasonsTable.getBy(editedExistingData.getTVSeasonForeignKey());
            
            if (existingTVSeason == null) 
            {
                //exception
            }
   
            TVEpisode newData = new TVEpisode(primaryKey, editedExistingData.getRuntime(), 
                    editedExistingData.getName(), editedExistingData.getPercentageRating(), 
                    editedExistingData.getWasWatched(), 
                    editedExistingData.getHyperlinkForContentWatch(),
                    editedExistingData.getShortContentSummary(),
                    editedExistingData.getOrderInTVShowSeason(),
                    editedExistingData.getTVSeasonForeignKey());
            
            tvEpisodesData.remove(foundTVEpisode);
            tvEpisodesData.add(newData);
        }
        
        return areDataSame;
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
