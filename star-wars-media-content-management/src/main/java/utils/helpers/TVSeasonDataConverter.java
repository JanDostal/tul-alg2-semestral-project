package utils.helpers;

import app.models.data.PrimaryKey;
import app.models.data.TVSeason;
import app.models.input.TVSeasonInput;
import app.models.output.TVSeasonOutput;

/**
 * Represents a TV season data converter for input, output and data model of TV season
 * TVSeasonDataConverter class is used when converting input file tv season data to database tv season data
 * TVSeasonDataConverter class is used when converting database tv season data to output file tv season data
 * TVSeasonDataConverter class is used when converting output file tv season data to database tv season data
 * @author jan.dostal
 */
public final class TVSeasonDataConverter 
{
    private TVSeasonDataConverter()
    {
    }
    
    public static TVSeasonOutput convertToOutputDataFrom(TVSeason data) 
    {
        int id = data.getPrimaryKey().getId();
        
        int orderInTVShow = data.getOrderInTVShow();
        
        int tvShowId = data.getTVShowForeignKey().getId();
        
        return new TVSeasonOutput(id, orderInTVShow, tvShowId);
    }
    
    public static TVSeason convertToDataFrom(TVSeasonInput inputData, PrimaryKey tvShowForeignKey)
    {
        PrimaryKey placeholderKey = new PrimaryKey(0);
        
        int orderInTVShow = inputData.getOrderInTVShow();
        
        return new TVSeason(placeholderKey, orderInTVShow, tvShowForeignKey);
    }
    
    public static TVSeason convertToDataFrom(TVSeasonOutput outputData)
    {
        PrimaryKey primaryKey = new PrimaryKey(outputData.getId());       
        
        int orderInTVShow = outputData.getOrderInTVShow();
        
        PrimaryKey tvShowForeignKey = new PrimaryKey(outputData.getTVShowId());
        
        return new TVSeason(primaryKey, orderInTVShow, tvShowForeignKey);
    }
}
