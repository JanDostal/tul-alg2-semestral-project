package app.logic.datacontext;

import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.output.MovieOutput;
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
 * Represents a movies data table, which offers basic CRUD operations.
 * Movies data table works with movie data model and implements IDataTable interface.
 * Movies data table is made available through accessor and 
 * can communicate with other data tables through accessor or use common methods of accessor.
 * @author jan.dostal
 */
public class MoviesTable implements IDataTable<Movie>
{
    private static IDataTable<Movie> moviesTable;
    
    private final List<Movie> moviesData;
    
    private final DataContextAccessor dbContext;
    
    private final Random primaryKeysGenerator;
    
    /**
     * Creates singleton instance of MoviesTable.
     * Uses dependency injection to inject data context service.
     * @param dbContext Singleton instance of data context accessor. 
     * Can be used for using common methods from {@link DataContextAccessor} class or
     * communicating with other data tables.
     */
    private MoviesTable(DataContextAccessor dbContext) 
    {
        this.dbContext = dbContext;
        moviesData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @param dbContext singleton instance of data context accessor.
     * Can be used for using common methods from {@link DataContextAccessor} class or
     * communicating with other data tables.
     * @return singleton instance of MoviesTable as interface
     */
    protected static IDataTable<Movie> getInstance(DataContextAccessor dbContext) 
    {
        if (moviesTable == null) 
        {
            moviesTable = new MoviesTable(dbContext);
        }
        
        return moviesTable;
    }
        
    public @Override void addFrom(Movie inputData) throws DatabaseException
    {
        if (inputData == null) 
        {
            throw new DatabaseException("Data přidaného filmu jsou prázdná");
        }
        
        if (inputData.getPercentageRating() > 100) 
        {
            throw new DatabaseException("Procentuální hodnocení zhlédnutého přidaného filmu musí být v rozsahu 0 - 100");
        }
        
        if (inputData.getEra() == null) 
        {
            throw new DatabaseException("Chronologické období přidaného filmu musí být vybráno");
        }
        
        if (inputData.getName() == null) 
        {
            throw new DatabaseException("Přidaný film musí mít název");
        }
        
        if (inputData.getName().length() > MovieOutput.ATTRIBUTE_NAME_LENGTH) 
        {
            throw new DatabaseException("Název přidaného filmu nesmí mít délku větší než " + MovieOutput.ATTRIBUTE_NAME_LENGTH + 
                    " znaků");
        }
        
        if (inputData.getHyperlinkForContentWatch() != null && 
                inputData.getHyperlinkForContentWatch().length() > MovieOutput.ATTRIBUTE_HYPERLINK_LENGTH) 
        {
            throw new DatabaseException("Odkaz ke zhlédnutí přidaného filmu nesmí mít délku větší než " + 
                    MovieOutput.ATTRIBUTE_HYPERLINK_LENGTH + " znaků");
        }
        
        if (inputData.getShortContentSummary() != null && 
                inputData.getShortContentSummary().length() > MovieOutput.ATTRIBUTE_CONTENT_LENGTH) 
        {
            throw new DatabaseException("Krátké shrnutí obsahu přidaného filmu nesmí mít délku větší než " + 
                    MovieOutput.ATTRIBUTE_CONTENT_LENGTH + " znaků");
        }
            
        List<Movie> movieWithDuplicateData = filterBy(movie -> movie.equals(inputData));
        
        if (movieWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data přidaného filmu jsou duplicitní");
        }
        
        PrimaryKey newPrimaryKey = dbContext.generatePrimaryKey(this, primaryKeysGenerator);
        
        Movie newData = new Movie(newPrimaryKey, 
                    inputData.getRuntime(), 
                    inputData.getName(), 
                    inputData.getPercentageRating(), 
                    inputData.getWasWatched(), 
                    inputData.getHyperlinkForContentWatch(),
                    inputData.getShortContentSummary(), 
                    inputData.getReleaseDate(), 
                    inputData.getEra());
        
        moviesData.add(newData);
    }

    public @Override void loadFrom(Movie outputData) throws DatabaseException
    {
        if (outputData == null) 
        {
            throw new DatabaseException("Existující data filmu jsou prázdná");
        }
        
        if (outputData.getPrimaryKey().getId() <= 0) 
        {
            throw new DatabaseException("Identifikátor " + outputData.getPrimaryKey().getId() 
                    + " existujícího filmu musí být větší než nula");
        }
        
        if (outputData.getPercentageRating() > 100) 
        {
            throw new DatabaseException("Procentuální hodnocení zhlédnutého existujícího filmu s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " musí být v rozsahu 0 - 100");
        }
        
        if (outputData.getEra() == null) 
        {
            throw new DatabaseException("Chronologické období existujícího filmu s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " musí být vybráno");
        }
        
        if (outputData.getName() == null) 
        {
            throw new DatabaseException("Existující film s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " musí mít název");
        }
        
        Movie movieWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (movieWithDuplicateKey != null) 
        {
            throw new DatabaseException("Identifikátor " 
                    + outputData.getPrimaryKey().getId() + " existujícího filmu je duplicitní");
        }
        
        List<Movie> movieWithDuplicateData = filterBy(movie -> movie.equals(outputData));
        
        if (movieWithDuplicateData.isEmpty() == false) 
        {
            throw new DatabaseException("Data existujícího filmu s identifikátorem " 
                    + outputData.getPrimaryKey().getId() + " jsou duplicitní");
        }
        
        moviesData.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) throws DatabaseException
    {
        Movie foundMovie = getBy(primaryKey);
        
        if (foundMovie == null) 
        {
            throw new DatabaseException("Film vybraný pro smazání nebyl nalezen");
        }
        
        moviesData.remove(foundMovie);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, Movie editedExistingData) throws DatabaseException 
    {
        Movie foundMovie = getBy(primaryKey);
                
        if (foundMovie == null) 
        {
            throw new DatabaseException("Film vybraný pro editaci nebyl nalezen");
        }
        
        if (editedExistingData == null) 
        {
            throw new DatabaseException("Nová data editovaného filmu jsou prázdná");
        }
        
        if (editedExistingData.getPercentageRating() > 100) 
        {
            throw new DatabaseException("Procentuální hodnocení zhlédnutého editovaného filmu musí být v rozsahu 0 - 100");
        }
        
        if (editedExistingData.getEra() == null) 
        {
            throw new DatabaseException("Chronologické období editovaného filmu musí být vybráno");
        }
        
        if (editedExistingData.getName() == null) 
        {
            throw new DatabaseException("Editovaný film musí mít název");
        }
        
        if (editedExistingData.getName().length() > MovieOutput.ATTRIBUTE_NAME_LENGTH) 
        {
            throw new DatabaseException("Název editovaného filmu nesmí mít délku větší než " + MovieOutput.ATTRIBUTE_NAME_LENGTH + 
                    " znaků");
        }
        
        if (editedExistingData.getHyperlinkForContentWatch() != null && 
                editedExistingData.getHyperlinkForContentWatch().length() > MovieOutput.ATTRIBUTE_HYPERLINK_LENGTH) 
        {
            throw new DatabaseException("Odkaz ke zhlédnutí editovaného filmu nesmí mít délku větší než " + 
                    MovieOutput.ATTRIBUTE_HYPERLINK_LENGTH + " znaků");
        }
        
        if (editedExistingData.getShortContentSummary() != null && 
                editedExistingData.getShortContentSummary().length() > MovieOutput.ATTRIBUTE_CONTENT_LENGTH) 
        {
            throw new DatabaseException("Krátké shrnutí obsahu editovaného filmu nesmí mít délku větší než " + 
                    MovieOutput.ATTRIBUTE_CONTENT_LENGTH + " znaků");
        }
        
        boolean wasDataChanged = false;
        
        if (Objects.equals(foundMovie.getShortContentSummary(), editedExistingData.getShortContentSummary()) == false || 
            foundMovie.getWasWatched() != editedExistingData.getWasWatched() ||
            Objects.equals(foundMovie.getRuntime(), editedExistingData.getRuntime()) == false ||
            Objects.equals(foundMovie.getReleaseDate(), editedExistingData.getReleaseDate()) == false ||
            foundMovie.getPercentageRating() != editedExistingData.getPercentageRating() ||
            Objects.equals(foundMovie.getName(), editedExistingData.getName()) == false ||
            Objects.equals(foundMovie.getHyperlinkForContentWatch(), editedExistingData.getHyperlinkForContentWatch()) == false ||
            foundMovie.getEra() != editedExistingData.getEra())
        {
            wasDataChanged = true;
        }
        
        if (wasDataChanged == true) 
        {
            List<Movie> movieWithDuplicateData = filterBy(movie -> 
                movie.getPrimaryKey().equals(foundMovie.getPrimaryKey()) == false
                && movie.equals(editedExistingData));
        
            if (movieWithDuplicateData.isEmpty() == false) 
            {
                throw new DatabaseException("Upravená data editovaného filmu jsou duplicitní");
            }
            
            Movie newData = new Movie(primaryKey, 
                    editedExistingData.getRuntime(), 
                    editedExistingData.getName(), 
                    editedExistingData.getPercentageRating(), 
                    editedExistingData.getWasWatched(), 
                    editedExistingData.getHyperlinkForContentWatch(),
                    editedExistingData.getShortContentSummary(), 
                    editedExistingData.getReleaseDate(), 
                    editedExistingData.getEra());
            
            moviesData.remove(foundMovie);
            moviesData.add(newData);
        }
        
        return wasDataChanged;
    }

    public @Override Movie getBy(PrimaryKey primaryKey) 
    {
        Optional<Movie> foundMovie = moviesData.stream().filter(movie -> 
                movie.getPrimaryKey().equals(primaryKey)).findFirst();
        
        if (foundMovie.isEmpty()) 
        {
            return null;
        }
        
        return foundMovie.get();
    }

    public @Override List<Movie> getAll() 
    {
        List<Movie> moviesCopy = new ArrayList<>(moviesData);
        return moviesCopy;
    }

    public @Override List<Movie> filterBy(Predicate<Movie> condition) 
    {
        return moviesData.stream().filter(condition).collect(Collectors.toList());
    }

    public @Override void sortBy(Comparator<Movie> comparator, List<Movie> sourceList) 
    {
        Collections.sort(sourceList, comparator);
    }
    
    public @Override void sortByPrimaryKey(List<Movie> sourceList) 
    {
        Collections.sort(sourceList);
    }
    
    public @Override void clearData() 
    {
        moviesData.clear();
    }
}
