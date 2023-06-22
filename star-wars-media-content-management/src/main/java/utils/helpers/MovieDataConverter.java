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
 * Represents a Movie data converter helper class for input, output and data model of movie.
 * MovieDataConverter class is used when converting input file movie data to database movie data.
 * MovieDataConverter class is used when converting database movie data to output file movie data.
 * MovieDataConverter class is used when converting output file movie data to database movie data.
 * @author jan.dostal
 */
public final class MovieDataConverter 
{
    private MovieDataConverter()
    {
    }
    
    /**
     * Method converts movie database model data into movie output model data 
     * (from database to output files, writing into output files)
     * @param data represents movie database model data
     * @return converted data as movie output model data
     */
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
    
    /**
     * Method converts movie input model data into movie database model data 
     * (from input file to database, parsing input file)
     * @param inputData represents movie input model data
     * @return converted data as movie database model data
     * @throws utils.exceptions.DataConversionException if input data 
     * release date in epoch seconds number is too big
     */
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
    
    /**
     * Method converts movie output model data into movie database model data 
     * (from output file to database, parsing output file)
     * @param outputData represents movie output model data
     * @return converted data as movie database model data
     * @throws utils.exceptions.DataConversionException if output data 
     * release date in epoch seconds number is too big
     */
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
