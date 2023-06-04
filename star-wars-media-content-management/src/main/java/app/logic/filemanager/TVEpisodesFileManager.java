
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVEpisodeInput;
import app.models.output.TVEpisodeOutput;
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
public class TVEpisodesFileManager implements IDataFileManager<TVEpisodeInput, TVEpisodeOutput>
{
    private static IDataFileManager<TVEpisodeInput, TVEpisodeOutput> tvEpisodesFileManager;
    
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
    
    protected static IDataFileManager<TVEpisodeInput, TVEpisodeOutput> getInstance(String filenameSeparator, 
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
    
    public @Override StringBuilder getTextOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextOutputTVEpisodesFilename()), StandardCharsets.UTF_8))) 
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
                throw new FileEmptyException("Soubor " + DataStore.getTextOutputTVEpisodesFilename() + " je prázdný");
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
                filenameSeparator + DataStore.getBinaryOutputTVEpisodesFilename())))) 
        {
            boolean fileEndReached = false;
            int tvEpisodeId;
            long tvEpisodeRuntime;
            char[] tvEpisodeName;
            int tvEpisodePercentageRating;
            char[] tvEpisodeHyperlink;
            char[] tvEpisodeContent;
            int tvEpisodeOrderInTVShowSeason;
            int tvEpisodeTVSeasonId;

            while (fileEndReached == false) 
            {
                try 
                {
                    tvEpisodeId = dataInputStream.readInt();
                    tvEpisodeRuntime = dataInputStream.readLong();

                    tvEpisodeName = new char[TVEpisodeOutput.ATTRIBUTE_NAME_LENGTH];

                    for (int i = 0; i < tvEpisodeName.length; i++) 
                    {
                        tvEpisodeName[i] = dataInputStream.readChar();
                    }

                    tvEpisodePercentageRating = dataInputStream.readInt();

                    tvEpisodeHyperlink = new char[TVEpisodeOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                    for (int i = 0; i < tvEpisodeHyperlink.length; i++) 
                    {
                        tvEpisodeHyperlink[i] = dataInputStream.readChar();
                    }

                    tvEpisodeContent = new char[TVEpisodeOutput.ATTRIBUTE_CONTENT_LENGTH];

                    for (int i = 0; i < tvEpisodeContent.length; i++) 
                    {
                        tvEpisodeContent[i] = dataInputStream.readChar();
                    }

                    tvEpisodeOrderInTVShowSeason = dataInputStream.readInt();

                    tvEpisodeTVSeasonId = dataInputStream.readInt();
                    
                    text.append(tvEpisodeId).append(" ").append(tvEpisodeRuntime).append(" ")
                            .append(new String(tvEpisodeName)).append(" ")
                            .append(tvEpisodePercentageRating).append(" ").append(new String(tvEpisodeHyperlink))
                            .append(" ").append(new String(tvEpisodeContent)).append(" ")
                            .append(tvEpisodeOrderInTVShowSeason).append(" ")
                            .append(tvEpisodeTVSeasonId).append("\n\n");
                } 
                catch (EOFException e) 
                {
                    fileEndReached = true;
                }
            }
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator 
                + DataStore.getBinaryOutputTVEpisodesFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryOutputTVEpisodesFilename() + " je prázdný");
        }
        
        return text;
    }

    public @Override StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException  
    {
        StringBuilder text = new StringBuilder();
                
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
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextInputTVEpisodesFilename() + " je prázdný");
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
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputTVEpisodesFilename() + " je prázdný");
        }
        
        return text;
    }
    
    public @Override List<TVEpisodeOutput> loadOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException 
    {
        List<TVEpisodeOutput> parsedTVEpisodes = new ArrayList<>();
        
        if (fromBinary == true) 
        {
            File outputTVEpisodesBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryOutputTVEpisodesFilename());
            
            outputTVEpisodesBinary.createNewFile();
            
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVEpisodesFilename())))) 
            {
                boolean fileEndReached = false;
                int tvEpisodeId;
                long tvEpisodeRuntime;
                char[] tvEpisodeName;
                int tvEpisodePercentageRating;
                char[] tvEpisodeHyperlink;
                char[] tvEpisodeContent;
                int tvEpisodeOrderInTVShowSeason;
                int tvEpisodeTVSeasonId;
                
                while (fileEndReached == false) 
                {
                    try 
                    {
                        tvEpisodeId = dataInputStream.readInt();
                        tvEpisodeRuntime = dataInputStream.readLong();

                        tvEpisodeName = new char[TVEpisodeOutput.ATTRIBUTE_NAME_LENGTH];

                        for (int i = 0; i < tvEpisodeName.length; i++) 
                        {
                            tvEpisodeName[i] = dataInputStream.readChar();
                        }

                        tvEpisodePercentageRating = dataInputStream.readInt();

                        tvEpisodeHyperlink = new char[TVEpisodeOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                        for (int i = 0; i < tvEpisodeHyperlink.length; i++) 
                        {
                            tvEpisodeHyperlink[i] = dataInputStream.readChar();
                        }

                        tvEpisodeContent = new char[TVEpisodeOutput.ATTRIBUTE_CONTENT_LENGTH];

                        for (int i = 0; i < tvEpisodeContent.length; i++) 
                        {
                            tvEpisodeContent[i] = dataInputStream.readChar();
                        }

                        tvEpisodeOrderInTVShowSeason = dataInputStream.readInt();

                        tvEpisodeTVSeasonId = dataInputStream.readInt();

                        parsedTVEpisodes.add(new TVEpisodeOutput(tvEpisodeId, tvEpisodeRuntime, new String(tvEpisodeName), 
                                tvEpisodePercentageRating, new String(tvEpisodeHyperlink), new String(tvEpisodeContent), 
                                tvEpisodeOrderInTVShowSeason, tvEpisodeTVSeasonId));
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
            File outputTVEpisodesText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextOutputTVEpisodesFilename());
        
            outputTVEpisodesText.createNewFile();
            
            StringBuilder text = new StringBuilder();
            String errorParsingMessage = "Soubor " + DataStore.getTextOutputTVEpisodesFilename() + " má poškozená data";
            
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextOutputTVEpisodesFilename()), StandardCharsets.UTF_8))) 
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

            Class<?> tvEpisodeOutputClass = TVEpisodeOutput.class;
            Field[] tvEpisodeOutputFields = tvEpisodeOutputClass.getDeclaredFields();
            Map<String, StringBuilder> tvEpisodeOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> tvEpisodeOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : tvEpisodeOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvEpisodeOutputFieldsIds.put(k + 1, field.getName());
                    tvEpisodeOutputFieldsValues.put(field.getName(), new StringBuilder());
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
                            parseOutputData(tvEpisodeOutputFieldsValues, parsedTVEpisodes, tvEpisodeOutputFields);
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
                            parseOutputData(tvEpisodeOutputFieldsValues, parsedTVEpisodes, tvEpisodeOutputFields);
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

                        String fieldName = tvEpisodeOutputFieldsIds.get(fieldId);

                        if (fieldName == null) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        StringBuilder fieldValue = tvEpisodeOutputFieldsValues.get(fieldName);
                        StringBuilder newFieldValue = fieldValue.append(parts[0]);

                        if (fieldName.equals("shortContentSummary")) 
                        {
                            newFieldValue.append("\n");
                        }

                        tvEpisodeOutputFieldsValues.put(fieldName, newFieldValue);
                    }
                }
            }
        }
                                
        return parsedTVEpisodes;
    }
    
    public @Override void tryDeleteDataOutputFilesCopies() 
    {
        File outputTVEpisodesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputTVEpisodesFilename());
        
        File outputTVEpisodesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputTVEpisodesFilename());
        
        outputTVEpisodesTextCopy.delete();
        outputTVEpisodesBinaryCopy.delete();
    }
    
    public @Override void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
    {
        File outputTVEpisodesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputTVEpisodesFilename());
        
        File outputTVEpisodesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputTVEpisodesFilename());
        
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = outputTVEpisodesTextCopy.getName();
            sourceBinaryFile = outputTVEpisodesBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryOutputTVEpisodesFilename();
            destinationTextFile = DataStore.getTextOutputTVEpisodesFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextOutputTVEpisodesFilename();
            sourceBinaryFile = DataStore.getBinaryOutputTVEpisodesFilename();
            destinationBinaryFile = outputTVEpisodesBinaryCopy.getName();
            destinationTextFile = outputTVEpisodesTextCopy.getName();
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
            outputTVEpisodesTextCopy.delete();
            outputTVEpisodesBinaryCopy.delete();
            throw new IOException();
        }
    }

    public @Override void saveOutputDataIntoFiles(List<TVEpisodeOutput> newOutputData) throws IOException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextOutputTVEpisodesFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVEpisodesFilename(), false)))) 
        {
            StringBuilder generatedTVEpisodesTextRepresentations = 
                    createOutputDataTextRepresentation(newOutputData);
            
            for (TVEpisodeOutput m : newOutputData) 
            {
                dataOutputStream.writeInt(m.getId());
                dataOutputStream.writeLong(m.getRuntimeInSeconds());
                
                for (char c : m.getName().toCharArray()) 
                {
                    dataOutputStream.writeChar(c);
                }
                
                dataOutputStream.writeInt(m.getPercentageRating());
                
                for (char c : m.getHyperlinkForContentWatch().toCharArray()) 
                {
                    dataOutputStream.writeChar(c);
                }
                
                for (char c : m.getShortContentSummary().toCharArray()) 
                {
                    dataOutputStream.writeChar(c);
                }
                
                dataOutputStream.writeInt(m.getOrderInTVShowSeason());
                
                dataOutputStream.writeInt(m.getTVSeasonId());
            }

            bufferedWriter.write(generatedTVEpisodesTextRepresentations.toString());
        }
    }

    public @Override List<TVEpisodeInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileEmptyException, FileNotFoundException
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
            catch (FileNotFoundException e) 
            {
                throw new FileNotFoundException("Soubor " + 
                        DataStore.getBinaryInputTVEpisodesFilename() + " neexistuje");
            }
            
            File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputTVEpisodesFilename());
        
            if (binaryFile.length() == 0) 
            {
                throw new FileEmptyException("Soubor " + DataStore.getBinaryInputTVEpisodesFilename() + " je prázdný");
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
            catch (FileNotFoundException e) 
            {
                throw new FileNotFoundException("Soubor " + 
                        DataStore.getTextInputTVEpisodesFilename() + " neexistuje");
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
            throw new FileEmptyException("Soubor " + DataStore.getTextInputTVEpisodesFilename() + " je prázdný");
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
            List<TVEpisodeOutput> parsedTVEpisodes, Field[] tvEpisodeOutputFields) 
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
        
        if (newOutputTVEpisodes.isEmpty() == false) 
        {
            String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
            outputTextData.append(endMarking).append("\n");
        }
        
        return outputTextData;
    }
}
