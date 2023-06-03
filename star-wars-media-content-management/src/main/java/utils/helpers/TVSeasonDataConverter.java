/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.helpers;

import app.models.data.PrimaryKey;
import app.models.data.TVSeason;
import app.models.input.TVSeasonInput;
import app.models.output.TVSeasonOutput;

/**
 *
 * @author Admin
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
