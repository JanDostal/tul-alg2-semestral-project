/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVEpisodeInput;
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

/**
 *
 * @author Admin
 */
public class TVEpisodesFileManager 
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
    
    public List<TVEpisodeInput> loadInputTVEpisodesFromBinary() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        
        try ( BufferedInputStream r = new BufferedInputStream(new FileInputStream(FileManagerAccessor.
                getDataDirectoryPath() + filenameSeparator + DataStore.getBinaryInputTVEpisodesFilename()))) 
        {
            byte[] buffer = new byte[1024];
            int bytesRead;
            String textPart;

            while ((bytesRead = r.read(buffer)) != -1) 
            {
                textPart = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                text.append(textPart);
            }
        }
        
        File f = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputTVEpisodesFilename());
        
        if (f.length() == 0) 
        {
            //exception
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
               
        List<TVEpisodeInput> parsedEpisodes = new ArrayList<>();
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            String textLine;
            
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
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes, tvEpisodeInputFields);
                    
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
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes,
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
        
        return parsedEpisodes;
    }
    
    public List<TVEpisodeInput> loadInputTVEpisodesFromText() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                        DataStore.getTextInputTVEpisodesFilename()), StandardCharsets.UTF_8))) 
        {
            char[] buffer = new char[1024];
            int bytesRead;
            String textPart;
            
            while((bytesRead = r.read(buffer)) != -1) 
            {
               textPart = new String(buffer, 0, bytesRead);
               text.append(textPart);
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
        
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        boolean isFileEmpty = true;
        
        List<TVEpisodeInput> parsedEpisodes = new ArrayList<>();
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            String textLine;
            
            if (sc.hasNextLine() == true) 
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
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes,
                            tvEpisodeInputFields);
                     
                    break;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileValuesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == true)
                {
                    enteredSectionValues = true;
                    continue;
                }
                else if ((textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionValues == true)) 
                {
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes,
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

        if (isFileEmpty == true) 
        {
            //exception
        }
        
        return parsedEpisodes;
    }
    
    private void parseTVEpisodeInputData(Map<String, StringBuilder> tvEpisodeInputFieldsValues,
            List<TVEpisodeInput> parsedEpisodes, Field[] tvEpisodeInputFields) 
    {
        try 
        {
            long runtime = Long.parseLong(tvEpisodeInputFieldsValues.get("runtimeInSeconds").toString());
            int percentage = Integer.parseInt(tvEpisodeInputFieldsValues.get("percentageRating").toString());
            int orderInTVShowSeason = Integer.parseInt(tvEpisodeInputFieldsValues.get("orderInTVShowSeason").
                    toString());
                        
            parsedEpisodes.add(new TVEpisodeInput(runtime, tvEpisodeInputFieldsValues.get("name").toString(), 
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
}
