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
 * Represents a tv episodes file manager, which works specifically with TV episodes data files.
 * TV episodes file manager works with tv episode input and output data models and implements IDataFileManager interface.
 * TV episodes file manager is made available through accessor.
 * @author jan.dostal
 */
public class TVEpisodesFileManager implements IDataFileManager<TVEpisodeInput, TVEpisodeOutput>
{
    private static IDataFileManager<TVEpisodeInput, TVEpisodeOutput> tvEpisodesFileManager;
    
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
    
    /**
     * Creates singleton instance of TVEpisodesFileManager.
     * Receives filenameSeparator, inputFileEndMarking, inputFileValuesSectionMarking 
     * and inputFileAttributesSectionMarking parameters in constructor from {@link FileManagerAccessor} class.
     * @param filenameSeparator file path separator dependent on application running operating system
     * @param inputFileEndMarking control string for detecting file end
     * @param inputFileValuesSectionMarking control string for detecting values section in file
     * @param inputFileAttributesSectionMarking control string for detecting attributes section in file
     */
    private TVEpisodesFileManager(String filenameSeparator, 
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
     * @return singleton instance of TVEpisodesFileManager as interface
     */
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
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("Soubor " + 
                    DataStore.getTextOutputTVEpisodesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextOutputTVEpisodesFilename());
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
        String tvEpisodesDivider = "\n\n\n\n\n\n\n\n\n";
        
        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputTVEpisodesFilename())))) 
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

                    tvEpisodeContent = new char[TVEpisodeOutput.ATTRIBUTE_SUMMARY_LENGTH];

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
                    DataStore.getBinaryOutputTVEpisodesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryOutputTVEpisodesFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator 
                + DataStore.getBinaryOutputTVEpisodesFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryOutputTVEpisodesFilename() + " je prázdný");
        }
        
        text.delete(text.length() - tvEpisodesDivider.length(), text.length());
        
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
        File outputTVEpisodesText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextOutputTVEpisodesFilename());
        File outputTVEpisodesBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryOutputTVEpisodesFilename());
        
        outputTVEpisodesBinary.createNewFile();
        outputTVEpisodesText.createNewFile();
        
        if (fromBinary == true) 
        {
            String errorParsingMessage = "Soubor " + DataStore.getBinaryOutputTVEpisodesFilename()+ " má poškozená data";
            
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

                        tvEpisodeContent = new char[TVEpisodeOutput.ATTRIBUTE_SUMMARY_LENGTH];

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
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getBinaryOutputTVEpisodesFilename());
            }
            
            if (outputTVEpisodesBinary.length() != 0 && parsedTVEpisodes.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
        else 
        {
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
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getTextOutputTVEpisodesFilename());
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
            
            if (isFileEmpty == false && parsedTVEpisodes.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
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
            throw new IOException("Chyba při kopírování mezi výstupními soubory epizod sezón a kopiemi");
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
        catch (IOException e) 
        {
            throw new IOException("Chyba při zápisu výstupních souborů epizod sezón");
        }
    }

    public @Override Map<Integer, TVEpisodeInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileEmptyException, FileNotFoundException, FileParsingException
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
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + 
                        DataStore.getBinaryInputTVEpisodesFilename());
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
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + 
                        DataStore.getTextInputTVEpisodesFilename());
            }
            
            try (Scanner sc = new Scanner(text.toString())) 
            {
                if (sc.hasNextLine() == false)
                {
                    sc.close();
                    throw new FileEmptyException("Soubor " + DataStore.getTextInputTVEpisodesFilename() + " je prázdný");
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
                
        Map<Integer, TVEpisodeInput> parsedTVEpisodes = new LinkedHashMap<>();
        int inputTVEpisodeOrder = 0;
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
                    parseInputData(tvEpisodeInputFieldsValues, parsedTVEpisodes, tvEpisodeInputFields, inputTVEpisodeOrder);
                    
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
                    parseInputData(tvEpisodeInputFieldsValues, parsedTVEpisodes, tvEpisodeInputFields, inputTVEpisodeOrder);
                    
                    inputTVEpisodeOrder++;
                    enteredSectionAttributes = true;
                    enteredSectionValues = false;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == false) 
                {
                    inputTVEpisodeOrder++;
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
     * Represents a method which parses tv episode output data (one record) from output text file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param tvEpisodeOutputFieldsValues represents list of parsed tv episode record values mapped to
     * according tv episode output model data attributes names.
     * @param parsedTVEpisodes represents a list of currently parsed tv episodes
     * @param tvEpisodeOutputFields represents tv episode output model data attributes names, which are
     * used for indexing particular fields values.
     * @throws NumberFormatException when parsed tv episode record number values cannot be converted to from String
     */
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
    
    /**
     * Represents a method which creates tv episode output data (multiple records) text representation
     * for output text file.
     * @param newOutputTVEpisodes represents list of tv episode output models data from database
     * @return text content to save into output text file
     */
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
            
            outputTextData.append("Identificator: ").append(m.getId()).append("\n");
            
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
