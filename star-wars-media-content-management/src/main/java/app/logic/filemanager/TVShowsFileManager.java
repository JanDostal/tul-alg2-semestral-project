package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVShowInput;
import app.models.inputoutput.TVShowInputOutput;
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
 * Represents a tv shows file manager, which works specifically with TV shows data files.
 * TV shows file manager works with tv show input and input/output data models and implements IDataFileManager interface.
 * TV shows file manager is made available through accessor.
 * @author jan.dostal
 */
public class TVShowsFileManager implements IDataFileManager<TVShowInput, TVShowInputOutput>
{
    private static IDataFileManager<TVShowInput, TVShowInputOutput> tvShowsFileManager;
    
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
    
    /**
     * Creates singleton instance of TVShowsFileManager.
     * Receives filenameSeparator, inputFileEndMarking, inputFileValuesSectionMarking 
     * and inputFileAttributesSectionMarking parameters in constructor from {@link FileManagerAccessor} class.
     * @param filenameSeparator file path separator dependent on application running operating system
     * @param inputFileEndMarking control string for detecting file end
     * @param inputFileValuesSectionMarking control string for detecting values section in file
     * @param inputFileAttributesSectionMarking control string for detecting attributes section in file
     */
    private TVShowsFileManager(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        this.filenameSeparator = filenameSeparator;
        this.inputFileEndMarking = inputFileEndMarking;
        this.inputFileValuesSectionMarking = inputFileValuesSectionMarking;
        this.inputFileAttributesSectionMarking = inputFileAttributesSectionMarking;
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @param filenameSeparator file path separator dependent on application running operating system
     * @param inputFileEndMarking control string for detecting file end
     * @param inputFileValuesSectionMarking control string for detecting values section in file
     * @param inputFileAttributesSectionMarking control string for detecting attributes section in file
     * @return singleton instance of TVShowsFileManager as interface
     */
    protected static IDataFileManager<TVShowInput, TVShowInputOutput> getInstance(String filenameSeparator, 
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
    
    public @Override StringBuilder getTextInputOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextInputOutputTVShowsFilename()), StandardCharsets.UTF_8))) 
        {
            String textLine;
            
            while((textLine = bufferedReader.readLine()) != null) 
            {
               text.append(textLine);
            }
        }
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("Soubor " + 
                    DataStore.getTextInputOutputTVShowsFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputOutputTVShowsFilename());
        }
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextInputOutputTVShowsFilename() + " je prázdný");
            }
        }
        
        return text;
    }

    public @Override StringBuilder getBinaryInputOutputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
        String tvShowsDivider = "\n\n\n\n\n\n\n\n\n";
        
        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputTVShowsFilename())))) 
        {
            boolean fileEndReached = false;
            
            int tvShowId;
            char[] tvShowName;
            long tvShowReleaseDateInEpochSeconds;
            char[] tvShowEraCodeDesignation;

            while (fileEndReached == false) 
            {
                try 
                {
                    tvShowId = dataInputStream.readInt();

                    tvShowName = new char[TVShowInputOutput.ATTRIBUTE_NAME_LENGTH];

                    for (int i = 0; i < tvShowName.length; i++) 
                    {
                        tvShowName[i] = dataInputStream.readChar();
                    }

                    tvShowReleaseDateInEpochSeconds = dataInputStream.readLong();

                    tvShowEraCodeDesignation = new char[TVShowInputOutput.ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH];

                    for (int i = 0; i < tvShowEraCodeDesignation.length; i++) 
                    {
                        tvShowEraCodeDesignation[i] = dataInputStream.readChar();
                    }
                    
                    text.append(String.format("%-38s%d", "Identifikátor:", tvShowId)).append("\n");
                    text.append(String.format("%-38s%s", "Název:", new String(tvShowName))).append("\n");
                    text.append(String.format("%-38s%d", "Datum uvedení v epoch sekundách:", tvShowReleaseDateInEpochSeconds)).append("\n");
                    text.append(String.format("%-38s%s", "Chronologická éra:", new String(tvShowEraCodeDesignation))).append(tvShowsDivider);
                } 
                catch (EOFException e) 
                {
                    fileEndReached = true;
                }
            }
        }
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("Soubor " + 
                    DataStore.getBinaryInputOutputTVShowsFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryInputOutputTVShowsFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator 
                + DataStore.getBinaryInputOutputTVShowsFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputOutputTVShowsFilename() + " je prázdný");
        }
        
        text.delete(text.length() - tvShowsDivider.length(), text.length());
        
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
            String textLine;
            
            while((textLine = bufferedReader.readLine()) != null) 
            {
               text.append(textLine);
            }
        }
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("Soubor " + 
                    DataStore.getTextInputTVShowsFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputTVShowsFilename());
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
            byte[] buffer = new byte[8192];
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
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryInputTVShowsFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputTVShowsFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputTVShowsFilename() + " je prázdný");
        }
        
        return text;
    }
    
    public @Override List<TVShowInputOutput> loadInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException
    {
        List<TVShowInputOutput> parsedTVShows = new ArrayList<>();
        
        File inputOutputTVShowsText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getTextInputOutputTVShowsFilename());
        File inputOutputTVShowsBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryInputOutputTVShowsFilename());
        
        inputOutputTVShowsText.createNewFile();
        inputOutputTVShowsBinary.createNewFile();
        
        if (fromBinary == true) 
        {            
            String errorParsingMessage = "Soubor " + DataStore.getBinaryInputOutputTVShowsFilename()+ " má poškozená data";
            
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputTVShowsFilename())))) 
            {
                boolean fileEndReached = false;
                int tvShowId;
                char[] tvShowName;
                long tvShowReleaseDate;
                char[] tvShowEraCodeDesignation;
                
                while (fileEndReached == false) 
                {
                    try 
                    {
                        tvShowId = dataInputStream.readInt();

                        tvShowName = new char[TVShowInputOutput.ATTRIBUTE_NAME_LENGTH];

                        for (int i = 0; i < tvShowName.length; i++) 
                        {
                            tvShowName[i] = dataInputStream.readChar();
                        }

                        tvShowReleaseDate = dataInputStream.readLong();

                        tvShowEraCodeDesignation = new char[TVShowInputOutput.ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH];

                        for (int i = 0; i < tvShowEraCodeDesignation.length; i++) 
                        {
                            tvShowEraCodeDesignation[i] = dataInputStream.readChar();
                        }

                        parsedTVShows.add(new TVShowInputOutput(tvShowId, new String(tvShowName), 
                                tvShowReleaseDate, new String(tvShowEraCodeDesignation)));
                    }
                    catch (EOFException e) 
                    {
                        fileEndReached = true;
                    }
                }
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getBinaryInputOutputTVShowsFilename());
            }
            
            if (inputOutputTVShowsBinary.length() != 0 && parsedTVShows.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
        else
        {
            StringBuilder text = new StringBuilder();
            String errorParsingMessage = "Soubor " + DataStore.getTextInputOutputTVShowsFilename() + " má poškozená data";
            
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextInputOutputTVShowsFilename()), StandardCharsets.UTF_8))) 
            {
                String textLine;
            
                while((textLine = bufferedReader.readLine()) != null) 
                {
                    text.append(textLine);
                }
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getTextInputOutputTVShowsFilename());
            }

            Class<?> tvShowInputOutputClass = TVShowInputOutput.class;
            Field[] tvShowInputOutputFields = tvShowInputOutputClass.getDeclaredFields();
            Map<String, StringBuilder> tvShowInputOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> tvShowInputOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : tvShowInputOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvShowInputOutputFieldsIds.put(k + 1, field.getName());
                    tvShowInputOutputFieldsValues.put(field.getName(), new StringBuilder());
                    k++;
                }
            }

            boolean enteredSectionAttributes = false;
            boolean enteredSectionValues = false;
            boolean isFileEmpty = true;

            try (Scanner sc = new Scanner(text.toString())) 
            {
                String textLine;
                String[] fieldParts;
                int fieldId;
                String fieldName;
                StringBuilder fieldValue;
                StringBuilder newFieldValue;
                
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
                        try 
                        {
                            parseInputOutputData(tvShowInputOutputFieldsValues, parsedTVShows, tvShowInputOutputFields);
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
                            parseInputOutputData(tvShowInputOutputFieldsValues, parsedTVShows, tvShowInputOutputFields);
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
                        fieldParts = textLine.split(" (?=[^ ]+$)");

                        if (fieldParts.length != 2) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        try 
                        {
                            fieldId = Integer.parseInt(fieldParts[1]);
                        } 
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        fieldName = tvShowInputOutputFieldsIds.get(fieldId);

                        if (fieldName == null) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        fieldValue = tvShowInputOutputFieldsValues.get(fieldName);
                        newFieldValue = fieldValue.append(fieldParts[0]);

                        tvShowInputOutputFieldsValues.put(fieldName, newFieldValue);
                    }
                }
            }
            
            if (isFileEmpty == false && parsedTVShows.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
                                
        return parsedTVShows;
    }
    
    public @Override void tryDeleteDataInputOutputFilesCopies() 
    {
        File inputOutputTVShowsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextInputOutputTVShowsFilename());
        
        File inputOutputTVShowsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryInputOutputTVShowsFilename());
        
        inputOutputTVShowsTextCopy.delete();
        inputOutputTVShowsBinaryCopy.delete();
    }
         
    public @Override void transferBetweenInputOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
    {
        File inputOutputTVShowsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextInputOutputTVShowsFilename());
        
        File inputOutputTVShowsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryInputOutputTVShowsFilename());
        
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = inputOutputTVShowsTextCopy.getName();
            sourceBinaryFile = inputOutputTVShowsBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryInputOutputTVShowsFilename();
            destinationTextFile = DataStore.getTextInputOutputTVShowsFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextInputOutputTVShowsFilename();
            sourceBinaryFile = DataStore.getBinaryInputOutputTVShowsFilename();
            destinationBinaryFile = inputOutputTVShowsBinaryCopy.getName();
            destinationTextFile = inputOutputTVShowsTextCopy.getName();
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
            byte[] byteBuffer = new byte[8192];
            int bytesRead;
            String textLine;
            
            while ((textLine = bufferedReader.readLine()) != null) {
                
                bufferedWriter.write(textLine);
            }
            
            while ((bytesRead = dataInputStream.read(byteBuffer)) != -1) 
            {
                dataOutputStream.write(byteBuffer, 0, bytesRead);
            }
        }
        catch (IOException e) 
        {
            inputOutputTVShowsTextCopy.delete();
            inputOutputTVShowsBinaryCopy.delete();
            throw new IOException("Chyba při kopírování mezi výstupními soubory seriálů a kopiemi");
        }
    }
    
    public @Override void saveInputOutputDataIntoFiles(List<TVShowInputOutput> newInputOutputData) throws IOException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextInputOutputTVShowsFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputTVShowsFilename(), false)))) 
        {
            StringBuilder generatedTVShowsTextRepresentations = 
                    createInputOutputDataTextRepresentation(newInputOutputData);
            
            for (TVShowInputOutput m : newInputOutputData) 
            {
                dataOutputStream.writeInt(m.getId());
                
                for (char c : m.getName().toCharArray()) 
                {
                    dataOutputStream.writeChar(c);
                }
                
                dataOutputStream.writeLong(m.getReleaseDateInEpochSeconds());
                
                for (char c : m.getEraCodeDesignation().toCharArray()) 
                {
                    dataOutputStream.writeChar(c);
                }
            }

            bufferedWriter.write(generatedTVShowsTextRepresentations.toString());
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při zápisu výstupních souborů seriálů");
        }
    }
    
    public @Override Map<Integer, TVShowInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileEmptyException, FileNotFoundException, FileParsingException
    {
        StringBuilder text = new StringBuilder();
        
        if (fromBinary == true) 
        {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(
                    FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                            DataStore.getBinaryInputTVShowsFilename()))) 
            {
                byte[] buffer = new byte[8192];
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
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + 
                        DataStore.getBinaryInputTVShowsFilename());
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
                String textLine;
            
                while((textLine = bufferedReader.readLine()) != null) 
                {
                    text.append(textLine);
                }
            }
            catch (FileNotFoundException e) 
            {
                throw new FileNotFoundException("Soubor " + 
                        DataStore.getTextInputTVShowsFilename() + " neexistuje");
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + 
                        DataStore.getTextInputTVShowsFilename());
            }
            
            try (Scanner sc = new Scanner(text.toString())) 
            {
                if (sc.hasNextLine() == false)
                {
                    sc.close();
                    throw new FileEmptyException("Soubor " + DataStore.getTextInputTVShowsFilename() + " je prázdný");
                }
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
                
        Map<Integer, TVShowInput> parsedTVShows = new LinkedHashMap<>();
        int inputTVShowOrder = 0;
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            String textLine;
            String[] fieldParts;
            int fieldId;
            String fieldName;
            StringBuilder fieldValue;
            StringBuilder newFieldValue;
                 
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
                    parseInputData(tvShowInputFieldsValues, parsedTVShows, tvShowInputFields, inputTVShowOrder);
                    
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
                    parseInputData(tvShowInputFieldsValues, parsedTVShows, tvShowInputFields, inputTVShowOrder);
                    
                    inputTVShowOrder++;
                    enteredSectionAttributes = true;
                    enteredSectionValues = false;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == false) 
                {
                    inputTVShowOrder++;
                    enteredSectionAttributes = true;
                    continue;
                }
                
                if (enteredSectionValues == true)
                {
                    fieldParts = textLine.split(" (?=[^ ]+$)");
                    
                    if (fieldParts.length != 2)
                    {
                        continue;
                    }
                                        
                    try
                    {
                        fieldId = Integer.parseInt(fieldParts[1]);
                    }
                    catch (NumberFormatException ex)
                    {
                        continue;
                    }
                    
                    fieldName = tvShowInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    fieldValue = tvShowInputFieldsValues.get(fieldName);
                    newFieldValue = fieldValue.append(fieldParts[0]);
                                        
                    tvShowInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        if (parsedTVShows.isEmpty()) 
        {
            throw new FileParsingException(String.format("Nic se nenahrálo ze souboru %s", fromBinary == true ? 
                    DataStore.getBinaryInputTVShowsFilename() : DataStore.getTextInputTVShowsFilename()));
        }

        return parsedTVShows;
    }
    
    
    /**
     * Represents a method which parses tv show input data (one record) from input binary or text 
     * file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param tvShowInputFieldsValues represents list of parsed tv show record values mapped to
     * according tv show input model data attributes names.
     * @param parsedTVShows represents a list of currently parsed tv shows
     * @param tvShowInputFields represents tv show input model data attributes names, which are
     * used for indexing particular fields values.
     * @param inputTVShowOrder represents int value, which specifies tv show record loading 
     * order from file (record on start of file - value 1, record after - value 2).
     */
    private void parseInputData(Map<String, StringBuilder> tvShowInputFieldsValues,
            Map<Integer, TVShowInput> parsedTVShows, Field[] tvShowInputFields, int inputTVShowOrder)
    {
        try 
        {
            long epochSeconds = Long.parseLong(tvShowInputFieldsValues.get("releaseDateInEpochSeconds").toString());
                        
            parsedTVShows.put(inputTVShowOrder, new TVShowInput(tvShowInputFieldsValues.get("name").toString(), epochSeconds, 
                    tvShowInputFieldsValues.get("eraCodeDesignation").toString()));
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
    
    /**
     * Represents a method which parses tv show input/output data (one record) from input/output text file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param tvShowInputOutputFieldsValues represents list of parsed tv show record values mapped to
     * according tv show input/output model data attributes names.
     * @param parsedTVShows represents a list of currently parsed tv shows
     * @param tvShowInputOutputFields represents tv show input/output model data attributes names, which are
     * used for indexing particular fields values.
     * @throws NumberFormatException when parsed tv show record number values cannot be converted to from String
     */
    private void parseInputOutputData(Map<String, StringBuilder> tvShowInputOutputFieldsValues,
            List<TVShowInputOutput> parsedTVShows, Field[] tvShowInputOutputFields)
    {        
        int id = Integer.parseInt(tvShowInputOutputFieldsValues.get("id").toString());
        long epochSeconds = Long.parseLong(tvShowInputOutputFieldsValues.get("releaseDateInEpochSeconds").toString());

        parsedTVShows.add(new TVShowInputOutput(id, tvShowInputOutputFieldsValues.get("name").toString(),
                epochSeconds, tvShowInputOutputFieldsValues.get("eraCodeDesignation").toString()));

        tvShowInputOutputFieldsValues.clear();

        for (Field field : tvShowInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvShowInputOutputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    /**
     * Represents a method which creates tv show input/output data (multiple records) text representation
     * for input/output text file.
     * @param newInputOutputTVShows represents list of tv show input/output models data from database
     * @return text content to save into input/output text file
     */
    private StringBuilder createInputOutputDataTextRepresentation(List<TVShowInputOutput> newInputOutputTVShows) 
    {
        Class<?> tvShowInputOutputClass = TVShowInputOutput.class;
        Field[] tvShowInputOutputFields = tvShowInputOutputClass.getDeclaredFields();
        Map<String, Integer> tvShowInputOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvShowInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvShowInputOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder inputOutputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;
        StringBuilder changedStringField;

        for (TVShowInputOutput m : newInputOutputTVShows) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            inputOutputTextData.append(attributesMarking).append("\n");
            inputOutputTextData.append("\n");
            
            inputOutputTextData.append("Identificator: ").append(m.getId()).append("\n");
            
            inputOutputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : tvShowInputOutputFieldsIds.entrySet()) 
            {
                inputOutputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = inputFileValuesSectionMarking.replaceAll("\\\\", "");
            inputOutputTextData.append("\n");
            inputOutputTextData.append(valuesMarking).append("\n");
            inputOutputTextData.append("\n");

            inputOutputTextData.append(m.getId()).append(" ").
                    append(tvShowInputOutputFieldsIds.get("id")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getName().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }
            
            inputOutputTextData.append(changedStringField.toString()).append(" ").
                    append(tvShowInputOutputFieldsIds.get("name")).append("\n");
            inputOutputTextData.append(m.getReleaseDateInEpochSeconds()).
                    append(" ").append(tvShowInputOutputFieldsIds.get("releaseDateInEpochSeconds")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getEraCodeDesignation().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }
            
            inputOutputTextData.append(changedStringField.toString()).
                    append(" ").append(tvShowInputOutputFieldsIds.get("eraCodeDesignation")).
                    append("\n").append("\n");
        }

        if (newInputOutputTVShows.isEmpty() == false) 
        {
            String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
            inputOutputTextData.append(endMarking).append("\n");
        }
        
        return inputOutputTextData;
    }
}
