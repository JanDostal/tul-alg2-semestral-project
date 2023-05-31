/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVSeasonInput;
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
public class TVSeasonsFileManager 
{
    private static TVSeasonsFileManager tvSeasonsFileManager;
   
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
    
    
    private TVSeasonsFileManager(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        this.filenameSeparator = filenameSeparator;
        this.inputFileEndMarking = inputFileEndMarking;
        this.inputFileValuesSectionMarking = inputFileValuesSectionMarking;
        this.inputFileAttributesSectionMarking = inputFileAttributesSectionMarking;
    }
    
    protected static TVSeasonsFileManager getInstance(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        if (tvSeasonsFileManager == null) 
        {
            tvSeasonsFileManager = new TVSeasonsFileManager(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking);
        }
        
        return tvSeasonsFileManager;
    }
    
    public List<TVSeasonInput> loadInputTVSeasonsFromBinary() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();

        try (BufferedInputStream r = new BufferedInputStream(new FileInputStream(FileManagerAccessor.
                getDataDirectoryPath() + filenameSeparator + DataStore.getBinaryInputTVSeasonsFilename()))) 
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
                + DataStore.getBinaryInputTVSeasonsFilename());
        
        if (f.length() == 0) 
        {
            //exception
        }
                
        Class<?> tvSeasonInputClass = TVSeasonInput.class;
        Field[] tvSeasonInputFields = tvSeasonInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvSeasonInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvSeasonInputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvSeasonInputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {                
                tvSeasonInputFieldsIds.put(k + 1, field.getName());
                tvSeasonInputFieldsValues.put(field.getName(), new StringBuilder());
                k++;
            }
        }
                                
        List<TVSeasonInput> parsedSeasons = new ArrayList<>();
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
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedSeasons, tvSeasonInputFields);
                    
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
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedSeasons,
                            tvSeasonInputFields);
                    
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
                    
                    String fieldName = tvSeasonInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    StringBuilder fieldValue = tvSeasonInputFieldsValues.get(fieldName);
                    StringBuilder newFieldValue = fieldValue.append(parts[0]);
                    
                    tvSeasonInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        return parsedSeasons;
    }
    
     public List<TVSeasonInput> loadInputTVSeasonsFromText() throws IOException, 
            FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                        DataStore.getTextInputTVSeasonsFilename()), StandardCharsets.UTF_8))) 
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
            
        Class<?> tvSeasonInputClass = TVSeasonInput.class;
        Field[] tvSeasonInputFields = tvSeasonInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvSeasonInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvSeasonInputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvSeasonInputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {                
                tvSeasonInputFieldsIds.put(k + 1, field.getName());
                tvSeasonInputFieldsValues.put(field.getName(), new StringBuilder());
                k++;
            }
        }
                
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        boolean isFileEmpty = true;
        
        List<TVSeasonInput> parsedTVSeasons = new ArrayList<>();
        
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
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedTVSeasons,
                            tvSeasonInputFields);
                     
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
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedTVSeasons, tvSeasonInputFields);
                    
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
                    
                    String fieldName = tvSeasonInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    StringBuilder fieldValue = tvSeasonInputFieldsValues.get(fieldName);
                    StringBuilder newFieldValue = fieldValue.append(parts[0]);
                    
                    tvSeasonInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        if (isFileEmpty == true) 
        {
            //exception
        }
        
        return parsedTVSeasons;
    }
    
    private void parseTVSeasonInputData(Map<String, StringBuilder> tvSeasonInputFieldsValues,
            List<TVSeasonInput> parsedEpisodes, Field[] tvSeasonInputFields) 
    {
        try 
        {
            int orderInTVShow = Integer.parseInt(tvSeasonInputFieldsValues.
                    get("orderInTVShow").toString());
                        
            parsedEpisodes.add(new TVSeasonInput(orderInTVShow));
        }
        catch (NumberFormatException ex) 
        {   
        }
        finally 
        {
            tvSeasonInputFieldsValues.clear();
            
            for (Field field : tvSeasonInputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvSeasonInputFieldsValues.put(field.getName(), new StringBuilder());
                }
            }
        }
    }
}
