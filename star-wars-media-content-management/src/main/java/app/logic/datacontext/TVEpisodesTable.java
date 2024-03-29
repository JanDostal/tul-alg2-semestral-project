package app.logic.datacontext;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.inputoutput.TVEpisodeInputOutput;
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
 * Represents a tv episodes data table, which offers basic CRUD operations.
 * TV episodes data table works with tv episode data model and implements IDataTable interface.
 * TV episodes data table is made available 
 * through accessor and can communicate with other data tables through accessor 
 * or use common methods of accessor.
 * @author jan.dostal
 */
public class TVEpisodesTable implements IDataTable<TVEpisode>
{
    private static IDataTable<TVEpisode> tvEpisodesTable;
    
    private final List<TVEpisode> tvEpisodesData;
        
    private final DataContextAccessor dbContext;
    
    private final Random primaryKeysGenerator;
    
    /**
     * Creates singleton instance of TVEpisodesTable.
     * Uses dependency injection to inject data context service.
     * @param dbContext Singleton instance of data context accessor.
     * Can be used for using common methods from {@link DataContextAccessor} class or
     * communicating with other data tables.
     */
    private TVEpisodesTable(DataContextAccessor dbContext) 
    {
        this.dbContext = dbContext;
        tvEpisodesData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @param dbContext singleton instance of data context accessor.
     * Can be used for using common methods from {@link DataContextAccessor} class or
     * communicating with other data tables.
     * @return singleton instance of TVEpisodesTable as interface
     */
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
        
        if (inputData.getRuntime() == null) 
        {
            throw new DatabaseException("Přidaná epizoda sezóny musí mít délku trvání");
        }
        
        if (inputData.getName() != null && inputData.getName().length() > TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH) 
        {
            throw new DatabaseException("Název přidané epizody sezóny nesmí mít délku větší než " + 
                    TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH + " znaků");
        }
        
        if (inputData.getHyperlinkForContentWatch() != null && 
                inputData.getHyperlinkForContentWatch().length() > TVEpisodeInputOutput.ATTRIBUTE_HYPERLINK_LENGTH) 
        {
            throw new DatabaseException("Odkaz ke zhlédnutí přidané epizody sezóny nesmí mít délku větší než " + 
                    TVEpisodeInputOutput.ATTRIBUTE_HYPERLINK_LENGTH + " znaků");
        }
        
        if (inputData.getShortContentSummary() != null && 
                inputData.getShortContentSummary().length() > TVEpisodeInputOutput.ATTRIBUTE_SUMMARY_LENGTH) 
        {
            throw new DatabaseException("Krátké shrnutí obsahu přidané epizody sezóny nesmí mít délku větší než " + 
                    TVEpisodeInputOutput.ATTRIBUTE_SUMMARY_LENGTH + " znaků");
        }
        
        TVSeason existingTVSeason = dbContext.getTVSeasonsTable().getBy(inputData.getTVSeasonForeignKey());
            
        if (existingTVSeason == null) 
        {
            throw new DatabaseException("Identifikátor sezóny pro přidanou epizodu neodkazuje na žádnou sezónu");
        }
                
        List<TVEpisode> tvEpisodeWithDuplicateData = filterBy(episode -> episode.equals(inputData));
        
        if (tvEpisodeWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data přidané epizody sezóny jsou duplicitní");
        }
        
        PrimaryKey newPrimaryKey = dbContext.generatePrimaryKey(this, primaryKeysGenerator);
        
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

    public @Override void loadFrom(TVEpisode inputOutputData) throws DatabaseException
    {
        if (inputOutputData == null) 
        {
            throw new DatabaseException("Existující data epizody sezóny jsou prázdná");
        }
        
        if (inputOutputData.getPrimaryKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor " + inputOutputData.getPrimaryKey().getId() 
                    + " existující epizody sezóny musí být větší než nula");
        }
        
        if (inputOutputData.getPercentageRating() > 100) 
        {
            throw new DatabaseException("Procentuální hodnocení zhlédnuté existující epizody sezóny s identifikátorem " 
                    + inputOutputData.getPrimaryKey().getId() + " musí být v rozsahu 0 - 100");
        }
        
        if (inputOutputData.getOrderInTVShowSeason() <= 0)
        {
            throw new DatabaseException("Pořadí existující epizody sezóny s identifikátorem " 
                    + inputOutputData.getPrimaryKey().getId() + " musí být větší než nula");
        }
        
        if (inputOutputData.getTVSeasonForeignKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor sezóny pro existující epizodu s identifikátorem " 
                    + inputOutputData.getPrimaryKey().getId() + " musí být větší než nula");
        }
        
        if (inputOutputData.getRuntime() == null) 
        {
            throw new DatabaseException("Délka trvání existující epizody sezóny s identifikátorem "
                    + inputOutputData.getPrimaryKey().getId() + " musí být nastavena");
        }
        
        TVEpisode tvEpisodeWithDuplicateKey = getBy(inputOutputData.getPrimaryKey());
        
        if (tvEpisodeWithDuplicateKey != null) 
        {
            throw new DatabaseException("Identifikátor " + inputOutputData.getPrimaryKey().getId() 
                    + " existující epizody sezóny je duplicitní");
        }
        
        TVSeason existingTVSeason = dbContext.getTVSeasonsTable().getBy(inputOutputData.getTVSeasonForeignKey());
            
        if (existingTVSeason == null) 
        {
            throw new DatabaseException("Identifikátor sezóny pro existující epizodu s identifikátorem " 
                    + inputOutputData.getPrimaryKey().getId() + " neodkazuje na žádnou sezónu");
        }
        
        List<TVEpisode> tvEpisodeWithDuplicateData = filterBy(episode -> episode.equals(inputOutputData));
        
        if (tvEpisodeWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data existující epizody sezóny s identifikátorem " 
                    + inputOutputData.getPrimaryKey().getId() + " jsou duplicitní");
        }
        
        tvEpisodesData.add(inputOutputData);
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
        
        if (editedExistingData.getRuntime() == null) 
        {
            throw new DatabaseException("Editovaná epizoda sezóny musí mít délku trvání");
        }
        
        if (editedExistingData.getName() != null && editedExistingData.getName().length() > TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH) 
        {
            throw new DatabaseException("Název editované epizody sezóny nesmí mít délku větší než " + 
                    TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH + " znaků");
        }
        
        if (editedExistingData.getHyperlinkForContentWatch() != null && 
                editedExistingData.getHyperlinkForContentWatch().length() > TVEpisodeInputOutput.ATTRIBUTE_HYPERLINK_LENGTH) 
        {
            throw new DatabaseException("Odkaz ke zhlédnutí editované epizody sezóny nesmí mít délku větší než " + 
                    TVEpisodeInputOutput.ATTRIBUTE_HYPERLINK_LENGTH + " znaků");
        }
        
        if (editedExistingData.getShortContentSummary() != null && 
                editedExistingData.getShortContentSummary().length() > TVEpisodeInputOutput.ATTRIBUTE_SUMMARY_LENGTH) 
        {
            throw new DatabaseException("Krátké shrnutí obsahu editované epizody sezóny nesmí mít délku větší než " + 
                    TVEpisodeInputOutput.ATTRIBUTE_SUMMARY_LENGTH + " znaků");
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
