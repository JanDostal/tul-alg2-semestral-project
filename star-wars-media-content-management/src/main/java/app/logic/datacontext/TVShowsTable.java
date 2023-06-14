
package app.logic.datacontext;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import app.models.output.TVShowOutput;
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
public class TVShowsTable implements IDataTable<TVShow>
{
    private static IDataTable<TVShow> tvShowsTable;
    
    private final List<TVShow> tvShowsData;
        
    private final DataContextAccessor dbContext;
    
    private final Random primaryKeysGenerator;
    
    private TVShowsTable(DataContextAccessor dbContext) 
    {
        this.dbContext = dbContext;
        tvShowsData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    protected static IDataTable<TVShow> getInstance(DataContextAccessor dbContext) 
    {
        if (tvShowsTable == null) 
        {
            tvShowsTable = new TVShowsTable(dbContext);
        }
        
        return tvShowsTable;
    }
    
    public @Override void addFrom(TVShow inputData) throws DatabaseException
    {
        if (inputData == null) 
        {
            throw new DatabaseException("Data přidaného seriálu jsou prázdná");
        }
        
        if (inputData.getEra() == null) 
        {
            throw new DatabaseException("Chronologické období přidaného seriálu musí být vybráno");
        }
        
        if (inputData.getName() == null) 
        {
            throw new DatabaseException("Přidaný seriál musí mít název");
        }
        
        if (inputData.getName().length() > TVShowOutput.ATTRIBUTE_NAME_LENGTH) 
        {
            throw new DatabaseException("Název přidaného seriálu nesmí mít délku větší než " + TVShowOutput.ATTRIBUTE_NAME_LENGTH + 
                    " znaků");
        }
                
        List<TVShow> tvShowWithDuplicateData = filterBy(show -> show.equals(inputData));
        
        if (tvShowWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data přidaného seriálu jsou duplicitní");
        }
        
        PrimaryKey newPrimaryKey = dbContext.generatePrimaryKey(this, primaryKeysGenerator);
        
        TVShow newData = new TVShow(newPrimaryKey, inputData.getName(), 
                inputData.getReleaseDate(), inputData.getEra());
        tvShowsData.add(newData);        
    }

    public @Override void loadFrom(TVShow outputData) throws DatabaseException
    {
        if (outputData == null) 
        {
            throw new DatabaseException("Existující data seriálu jsou prázdná");
        }
        
        if (outputData.getPrimaryKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor " + outputData.getPrimaryKey().getId() + 
                    " existujícího seriálu musí být větší než nula");
        }
        
        if (outputData.getEra() == null) 
        {
            throw new DatabaseException("Chronologické období existujícího seriálu s identifikátorem " + 
                    outputData.getPrimaryKey().getId() + " musí být vybráno");
        }
        
        if (outputData.getName() == null) 
        {
            throw new DatabaseException("Existující seriál s identifikátorem " + 
                    outputData.getPrimaryKey().getId() + " musí mít název");
        }
        
        TVShow tvShowWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (tvShowWithDuplicateKey != null) 
        {
            throw new DatabaseException("Identifikátor " + outputData.getPrimaryKey().getId() + 
                    " existujícího seriálu je duplicitní");
        }
        
        List<TVShow> tvShowWithDuplicateData = filterBy(show -> show.equals(outputData));
        
        if (tvShowWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data existujícího seriálu s identifikátorem " + 
                    outputData.getPrimaryKey().getId() + " jsou duplicitní");
        }
        
        tvShowsData.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) throws DatabaseException
    {
        TVShow foundTVShow = getBy(primaryKey);
        
        if (foundTVShow == null) 
        {
            throw new DatabaseException("Seriál vybraný pro smazání nebyl nalezen");
        }
        
        List<TVSeason> showSeasons = dbContext.getTVSeasonsTable().filterBy(e -> 
                    e.getTVShowForeignKey().equals(primaryKey));
        List<TVEpisode> seasonEpisodes;
            
        for (TVSeason o : showSeasons) 
        {
            seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e ->
                    e.getTVSeasonForeignKey().equals(o.getPrimaryKey()));
                
            for (TVEpisode s : seasonEpisodes) 
            {
                try 
                {
                    dbContext.getTVEpisodesTable().deleteBy(s.getPrimaryKey());
                }
                catch (DatabaseException e) 
                {
                }
            }
            
            try 
            {
                dbContext.getTVSeasonsTable().deleteBy(o.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }
        
        tvShowsData.remove(foundTVShow);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, TVShow editedExistingData) throws DatabaseException
    {
        TVShow foundTVShow = getBy(primaryKey);
        
        if (foundTVShow == null) 
        {
            throw new DatabaseException("Seriál vybraný pro editaci nebyl nalezen");
        }
        
        if (editedExistingData == null) 
        {
            throw new DatabaseException("Nová data editovaného seriálu jsou prázdná");
        }
        
        if (editedExistingData.getEra() == null) 
        {
            throw new DatabaseException("Chronologické období editovaného seriálu musí být vybráno");
        }
        
        if (editedExistingData.getName() == null) 
        {
            throw new DatabaseException("Editovaný seriál musí mít název");
        }
        
        if (editedExistingData.getName().length() > TVShowOutput.ATTRIBUTE_NAME_LENGTH) 
        {
            throw new DatabaseException("Název editovaného seriálu nesmí mít délku větší než " + TVShowOutput.ATTRIBUTE_NAME_LENGTH + 
                    " znaků");
        }
        
        boolean wasDataChanged = false;
        
        if (Objects.equals(foundTVShow.getReleaseDate(), editedExistingData.getReleaseDate()) == false || 
            Objects.equals(foundTVShow.getName(), editedExistingData.getName()) == false || 
                foundTVShow.getEra() != editedExistingData.getEra())
        {
            wasDataChanged = true;
        }
        
        if (wasDataChanged == true) 
        {
            List<TVShow> showWithDuplicateData = filterBy(show -> 
                show.getPrimaryKey().equals(foundTVShow.getPrimaryKey()) == false
                && show.equals(editedExistingData));
        
            if (showWithDuplicateData.isEmpty() == false) 
            {
                throw new DatabaseException("Upravená data editovaného seriálu jsou duplicitní");
            }
            
            TVShow newData = new TVShow(primaryKey, editedExistingData.getName(), 
                    editedExistingData.getReleaseDate(), editedExistingData.getEra());
            
            tvShowsData.remove(foundTVShow);
            tvShowsData.add(newData);
        }
        
        return wasDataChanged;
    }

    public @Override TVShow getBy(PrimaryKey primaryKey) 
    {
        Optional<TVShow> tvShow = tvShowsData.stream().filter(show -> 
                show.getPrimaryKey().equals(primaryKey)).findFirst();
        
        if (tvShow.isEmpty()) 
        {
            return null;
        }
        
        return tvShow.get();
    }

    public @Override List<TVShow> getAll() 
    {
        List<TVShow> tvShowsCopy = new ArrayList<>(tvShowsData);
        return tvShowsCopy;
    }

    public @Override List<TVShow> filterBy(Predicate<TVShow> condition) 
    {
        return tvShowsData.stream().filter(condition).collect(Collectors.toList());
    }

    public @Override void sortBy(Comparator<TVShow> comparator, List<TVShow> sourceList) 
    {
        Collections.sort(sourceList, comparator);
    }
    
    public @Override void sortByPrimaryKey(List<TVShow> sourceList) 
    {
        Collections.sort(sourceList);
    }
    
    public @Override void clearData() 
    {
        tvShowsData.clear();
    }
}
