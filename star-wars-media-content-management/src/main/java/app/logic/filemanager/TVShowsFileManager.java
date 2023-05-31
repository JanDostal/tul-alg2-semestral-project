/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVShowInput;
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
public class TVShowsFileManager 
{
    private static TVShowsFileManager tvShowsFileManager;
    
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
    
    private TVShowsFileManager(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        this.filenameSeparator = filenameSeparator;
        this.inputFileEndMarking = inputFileEndMarking;
        this.inputFileValuesSectionMarking = inputFileValuesSectionMarking;
        this.inputFileAttributesSectionMarking = inputFileAttributesSectionMarking;
    }
    
    protected static TVShowsFileManager getInstance(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        if (tvShowsFileManager == null) 
        {
            tvShowsFileManager = new TVShowsFileManager(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking);
        }
        
        return tvShowsFileManager;
    }
    
    public List<TVShowInput> loadInputTVShowsFromBinary() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();

        try (BufferedInputStream r = new BufferedInputStream(new FileInputStream(FileManagerAccessor.
                getDataDirectoryPath() + filenameSeparator + DataStore.getBinaryInputTVShowsFilename()))) 
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
                + DataStore.getBinaryInputTVShowsFilename());
        
        if (f.length() == 0) 
        {
            //exception
        }
        
        Class<?> tvShowInputClass = TVShowInput.class;
        Field[] tvShowInputFields = tvShowInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvShowInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvShowInputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvShowInputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {                
                tvShowInputFieldsIds.put(k + 1, field.getName());
                tvShowInputFieldsValues.put(field.getName(), new StringBuilder());
                k++;
            }
        }
                        
        List<TVShowInput> parsedShows = new ArrayList<>();
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
                    parseTVShowInputData(tvShowInputFieldsValues, parsedShows, tvShowInputFields);
                    
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
                    parseTVShowInputData(tvShowInputFieldsValues, parsedShows,
                            tvShowInputFields);
                    
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
                    
                    String fieldName = tvShowInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    StringBuilder fieldValue = tvShowInputFieldsValues.get(fieldName);
                    StringBuilder newFieldValue = fieldValue.append(parts[0]);
                    
                    tvShowInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        return parsedShows;
    }
    
    public List<TVShowInput> loadInputTVShowsFromText() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextInputTVShowsFilename()), StandardCharsets.UTF_8))) 
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
        
        Class<?> tvShowInputClass = TVShowInput.class;
        Field[] tvShowInputFields = tvShowInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvShowInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvShowInputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvShowInputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {                
                tvShowInputFieldsIds.put(k + 1, field.getName());
                tvShowInputFieldsValues.put(field.getName(), new StringBuilder());
                k++;
            }
        }
                                
        List<TVShowInput> parsedShows = new ArrayList<>();
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        boolean isFileEmpty = true;
                    
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
                    
                    parseTVShowInputData(tvShowInputFieldsValues, parsedShows, tvShowInputFields);
                     
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
                    parseTVShowInputData(tvShowInputFieldsValues, parsedShows,
                            tvShowInputFields);
                    
                    enteredSectionAttributes = true;
                    enteredSectionValues = false;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$")
                        && enteredSectionAttributes == false)
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
                    
                    String fieldName = tvShowInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    StringBuilder fieldValue = tvShowInputFieldsValues.get(fieldName);
                    StringBuilder newFieldValue = fieldValue.append(parts[0]);
                    
                    tvShowInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        if (isFileEmpty == true) 
        {
            //exception
        }
        
        return parsedShows;
    }
    
    private void parseTVShowInputData(Map<String, StringBuilder> tvShowInputFieldsValues,
            List<TVShowInput> parsedShows, Field[] tvShowInputFields)
    {
        try 
        {
            long epochSeconds = Long.parseLong(tvShowInputFieldsValues.get("releaseDateInEpochSeconds").
                toString());
                        
            parsedShows.add(new TVShowInput(tvShowInputFieldsValues.get("name").toString(), epochSeconds, 
                    tvShowInputFieldsValues.get("era").toString()));
        }
        catch (NumberFormatException ex) 
        {   
        }
        finally 
        {
            tvShowInputFieldsValues.clear();
            
            for (Field field : tvShowInputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvShowInputFieldsValues.put(field.getName(), new StringBuilder());
                }
            }
        }
    }
}
