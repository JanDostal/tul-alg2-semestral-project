package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.TVSeasonInput;
import app.models.inputoutput.TVSeasonInputOutput;
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
 * Represents a tv seasons file manager, which works specifically with TV seasons data files.
 * TV seasons file manager works with tv season input and input/output data models and implements IDataFileManager interface.
 * TV seasons file manager is made available through accessor.
 * @author jan.dostal
 */
public class TVSeasonsFileManager implements IDataFileManager<TVSeasonInput, TVSeasonInputOutput>
{
    private static IDataFileManager<TVSeasonInput, TVSeasonInputOutput> tvSeasonsFileManager;
   
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
   
    /**
     * Creates singleton instance of TVSeasonsFileManager.
     * Receives filenameSeparator, inputFileEndMarking, inputFileValuesSectionMarking 
     * and inputFileAttributesSectionMarking parameters in constructor from {@link FileManagerAccessor} class.
     * @param filenameSeparator file path separator dependent on application running operating system
     * @param inputFileEndMarking control string for detecting file end
     * @param inputFileValuesSectionMarking control string for detecting values section in file
     * @param inputFileAttributesSectionMarking control string for detecting attributes section in file
     */
    private TVSeasonsFileManager(String filenameSeparator, 
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
     * @return singleton instance of TVSeasonsFileManager as interface
     */
    protected static IDataFileManager<TVSeasonInput, TVSeasonInputOutput> getInstance(String filenameSeparator, 
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
    
    public @Override StringBuilder getTextInputOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextInputOutputTVSeasonsFilename()), StandardCharsets.UTF_8))) 
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
                    DataStore.getTextInputOutputTVSeasonsFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputOutputTVSeasonsFilename());
        }
        
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextInputOutputTVSeasonsFilename() + " je prázdný");
            }
        }
        
        text.deleteCharAt(text.length() - 1);
        
        return text;
    }

    public @Override StringBuilder getBinaryInputOutputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
        String tvSeasonsDivider = "\n\n\n\n\n\n\n\n\n";
        
        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputTVSeasonsFilename())))) 
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
                                        
                    text.append(String.format("%-38s%d", "Identifikátor:", tvSeasonId)).append("\n");
                    text.append(String.format("%-38s%d", "Pořadí sezóny v rámci seriálu:", tvSeasonOrderInTVShow)).append("\n");
                    text.append(String.format("%-38s%d", "Identifikátor seriálu pro sezónu:", tvSeasonTVShowId)).append(tvSeasonsDivider);
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
                    DataStore.getBinaryInputOutputTVSeasonsFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryInputOutputTVSeasonsFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator 
                + DataStore.getBinaryInputOutputTVSeasonsFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputOutputTVSeasonsFilename() + " je prázdný");
        }
        
        text.delete(text.length() - tvSeasonsDivider.length(), text.length());
        
        return text;
    }

    public @Override StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextInputTVSeasonsFilename()), StandardCharsets.UTF_8))) 
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
                    DataStore.getTextInputTVSeasonsFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputTVSeasonsFilename());
        }
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextInputTVSeasonsFilename() + " je prázdný");
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
                FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getBinaryInputTVSeasonsFilename()))) 
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
                    DataStore.getBinaryInputTVSeasonsFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputTVSeasonsFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputTVSeasonsFilename() + " je prázdný");
        }
        
        return text;
    }
    
    public @Override List<TVSeasonInputOutput> loadInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException
    {
        List<TVSeasonInputOutput> parsedTVSeasons = new ArrayList<>();
        File inputOutputTVSeasonsBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryInputOutputTVSeasonsFilename());
        File inputOutputTVSeasonsText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextInputOutputTVSeasonsFilename());
        
        inputOutputTVSeasonsBinary.createNewFile();
        inputOutputTVSeasonsText.createNewFile();
        
        if (fromBinary == true) 
        {            
            String errorParsingMessage = "Soubor " + DataStore.getBinaryInputOutputTVSeasonsFilename() + " má poškozená data";
            
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputTVSeasonsFilename())))) 
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

                        parsedTVSeasons.add(new TVSeasonInputOutput(tvSeasonId, tvSeasonOrderInTVShow, tvSeasonTVShowId));
                    }
                    catch (EOFException e) 
                    {
                        fileEndReached = true;
                    }
                }
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getBinaryInputOutputTVSeasonsFilename());
            }
            
            if (inputOutputTVSeasonsBinary.length() != 0 && parsedTVSeasons.isEmpty()) throw new FileParsingException(errorParsingMessage);
        }
        else 
        {            
            String errorParsingMessage = "Soubor " + DataStore.getTextInputOutputTVSeasonsFilename() + " má poškozená data";
            StringBuilder text;
            
            try 
            {
                text = getTextInputOutputFileContent();
            }
            catch (FileEmptyException ex) 
            {
                text = new StringBuilder();
            }

            Class<?> tvSeasonInputOutputClass = TVSeasonInputOutput.class;
            Field[] tvSeasonInputOutputFields = tvSeasonInputOutputClass.getDeclaredFields();
            Map<String, StringBuilder> tvSeasonInputOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> tvSeasonInputOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : tvSeasonInputOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    tvSeasonInputOutputFieldsIds.put(k + 1, field.getName());
                    tvSeasonInputOutputFieldsValues.put(field.getName(), new StringBuilder());
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
                    else if (textLine.matches("^[\\s\t]*" + inputFileEndMarking 
                            + "[\\s\t]*$") && enteredSectionValues == true) 
                    {
                        try 
                        {
                            parseInputOutputDataFromTextFile(tvSeasonInputOutputFieldsValues, parsedTVSeasons, tvSeasonInputOutputFields);
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
                            parseInputOutputDataFromTextFile(tvSeasonInputOutputFieldsValues, parsedTVSeasons, tvSeasonInputOutputFields);
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

                        if (fieldParts.length != 2) throw new FileParsingException(errorParsingMessage);

                        try 
                        {
                            fieldId = Integer.parseInt(fieldParts[1]);
                        } 
                        catch (NumberFormatException ex) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        fieldName = tvSeasonInputOutputFieldsIds.get(fieldId);

                        if (fieldName == null) throw new FileParsingException(errorParsingMessage);

                        fieldValue = tvSeasonInputOutputFieldsValues.get(fieldName);
                        newFieldValue = fieldValue.append(fieldParts[0]);

                        tvSeasonInputOutputFieldsValues.put(fieldName, newFieldValue);
                    }
                }
            }
            
            if (isFileEmpty == false && parsedTVSeasons.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
                                
        return parsedTVSeasons;
    }
    
    public @Override void tryDeleteDataInputOutputFilesCopies() 
    {
        File inputOutputTVSeasonsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextInputOutputTVSeasonsFilename());
        
        File inputOutputTVSeasonsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryInputOutputTVSeasonsFilename());
        
        inputOutputTVSeasonsTextCopy.delete();
        inputOutputTVSeasonsBinaryCopy.delete();
    }
         
    public @Override void transferBetweenInputOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
    {
        File inputOutputTVSeasonsTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextInputOutputTVSeasonsFilename());
        
        File inputOutputTVSeasonsBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryInputOutputTVSeasonsFilename());
        
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = inputOutputTVSeasonsTextCopy.getName();
            sourceBinaryFile = inputOutputTVSeasonsBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryInputOutputTVSeasonsFilename();
            destinationTextFile = DataStore.getTextInputOutputTVSeasonsFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextInputOutputTVSeasonsFilename();
            sourceBinaryFile = DataStore.getBinaryInputOutputTVSeasonsFilename();
            destinationBinaryFile = inputOutputTVSeasonsBinaryCopy.getName();
            destinationTextFile = inputOutputTVSeasonsTextCopy.getName();
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
            inputOutputTVSeasonsTextCopy.delete();
            inputOutputTVSeasonsBinaryCopy.delete();
            throw new IOException("Chyba při kopírování mezi vstupními/výstupními soubory sezón seriálů a kopiemi");
        }
    }
    
    public @Override void saveInputOutputDataIntoFiles(List<TVSeasonInputOutput> newInputOutputData) throws IOException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextInputOutputTVSeasonsFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputTVSeasonsFilename(), false)))) 
        {
            StringBuilder generatedTVSeasonsTextRepresentations = 
                    createInputOutputDataTextRepresentation(newInputOutputData);
            
            for (TVSeasonInputOutput m : newInputOutputData) 
            {
                dataOutputStream.writeInt(m.getId());
                dataOutputStream.writeInt(m.getOrderInTVShow());
                dataOutputStream.writeInt(m.getTVShowId());
            }

            bufferedWriter.write(generatedTVSeasonsTextRepresentations.toString());
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při zápisu vstupních/výstupních souborů sezón seriálů");
        }
    }
    
    public @Override Map<Integer, TVSeasonInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileEmptyException, FileNotFoundException, FileParsingException
    {
        StringBuilder text = fromBinary == true ? getBinaryInputFileContent() : getTextInputFileContent();
                             
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
                
        Map<Integer, TVSeasonInput> parsedTVSeasons = new LinkedHashMap<>();
        int inputTVSeasonOrder = 0;
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
                    parseInputData(tvSeasonInputFieldsValues, parsedTVSeasons, tvSeasonInputFields, inputTVSeasonOrder);
                    
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
                    parseInputData(tvSeasonInputFieldsValues, parsedTVSeasons, tvSeasonInputFields, inputTVSeasonOrder);
                    
                    inputTVSeasonOrder++;
                    enteredSectionAttributes = true;
                    enteredSectionValues = false;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == false) 
                {
                    inputTVSeasonOrder++;
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
                    
                    fieldName = tvSeasonInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null) continue;
                    
                    fieldValue = tvSeasonInputFieldsValues.get(fieldName);
                    newFieldValue = fieldValue.append(fieldParts[0]);
                    
                    tvSeasonInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        if (parsedTVSeasons.isEmpty()) 
        {
            throw new FileParsingException(String.format("Nic se nenahrálo ze souboru %s", fromBinary == true ? 
                    DataStore.getBinaryInputTVSeasonsFilename() : DataStore.getTextInputTVSeasonsFilename()));
        }
        
        return parsedTVSeasons;
    }
    
    
    /**
     * Represents a method which parses tv season input data (one record) from input binary or text 
     * file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param tvSeasonInputFieldsValues represents list of parsed tv season record values mapped to
     * according tv season input model data attributes names.
     * @param parsedTVSeasons represents a list of currently parsed tv seasons
     * @param tvSeasonInputFields represents tv season input model data attributes names, which are
     * used for indexing particular fields values.
     * @param inputTVSeasonOrder represents int value, which specifies tv season record loading 
     * order from file (record on start of file - value 1, record after - value 2).
     */
    private void parseInputData(Map<String, StringBuilder> tvSeasonInputFieldsValues,
            Map<Integer, TVSeasonInput> parsedTVSeasons, Field[] tvSeasonInputFields, int inputTVSeasonOrder) 
    {
        try 
        {
            int orderInTVShow = Integer.parseInt(tvSeasonInputFieldsValues.
                    get("orderInTVShow").toString());
                        
            parsedTVSeasons.put(inputTVSeasonOrder, new TVSeasonInput(orderInTVShow));
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
    
    /**
     * Represents a method which parses tv season input/output data (one record) from input/output text file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param tvSeasonInputOutputFieldsValues represents list of parsed tv season record values mapped to
     * according tv season input/output model data attributes names.
     * @param parsedTVSeasons represents a list of currently parsed tv seasons
     * @param tvSeasonInputOutputFields represents tv season input/output model data attributes names, which are
     * used for indexing particular fields values.
     * @throws NumberFormatException when parsed tv season record number values cannot be converted to from String
     */
    private void parseInputOutputDataFromTextFile(Map<String, StringBuilder> tvSeasonInputOutputFieldsValues,
            List<TVSeasonInputOutput> parsedTVSeasons, Field[] tvSeasonInputOutputFields) 
    {        
        int id = Integer.parseInt(tvSeasonInputOutputFieldsValues.get("id").toString());
        int orderInTVShow = Integer.parseInt(tvSeasonInputOutputFieldsValues.get("orderInTVShow").toString());
        int tvShowId = Integer.parseInt(tvSeasonInputOutputFieldsValues.get("tvShowId").toString());

        parsedTVSeasons.add(new TVSeasonInputOutput(id, orderInTVShow, tvShowId));

        tvSeasonInputOutputFieldsValues.clear();

        for (Field field : tvSeasonInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvSeasonInputOutputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    /**
     * Represents a method which creates tv season input/output data (multiple records) text representation
     * for input/output text file.
     * @param newInputOutputTVSeasons represents list of tv season input/output models data from database
     * @return text content to save into input/output text file
     */
    private StringBuilder createInputOutputDataTextRepresentation(List<TVSeasonInputOutput> newInputOutputTVSeasons) 
    {
        Class<?> tvSeasonInputOutputClass = TVSeasonInputOutput.class;
        Field[] tvSeasonInputOutputFields = tvSeasonInputOutputClass.getDeclaredFields();
        Map<String, Integer> tvSeasonInputOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : tvSeasonInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                tvSeasonInputOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder inputOutputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;

        for (TVSeasonInputOutput m : newInputOutputTVSeasons) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            inputOutputTextData.append(attributesMarking).append("\n");
            inputOutputTextData.append("\n");
            
            inputOutputTextData.append("Identificator: ").append(m.getId()).append("\n");
            
            inputOutputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : tvSeasonInputOutputFieldsIds.entrySet()) 
            {
                inputOutputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = inputFileValuesSectionMarking.replaceAll("\\\\", "");
            inputOutputTextData.append("\n");
            inputOutputTextData.append(valuesMarking).append("\n");
            inputOutputTextData.append("\n");

            inputOutputTextData.append(m.getId()).append(" ").
                    append(tvSeasonInputOutputFieldsIds.get("id")).
                    append("\n");
            inputOutputTextData.append(m.getOrderInTVShow()).
                    append(" ").append(tvSeasonInputOutputFieldsIds.get("orderInTVShow")).
                    append("\n");
            
            inputOutputTextData.append(m.getTVShowId()).
                    append(" ").append(tvSeasonInputOutputFieldsIds.get("tvShowId")).
                    append("\n").append("\n");
        }
        
        if (newInputOutputTVSeasons.isEmpty() == false) 
        {
            String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
            inputOutputTextData.append(endMarking).append("\n");
        }
        
        return inputOutputTextData;
    }
}
