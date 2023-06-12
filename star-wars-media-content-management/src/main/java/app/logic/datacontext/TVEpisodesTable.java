
package app.logic.datacontext;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
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
public class TVEpisodesTable implements IDataTable<TVEpisode>
{
    private static IDataTable<TVEpisode> tvEpisodesTable;
    
    private final List<TVEpisode> tvEpisodesData;
        
    private final DataContextAccessor dbContext;
    
    private final Random primaryKeysGenerator;
    
    private TVEpisodesTable(DataContextAccessor dbContext) 
    {
        this.dbContext = dbContext;
        tvEpisodesData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    protected static IDataTable<TVEpisode> getInstance(DataContextAccessor dbContext) 
    {
        if (tvEpisodesTable == null) 
        {
            tvEpisodesTable = new TVEpisodesTable(dbContext);
        }
        
        return tvEpisodesTable;
    }
    
    public @Override void addFrom(TVEpisode inputData) throws DatabaseException
    {
        if (inputData == null) 
        {
            throw new DatabaseException("Data přidané epizody sezóny jsou prázdná");
        }
                
        if (inputData.getPercentageRating() > 100) 
        {
            throw new DatabaseException("Procentuální hodnocení zhlédnuté přidané epizody sezóny musí být v rozsahu 0 - 100");
        }
        
        if (inputData.getOrderInTVShowSeason() <= 0)
        {
            throw new DatabaseException("Pořadí přidané epizody sezóny musí být větší než nula");
        }
        
        if (inputData.getTVSeasonForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor sezóny pro přidanou epizodu musí být větší než nula");
        }
        
        TVSeason existingTVSeason = dbContext.getTVSeasonsTable().getBy(inputData.getTVSeasonForeignKey());
            
        if (existingTVSeason == null) 
        {
            throw new DatabaseException("Identifikátor sezóny pro přidanou epizodu neodkazuje na žádnou sezónu");
        }
                
        PrimaryKey newPrimaryKey = dbContext.generatePrimaryKey(this, primaryKeysGenerator);
        
        List<TVEpisode> tvEpisodeWithDuplicateData = filterBy(episode -> episode.equals(inputData));
        
        if (tvEpisodeWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data přidané epizody sezóny jsou duplicitní");
        }
        
        TVEpisode newData = new TVEpisode(newPrimaryKey, 
                inputData.getRuntime(), 
                inputData.getName(), 
                inputData.getPercentageRating(), 
                inputData.getWasWatched(), 
                inputData.getHyperlinkForContentWatch(),
                inputData.getShortContentSummary(),
                inputData.getOrderInTVShowSeason(),
                inputData.getTVSeasonForeignKey());
        
        tvEpisodesData.add(newData);        
    }

    public @Override void loadFrom(TVEpisode outputData) throws DatabaseException
    {
        if (outputData == null) 
        {
            throw new DatabaseException("Existující data epizody sezóny jsou prázdná");
        }
        
        if (outputData.getPrimaryKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor " + outputData.getPrimaryKey().getId() 
                    + " existující epizody sezóny musí být větší než nula");
        }
        
        if (outputData.getPercentageRating() > 100) 
        {
            throw new DatabaseException("Procentuální hodnocení zhlédnuté existující epizody sezóny s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " musí být v rozsahu 0 - 100");
        }
        
        if (outputData.getOrderInTVShowSeason() <= 0)
        {
            throw new DatabaseException("Pořadí existující epizody sezóny s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " musí být větší než nula");
        }
        
        if (outputData.getTVSeasonForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor sezóny pro existující epizodu s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " musí být větší než nula");
        }
        
        TVEpisode tvEpisodeWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (tvEpisodeWithDuplicateKey != null) 
        {
            throw new DatabaseException("Identifikátor " + outputData.getPrimaryKey().getId() 
                    + " existující epizody sezóny je duplicitní");
        }
        
        TVSeason existingTVSeason = dbContext.getTVSeasonsTable().getBy(outputData.getTVSeasonForeignKey());
            
        if (existingTVSeason == null) 
        {
            throw new DatabaseException("Identifikátor sezóny pro existující epizodu s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " neodkazuje na žádnou sezónu");
        }
        
        List<TVEpisode> tvEpisodeWithDuplicateData = filterBy(episode -> episode.equals(outputData));
        
        if (tvEpisodeWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data existující epizody sezóny s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " jsou duplicitní");
        }
        
        tvEpisodesData.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) throws DatabaseException
    {
        TVEpisode foundTVEpisode = getBy(primaryKey);
        
        if (foundTVEpisode == null) 
        {
            throw new DatabaseException("Epizoda sezóny vybraná pro smazání nebyla nalezena");
        }
        
        tvEpisodesData.remove(foundTVEpisode);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, TVEpisode editedExistingData) throws DatabaseException
    {
        TVEpisode foundTVEpisode = getBy(primaryKey);
        
        if (foundTVEpisode == null) 
        {
            throw new DatabaseException("Epizoda sezóny vybraná pro editaci nebyla nalezena");
        }
        
        if (editedExistingData == null) 
        {
            throw new DatabaseException("Nová data editované epizody sezóny jsou prázdná");
        }
        
        if (editedExistingData.getPercentageRating() > 100) 
        {
            throw new DatabaseException("Procentuální hodnocení zhlédnuté editované epizody sezóny musí být v rozsahu 0 - 100");
        }
        
        if (editedExistingData.getOrderInTVShowSeason() <= 0)
        {
            throw new DatabaseException("Pořadí editované epizody sezóny musí být větší než nula");
        }
        
        if (editedExistingData.getTVSeasonForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor sezóny pro editovanou epizodu musí být větší než nula");
        }
        
        boolean wasDataChanged = false;
        
        if (Objects.equals(foundTVEpisode.getShortContentSummary(), editedExistingData.getShortContentSummary()) == false || 
            foundTVEpisode.getWasWatched() != editedExistingData.getWasWatched() ||
            Objects.equals(foundTVEpisode.getRuntime(), editedExistingData.getRuntime()) == false ||
            foundTVEpisode.getOrderInTVShowSeason() != editedExistingData.getOrderInTVShowSeason() ||
            foundTVEpisode.getPercentageRating() != editedExistingData.getPercentageRating() ||
            Objects.equals(foundTVEpisode.getName(), editedExistingData.getName()) == false ||
            Objects.equals(foundTVEpisode.getHyperlinkForContentWatch(), editedExistingData.getHyperlinkForContentWatch()) == false ||
            Objects.equals(foundTVEpisode.getTVSeasonForeignKey(), editedExistingData.getTVSeasonForeignKey()) == false)
        {
            wasDataChanged = true;
        }
        
        if (wasDataChanged == true)
        {
            TVSeason existingTVSeason = dbContext.getTVSeasonsTable().getBy(editedExistingData.getTVSeasonForeignKey());
            
            if (existingTVSeason == null) 
            {
                throw new DatabaseException("Upravený identifikátor sezóny pro "
                        + "editovanou epizodu neodkazuje na žádnou sezónu");
            }
            
            List<TVEpisode> episodeWithDuplicateData = filterBy(episode -> 
                episode.getPrimaryKey().equals(foundTVEpisode.getPrimaryKey()) == false
                && episode.equals(editedExistingData));
        
            if (episodeWithDuplicateData.isEmpty() == false) 
            {
                throw new DatabaseException("Upravená data editované epizody jsou duplicitní");
            }
            
            TVEpisode newData = new TVEpisode(primaryKey, 
                    editedExistingData.getRuntime(), 
                    editedExistingData.getName(), 
                    editedExistingData.getPercentageRating(), 
                    editedExistingData.getWasWatched(), 
                    editedExistingData.getHyperlinkForContentWatch(),
                    editedExistingData.getShortContentSummary(),
                    editedExistingData.getOrderInTVShowSeason(),
                    editedExistingData.getTVSeasonForeignKey());
            
            tvEpisodesData.remove(foundTVEpisode);
            tvEpisodesData.add(newData);
        }
        
        return wasDataChanged;
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
    
    public @Override void sortByPrimaryKey(List<TVEpisode> sourceList) 
    {
        Collections.sort(sourceList);
    }
    
    public @Override void clearData() 
    {
        tvEpisodesData.clear();
    }
}
