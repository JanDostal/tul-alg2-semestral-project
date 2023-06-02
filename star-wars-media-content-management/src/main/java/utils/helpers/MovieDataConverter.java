
package utils.helpers;

import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.input.MovieInput;
import app.models.output.MovieOutput;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * meziclanek mezi databazi a soubory
 * @author Admin
 */
public final class MovieDataConverter 
{
    private MovieDataConverter()
    {
    }
    
    
    public static MovieOutput convertToOutputDataFrom(Movie data) 
    {
        int id = data.getPrimaryKey().getId();
        long runtime = data.getRuntime().toSeconds();
        String name = data.getName();
        int percentage = data.getPercentageRating();
        String hyperlink = data.getHyperlinkForContentWatch();
        String content = data.getShortContentSummary();
        
        LocalDateTime releaseDataDateTime = data.getReleaseDate().atStartOfDay();
        long epochSeconds = releaseDataDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        
        String era = data.getEra().toString();
        
        return new MovieOutput(id, runtime, name, percentage, 
                hyperlink, content, epochSeconds, era);
    }
    
    public static Movie convertToDataFrom(MovieInput inputData) throws DateTimeException, 
            IllegalArgumentException
    {
        PrimaryKey placeholderKey = new PrimaryKey(0);
        Duration runtime = Duration.ofSeconds(inputData.getRuntimeInSeconds());
        String name = inputData.getName();
        int percentageRating = inputData.getPercentageRating();
        boolean wasWatched;
        
        if (percentageRating < 0) 
        {
            wasWatched = false;
        }
        else 
        {
            wasWatched = true;
        }
        
        String hyperlink = inputData.getHyperlinkForContentWatch();
        String content = inputData.getShortContentSummary();
        
        //exception
        LocalDate releaseDate = Instant.ofEpochSecond(inputData.getReleaseDateInEpochSeconds()).
                atZone(ZoneId.systemDefault()).toLocalDate();
        
        //exception
        Era era = Era.valueOf(inputData.getEra());
        
        return new Movie(placeholderKey, runtime, name, percentageRating, 
                wasWatched, hyperlink, content, releaseDate, era);
    }
    
    public static Movie convertToDataFrom(MovieOutput outputData) throws DateTimeException, 
            IllegalArgumentException
    {
        PrimaryKey primaryKey = new PrimaryKey(outputData.getId());
        Duration runtime = Duration.ofSeconds(outputData.getRuntimeInSeconds());
        String name = outputData.getName();
        int percentage = outputData.getPercentageRating();
        boolean wasWatched;
        
        if (percentage < 0) 
        {
            wasWatched = false;
        }
        else 
        {
            wasWatched = true;
        }
        
        String hyperlink = outputData.getHyperlinkForContentWatch();
        String content = outputData.getShortContentSummary();
        
        //exception
        LocalDate releaseDate = Instant.ofEpochSecond(outputData.getReleaseDateInEpochSeconds()).
                atZone(ZoneId.systemDefault()).toLocalDate();
        
        //exception
        Era era = Era.valueOf(outputData.getEra());
        
        return new Movie(primaryKey, runtime, name, percentage, wasWatched,
                hyperlink, content, releaseDate, era);
    }
}
