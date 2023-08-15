package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.MovieInput;
import app.models.inputoutput.MovieInputOutput;
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
 * Represents a movies file manager, which works specifically with movies data files.
 * Movies file manager works with movie input and input/output data models and implements IDataFileManager interface.
 * Movies file manager is made available through accessor.
 * @author jan.dostal
 */
public class MoviesFileManager implements IDataFileManager<MovieInput, MovieInputOutput>
{
    private static IDataFileManager<MovieInput, MovieInputOutput> moviesFileManager;
        
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
    
    /**
     * Creates singleton instance of MoviesFileManager.
     * Receives filenameSeparator, inputFileEndMarking, inputFileValuesSectionMarking 
     * and inputFileAttributesSectionMarking parameters in constructor from {@link FileManagerAccessor} class.
     * @param filenameSeparator file path separator dependent on application running operating system
     * @param inputFileEndMarking control string for detecting file end
     * @param inputFileValuesSectionMarking control string for detecting values section in file
     * @param inputFileAttributesSectionMarking control string for detecting attributes section in file
     */
    private MoviesFileManager(String filenameSeparator, 
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
     * @return singleton instance of MoviesFileManager as interface
     */
    protected static IDataFileManager<MovieInput, MovieInputOutput> getInstance(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        if (moviesFileManager == null) 
        {
            moviesFileManager = new MoviesFileManager(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking);
        }
        
        return moviesFileManager;
    }
    
    public @Override StringBuilder getTextInputOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextInputOutputMoviesFilename()), StandardCharsets.UTF_8))) 
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
                    DataStore.getTextInputOutputMoviesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputOutputMoviesFilename());
        }
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextInputOutputMoviesFilename() + " je prázdný");
            }
        }
        
        return text;
    }

    public @Override StringBuilder getBinaryInputOutputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException
    {
        StringBuilder text = new StringBuilder();
        String moviesDivider = "\n\n\n\n\n\n\n\n\n";
        
        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputMoviesFilename())))) 
        {
            boolean fileEndReached = false;
            
            int movieId;
            long movieRuntimeInSeconds;
            char[] movieName;
            int moviePercentageRating;
            char[] movieHyperlink;
            char[] movieContent;
            long movieReleaseDateInEpochSeconds;
            char[] movieEraCodeDesignation;

            while (fileEndReached == false) 
            {
                try 
                {
                    movieId = dataInputStream.readInt();
                    movieRuntimeInSeconds = dataInputStream.readLong();

                    movieName = new char[MovieInputOutput.ATTRIBUTE_NAME_LENGTH];

                    for (int i = 0; i < movieName.length; i++) 
                    {
                        movieName[i] = dataInputStream.readChar();
                    }

                    moviePercentageRating = dataInputStream.readInt();

                    movieHyperlink = new char[MovieInputOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                    for (int i = 0; i < movieHyperlink.length; i++) 
                    {
                        movieHyperlink[i] = dataInputStream.readChar();
                    }

                    movieContent = new char[MovieInputOutput.ATTRIBUTE_SUMMARY_LENGTH];

                    for (int i = 0; i < movieContent.length; i++) 
                    {
                        movieContent[i] = dataInputStream.readChar();
                    }

                    movieReleaseDateInEpochSeconds = dataInputStream.readLong();

                    movieEraCodeDesignation = new char[MovieInputOutput.ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH];

                    for (int i = 0; i < movieEraCodeDesignation.length; i++) 
                    {
                        movieEraCodeDesignation[i] = dataInputStream.readChar();
                    }
                    
                    text.append(String.format("%-38s%d", "Identifikátor:", movieId)).append("\n");
                    text.append(String.format("%-38s%d", "Délka filmu v sekundách:", movieRuntimeInSeconds)).append("\n");
                    text.append(String.format("%-38s%s", "Název:", new String(movieName))).append("\n");
                    text.append(String.format("%-38s%d", "Procentuální hodnocení:", moviePercentageRating)).append("\n");
                    text.append(String.format("%-38s%s", "Odkaz ke zhlédnutí:", new String(movieHyperlink))).append("\n");
                    
                    text.append("Krátké shrnutí obsahu:").append("\n");
                    text.append("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||").append("\n");
                    text.append(new String(movieContent));
                    text.append("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||").append("\n");
                    
                    text.append(String.format("%-38s%d", "Datum uvedení v epoch sekundách:", movieReleaseDateInEpochSeconds)).append("\n");
                    text.append(String.format("%-38s%s", "Chronologická éra:", new String(movieEraCodeDesignation))).append(moviesDivider);
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
                    DataStore.getBinaryInputOutputMoviesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryInputOutputMoviesFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator 
                + DataStore.getBinaryInputOutputMoviesFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputOutputMoviesFilename() + " je prázdný");
        }
        
        text.delete(text.length() - moviesDivider.length(), text.length());
        
        return text;
    }

    public @Override StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException  
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextInputMoviesFilename()), StandardCharsets.UTF_8))) 
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
                    DataStore.getTextInputMoviesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextInputMoviesFilename());
        }
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextInputMoviesFilename() + " je prázdný");
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
                        DataStore.getBinaryInputMoviesFilename()))) 
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
                    DataStore.getBinaryInputMoviesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryInputMoviesFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputMoviesFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryInputMoviesFilename() + " je prázdný");
        }
        
        return text;
    }
    
    public @Override List<MovieInputOutput> loadInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException
    {
        List<MovieInputOutput> parsedMovies = new ArrayList<>();
        File inputOutputMoviesBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryInputOutputMoviesFilename());
        File inputOutputMoviesText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextInputOutputMoviesFilename());
        
        inputOutputMoviesBinary.createNewFile();
        inputOutputMoviesText.createNewFile();
        
        if (fromBinary == true) 
        {
            String errorParsingMessage = "Soubor " + DataStore.getBinaryInputOutputMoviesFilename()+ " má poškozená data";
            
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputMoviesFilename())))) 
            {
                boolean fileEndReached = false;
                int movieId;
                long movieRuntime;
                char[] movieName;
                int moviePercentageRating;
                char[] movieHyperlink;
                char[] movieContent;
                long movieReleaseDate;
                char[] movieEraCodeDesignation;
                
                while (fileEndReached == false) 
                {
                    try 
                    {
                        movieId = dataInputStream.readInt();
                        movieRuntime = dataInputStream.readLong();

                        movieName = new char[MovieInputOutput.ATTRIBUTE_NAME_LENGTH];

                        for (int i = 0; i < movieName.length; i++) 
                        {
                            movieName[i] = dataInputStream.readChar();
                        }

                        moviePercentageRating = dataInputStream.readInt();

                        movieHyperlink = new char[MovieInputOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                        for (int i = 0; i < movieHyperlink.length; i++) 
                        {
                            movieHyperlink[i] = dataInputStream.readChar();
                        }

                        movieContent = new char[MovieInputOutput.ATTRIBUTE_SUMMARY_LENGTH];

                        for (int i = 0; i < movieContent.length; i++) 
                        {
                            movieContent[i] = dataInputStream.readChar();
                        }

                        movieReleaseDate = dataInputStream.readLong();

                        movieEraCodeDesignation = new char[MovieInputOutput.ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH];

                        for (int i = 0; i < movieEraCodeDesignation.length; i++) 
                        {
                            movieEraCodeDesignation[i] = dataInputStream.readChar();
                        }
                        
                        parsedMovies.add(new MovieInputOutput(movieId, movieRuntime, new String(movieName), 
                                moviePercentageRating, new String(movieHyperlink), new String(movieContent), 
                                movieReleaseDate, new String(movieEraCodeDesignation)));
                    }
                    catch (EOFException e) 
                    {
                        fileEndReached = true;
                    }
                }
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getBinaryInputOutputMoviesFilename());
            }
            
            if (inputOutputMoviesBinary.length() != 0 && parsedMovies.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
        else 
        {            
            StringBuilder text = new StringBuilder();
            String errorParsingMessage = "Soubor " + DataStore.getTextInputOutputMoviesFilename() + " má poškozená data";
            
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextInputOutputMoviesFilename()), StandardCharsets.UTF_8))) 
            {
                String textLine;
            
                while((textLine = bufferedReader.readLine()) != null) 
                {
                    text.append(textLine);
                }
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + DataStore.getTextInputOutputMoviesFilename());
            }

            Class<?> movieInputOutputClass = MovieInputOutput.class;
            Field[] movieInputOutputFields = movieInputOutputClass.getDeclaredFields();
            Map<String, StringBuilder> movieInputOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> movieInputOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : movieInputOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    movieInputOutputFieldsIds.put(k + 1, field.getName());
                    movieInputOutputFieldsValues.put(field.getName(), new StringBuilder());
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
                            parseInputOutputData(movieInputOutputFieldsValues, parsedMovies, movieInputOutputFields);
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
                            parseInputOutputData(movieInputOutputFieldsValues, parsedMovies, movieInputOutputFields);
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

                        fieldName = movieInputOutputFieldsIds.get(fieldId);

                        if (fieldName == null) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        fieldValue = movieInputOutputFieldsValues.get(fieldName);
                        newFieldValue = fieldValue.append(fieldParts[0]);

                        if (fieldName.equals("shortContentSummary")) 
                        {
                            newFieldValue.append("\n");
                        }

                        movieInputOutputFieldsValues.put(fieldName, newFieldValue);
                    }
                }
            }
            
            if (isFileEmpty == false && parsedMovies.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
                                
        return parsedMovies;
    }
    
    public @Override void tryDeleteDataInputOutputFilesCopies() 
    {
        File inputOutputMoviesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextInputOutputMoviesFilename());
        
        File inputOutputMoviesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryInputOutputMoviesFilename());
        
        inputOutputMoviesTextCopy.delete();
        inputOutputMoviesBinaryCopy.delete();
    }
    
    public @Override void transferBetweenInputOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
    {
        File inputOutputMoviesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextInputOutputMoviesFilename());
        
        File inputOutputMoviesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryInputOutputMoviesFilename());
                
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = inputOutputMoviesTextCopy.getName();
            sourceBinaryFile = inputOutputMoviesBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryInputOutputMoviesFilename();
            destinationTextFile = DataStore.getTextInputOutputMoviesFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextInputOutputMoviesFilename();
            sourceBinaryFile = DataStore.getBinaryInputOutputMoviesFilename();
            destinationBinaryFile = inputOutputMoviesBinaryCopy.getName();
            destinationTextFile = inputOutputMoviesTextCopy.getName();
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
            
            while ((textLine = bufferedReader.readLine()) != null) 
            {
                bufferedWriter.write(textLine);
            }
            
            while ((bytesRead = dataInputStream.read(byteBuffer)) != -1) 
            {
                dataOutputStream.write(byteBuffer, 0, bytesRead);
            }
        }
        catch (IOException e) 
        {
            inputOutputMoviesTextCopy.delete();
            inputOutputMoviesBinaryCopy.delete();
            throw new IOException("Chyba při kopírování mezi výstupními soubory filmů a kopiemi");
        }
    }
        
    public @Override void saveInputOutputDataIntoFiles(List<MovieInputOutput> newInputOutputData) throws IOException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextInputOutputMoviesFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryInputOutputMoviesFilename(), false)))) 
        {
            StringBuilder generatedMoviesTextRepresentations = 
                    createInputOutputDataTextRepresentation(newInputOutputData);
            
            for (MovieInputOutput m : newInputOutputData) 
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
                
                dataOutputStream.writeLong(m.getReleaseDateInEpochSeconds());
                
                for (char c : m.getEraCodeDesignation().toCharArray())     
                {
                    dataOutputStream.writeChar(c);
                }
            }

            bufferedWriter.write(generatedMoviesTextRepresentations.toString());
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při zápisu výstupních souborů filmů");
        }
    }
    
    public @Override Map<Integer, MovieInput> loadInputDataFrom(boolean fromBinary) throws IOException, 
            FileEmptyException, FileNotFoundException, FileParsingException
    {
        StringBuilder text = new StringBuilder();
        
        if (fromBinary == true) 
        {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(
                    FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                            DataStore.getBinaryInputMoviesFilename()))) 
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
                        DataStore.getBinaryInputMoviesFilename() + " neexistuje");
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + 
                        DataStore.getBinaryInputMoviesFilename());
            }
            
            File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator
                + DataStore.getBinaryInputMoviesFilename());
        
            if (binaryFile.length() == 0) 
            {
                throw new FileEmptyException("Soubor " + DataStore.getBinaryInputMoviesFilename() + " je prázdný");
            }
        }
        else 
        {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextInputMoviesFilename()), StandardCharsets.UTF_8))) 
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
                        DataStore.getTextInputMoviesFilename() + " neexistuje");
            }
            catch (IOException e) 
            {
                throw new IOException("Chyba při čtení souboru " + 
                        DataStore.getTextInputMoviesFilename());
            }
            
            try (Scanner sc = new Scanner(text.toString())) 
            {
                if (sc.hasNextLine() == false)
                {
                    sc.close();
                    throw new FileEmptyException("Soubor " + DataStore.getTextInputMoviesFilename() + " je prázdný");
                }
            }
        }
                                
        Class<?> movieInputClass = MovieInput.class;
        Field[] movieInputFields = movieInputClass.getDeclaredFields();
        Map<String, StringBuilder> movieInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> movieInputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : movieInputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {                
                movieInputFieldsIds.put(k + 1, field.getName());
                movieInputFieldsValues.put(field.getName(), new StringBuilder());
                k++;
            }
        }
                
        Map<Integer, MovieInput> parsedMovies = new LinkedHashMap<>();   
        int inputMovieOrder = 0;
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
                    parseInputData(movieInputFieldsValues, parsedMovies, movieInputFields, inputMovieOrder);
                    
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
                    parseInputData(movieInputFieldsValues, parsedMovies, movieInputFields, inputMovieOrder);
                    
                    inputMovieOrder++;
                    enteredSectionAttributes = true;
                    enteredSectionValues = false;
                    
                    continue;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == false) 
                {
                    inputMovieOrder++;
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
                    
                    fieldName = movieInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    fieldValue = movieInputFieldsValues.get(fieldName);
                    newFieldValue = fieldValue.append(fieldParts[0]);
                    
                    if (fieldName.equals("shortContentSummary"))
                    {
                        newFieldValue.append("\n");
                    }
                    
                    movieInputFieldsValues.put(fieldName, newFieldValue);
                }
            }
        }
        
        if (parsedMovies.isEmpty()) 
        {
            throw new FileParsingException(String.format("Nic se nenahrálo ze souboru %s", fromBinary == true ? 
                    DataStore.getBinaryInputMoviesFilename() : DataStore.getTextInputMoviesFilename()));
        }

        return parsedMovies;
    }
     
    
    /**
     * Represents a method which parses movie input data (one record) from input binary or text 
     * file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param movieInputFieldsValues represents list of parsed movie record values mapped to
     * according movie input model data attributes names.
     * @param parsedMovies represents a list of currently parsed movies
     * @param movieInputFields represents movie input model data attributes names, which are
     * used for indexing particular fields values.
     * @param inputMovieOrder represents int value, which specifies movie record loading 
     * order from file (record on start of file - value 1, record after - value 2).
     */
    private void parseInputData(Map<String, StringBuilder> movieInputFieldsValues,
            Map<Integer, MovieInput> parsedMovies, Field[] movieInputFields, int inputMovieOrder) 
    {        
        try 
        {
            long runtime = Long.parseLong(movieInputFieldsValues.get("runtimeInSeconds").toString());
            int percentage = Integer.parseInt(movieInputFieldsValues.get("percentageRating").toString());
            long epochSeconds = Long.parseLong(movieInputFieldsValues.get("releaseDateInEpochSeconds").
                                toString());
                        
            parsedMovies.put(inputMovieOrder, new MovieInput(runtime, movieInputFieldsValues.get("name").toString(), 
                    percentage, movieInputFieldsValues.get("hyperlinkForContentWatch").toString(), 
                    movieInputFieldsValues.get("shortContentSummary").toString(), 
                    epochSeconds, movieInputFieldsValues.get("eraCodeDesignation").toString()));
        }
        catch (NumberFormatException ex) 
        {   
        }
        finally 
        {
            movieInputFieldsValues.clear();
            
            for (Field field : movieInputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    movieInputFieldsValues.put(field.getName(), new StringBuilder());
                }
            }
        }
    }
    
    /**
     * Represents a method which parses movie input/output data (one record) from input/output text file when position in
     * file reaches either end marking or reaches another attributes section marking.
     * @param movieInputOutputFieldsValues represents list of parsed movie record values mapped to
     * according movie input/output model data attributes names.
     * @param parsedMovies represents a list of currently parsed movies
     * @param movieInputOutputFields represents movie input/output model data attributes names, which are
     * used for indexing particular fields values.
     * @throws NumberFormatException when parsed movie record number values cannot be converted to from String
     */
    private void parseInputOutputData(Map<String, StringBuilder> movieInputOutputFieldsValues,
            List<MovieInputOutput> parsedMovies, Field[] movieInputOutputFields)
    {        
        int id = Integer.parseInt(movieInputOutputFieldsValues.get("id").toString());
        long runtime = Long.parseLong(movieInputOutputFieldsValues.get("runtimeInSeconds").toString());
        int percentage = Integer.parseInt(movieInputOutputFieldsValues.get("percentageRating").toString());
        long epochSeconds = Long.parseLong(movieInputOutputFieldsValues.get("releaseDateInEpochSeconds").toString());

        parsedMovies.add(new MovieInputOutput(id, runtime, movieInputOutputFieldsValues.get("name").toString(),
                percentage, movieInputOutputFieldsValues.get("hyperlinkForContentWatch").toString(),
                movieInputOutputFieldsValues.get("shortContentSummary").toString(),
                epochSeconds, movieInputOutputFieldsValues.get("eraCodeDesignation").toString()));

        movieInputOutputFieldsValues.clear();

        for (Field field : movieInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                movieInputOutputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    
    /**
     * Represents a method which creates movie input/output data (multiple records) text representation
     * for input/output text file.
     * @param newInputOutputMovies represents list of movie input/output models data from database
     * @return text content to save into input/output text file
     */
    private StringBuilder createInputOutputDataTextRepresentation(List<MovieInputOutput> newInputOutputMovies) 
    {
        Class<?> movieInputOutputClass = MovieInputOutput.class;
        Field[] movieInputOutputFields = movieInputOutputClass.getDeclaredFields();
        Map<String, Integer> movieInputOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : movieInputOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                movieInputOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder inputOutputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;
        StringBuilder changedStringField;
        String[] shortContentSummaryLines;

        for (MovieInputOutput m : newInputOutputMovies) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            inputOutputTextData.append(attributesMarking).append("\n");
            inputOutputTextData.append("\n");
            
            inputOutputTextData.append("Identificator: ").append(m.getId()).append("\n");
            
            inputOutputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : movieInputOutputFieldsIds.entrySet()) 
            {
                inputOutputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = inputFileValuesSectionMarking.replaceAll("\\\\", "");
            inputOutputTextData.append("\n");
            inputOutputTextData.append(valuesMarking).append("\n");
            inputOutputTextData.append("\n");

            inputOutputTextData.append(m.getId()).append(" ").
                    append(movieInputOutputFieldsIds.get("id")).
                    append("\n");
            inputOutputTextData.append(m.getRuntimeInSeconds()).
                    append(" ").append(movieInputOutputFieldsIds.get("runtimeInSeconds")).
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
                    append(movieInputOutputFieldsIds.get("name")).append("\n");
            inputOutputTextData.append(m.getPercentageRating()).
                    append(" ").append(movieInputOutputFieldsIds.get("percentageRating")).
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
                    append(" ").append(movieInputOutputFieldsIds.get("hyperlinkForContentWatch")).
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
                        append(movieInputOutputFieldsIds.get("shortContentSummary")).append("\n");
            }

            inputOutputTextData.append(m.getReleaseDateInEpochSeconds()).
                    append(" ").append(movieInputOutputFieldsIds.get("releaseDateInEpochSeconds")).
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
                    append(" ").append(movieInputOutputFieldsIds.get("eraCodeDesignation")).
                    append("\n").append("\n");
        }
        
        if (newInputOutputMovies.isEmpty() == false) 
        {
            String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
            inputOutputTextData.append(endMarking).append("\n");
        }
        
        return inputOutputTextData;
    }
}
