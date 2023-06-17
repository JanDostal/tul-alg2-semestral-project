
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
import java.time.ZoneOffset;
import utils.exceptions.DataConversionException;

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
        long runtime;
        
        if (data.getRuntime() == null) 
        {
            runtime = -1;
        }
        else 
        {
            runtime = data.getRuntime().toSeconds();
        }

        String name = data.getName();
        int percentage = data.getPercentageRating();
        String hyperlink = data.getHyperlinkForContentWatch();
        String content = data.getShortContentSummary();
        
        long epochSeconds;
        
        if (data.getReleaseDate() == null) 
        {
            epochSeconds = -1;
        }
        else 
        {
            LocalDateTime releaseDataDateTime = data.getReleaseDate().atStartOfDay();
            epochSeconds = releaseDataDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
        }
        
        String eraCodeDesignation = data.getEra().toString();
        
        return new MovieOutput(id, runtime, name, percentage, 
                hyperlink, content, epochSeconds, eraCodeDesignation);
    }
    
    public static Movie convertToDataFrom(MovieInput inputData) throws DataConversionException
    {
        PrimaryKey placeholderKey = new PrimaryKey(0);
        Duration runtime;
        
        if (inputData.getRuntimeInSeconds() <= 0) 
        {
            runtime = null;
        }
        else 
        {
            runtime = Duration.ofSeconds(inputData.getRuntimeInSeconds());
        }
        
        String name;
        
        if (inputData.getName().isBlank() || inputData.getName().isEmpty()) 
        {
            name = null;
        }
        else 
        {
            name = inputData.getName();
        }
        
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
        
        String hyperlink;
        
        if (inputData.getHyperlinkForContentWatch().isBlank() || 
                inputData.getHyperlinkForContentWatch().isEmpty()) 
        {
            hyperlink = null;
        }
        else 
        {
            hyperlink = inputData.getHyperlinkForContentWatch();
        }
        
        String content;
                
        if (inputData.getShortContentSummary().isBlank() || inputData.getShortContentSummary().isEmpty()) 
        {
            content = null;
        }
        else 
        {
            content = inputData.getShortContentSummary();
        }
        
        LocalDate releaseDate;
        
        if (inputData.getReleaseDateInEpochSeconds() < 0) 
        {
            releaseDate = null;
        }
        else 
        {
            try 
            {
                releaseDate = Instant.ofEpochSecond(inputData.getReleaseDateInEpochSeconds()).
                        atZone(ZoneOffset.UTC).toLocalDate();
            }
            catch (DateTimeException e) 
            {
                throw new DataConversionException("Příliš velký počet epoch sekund jako datum uvedení konvertovaného filmu");
            }
        }
        
        Era era;
                
        try 
        {
            era = Era.valueOf(inputData.getEraCodeDesignation());
        }
        catch (IllegalArgumentException ex) 
        {
            era = null;
        }
        
        return new Movie(placeholderKey, runtime, name, percentageRating, 
                wasWatched, hyperlink, content, releaseDate, era);
    }
    
    public static Movie convertToDataFrom(MovieOutput outputData) throws DataConversionException
    {
        PrimaryKey primaryKey = new PrimaryKey(outputData.getId());       
        Duration runtime;
        
        if (outputData.getRuntimeInSeconds() <= 0) 
        {
            runtime = null;
        }
        else 
        {
            runtime = Duration.ofSeconds(outputData.getRuntimeInSeconds());
        }
               
        StringBuilder name = new StringBuilder();
        
        for (char c : outputData.getName().toCharArray()) 
        {
            if (c != Character.MIN_VALUE) 
            {
                name.append(c);
            }
        }
        
        String stringName = name.toString();
        
        if (stringName.isBlank() || stringName.isEmpty()) 
        {
            stringName = null;
        }
        
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
                
        StringBuilder hyperlink = new StringBuilder();
        
        for (char c : outputData.getHyperlinkForContentWatch().toCharArray()) 
        {
            if (c != Character.MIN_VALUE) 
            {
                hyperlink.append(c);
            }
        }
        
        String stringHyperlink = hyperlink.toString();
        
        if (stringHyperlink.isBlank() || 
                stringHyperlink.isEmpty()) 
        {
            stringHyperlink = null;
        }
        
        StringBuilder content = new StringBuilder();
        
        for (char c : outputData.getShortContentSummary().toCharArray()) 
        {
            if (c != Character.MIN_VALUE) 
            {
                content.append(c);
            }
        }
        
        String stringContent = content.toString();
        
        if (stringContent.isBlank() || stringContent.isEmpty()) 
        {
            stringContent = null;
        }

        LocalDate releaseDate;
        
        if (outputData.getReleaseDateInEpochSeconds() < 0) 
        {
            releaseDate = null;
        }
        else 
        {
            try 
            {
                releaseDate = Instant.ofEpochSecond(outputData.getReleaseDateInEpochSeconds()).
                        atZone(ZoneOffset.UTC).toLocalDate();
            }
            catch (DateTimeException e) 
            {
                throw new DataConversionException("Příliš velký počet epoch sekund jako datum uvedení "
                        + "konvertovaného filmu s identifikátorem " + outputData.getId());
            }
        }
        
        StringBuilder stringEraCodeDesignation = new StringBuilder();
        
        for (char c : outputData.getEraCodeDesignation().toCharArray()) 
        {
            if (c != Character.MIN_VALUE) 
            {
                stringEraCodeDesignation.append(c);
            }
        }
        
        Era era;
                
        try 
        {
            era = Era.valueOf(stringEraCodeDesignation.toString());
        }
        catch (IllegalArgumentException ex) 
        {
            era = null;
        }
        
        return new Movie(primaryKey, runtime, stringName, percentage, wasWatched,
                stringHyperlink, stringContent, releaseDate, era);
    }
}
