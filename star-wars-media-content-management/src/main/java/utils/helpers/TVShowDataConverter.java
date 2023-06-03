
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

/**
 *
 * @author Admin
 */
public final class TVShowDataConverter 
{
    private TVShowDataConverter()
    {
    }
    
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
        
        String era = data.getEra().toString();
        
        return new TVShowOutput(id, name, epochSeconds, era);
    }
    
    public static TVShow convertToDataFrom(TVShowInput inputData) throws DateTimeException
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
         
        //exception
        LocalDate releaseDate;
        
        if (inputData.getReleaseDateInEpochSeconds() < 0) 
        {
            releaseDate = null;
        }
        else 
        {
            releaseDate = Instant.ofEpochSecond(inputData.getReleaseDateInEpochSeconds()).
                atZone(ZoneOffset.UTC).toLocalDate();
        }
        
        //exception
        Era era;
                
        try 
        {
            era = Era.valueOf(inputData.getEra());
        }
        catch (IllegalArgumentException ex) 
        {
            era = null;
        }
        
        return new TVShow(placeholderKey, name, releaseDate, era);
    }
    
    public static TVShow convertToDataFrom(TVShowOutput outputData) throws DateTimeException
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

        //exception
        LocalDate releaseDate;
        
        if (outputData.getReleaseDateInEpochSeconds() < 0) 
        {
            releaseDate = null;
        }
        else 
        {
            releaseDate = Instant.ofEpochSecond(outputData.getReleaseDateInEpochSeconds()).
                atZone(ZoneOffset.UTC).toLocalDate();
        }
        
        StringBuilder stringEra = new StringBuilder();
        
        for (char c : outputData.getEra().toCharArray()) 
        {
            if (c != Character.MIN_VALUE) 
            {
                stringEra.append(c);
            }
        }
        
        //exception
        Era era;
                
        try 
        {
            era = Era.valueOf(stringEra.toString());
        }
        catch (IllegalArgumentException ex) 
        {
            era = null;
        }
        
        return new TVShow(primaryKey, stringName, releaseDate, era);
    }
}
