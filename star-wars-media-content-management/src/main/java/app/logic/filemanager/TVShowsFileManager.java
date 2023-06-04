package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVShowInput;
import app.models.output.TVShowOutput;
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
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;
import utils.interfaces.IDataFileManager;

/**
 *
 * @author Admin
 */
public class TVShowsFileManager implements IDataFileManager<TVShowInput, TVShowOutput>
{
    private static IDataFileManager<TVShowInput, TVShowOutput> tvShowsFileManager;
    
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
    
    protected static IDataFileManager<TVShowInput, TVShowOutput> getInstance(String filenameSeparator, 
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
    
    public @Override StringBuilder getTextOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextOutputTVShowsFilename()), StandardCharsets.UTF_8))) 
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
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextOutputTVShowsFilename() + " je prázdný");
            }
        }
        
        return text;
    }

    public @Override StringBuilder getBinaryOutputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
        
        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVShowsFilename())))) 
        {
            boolean fileEndReached = false;
            int tvShowId;
            char[] tvShowName;
            long tvShowReleaseDate;
            char[] tvShowEra;

            while (fileEndReached == false) 
            {
                try 
                {
                    tvShowId = dataInputStream.readInt();

                    tvShowName = new char[TVShowOutput.ATTRIBUTE_NAME_LENGTH];

                    for (int i = 0; i < tvShowName.length; i++) 
                    {
                        tvShowName[i] = dataInputStream.readChar();
                    }

                    tvShowReleaseDate = dataInputStream.readLong();

                    tvShowEra = new char[TVShowOutput.ATTRIBUTE_ERA_LENGTH];

                    for (int i = 0; i < tvShowEra.length; i++) 
                    {
                        tvShowEra[i] = dataInputStream.readChar();
                    }
                    
                    text.append(tvShowId).append(" ").append(new String(tvShowName))
                            .append(" ").append(tvShowReleaseDate).append(" ").append(new String(tvShowEra)).
                            append("\n\n");
                } 
                catch (EOFException e) 
                {
                    fileEndReached = true;
                }
            }
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator 
                + DataStore.getBinaryOutputTVShowsFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryOutputTVShowsFilename() + " je prázdný");
        }
        
        return text;
    }


    public @Override StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextInputTVShowsFilename()), StandardCharsets.UTF_8))) 
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
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextInputTVShowsFilename() + " je prázdný");
            }
        }
        
        return text;
    }
    
    public @Override StringBuilder getBinaryInputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
        
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(
                FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getBinaryInputTVShowsFilename()))) 
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
                + DataStore.getBinaryInputTVShowsFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputTVShowsFilename() + " je prázdný");
        }
        
        return text;
    }
    
    public @Override List<TVShowOutput> loadOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException
    {
        List<TVShowOutput> parsedTVShows = new ArrayList<>();
        
        if (fromBinary == true) 
        {
            File outputTVShowsBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryOutputTVShowsFilename());
            
            outputTVShowsBinary.createNewFile();
            
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVShowsFilename())))) 
            {
                boolean fileEndReached = false;
                int tvShowId;
                char[] tvShowName;
                long tvShowReleaseDate;
                char[] tvShowEra;
                
                while (fileEndReached == false) 
                {
                    try 
                    {
                        tvShowId = dataInputStream.readInt();

                        tvShowName = new char[TVShowOutput.ATTRIBUTE_NAME_LENGTH];

                        for (int i = 0; i < tvShowName.length; i++) 
                        {
                            tvShowName[i] = dataInputStream.readChar();
                        }

                        tvShowReleaseDate = dataInputStream.readLong();

                        tvShowEra = new char[TVShowOutput.ATTRIBUTE_ERA_LENGTH];

                        for (int i = 0; i < tvShowEra.length; i++) 
                        {
                            tvShowEra[i] = dataInputStream.readChar();
                        }

                        parsedTVShows.add(new TVShowOutput(tvShowId, new String(tvShowName), 
                                tvShowReleaseDate, new String(tvShowEra)));
                    }
                    catch (EOFException e) 
                    {
                        fileEndReached = true;
                    }
                }
            }
        }
        else
        {
            File outputTVShowsText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getTextOutputTVShowsFilename());
        
            outputTVShowsText.createNewFile();
            
            StringBuilder text = new StringBuilder();
            String errorParsingMessage = "Soubor " + DataStore.getTextOutputTVShowsFilename() + " má poškozená data";
            
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextOutputTVShowsFilename()), StandardCharsets.UTF_8))) 
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

            Class<?> tvShowOutputClass = TVShowOutput.class;
            Field[] tvShowOutputFields = tvShowOutputClass.getDeclaredFields();
            Map<String, StringBuilder> tvShowOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> tvShowOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : tvShowOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvShowOutputFieldsIds.put(k + 1, field.getName());
                    tvShowOutputFieldsValues.put(field.getName(), new StringBuilder());
                    k++;
                }
            }

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
                    else if (textLine.matches("^[\\s\t]*" + inputFileEndMarking 
                            + "[\\s\t]*$") && enteredSectionValues == true)
                    {
                        try 
                        {
                            parseOutputData(tvShowOutputFieldsValues, parsedTVShows, tvShowOutputFields);
                        }
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

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
                        try 
                        {
                            parseOutputData(tvShowOutputFieldsValues, parsedTVShows, tvShowOutputFields);
                        }
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }
                        
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
                            throw new FileParsingException(errorParsingMessage);
                        }

                        int fieldId;

                        try 
                        {
                            fieldId = Integer.parseInt(parts[1]);
                        } 
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        String fieldName = tvShowOutputFieldsIds.get(fieldId);

                        if (fieldName == null) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        StringBuilder fieldValue = tvShowOutputFieldsValues.get(fieldName);
                        StringBuilder newFieldValue = fieldValue.append(parts[0]);

                        tvShowOutputFieldsValues.put(fieldName, newFieldValue);
                    }
                }
            }
        }
                                
        return parsedTVShows;
    }
    
    public @Override void tryDeleteDataOutputFilesCopies() 
    {
        File outputTVShowsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputTVShowsFilename());
        
        File outputTVShowsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputTVShowsFilename());
        
        outputTVShowsTextCopy.delete();
        outputTVShowsBinaryCopy.delete();
    }
         
    public @Override void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
    {
        File outputTVShowsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputTVShowsFilename());
        
        File outputTVShowsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputTVShowsFilename());
        
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = outputTVShowsTextCopy.getName();
            sourceBinaryFile = outputTVShowsBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryOutputTVShowsFilename();
            destinationTextFile = DataStore.getTextOutputTVShowsFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextOutputTVShowsFilename();
            sourceBinaryFile = DataStore.getBinaryOutputTVShowsFilename();
            destinationBinaryFile = outputTVShowsBinaryCopy.getName();
            destinationTextFile = outputTVShowsTextCopy.getName();
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
        catch (IOException e) 
        {
            outputTVShowsTextCopy.delete();
            outputTVShowsBinaryCopy.delete();
            throw new IOException();
        }
    }
    
    public @Override void saveOutputDataIntoFiles(List<TVShowOutput> newOutputData) throws IOException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextOutputTVShowsFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVShowsFilename(), false)))) 
        {
            StringBuilder generatedTVShowsTextRepresentations = 
                    createOutputDataTextRepresentation(newOutputData);
            
            for (TVShowOutput m : newOutputData) 
            {
                dataOutputStream.writeInt(m.getId());
                
                for (char c : m.getName().toCharArray()) 
                {
                    dataOutputStream.writeChar(c);
                }
                
                dataOutputStream.writeLong(m.getReleaseDateInEpochSeconds());
                
                for (char c : m.getEra().toCharArray()) 
                {
                    dataOutputStream.writeChar(c);
                }
            }

            bufferedWriter.write(generatedTVShowsTextRepresentations.toString());
        }
    }
    
    public @Override List<TVShowInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileEmptyException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        
        if (fromBinary == true) 
        {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(
                    FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                            DataStore.getBinaryInputTVShowsFilename()))) 
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
            catch (FileNotFoundException e) 
            {
                throw new FileNotFoundException("Soubor " + 
                        DataStore.getBinaryInputTVShowsFilename() + " neexistuje");
            }
            
            File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputTVShowsFilename());
        
            if (binaryFile.length() == 0) 
            {
                throw new FileEmptyException("Soubor " + DataStore.getBinaryInputTVShowsFilename() + " je prázdný");
            }
        }
        else 
        {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextInputTVShowsFilename()), StandardCharsets.UTF_8))) 
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
            catch (FileNotFoundException e) 
            {
                throw new FileNotFoundException("Soubor " + 
                        DataStore.getTextInputTVShowsFilename() + " neexistuje");
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
                
        List<TVShowInput> parsedTVShows = new ArrayList<>();
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
                    parseInputData(tvShowInputFieldsValues, parsedTVShows, tvShowInputFields);
                    
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
                    parseInputData(tvShowInputFieldsValues, parsedTVShows,
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
        
        if (isFileEmpty == true && fromBinary == false) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getTextInputTVShowsFilename() + " je prázdný");
        }

        return parsedTVShows;
    }
    
    private void parseInputData(Map<String, StringBuilder> tvShowInputFieldsValues,
            List<TVShowInput> parsedTVShows, Field[] tvShowInputFields)
    {
        try 
        {
            long epochSeconds = Long.parseLong(tvShowInputFieldsValues.get("releaseDateInEpochSeconds").toString());
                        
            parsedTVShows.add(new TVShowInput(tvShowInputFieldsValues.get("name").toString(), epochSeconds, 
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
    
    private void parseOutputData(Map<String, StringBuilder> tvShowOutputFieldsValues,
            List<TVShowOutput> parsedTVShows, Field[] tvShowOutputFields)
    {        
        int id = Integer.parseInt(tvShowOutputFieldsValues.get("id").toString());
        long epochSeconds = Long.parseLong(tvShowOutputFieldsValues.get("releaseDateInEpochSeconds").toString());

        parsedTVShows.add(new TVShowOutput(id, tvShowOutputFieldsValues.get("name").toString(),
                epochSeconds, tvShowOutputFieldsValues.get("era").toString()));

        tvShowOutputFieldsValues.clear();

        for (Field field : tvShowOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvShowOutputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    private StringBuilder createOutputDataTextRepresentation(List<TVShowOutput> newOutputTVShows) 
    {
        Class<?> tvShowOutputClass = TVShowOutput.class;
        Field[] tvShowOutputFields = tvShowOutputClass.getDeclaredFields();
        Map<String, Integer> tvShowOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvShowOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvShowOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder outputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;
        StringBuilder changedStringField;

        for (TVShowOutput m : newOutputTVShows) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append(attributesMarking).append("\n");
            outputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : tvShowOutputFieldsIds.entrySet()) 
            {
                outputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = inputFileValuesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append("\n");
            outputTextData.append(valuesMarking).append("\n");
            outputTextData.append("\n");

            outputTextData.append(m.getId()).append(" ").
                    append(tvShowOutputFieldsIds.get("id")).
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
                    append(tvShowOutputFieldsIds.get("name")).append("\n");
            outputTextData.append(m.getReleaseDateInEpochSeconds()).
                    append(" ").append(tvShowOutputFieldsIds.get("releaseDateInEpochSeconds")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getEra().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }
            
            outputTextData.append(changedStringField.toString()).
                    append(" ").append(tvShowOutputFieldsIds.get("era")).
                    append("\n").append("\n");
        }

        if (newOutputTVShows.isEmpty() == false) 
        {
            String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
            outputTextData.append(endMarking).append("\n");
        }
        
        return outputTextData;
    }
}
