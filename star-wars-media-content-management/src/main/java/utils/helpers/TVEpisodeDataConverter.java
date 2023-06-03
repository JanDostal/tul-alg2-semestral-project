/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.helpers;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.input.TVEpisodeInput;
import app.models.output.TVEpisodeOutput;
import java.time.Duration;

/**
 *
 * @author Admin
 */
public final class TVEpisodeDataConverter 
{
    private TVEpisodeDataConverter() 
    {
    }
    
    public static TVEpisodeOutput convertToOutputDataFrom(TVEpisode data) 
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
        
        int orderInTVShowSeason = data.getOrderInTVShowSeason();
        int tvSeasonId = data.getTVSeasonForeignKey().getId();
        
        return new TVEpisodeOutput(id, runtime, name, percentage, 
                hyperlink, content, orderInTVShowSeason, tvSeasonId);
    }
    
    public static TVEpisode convertToDataFrom(TVEpisodeInput inputData, PrimaryKey tvSeasonForeignKey)
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
        
        int orderInTVShowSeason = inputData.getOrderInTVShowSeason();
        
        return new TVEpisode(placeholderKey, runtime, name, percentageRating, 
                wasWatched, hyperlink, content, orderInTVShowSeason, tvSeasonForeignKey);
    }
    
    public static TVEpisode convertToDataFrom(TVEpisodeOutput outputData)
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

        int orderInTVShowSeason = outputData.getOrderInTVShowSeason();
        PrimaryKey tvSeasonForeignKey = new PrimaryKey(outputData.getTVSeasonId());
        
        return new TVEpisode(primaryKey, runtime, stringName, percentage, wasWatched,
                stringHyperlink, stringContent, orderInTVShowSeason, tvSeasonForeignKey);
    }
}
