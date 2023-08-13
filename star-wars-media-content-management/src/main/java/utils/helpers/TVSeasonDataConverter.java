package utils.helpers;

import app.models.data.PrimaryKey;
import app.models.data.TVSeason;
import app.models.input.TVSeasonInput;
import app.models.output.TVSeasonOutput;

/**
 * Represents a TV season data converter helper class for input, input/output and data model of TV season.
 * TVSeasonDataConverter class is used when converting input file tv season data to database tv season data.
 * TVSeasonDataConverter class is used when converting database tv season data to input/output file tv season data.
 * TVSeasonDataConverter class is used when converting input/output file tv season data to database tv season data.
 * @author jan.dostal
 */
public final class TVSeasonDataConverter 
{
    private TVSeasonDataConverter()
    {
    }
    
    /**
     * Method converts tv season database model data into tv season input/output model data 
     * (from database to input/output files, writing into input/output files)
     * @param data represents tv season database data model
     * @return converted data as tv season input/output model data
     */
    public static TVSeasonOutput convertToInputOutputDataFrom(TVSeason data) 
    {
        int id = data.getPrimaryKey().getId();
        
        int orderInTVShow = data.getOrderInTVShow();
        
        int tvShowId = data.getTVShowForeignKey().getId();
        
        return new TVSeasonOutput(id, orderInTVShow, tvShowId);
    }
    
    /**
     * Method converts tv season input model data into tv season database model data 
     * (from input file to database, parsing input file)
     * @param inputData represents tv season input data model
     * @param tvShowForeignKey represents parent tv show primary key, to which to link/bind input tv season
     * @return converted data as tv season database model data
     */
    public static TVSeason convertToDataFrom(TVSeasonInput inputData, PrimaryKey tvShowForeignKey)
    {
        PrimaryKey placeholderKey = new PrimaryKey(0);
        
        int orderInTVShow = inputData.getOrderInTVShow();
        
        return new TVSeason(placeholderKey, orderInTVShow, tvShowForeignKey);
    }
    
    /**
     * Method converts tv season input/output model data into tv season database model data 
     * (from input/output file to database, parsing input/output file)
     * @param inputOutputData represents tv season input/output data model
     * @return converted data as tv season database model data
     */
    public static TVSeason convertToDataFrom(TVSeasonOutput inputOutputData)
    {
        PrimaryKey primaryKey = new PrimaryKey(inputOutputData.getId());       
        
        int orderInTVShow = inputOutputData.getOrderInTVShow();
        
        PrimaryKey tvShowForeignKey = new PrimaryKey(inputOutputData.getTVShowId());
        
        return new TVSeason(primaryKey, orderInTVShow, tvShowForeignKey);
    }
}
