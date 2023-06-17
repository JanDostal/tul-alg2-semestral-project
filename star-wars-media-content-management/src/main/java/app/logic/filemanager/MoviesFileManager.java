    
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.MovieInput;
import app.models.output.MovieOutput;
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
public class MoviesFileManager implements IDataFileManager<MovieInput, MovieOutput>
{
    private static IDataFileManager<MovieInput, MovieOutput> moviesFileManager;
        
    private final String filenameSeparator;
    
    private final String inputFileEndMarking;
    
    private final String inputFileValuesSectionMarking;
    
    private final String inputFileAttributesSectionMarking;
    
    private MoviesFileManager(String filenameSeparator, 
            String inputFileEndMarking, String inputFileValuesSectionMarking,
            String inputFileAttributesSectionMarking) 
    {
        this.filenameSeparator = filenameSeparator;
        this.inputFileEndMarking = inputFileEndMarking;
        this.inputFileValuesSectionMarking = inputFileValuesSectionMarking;
        this.inputFileAttributesSectionMarking = inputFileAttributesSectionMarking;
    }
    
    protected static IDataFileManager<MovieInput, MovieOutput> getInstance(String filenameSeparator, 
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
    
    public @Override StringBuilder getTextOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                        DataStore.getTextOutputMoviesFilename()), StandardCharsets.UTF_8))) 
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
                    DataStore.getTextOutputMoviesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getTextOutputMoviesFilename());
        }
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                throw new FileEmptyException("Soubor " + DataStore.getTextOutputMoviesFilename() + " je prázdný");
            }
        }
        
        return text;
    }

    public @Override StringBuilder getBinaryOutputFileContent() throws FileNotFoundException, IOException, 
            FileEmptyException
    {
        StringBuilder text = new StringBuilder();
        String moviesDivider = "\n\n\n\n\n\n\n\n\n";
        
        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputMoviesFilename())))) 
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

                    movieName = new char[MovieOutput.ATTRIBUTE_NAME_LENGTH];

                    for (int i = 0; i < movieName.length; i++) 
                    {
                        movieName[i] = dataInputStream.readChar();
                    }

                    moviePercentageRating = dataInputStream.readInt();

                    movieHyperlink = new char[MovieOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                    for (int i = 0; i < movieHyperlink.length; i++) 
                    {
                        movieHyperlink[i] = dataInputStream.readChar();
                    }

                    movieContent = new char[MovieOutput.ATTRIBUTE_CONTENT_LENGTH];

                    for (int i = 0; i < movieContent.length; i++) 
                    {
                        movieContent[i] = dataInputStream.readChar();
                    }

                    movieReleaseDateInEpochSeconds = dataInputStream.readLong();

                    movieEraCodeDesignation = new char[MovieOutput.ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH];

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
                    DataStore.getBinaryOutputMoviesFilename() + " neexistuje");
        }
        catch (IOException e) 
        {
            throw new IOException("Chyba při čtení souboru " + 
                    DataStore.getBinaryOutputMoviesFilename());
        }
        
        File binaryFile = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator 
                + DataStore.getBinaryOutputMoviesFilename());
        
        if (binaryFile.length() == 0) 
        {
            throw new FileEmptyException("Soubor " + DataStore.getBinaryOutputMoviesFilename() + " je prázdný");
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
    
    public @Override List<MovieOutput> loadOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException
    {
        List<MovieOutput> parsedMovies = new ArrayList<>();
        File outputMoviesBinary = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                DataStore.getBinaryOutputMoviesFilename());
        File outputMoviesText = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextOutputMoviesFilename());
        
        outputMoviesBinary.createNewFile();
        outputMoviesText.createNewFile();
        
        if (fromBinary == true) 
        {
            String errorParsingMessage = "Soubor " + DataStore.getBinaryOutputMoviesFilename()+ " má poškozená data";
            
            try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputMoviesFilename())))) 
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

                        movieName = new char[MovieOutput.ATTRIBUTE_NAME_LENGTH];

                        for (int i = 0; i < movieName.length; i++) 
                        {
                            movieName[i] = dataInputStream.readChar();
                        }

                        moviePercentageRating = dataInputStream.readInt();

                        movieHyperlink = new char[MovieOutput.ATTRIBUTE_HYPERLINK_LENGTH];

                        for (int i = 0; i < movieHyperlink.length; i++) 
                        {
                            movieHyperlink[i] = dataInputStream.readChar();
                        }

                        movieContent = new char[MovieOutput.ATTRIBUTE_CONTENT_LENGTH];

                        for (int i = 0; i < movieContent.length; i++) 
                        {
                            movieContent[i] = dataInputStream.readChar();
                        }

                        movieReleaseDate = dataInputStream.readLong();

                        movieEraCodeDesignation = new char[MovieOutput.ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH];

                        for (int i = 0; i < movieEraCodeDesignation.length; i++) 
                        {
                            movieEraCodeDesignation[i] = dataInputStream.readChar();
                        }
                        
                        parsedMovies.add(new MovieOutput(movieId, movieRuntime, new String(movieName), 
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
                throw new IOException("Chyba při čtení souboru " + DataStore.getBinaryOutputMoviesFilename());
            }
            
            if (outputMoviesBinary.length() != 0 && parsedMovies.isEmpty()) 
            {
                throw new FileParsingException(errorParsingMessage);
            }
        }
        else 
        {            
            StringBuilder text = new StringBuilder();
            String errorParsingMessage = "Soubor " + DataStore.getTextOutputMoviesFilename() + " má poškozená data";
            
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                    DataStore.getTextOutputMoviesFilename()), StandardCharsets.UTF_8))) 
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
                throw new IOException("Chyba při čtení souboru " + DataStore.getTextOutputMoviesFilename());
            }

            Class<?> movieOutputClass = MovieOutput.class;
            Field[] movieOutputFields = movieOutputClass.getDeclaredFields();
            Map<String, StringBuilder> movieOutputFieldsValues = new LinkedHashMap<>();
            Map<Integer, String> movieOutputFieldsIds = new LinkedHashMap<>();

            int k = 0;

            for (Field field : movieOutputFields) 
            {
                if (!Modifier.isStatic(field.getModifiers())) 
                {
                    movieOutputFieldsIds.put(k + 1, field.getName());
                    movieOutputFieldsValues.put(field.getName(), new StringBuilder());
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
                            parseOutputData(movieOutputFieldsValues, parsedMovies, movieOutputFields);
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
                            parseOutputData(movieOutputFieldsValues, parsedMovies, movieOutputFields);
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

                        String fieldName = movieOutputFieldsIds.get(fieldId);

                        if (fieldName == null) 
                        {
                            throw new FileParsingException(errorParsingMessage);
                        }

                        StringBuilder fieldValue = movieOutputFieldsValues.get(fieldName);
                        StringBuilder newFieldValue = fieldValue.append(parts[0]);

                        if (fieldName.equals("shortContentSummary")) 
                        {
                            newFieldValue.append("\n");
                        }

                        movieOutputFieldsValues.put(fieldName, newFieldValue);
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
    
    public @Override void tryDeleteDataOutputFilesCopies() 
    {
        File outputMoviesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputMoviesFilename());
        
        File outputMoviesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputMoviesFilename());
        
        outputMoviesTextCopy.delete();
        outputMoviesBinaryCopy.delete();
    }
    
    public @Override void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
    {
        File outputMoviesTextCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getTextOutputMoviesFilename());
        
        File outputMoviesBinaryCopy = new File(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator +
                "copy_" + DataStore.getBinaryOutputMoviesFilename());
                
        String sourceTextFile;
        String sourceBinaryFile;
        String destinationBinaryFile;
        String destinationTextFile;
        
        if (fromCopyFiles == true) 
        {
            sourceTextFile = outputMoviesTextCopy.getName();
            sourceBinaryFile = outputMoviesBinaryCopy.getName();
            destinationBinaryFile = DataStore.getBinaryOutputMoviesFilename();
            destinationTextFile = DataStore.getTextOutputMoviesFilename();
        }
        else 
        {
            sourceTextFile = DataStore.getTextOutputMoviesFilename();
            sourceBinaryFile = DataStore.getBinaryOutputMoviesFilename();
            destinationBinaryFile = outputMoviesBinaryCopy.getName();
            destinationTextFile = outputMoviesTextCopy.getName();
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
            outputMoviesTextCopy.delete();
            outputMoviesBinaryCopy.delete();
            throw new IOException("Chyba při kopírování mezi výstupními soubory filmů a kopiemi");
        }
    }
        
    public @Override void saveOutputDataIntoFiles(List<MovieOutput> newOutputData) throws IOException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + filenameSeparator + 
                DataStore.getTextOutputMoviesFilename(), false), StandardCharsets.UTF_8));
             DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FileManagerAccessor.getDataDirectoryPath() + 
                filenameSeparator + DataStore.getBinaryOutputMoviesFilename(), false)))) 
        {
            StringBuilder generatedMoviesTextRepresentations = 
                    createOutputDataTextRepresentation(newOutputData);
            
            for (MovieOutput m : newOutputData) 
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
                    
                    String fieldName = movieInputFieldsIds.get(fieldId);
                    
                    if (fieldName == null)
                    {
                        continue;
                    }
                    
                    StringBuilder fieldValue = movieInputFieldsValues.get(fieldName);
                    StringBuilder newFieldValue = fieldValue.append(parts[0]);
                    
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
    
    private void parseOutputData(Map<String, StringBuilder> movieOutputFieldsValues,
            List<MovieOutput> parsedMovies, Field[] movieOutputFields)
    {        
        int id = Integer.parseInt(movieOutputFieldsValues.get("id").toString());
        long runtime = Long.parseLong(movieOutputFieldsValues.get("runtimeInSeconds").toString());
        int percentage = Integer.parseInt(movieOutputFieldsValues.get("percentageRating").toString());
        long epochSeconds = Long.parseLong(movieOutputFieldsValues.get("releaseDateInEpochSeconds").toString());

        parsedMovies.add(new MovieOutput(id, runtime, movieOutputFieldsValues.get("name").toString(),
                percentage, movieOutputFieldsValues.get("hyperlinkForContentWatch").toString(),
                movieOutputFieldsValues.get("shortContentSummary").toString(),
                epochSeconds, movieOutputFieldsValues.get("eraCodeDesignation").toString()));

        movieOutputFieldsValues.clear();

        for (Field field : movieOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                movieOutputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    private StringBuilder createOutputDataTextRepresentation(List<MovieOutput> newOutputMovies) 
    {
        Class<?> movieOutputClass = MovieOutput.class;
        Field[] movieOutputFields = movieOutputClass.getDeclaredFields();
        Map<String, Integer> movieOutputFieldsIds = new LinkedHashMap<>();
        
        int k = 0;
        
        for (Field field : movieOutputFields) 
        {
            if (!Modifier.isStatic(field.getModifiers())) 
            {
                movieOutputFieldsIds.put(field.getName(), k + 1);
                k++;
            }
        }

        StringBuilder outputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;
        StringBuilder changedStringField;

        for (MovieOutput m : newOutputMovies) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append(attributesMarking).append("\n");
            outputTextData.append("\n");
            
            outputTextData.append("Identificator: ").append(m.getId()).append("\n");
            
            outputTextData.append("\n");

            for (Map.Entry<String, Integer> entry : movieOutputFieldsIds.entrySet()) 
            {
                outputTextData.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }

            valuesMarking = inputFileValuesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append("\n");
            outputTextData.append(valuesMarking).append("\n");
            outputTextData.append("\n");

            outputTextData.append(m.getId()).append(" ").
                    append(movieOutputFieldsIds.get("id")).
                    append("\n");
            outputTextData.append(m.getRuntimeInSeconds()).
                    append(" ").append(movieOutputFieldsIds.get("runtimeInSeconds")).
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
                    append(movieOutputFieldsIds.get("name")).append("\n");
            outputTextData.append(m.getPercentageRating()).
                    append(" ").append(movieOutputFieldsIds.get("percentageRating")).
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
                    append(" ").append(movieOutputFieldsIds.get("hyperlinkForContentWatch")).
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
                        append(movieOutputFieldsIds.get("shortContentSummary")).append("\n");
            }

            outputTextData.append(m.getReleaseDateInEpochSeconds()).
                    append(" ").append(movieOutputFieldsIds.get("releaseDateInEpochSeconds")).
                    append("\n");
            
            changedStringField = new StringBuilder();
            
            for (char c : m.getEraCodeDesignation().toCharArray()) 
            {
                if (c != Character.MIN_VALUE) 
                {
                    changedStringField.append(c);
                }
            }
            
            outputTextData.append(changedStringField.toString()).
                    append(" ").append(movieOutputFieldsIds.get("eraCodeDesignation")).
                    append("\n").append("\n");
        }
        
        if (newOutputMovies.isEmpty() == false) 
        {
            String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
            outputTextData.append(endMarking).append("\n");
        }
        
        return outputTextData;
    }
}
