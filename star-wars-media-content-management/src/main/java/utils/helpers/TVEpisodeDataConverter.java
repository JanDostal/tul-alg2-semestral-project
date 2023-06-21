package utils.helpers;

import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.input.TVEpisodeInput;
import app.models.output.TVEpisodeOutput;
import java.time.Duration;

/**
 * Represents a TV episode data converter helper class for input, output and data model of TV episode.
 * TVEpisodeDataConverter class is used when converting input file tv episode data to database tv episode data.
 * TVEpisodeDataConverter class is used when converting database tv episode data to output file tv episode data.
 * TVEpisodeDataConverter class is used when converting output file tv episode data to database tv episode data.
 * @author jan.dostal
 */
public final class TVEpisodeDataConverter 
{
    private TVEpisodeDataConverter() 
    {
    }
    
    /**
     * Method converts tv episode database model data into tv episode output model data 
     * (from database to output files, writing into output files)
     * @param data represents tv episode database model data
     * @return converted data as tv episode output model data
     */
    public static TVEpisodeOutput convertToOutputDataFrom(TVEpisode data) 
    {
        int id = data.getPrimaryKey().getId();
        long runtime = data.getRuntime().toSeconds();
        
        String name = data.getName();
        int percentage = data.getPercentageRating();
        String hyperlink = data.getHyperlinkForContentWatch();
        String content = data.getShortContentSummary();
        
        int orderInTVShowSeason = data.getOrderInTVShowSeason();
        int tvSeasonId = data.getTVSeasonForeignKey().getId();
        
        return new TVEpisodeOutput(id, runtime, name, percentage, 
                hyperlink, content, orderInTVShowSeason, tvSeasonId);
    }
    
    /**
     * Method converts tv episode input model data into tv episode database model data 
     * (from input file to database, parsing input file)
     * @param inputData represents tv episode input data model
     * @param tvSeasonForeignKey represents chosen parent tv season primary key, to which to link/bind input tv episode
     * @return converted data as tv episode database model data
     */
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
    
    /**
     * Method converts tv episode output model data into tv episode database model data 
     * (from output file to database, parsing output file)
     * @param outputData represents tv episode output data model
     * @return converted data as tv episode database model data
     */
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
