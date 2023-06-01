/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVEpisodeInput;
import app.models.output.TVEpisodeOutput;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import utils.interfaces.IDataFileManager;

/**
 *
 * @author Admin
 */
public class TVEpisodesFileManager implements IDataFileManager<TVEpisodeInput, TVEpisodeOutput>
{
    private static TVEpisodesFileManager tvEpisodesFileManager;
    
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
    
    private TVEpisodesFileManager(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        this.filenameSeparator = filenameSeparator;
        this.inputFileEndMarking = inputFileEndMarking;
        this.inputFileValuesSectionMarking = inputFileValuesSectionMarking;
        this.inputFileAttributesSectionMarking = inputFileAttributesSectionMarking;
    }
    
    protected static TVEpisodesFileManager getInstance(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        if (tvEpisodesFileManager == null) 
        {
            tvEpisodesFileManager = new TVEpisodesFileManager(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking);
        }
        
        return tvEpisodesFileManager;
    }
    
    public @Override List<TVEpisodeOutput> loadOutputDataFrom(boolean fromBinary) throws IOException, 
            FileNotFoundException 
    {
        
    }
    
    @Override
    public void tryDeleteDataOutputFilesCopies() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void tryCreateDataOutputFiles() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void saveOutputDataIntoFiles(List<TVEpisodeOutput> newOutputData) throws IOException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public @Override List<TVEpisodeInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileNotFoundException 
    {
        StringBuilder text = new StringBuilder();
        
        if (fromBinary == true) 
        {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(
                    FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                            DataStore.getBinaryInputTVEpisodesFilename()))) 
            {
                byte[] buffer = new byte[1024];
                int bytesRead;
                String textPart;

                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) 
                {
                    textPart = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                    text.append(textPart);
                }
            }
            
            File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputTVEpisodesFilename());
        
            if (binaryFile.length() == 0) 
            {
                //exception
            }
        }
        else 
        {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextInputTVEpisodesFilename()), StandardCharsets.UTF_8))) 
            {
                char[] buffer = new char[1024];
                int charsRead;
                String textPart;
            
                while((charsRead = bufferedReader.read(buffer)) != -1) 
                {
                    textPart = new String(buffer, 0, charsRead);
                    text.append(textPart);
                }
            }
        }
                                
        Class<?> tvEpisodeInputClass = TVEpisodeInput.class;
        Field[] tvEpisodeInputFields = tvEpisodeInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvEpisodeInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvEpisodeInputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvEpisodeInputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {                
                tvEpisodeInputFieldsIds.put(k + 1, field.getName());
                tvEpisodeInputFieldsValues.put(field.getName(), new StringBuilder());
                k++;
            }
        }
                
        List<TVEpisodeInput> parsedTVEpisodes = new ArrayList<>();
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        boolean isFileEmpty = true;
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            String textLine;
            
            if (sc.hasNextLine() == true && fromBinary == false) 
            {
                isFileEmpty = false;
            }
            
            while (sc.hasNextLine() == true) 
            {
                textLine = sc.nextLine();
                
                if (textLine.matches("^$") || textLine.matches("^[\\s\t]+$")) 
                {
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileEndMarking +
                        "[\\s\t]*$") && enteredSectionValues == true) 
                {
                    parseInputData(tvEpisodeInputFieldsValues, parsedTVEpisodes, tvEpisodeInputFields);
                    
                    break; 
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileValuesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == true)
                {
                    enteredSectionValues = true;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionValues == true) 
                {
                    parseInputData(tvEpisodeInputFieldsValues, parsedTVEpisodes,
                            tvEpisodeInputFields);
                    
                    enteredSectionAttributes = true;
                    enteredSectionValues = false;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == false) 
                {
                    enteredSectionAttributes = true;
                    continue;
                }
                
                if (enteredSectionValues == true)
                {
                    String[] parts = textLine.split(" (?=[^ ]+$)");
                    
                    if (parts.length != 2)
                    {
                        continue;
                    }
                    
                    int fieldId;
                    
                    try
                    {
                        fieldId = Integer.parseInt(parts[1]);
                    }
                    catch (NumberFormatException ex)
                    {
                        continue;
                    }
                    
                    String fieldName = tvEpisodeInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    StringBuilder fieldValue = tvEpisodeInputFieldsValues.get(fieldName);
                    StringBuilder newFieldValue = fieldValue.append(parts[0]);
                    
                    if (fieldName.equals("shortContentSummary"))
                    {
                        newFieldValue.append("\n");
                    }
                    
                    tvEpisodeInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        if (isFileEmpty == true && fromBinary == false) 
        {
            //exception
        }

        return parsedTVEpisodes;
    }
    
    private void parseInputData(Map<String, StringBuilder> tvEpisodeInputFieldsValues,
            List<TVEpisodeInput> parsedTVEpisodes, Field[] tvEpisodeInputFields) 
    {
        try 
        {
            long runtime = Long.parseLong(tvEpisodeInputFieldsValues.get("runtimeInSeconds").toString());
            int percentage = Integer.parseInt(tvEpisodeInputFieldsValues.get("percentageRating").toString());
            int orderInTVShowSeason = Integer.parseInt(tvEpisodeInputFieldsValues.get("orderInTVShowSeason").
                    toString());
                        
            parsedTVEpisodes.add(new TVEpisodeInput(runtime, tvEpisodeInputFieldsValues.get("name").toString(), 
                    percentage, tvEpisodeInputFieldsValues.get("hyperlinkForContentWatch").toString(), 
                    tvEpisodeInputFieldsValues.get("shortContentSummary").toString(), orderInTVShowSeason));
        }
        catch (NumberFormatException ex) 
        {   
        }
        finally 
        {
            tvEpisodeInputFieldsValues.clear();
            
            for (Field field : tvEpisodeInputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvEpisodeInputFieldsValues.put(field.getName(), new StringBuilder());
                }
            }
        }
    }
    
    private void parseOutputData(Map<String, StringBuilder> tvEpisodeOutputFieldsValues,
            List<TVEpisodeOutput> parsedTVEpisodes, Field[] tvEpisodeOutputFields) throws IOException 
    {        
        try 
        {
            int id = Integer.parseInt(tvEpisodeOutputFieldsValues.get("id").toString());
            long runtime = Long.parseLong(tvEpisodeOutputFieldsValues.get("runtimeInSeconds").toString());
            int percentage = Integer.parseInt(tvEpisodeOutputFieldsValues.get("percentageRating").toString());
            int orderInTVShowSeason = Integer.parseInt(tvEpisodeOutputFieldsValues.get("orderInTVShowSeason").toString());
            int tvSeasonId = Integer.parseInt(tvEpisodeOutputFieldsValues.get("tvSeasonId").toString());
          
            parsedTVEpisodes.add(new TVEpisodeOutput(id, runtime, tvEpisodeOutputFieldsValues.get("name").toString(), 
                    percentage, tvEpisodeOutputFieldsValues.get("hyperlinkForContentWatch").toString(), 
                    tvEpisodeOutputFieldsValues.get("shortContentSummary").toString(), orderInTVShowSeason, tvSeasonId));
            
            tvEpisodeOutputFieldsValues.clear();
            
            for (Field field : tvEpisodeOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvEpisodeOutputFieldsValues.put(field.getName(), new StringBuilder());
                }
            }
        }
        catch (NumberFormatException ex) 
        {
            throw new IOException();
        }
    }
    
    private StringBuilder createOutputDataTextRepresentation(List<TVEpisodeOutput> newOutputTVEpisodes) 
    {
        Class<?> tvEpisodeOutputClass = TVEpisodeOutput.class;
        Field[] tvEpisodeOutputFields = tvEpisodeOutputClass.getDeclaredFields();
        Map<String, Integer> tvEpisodeOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvEpisodeOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvEpisodeOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder outputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;
        StringBuilder changedStringField;

        for (TVEpisodeOutput m : newOutputTVEpisodes) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append(attributesMarking).append("\n");
            outputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : tvEpisodeOutputFieldsIds.entrySet()) 
            {
                outputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = inputFileValuesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append("\n");
            outputTextData.append(valuesMarking).append("\n");
            outputTextData.append("\n");

            outputTextData.append(m.getId()).append(" ").
                    append(tvEpisodeOutputFieldsIds.get("id")).
                    append("\n");
            outputTextData.append(m.getRuntimeInSeconds()).
                    append(" ").append(tvEpisodeOutputFieldsIds.get("runtimeInSeconds")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getName().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }
            
            outputTextData.append(changedStringField.toString()).append(" ").
                    append(tvEpisodeOutputFieldsIds.get("name")).append("\n");
            outputTextData.append(m.getPercentageRating()).
                    append(" ").append(tvEpisodeOutputFieldsIds.get("percentageRating")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getHyperlinkForContentWatch().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }
            
            outputTextData.append(changedStringField.toString()).
                    append(" ").append(tvEpisodeOutputFieldsIds.get("hyperlinkForContentWatch")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getShortContentSummary().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }

            String[] shortContentSummaryLines = changedStringField.toString().split("\n");

            for (int i = 0; i < shortContentSummaryLines.length; i++) 
            {
                outputTextData.append(shortContentSummaryLines[i]).append(" ").
                        append(tvEpisodeOutputFieldsIds.get("shortContentSummary")).append("\n");
            }

            outputTextData.append(m.getOrderInTVShowSeason()).
                    append(" ").append(tvEpisodeOutputFieldsIds.get("orderInTVShowSeason")).
                    append("\n");
            
            outputTextData.append(m.getTVSeasonId()).
                    append(" ").append(tvEpisodeOutputFieldsIds.get("tvSeasonId")).
                    append("\n").append("\n");
        }

        String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
        outputTextData.append(endMarking).append("\n");
        
        return outputTextData;
    }
}
