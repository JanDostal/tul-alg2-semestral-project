package utils.helpers;

import app.models.data.Era;
import app.models.data.PrimaryKey;
import app.models.data.TVShow;
import app.models.input.TVShowInput;
import app.models.output.TVShowOutput;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import utils.exceptions.DataConversionException;

/**
 * Represents a TV show data converter helper class for input, output and data model of TV show.
 * TVShowDataConverter class is used when converting input file tv show data to database tv show data.
 * TVShowDataConverter class is used when converting database tv show data to output file tv show data.
 * TVShowDataConverter class is used when converting output file tv show data to database tv show data.
 * @author jan.dostal
 */
public final class TVShowDataConverter 
{
    private TVShowDataConverter()
    {
    }
    
    /**
     * Method converts tv show database model data into tv show output model data 
     * (from database to output files, writing into output files)
     * @param data represents tv show database data model
     * @return converted data as tv show output model data
     */
    public static TVShowOutput convertToOutputDataFrom(TVShow data) 
    {
        int id = data.getPrimaryKey().getId();
        
        String name = data.getName();
        
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
        
        return new TVShowOutput(id, name, epochSeconds, eraCodeDesignation);
    }
    
    /**
     * Method converts tv show input model data into tv show database model data 
     * (from input file to database, parsing input file)
     * @param inputData represents tv show input model data
     * @return converted data as tv show database model data
     * @throws utils.exceptions.DataConversionException if input data 
     * release date in epoch seconds number is too big
     */
    public static TVShow convertToDataFrom(TVShowInput inputData) throws DataConversionException
    {
        PrimaryKey placeholderKey = new PrimaryKey(0);
        
        String name;
        
        if (inputData.getName().isBlank() || inputData.getName().isEmpty()) 
        {
            name = null;
        }
        else 
        {
            name = inputData.getName();
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
                throw new DataConversionException("Příliš velký počet epoch sekund jako datum uvedení konvertovaného seriálu");
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
        
        return new TVShow(placeholderKey, name, releaseDate, era);
    }
    
    /**
     * Method converts tv show output model data into tv show database model data 
     * (from output file to database, parsing output file)
     * @param outputData represents tv show output model data
     * @return converted data as tv show database model data
     * @throws utils.exceptions.DataConversionException if output data 
     * release date in epoch seconds number is too big
     */
    public static TVShow convertToDataFrom(TVShowOutput outputData) throws DataConversionException
    {
        PrimaryKey primaryKey = new PrimaryKey(outputData.getId());       
               
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
                        + "konvertovaného seriálu s identifikátorem " + outputData.getId());
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
        
        return new TVShow(primaryKey, stringName, releaseDate, era);
    }
}
