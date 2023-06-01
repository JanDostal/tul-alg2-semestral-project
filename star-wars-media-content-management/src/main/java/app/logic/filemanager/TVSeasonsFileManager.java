/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVSeasonInput;
import app.models.output.TVSeasonOutput;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
public class TVSeasonsFileManager implements IDataFileManager<TVSeasonInput, TVSeasonOutput>
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
    
    public @Override List<TVSeasonOutput> loadOutputDataFrom(boolean fromBinary) throws FileNotFoundException, IOException
    {
        List<TVSeasonOutput> parsedTVSeasons = new ArrayList<>();
        
        if (fromBinary == true) 
        {
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVSeasonsFilename())))) 
            {
                boolean fileEndReached = false;
                int tvSeasonId;
                int tvSeasonOrderInTVShow;
                int tvSeasonTVShowId;
                
                while (fileEndReached == false) 
                {
                    try 
                    {
                        tvSeasonId = dataInputStream.readInt();
                        tvSeasonOrderInTVShow = dataInputStream.readInt();
                        tvSeasonTVShowId = dataInputStream.readInt();

                        parsedTVSeasons.add(new TVSeasonOutput(tvSeasonId, tvSeasonOrderInTVShow, tvSeasonTVShowId));
                    }
                    catch (EOFException e) 
                    {
                        fileEndReached = true;
                    }
                }
            }
            
            File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryOutputTVSeasonsFilename());
        
            if (binaryFile.length() == 0) 
            {
                //exception
            }
        }
        else 
        {
            StringBuilder text = new StringBuilder();
            
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextOutputTVSeasonsFilename()), StandardCharsets.UTF_8))) 
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

            Class<?> tvSeasonOutputClass = TVSeasonOutput.class;
            Field[] tvSeasonOutputFields = tvSeasonOutputClass.getDeclaredFields();
            Map<String, StringBuilder> tvSeasonOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> tvSeasonOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : tvSeasonOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvSeasonOutputFieldsIds.put(k + 1, field.getName());
                    tvSeasonOutputFieldsValues.put(field.getName(), new StringBuilder());
                    k++;
                }
            }

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
                    else if (textLine.matches("^[\\s\t]*" + inputFileEndMarking 
                            + "[\\s\t]*$") && enteredSectionValues == true) 
                    {
                        parseOutputData(tvSeasonOutputFieldsValues, parsedTVSeasons, tvSeasonOutputFields);

                        break;
                    } 
                    else if (textLine.matches("^[\\s\t]*" + inputFileValuesSectionMarking
                            + "[\\s\t]*$") && enteredSectionAttributes == true) 
                    {
                        enteredSectionValues = true;

                        continue;
                    } 
                    else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking
                            + "[\\s\t]*$") && enteredSectionValues == true) 
                    {
                        parseOutputData(tvSeasonOutputFieldsValues, parsedTVSeasons, tvSeasonOutputFields);

                        enteredSectionAttributes = true;
                        enteredSectionValues = false;

                        continue;
                    } 
                    else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking
                            + "[\\s\t]*$") && enteredSectionAttributes == false) 
                    {
                        enteredSectionAttributes = true;
                        continue;
                    }

                    if (enteredSectionValues == true)
                    {
                        String[] parts = textLine.split(" (?=[^ ]+$)");

                        if (parts.length != 2) 
                        {
                            throw new IOException();
                        }

                        int fieldId;

                        try 
                        {
                            fieldId = Integer.parseInt(parts[1]);
                        } 
                        catch (NumberFormatException ex) 
                        {
                            throw new IOException();
                        }

                        String fieldName = tvSeasonOutputFieldsIds.get(fieldId);

                        if (fieldName == null) 
                        {
                            throw new IOException();
                        }

                        StringBuilder fieldValue = tvSeasonOutputFieldsValues.get(fieldName);
                        StringBuilder newFieldValue = fieldValue.append(parts[0]);

                        tvSeasonOutputFieldsValues.put(fieldName, newFieldValue);
                    }
                }
            }

            if (isFileEmpty == true) {
                //exception
            }
        }
                                
        return parsedTVSeasons;
    }
    
    public @Override void tryDeleteDataOutputFilesCopies() 
    {
        File outputTVSeasonsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputTVSeasonsFilename());
        
        File outputTVSeasonsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputTVSeasonsFilename());
        
        outputTVSeasonsTextCopy.delete();
        outputTVSeasonsBinaryCopy.delete();
    }
    
    public @Override void tryCreateDataOutputFiles() throws IOException 
    {
        File outputTVSeasonsText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getTextOutputTVSeasonsFilename());
        
        File outputTVSeasonsBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryOutputTVSeasonsFilename());
        
        outputTVSeasonsText.createNewFile();
        outputTVSeasonsBinary.createNewFile();
    }
    
    public @Override void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException, 
            FileNotFoundException
    {
        File outputTVSeasonsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputTVSeasonsFilename());
        
        File outputTVSeasonsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputTVSeasonsFilename());
        
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = outputTVSeasonsTextCopy.getName();
            sourceBinaryFile = outputTVSeasonsBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryOutputTVSeasonsFilename();
            destinationTextFile = DataStore.getTextOutputTVSeasonsFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextOutputTVSeasonsFilename();
            sourceBinaryFile = DataStore.getBinaryOutputTVSeasonsFilename();
            destinationBinaryFile = outputTVSeasonsBinaryCopy.getName();
            destinationTextFile = outputTVSeasonsTextCopy.getName();
        }
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                sourceTextFile), StandardCharsets.UTF_8));
             DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new 
                FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                sourceBinaryFile)));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                destinationTextFile, false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + destinationBinaryFile, false)))
             )
        {
            char[] buffer = new char[1024];
            byte[] byteBuffer = new byte[1024];
            int charsRead;
            int bytesRead;
            
            while ((charsRead = bufferedReader.read(buffer)) != -1) {
                
                bufferedWriter.write(buffer, 0, charsRead);
            }
            
            while ((bytesRead = dataInputStream.read(byteBuffer)) != -1) 
            {
                dataOutputStream.write(byteBuffer, 0, bytesRead);
            }
        }
    }
    
    public @Override void saveOutputDataIntoFiles(List<TVSeasonOutput> newOutputData) throws IOException, 
            FileNotFoundException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextOutputTVSeasonsFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVSeasonsFilename(), false)))) 
        {
            StringBuilder generatedTVSeasonsTextRepresentations = 
                    createOutputDataTextRepresentation(newOutputData);
            
            for (TVSeasonOutput m : newOutputData) 
            {
                dataOutputStream.writeInt(m.getId());
                dataOutputStream.writeInt(m.getOrderInTVShow());
                dataOutputStream.writeInt(m.getTVShowId());
            }

            bufferedWriter.write(generatedTVSeasonsTextRepresentations.toString());
        }
    }
    
    public @Override List<TVSeasonInput> loadInputDataFrom(boolean fromBinary) throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        
        if (fromBinary == true) 
        {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(
                    FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                            DataStore.getBinaryInputTVSeasonsFilename()))) 
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
                + DataStore.getBinaryInputTVSeasonsFilename());
        
            if (binaryFile.length() == 0) 
            {
                //exception
            }
        }
        else 
        {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextInputTVSeasonsFilename()), StandardCharsets.UTF_8))) 
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
                
        List<TVSeasonInput> parsedTVSeasons = new ArrayList<>();
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
                    parseInputData(tvSeasonInputFieldsValues, parsedTVSeasons, tvSeasonInputFields);
                    
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
                    parseInputData(tvSeasonInputFieldsValues, parsedTVSeasons,
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
        
        if (isFileEmpty == true && fromBinary == false) 
        {
            //exception
        }

        return parsedTVSeasons;
    }
    
    private void parseInputData(Map<String, StringBuilder> tvSeasonInputFieldsValues,
            List<TVSeasonInput> parsedTVSeasons, Field[] tvSeasonInputFields) 
    {
        try 
        {
            int orderInTVShow = Integer.parseInt(tvSeasonInputFieldsValues.
                    get("orderInTVShow").toString());
                        
            parsedTVSeasons.add(new TVSeasonInput(orderInTVShow));
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
    
    private void parseOutputData(Map<String, StringBuilder> tvSeasonOutputFieldsValues,
            List<TVSeasonOutput> parsedTVSeasons, Field[] tvSeasonOutputFields) throws IOException 
    {        
        try 
        {
            int id = Integer.parseInt(tvSeasonOutputFieldsValues.get("id").toString());
            int orderInTVShow = Integer.parseInt(tvSeasonOutputFieldsValues.get("orderInTVShow").toString());
            int tvShowId = Integer.parseInt(tvSeasonOutputFieldsValues.get("tvShowId").toString());
          
            parsedTVSeasons.add(new TVSeasonOutput(id, orderInTVShow, tvShowId));
            
            tvSeasonOutputFieldsValues.clear();
            
            for (Field field : tvSeasonOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvSeasonOutputFieldsValues.put(field.getName(), new StringBuilder());
                }
            }
        }
        catch (NumberFormatException ex) 
        {
            throw new IOException();
        }
    }
    
    private StringBuilder createOutputDataTextRepresentation(List<TVSeasonOutput> newOutputTVSeasons) 
    {
        Class<?> tvSeasonOutputClass = TVSeasonOutput.class;
        Field[] tvSeasonOutputFields = tvSeasonOutputClass.getDeclaredFields();
        Map<String, Integer> tvSeasonOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvSeasonOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvSeasonOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder outputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;

        for (TVSeasonOutput m : newOutputTVSeasons) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append(attributesMarking).append("\n");
            outputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : tvSeasonOutputFieldsIds.entrySet()) 
            {
                outputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = inputFileValuesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append("\n");
            outputTextData.append(valuesMarking).append("\n");
            outputTextData.append("\n");

            outputTextData.append(m.getId()).append(" ").
                    append(tvSeasonOutputFieldsIds.get("id")).
                    append("\n");
            outputTextData.append(m.getOrderInTVShow()).
                    append(" ").append(tvSeasonOutputFieldsIds.get("orderInTVShow")).
                    append("\n");
            
            outputTextData.append(m.getTVShowId()).
                    append(" ").append(tvSeasonOutputFieldsIds.get("tvShowId")).
                    append("\n").append("\n");
        }

        String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
        outputTextData.append(endMarking).append("\n");
        
        return outputTextData;
    }
}
