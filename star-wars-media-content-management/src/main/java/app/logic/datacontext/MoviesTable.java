
package app.logic.datacontext;

import app.models.data.Movie;
import app.models.data.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import utils.interfaces.IDataTable;

/**
 *
 * @author jan.dostal
 */
public class MoviesTable implements IDataTable<Movie>
{
    private static IDataTable<Movie> moviesTable;
    
    private List<Movie> moviesData;
    
    private Random primaryKeysGenerator;
    
    private MoviesTable() 
    {
        moviesData = new ArrayList<>();
        primaryKeysGenerator = new Random();
    }
    
    protected static IDataTable<Movie> getInstance() 
    {
        if (moviesTable == null) 
        {
            moviesTable = new MoviesTable();
        }
        
        return moviesTable;
    }
        
    public @Override Movie addFrom(Movie inputData) 
    {
        //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
                
        PrimaryKey newPrimaryKey = generatePrimaryKey();
        
        List<Movie> movieWithDuplicateData = filterBy(movie -> movie.equals(inputData));
        
        if (movieWithDuplicateData.isEmpty() == false) 
        {
            //exception
        }
        
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
        
        return newData;
    }

    public @Override void loadFrom(Movie outputData) 
    {
        //porovnavani spravnosti vystupnich dat (pozdeji exceptions)
        
        Movie movieWithDuplicateKey = getBy(outputData.getPrimaryKey());
        
        if (movieWithDuplicateKey != null) 
        {
            //exception
        }
        
        List<Movie> movieWithDuplicateData = filterBy(movie -> movie.equals(outputData));
        
        if (movieWithDuplicateData.isEmpty() == false) 
        {
            //exception
        }
        
        moviesData.add(outputData);
    }

    public @Override void deleteBy(PrimaryKey primaryKey) 
    {
        Movie foundMovie = getBy(primaryKey);
        
        if (foundMovie == null) 
        {
            //exception
        }
        
        moviesData.remove(foundMovie);
    }

    public @Override boolean editBy(PrimaryKey primaryKey, Movie editedExistingData) 
    {
        Movie foundMovie = getBy(primaryKey);
        
        if (foundMovie == null) 
        {
            //exception
        }
        
        List<Movie> movieWithDuplicateData = filterBy(movie -> 
                movie.getPrimaryKey().equals(foundMovie.getPrimaryKey()) == false
                && movie.equals(editedExistingData));
        
        if (movieWithDuplicateData.isEmpty() == false) 
        {
            //exception
        }
        
        //porovnavani spravnosti vstupnich dat (pozdeji exceptions)
        
        
        boolean wasDataChanged = false;
        
        if (Objects.equals(foundMovie.getShortContentSummary(), editedExistingData.getShortContentSummary()) == false || 
            foundMovie.getWasWatched() != editedExistingData.getWasWatched() ||
            Objects.equals(foundMovie.getRuntime(), editedExistingData.getRuntime()) == false ||
            Objects.equals(foundMovie.getReleaseDate(), editedExistingData.getReleaseDate()) == false ||
            foundMovie.getPercentageRating() != editedExistingData.getPercentageRating() ||
            Objects.equals(foundMovie.getName(), editedExistingData.getName()) == false ||
            Objects.equals(foundMovie.getHyperlinkForContentWatch(), 
                    editedExistingData.getHyperlinkForContentWatch()) == false ||
            foundMovie.getEra() != editedExistingData.getEra())
        {
            wasDataChanged = true;
        }
        
        
        if (wasDataChanged == true) 
        {
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
    
    private PrimaryKey generatePrimaryKey() 
    {
        boolean isSame = true;
        PrimaryKey generatedPrimaryKey = null;
        
        do 
        {
            int id = primaryKeysGenerator.nextInt(Integer.MAX_VALUE) + 1;
            generatedPrimaryKey = new PrimaryKey(id);
            
            Movie movieWithDuplicateKey = getBy(generatedPrimaryKey);  

            if (movieWithDuplicateKey == null)
            {
                isSame = false;
            }
        }
        while(isSame);
        
        return generatedPrimaryKey;
    }
}
