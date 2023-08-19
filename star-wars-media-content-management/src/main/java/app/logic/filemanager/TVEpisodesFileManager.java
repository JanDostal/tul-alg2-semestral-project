package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVEpisodeInput;
import app.models.inputoutput.TVEpisodeInputOutput;
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
 * Represents a tv episodes file manager, which works specifically with TV episodes data files.
 * TV episodes file manager works with tv episode input and input/output data models and implements IDataFileManager interface.
 * TV episodes file manager is made available through accessor.
 * @author jan.dostal
 */
public class TVEpisodesFileManager implements IDataFileManager<TVEpisodeInput, TVEpisodeInputOutput>
{
    private static IDataFileManager<TVEpisodeInput, TVEpisodeInputOutput> tvEpisodesFileManager;
    
    /**
     * Creates singleton instance of TVEpisodesFileManager.
     */
    private TVEpisodesFileManager() 
    {
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @return singleton instance of TVEpisodesFileManager as interface
     */
    protected static IDataFileManager<TVEpisodeInput, TVEpisodeInputOutput> getInstance() 
    {
        if (tvEpisodesFileManager == null) 
        {
            tvEpisodesFileManager = new TVEpisodesFileManager();
        }
        
        return tvEpisodesFileManager;
    }
    
    public @Override StringBuilder getTextInputOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() + 
                        DataStore.getTextInputOutputTVEpisodesFilename()), StandardCharsets.UTF_8))) 
        {
            String textLine;
            
            while((textLine = bufferedReader.readLine()) != null) 
            {
               text.append(textLine).append("\n");
            }
        }
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("Soubor " + 
                    DataStore.getTextInputOutputTVEpisodesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputOutputTVEpisodesFilename());
        }
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                throw new FileEmptyException("Soubor " + DataStore.getTextInputOutputTVEpisodesFilename() + " je prázdný");
            }
        }
        
        text.deleteCharAt(text.length() - 1);
        
        return text;
    }

    public @Override StringBuilder getBinaryInputOutputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
        String tvEpisodesDivider = "\n\n\n\n\n\n\n\n\n";
        
        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                FileManagerAccessor.getFileSeparator() + DataStore.getBinaryInputOutputTVEpisodesFilename())))) 
        {
            boolean fileEndReached = false;
            
            int tvEpisodeId;
            long tvEpisodeRuntimeInSeconds;
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
                    tvEpisodeRuntimeInSeconds = dataInputStream.readLong();

                    tvEpisodeName = new char[TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH];

                    for (int i = 0; i < tvEpisodeName.length; i++) 
                    {
                        tvEpisodeName[i] = dataInputStream.readChar();
                    }

                    tvEpisodePercentageRating = dataInputStream.readInt();

                    tvEpisodeHyperlink = new char[TVEpisodeInputOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                    for (int i = 0; i < tvEpisodeHyperlink.length; i++) 
                    {
                        tvEpisodeHyperlink[i] = dataInputStream.readChar();
                    }

                    tvEpisodeContent = new char[TVEpisodeInputOutput.ATTRIBUTE_SUMMARY_LENGTH];

                    for (int i = 0; i < tvEpisodeContent.length; i++) 
                    {
                        tvEpisodeContent[i] = dataInputStream.readChar();
                    }

                    tvEpisodeOrderInTVShowSeason = dataInputStream.readInt();

                    tvEpisodeTVSeasonId = dataInputStream.readInt();
                    
                    text.append(String.format("%-38s%d", "Identifikátor:", tvEpisodeId)).append("\n");
                    text.append(String.format("%-38s%d", "Délka TV epizody v sekundách:", tvEpisodeRuntimeInSeconds)).append("\n");
                    text.append(String.format("%-38s%s", "Název:", new String(tvEpisodeName))).append("\n");
                    text.append(String.format("%-38s%d", "Procentuální hodnocení:", tvEpisodePercentageRating)).append("\n");
                    text.append(String.format("%-38s%s", "Odkaz ke zhlédnutí:", new String(tvEpisodeHyperlink))).append("\n");
                    
                    text.append("Krátké shrnutí obsahu:").append("\n");
                    text.append("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||").append("\n");
                    text.append(new String(tvEpisodeContent));
                    text.append("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||").append("\n");
                                        
                    text.append(String.format("%-38s%d", "Pořadí epizody v rámci sezóny:", tvEpisodeOrderInTVShowSeason)).append("\n");
                    text.append(String.format("%-38s%d", "Identifikátor sezóny pro epizodu:", tvEpisodeTVSeasonId)).append(tvEpisodesDivider);
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
                    DataStore.getBinaryInputOutputTVEpisodesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryInputOutputTVEpisodesFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() 
                + DataStore.getBinaryInputOutputTVEpisodesFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputOutputTVEpisodesFilename() + " je prázdný");
        }
        
        text.delete(text.length() - tvEpisodesDivider.length(), text.length());
        
        return text;
    }

    public @Override StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException  
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() + 
                        DataStore.getTextInputTVEpisodesFilename()), StandardCharsets.UTF_8))) 
        {
            String textLine;
            
            while((textLine = bufferedReader.readLine()) != null) 
            {
               text.append(textLine).append("\n");
            }
        }
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("Soubor " + 
                    DataStore.getTextInputTVEpisodesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputTVEpisodesFilename());
        }
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                throw new FileEmptyException("Soubor " + DataStore.getTextInputTVEpisodesFilename() + " je prázdný");
            }
        }
        
        text.deleteCharAt(text.length() - 1);
        
        return text;
    }

    public @Override StringBuilder getBinaryInputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException
    {
        StringBuilder text = new StringBuilder();
        
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(
                FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() + 
                        DataStore.getBinaryInputTVEpisodesFilename()))) 
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
                    DataStore.getBinaryInputTVEpisodesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryInputTVEpisodesFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator()
                + DataStore.getBinaryInputTVEpisodesFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputTVEpisodesFilename() + " je prázdný");
        }
        
        return text;
    }
    
    public @Override List<TVEpisodeInputOutput> loadInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException 
    {
        List<TVEpisodeInputOutput> parsedTVEpisodes = new ArrayList<>();
        File inputOutputTVEpisodesText = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() + 
                DataStore.getTextInputOutputTVEpisodesFilename());
        File inputOutputTVEpisodesBinary = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() +
                DataStore.getBinaryInputOutputTVEpisodesFilename());
        
        inputOutputTVEpisodesBinary.createNewFile();
        inputOutputTVEpisodesText.createNewFile();
        
        if (fromBinary == true) 
        {
            String errorParsingMessage = "Soubor " + DataStore.getBinaryInputOutputTVEpisodesFilename() + " má poškozená data";
            
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                FileManagerAccessor.getFileSeparator() + DataStore.getBinaryInputOutputTVEpisodesFilename())))) 
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

                        tvEpisodeName = new char[TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH];

                        for (int i = 0; i < tvEpisodeName.length; i++) 
                        {
                            tvEpisodeName[i] = dataInputStream.readChar();
                        }

                        tvEpisodePercentageRating = dataInputStream.readInt();

                        tvEpisodeHyperlink = new char[TVEpisodeInputOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                        for (int i = 0; i < tvEpisodeHyperlink.length; i++) 
                        {
                            tvEpisodeHyperlink[i] = dataInputStream.readChar();
                        }

                        tvEpisodeContent = new char[TVEpisodeInputOutput.ATTRIBUTE_SUMMARY_LENGTH];

                        for (int i = 0; i < tvEpisodeContent.length; i++) 
                        {
                            tvEpisodeContent[i] = dataInputStream.readChar();
                        }

                        tvEpisodeOrderInTVShowSeason = dataInputStream.readInt();

                        tvEpisodeTVSeasonId = dataInputStream.readInt();

                        parsedTVEpisodes.add(new TVEpisodeInputOutput(tvEpisodeId, tvEpisodeRuntime, new String(tvEpisodeName), 
                                tvEpisodePercentageRating, new String(tvEpisodeHyperlink), new String(tvEpisodeContent), 
                                tvEpisodeOrderInTVShowSeason, tvEpisodeTVSeasonId));
                    }
                    catch (EOFException e) 
                    {
                        fileEndReached = true;
                    }
                }
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getBinaryInputOutputTVEpisodesFilename());
            }
            
            if (inputOutputTVEpisodesBinary.length() != 0 && parsedTVEpisodes.isEmpty()) throw new FileParsingException(errorParsingMessage);
        }
        else 
        {
            String errorParsingMessage = "Soubor " + DataStore.getTextInputOutputTVEpisodesFilename() + " má poškozená data";
            StringBuilder text;
            
            try 
            {
                text = getTextInputOutputFileContent();
            }
            catch (FileEmptyException ex) 
            {
                text = new StringBuilder();
            }

            Class<?> tvEpisodeInputOutputClass = TVEpisodeInputOutput.class;
            Field[] tvEpisodeInputOutputFields = tvEpisodeInputOutputClass.getDeclaredFields();
            Map<String, StringBuilder> tvEpisodeInputOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> tvEpisodeInputOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : tvEpisodeInputOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvEpisodeInputOutputFieldsIds.put(k + 1, field.getName());
                    tvEpisodeInputOutputFieldsValues.put(field.getName(), new StringBuilder());
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
                
                if (sc.hasNextLine() == true) isFileEmpty = false;

                while (sc.hasNextLine() == true) 
                {
                    textLine = sc.nextLine();

                    if (textLine.matches("^$") || textLine.matches("^[\\s\t]+$")) 
                    {
                        continue;
                    } 
                    else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileEndMarking()
                            + "[\\s\t]*$") && enteredSectionValues == true) 
                    {
                        try 
                        {
                            parseInputOutputDataFromTextFile(tvEpisodeInputOutputFieldsValues, parsedTVEpisodes, tvEpisodeInputOutputFields);
                        }
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }
                        
                        break;
                    } 
                    else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileValuesSectionMarking()
                            + "[\\s\t]*$") && enteredSectionAttributes == true) 
                    {
                        enteredSectionValues = true;

                        continue;
                    } 
                    else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileAttributesSectionMarking()
                            + "[\\s\t]*$") && enteredSectionValues == true) 
                    {
                        try 
                        {
                            parseInputOutputDataFromTextFile(tvEpisodeInputOutputFieldsValues, parsedTVEpisodes, tvEpisodeInputOutputFields);
                        }
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        enteredSectionAttributes = true;
                        enteredSectionValues = false;

                        continue;
                    } 
                    else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileAttributesSectionMarking()
                            + "[\\s\t]*$") && enteredSectionAttributes == false) 
                    {
                        enteredSectionAttributes = true;
                        continue;
                    }

                    if (enteredSectionValues == true)
                    {
                        fieldParts = textLine.split(" (?=[^ ]+$)");

                        if (fieldParts.length != 2) throw new FileParsingException(errorParsingMessage);

                        try 
                        {
                            fieldId = Integer.parseInt(fieldParts[1]);
                        } 
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        fieldName = tvEpisodeInputOutputFieldsIds.get(fieldId);

                        if (fieldName == null) throw new FileParsingException(errorParsingMessage);

                        fieldValue = tvEpisodeInputOutputFieldsValues.get(fieldName);
                        newFieldValue = fieldValue.append(fieldParts[0]);

                        if (fieldName.equals("shortContentSummary")) newFieldValue.append("\n");

                        tvEpisodeInputOutputFieldsValues.put(fieldName, newFieldValue);
                    }
                }
            }
            
            if (isFileEmpty == false && parsedTVEpisodes.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
                                
        return parsedTVEpisodes;
    }
    
    public @Override void tryDeleteInputOutputDataFilesCopies() 
    {
        File inputOutputTVEpisodesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() +
                "copy_" + DataStore.getTextInputOutputTVEpisodesFilename());
        
        File inputOutputTVEpisodesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() +
                "copy_" + DataStore.getBinaryInputOutputTVEpisodesFilename());
        
        inputOutputTVEpisodesTextCopy.delete();
        inputOutputTVEpisodesBinaryCopy.delete();
    }
    
    public @Override void transferBetweenInputOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
    {
        File inputOutputTVEpisodesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() +
                "copy_" + DataStore.getTextInputOutputTVEpisodesFilename());
        
        File inputOutputTVEpisodesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() +
                "copy_" + DataStore.getBinaryInputOutputTVEpisodesFilename());
        
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = inputOutputTVEpisodesTextCopy.getName();
            sourceBinaryFile = inputOutputTVEpisodesBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryInputOutputTVEpisodesFilename();
            destinationTextFile = DataStore.getTextInputOutputTVEpisodesFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextInputOutputTVEpisodesFilename();
            sourceBinaryFile = DataStore.getBinaryInputOutputTVEpisodesFilename();
            destinationBinaryFile = inputOutputTVEpisodesBinaryCopy.getName();
            destinationTextFile = inputOutputTVEpisodesTextCopy.getName();
        }
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() +
                sourceTextFile), StandardCharsets.UTF_8));
             DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new 
                FileInputStream(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() + 
                sourceBinaryFile)));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() + 
                destinationTextFile, false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                FileManagerAccessor.getFileSeparator() + destinationBinaryFile, false)))
             )
        {
            byte[] byteBuffer = new byte[8192];
            int bytesRead;
            StringBuilder text = new StringBuilder();
            String textLine;
            
            while ((textLine = bufferedReader.readLine()) != null) 
            {
                text.append(textLine).append("\n");
            }
            
            if (text.length() != 0) text.deleteCharAt(text.length() - 1);
            
            bufferedWriter.write(text.toString());
            
            while ((bytesRead = dataInputStream.read(byteBuffer)) != -1) 
            {
                dataOutputStream.write(byteBuffer, 0, bytesRead);
            }
        }
        catch (IOException e) 
        {
            inputOutputTVEpisodesTextCopy.delete();
            inputOutputTVEpisodesBinaryCopy.delete();
            throw new IOException("Chyba při kopírování mezi vstupními/výstupními soubory epizod sezón a kopiemi");
        }
    }

    public @Override void saveInputOutputDataIntoFiles(List<TVEpisodeInputOutput> newInputOutputData) throws IOException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + FileManagerAccessor.getFileSeparator() + 
                DataStore.getTextInputOutputTVEpisodesFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                FileManagerAccessor.getFileSeparator() + DataStore.getBinaryInputOutputTVEpisodesFilename(), false)))) 
        {
            StringBuilder generatedTVEpisodesTextRepresentations = 
                    createInputOutputDataTextRepresentation(newInputOutputData);
            
            for (TVEpisodeInputOutput m : newInputOutputData) 
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
        catch (IOException e) 
        {
            throw new IOException("Chyba při zápisu vstupních/výstupních souborů epizod sezón");
        }
    }

    public @Override Map<Integer, TVEpisodeInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileEmptyException, FileNotFoundException, FileParsingException
    {
        StringBuilder text = fromBinary == true ? getBinaryInputFileContent() : getTextInputFileContent();
                   
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
                
        Map<Integer, TVEpisodeInput> parsedTVEpisodes = new LinkedHashMap<>();
        int inputTVEpisodeOrder = 0;
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
                else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileEndMarking() +
                        "[\\s\t]*$") && enteredSectionValues == true) 
                {
                    parseInputData(tvEpisodeInputFieldsValues, parsedTVEpisodes, tvEpisodeInputFields, inputTVEpisodeOrder);
                    
                    break; 
                }
                else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileValuesSectionMarking() +
                        "[\\s\t]*$") && enteredSectionAttributes == true)
                {
                    enteredSectionValues = true;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileAttributesSectionMarking() +
                        "[\\s\t]*$") && enteredSectionValues == true) 
                {
                    parseInputData(tvEpisodeInputFieldsValues, parsedTVEpisodes, tvEpisodeInputFields, inputTVEpisodeOrder);
                    
                    inputTVEpisodeOrder++;
                    enteredSectionAttributes = true;
                    enteredSectionValues = false;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + FileManagerAccessor.getTextFileAttributesSectionMarking() +
                        "[\\s\t]*$") && enteredSectionAttributes == false) 
                {
                    inputTVEpisodeOrder++;
                    enteredSectionAttributes = true;
                    continue;
                }
                
                if (enteredSectionValues == true)
                {
                    fieldParts = textLine.split(" (?=[^ ]+$)");
                    
                    if (fieldParts.length != 2) continue;
                                        
                    try
                    {
                        fieldId = Integer.parseInt(fieldParts[1]);
                    }
                    catch (NumberFormatException ex)
                    {
                        continue;
                    }
                    
                    fieldName = tvEpisodeInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null) continue;
                    
                    fieldValue = tvEpisodeInputFieldsValues.get(fieldName);
                    newFieldValue = fieldValue.append(fieldParts[0]);
                    
                    if (fieldName.equals("shortContentSummary")) newFieldValue.append("\n");
                    
                    tvEpisodeInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        if (parsedTVEpisodes.isEmpty()) 
        {
            throw new FileParsingException(String.format("Nic se nenahrálo ze souboru %s", fromBinary == true ? 
                    DataStore.getBinaryInputTVEpisodesFilename() : DataStore.getTextInputTVEpisodesFilename()));
        }
        
        return parsedTVEpisodes;
    }
    
    /**
     * Represents a method which parses tv episode input data (one record) from input binary or text 
     * file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param tvEpisodeInputFieldsValues represents list of parsed tv episode record values mapped to
     * according tv episode input model data attributes names.
     * @param parsedTVEpisodes represents a list of currently parsed tv episodes
     * @param tvEpisodeInputFields represents tv episode input model data attributes names, which are
     * used for indexing particular fields values.
     * @param inputTVEpisodeOrder represents int value, which specifies tv episode record loading 
     * order from file (record on start of file - value 1, record after - value 2).
     */
    private void parseInputData(Map<String, StringBuilder> tvEpisodeInputFieldsValues,
            Map<Integer, TVEpisodeInput> parsedTVEpisodes, Field[] tvEpisodeInputFields, int inputTVEpisodeOrder) 
    {
        try 
        {
            long runtime = Long.parseLong(tvEpisodeInputFieldsValues.get("runtimeInSeconds").toString());
            int percentage = Integer.parseInt(tvEpisodeInputFieldsValues.get("percentageRating").toString());
            int orderInTVShowSeason = Integer.parseInt(tvEpisodeInputFieldsValues.get("orderInTVShowSeason").
                    toString());
                        
            parsedTVEpisodes.put(inputTVEpisodeOrder, new TVEpisodeInput(runtime, tvEpisodeInputFieldsValues.get("name").toString(), 
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
    
    /**
     * Represents a method which parses tv episode input/output data (one record) from input/output text file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param tvEpisodeInputOutputFieldsValues represents list of parsed tv episode record values mapped to
     * according tv episode input/output model data attributes names.
     * @param parsedTVEpisodes represents a list of currently parsed tv episodes
     * @param tvEpisodeInputOutputFields represents tv episode input/output model data attributes names, which are
     * used for indexing particular fields values.
     * @throws NumberFormatException when parsed tv episode record number values cannot be converted to from String
     */
    private void parseInputOutputDataFromTextFile(Map<String, StringBuilder> tvEpisodeInputOutputFieldsValues,
            List<TVEpisodeInputOutput> parsedTVEpisodes, Field[] tvEpisodeInputOutputFields) 
    {        
        int id = Integer.parseInt(tvEpisodeInputOutputFieldsValues.get("id").toString());
        long runtime = Long.parseLong(tvEpisodeInputOutputFieldsValues.get("runtimeInSeconds").toString());
        int percentage = Integer.parseInt(tvEpisodeInputOutputFieldsValues.get("percentageRating").toString());
        int orderInTVShowSeason = Integer.parseInt(tvEpisodeInputOutputFieldsValues.get("orderInTVShowSeason").toString());
        int tvSeasonId = Integer.parseInt(tvEpisodeInputOutputFieldsValues.get("tvSeasonId").toString());

        parsedTVEpisodes.add(new TVEpisodeInputOutput(id, runtime, tvEpisodeInputOutputFieldsValues.get("name").toString(),
                percentage, tvEpisodeInputOutputFieldsValues.get("hyperlinkForContentWatch").toString(),
                tvEpisodeInputOutputFieldsValues.get("shortContentSummary").toString(), orderInTVShowSeason, tvSeasonId));

        tvEpisodeInputOutputFieldsValues.clear();

        for (Field field : tvEpisodeInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvEpisodeInputOutputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    /**
     * Represents a method which creates tv episode input/output data (multiple records) text representation
     * for input/output text file.
     * @param newInputOutputTVEpisodes represents list of tv episode input/output models data from database
     * @return text content to save into input/output text file
     */
    private StringBuilder createInputOutputDataTextRepresentation(List<TVEpisodeInputOutput> newInputOutputTVEpisodes) 
    {
        Class<?> tvEpisodeInputOutputClass = TVEpisodeInputOutput.class;
        Field[] tvEpisodeInputOutputFields = tvEpisodeInputOutputClass.getDeclaredFields();
        Map<String, Integer> tvEpisodeInputOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvEpisodeInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvEpisodeInputOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder inputOutputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;
        StringBuilder changedStringField;
        String[] shortContentSummaryLines;

        for (TVEpisodeInputOutput m : newInputOutputTVEpisodes) 
        {
            attributesMarking = FileManagerAccessor.getTextFileAttributesSectionMarking().replaceAll("\\\\", "");
            inputOutputTextData.append(attributesMarking).append("\n");
            inputOutputTextData.append("\n");
            
            inputOutputTextData.append("Identificator: ").append(m.getId()).append("\n");
            
            inputOutputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : tvEpisodeInputOutputFieldsIds.entrySet()) 
            {
                inputOutputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = FileManagerAccessor.getTextFileValuesSectionMarking().replaceAll("\\\\", "");
            inputOutputTextData.append("\n");
            inputOutputTextData.append(valuesMarking).append("\n");
            inputOutputTextData.append("\n");

            inputOutputTextData.append(m.getId()).append(" ").
                    append(tvEpisodeInputOutputFieldsIds.get("id")).
                    append("\n");
            inputOutputTextData.append(m.getRuntimeInSeconds()).
                    append(" ").append(tvEpisodeInputOutputFieldsIds.get("runtimeInSeconds")).
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
                    append(tvEpisodeInputOutputFieldsIds.get("name")).append("\n");
            inputOutputTextData.append(m.getPercentageRating()).
                    append(" ").append(tvEpisodeInputOutputFieldsIds.get("percentageRating")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getHyperlinkForContentWatch().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }
            
            inputOutputTextData.append(changedStringField.toString()).
                    append(" ").append(tvEpisodeInputOutputFieldsIds.get("hyperlinkForContentWatch")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getShortContentSummary().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }

            shortContentSummaryLines = changedStringField.toString().split("\n");

            for (int i = 0; i < shortContentSummaryLines.length; i++) 
            {
                inputOutputTextData.append(shortContentSummaryLines[i]).append(" ").
                        append(tvEpisodeInputOutputFieldsIds.get("shortContentSummary")).append("\n");
            }

            inputOutputTextData.append(m.getOrderInTVShowSeason()).
                    append(" ").append(tvEpisodeInputOutputFieldsIds.get("orderInTVShowSeason")).
                    append("\n");
            
            inputOutputTextData.append(m.getTVSeasonId()).
                    append(" ").append(tvEpisodeInputOutputFieldsIds.get("tvSeasonId")).
                    append("\n").append("\n");
        }
        
        if (newInputOutputTVEpisodes.isEmpty() == false) 
        {
            String endMarking = FileManagerAccessor.getTextFileEndMarking().replaceAll("\\\\", "");
            inputOutputTextData.append(endMarking);
        }
        
        return inputOutputTextData;
    }
}
