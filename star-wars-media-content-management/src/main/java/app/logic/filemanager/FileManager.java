
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.MovieInput;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
import app.models.input.TVShowInput;
import app.models.output.MovieOutput;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class FileManager 
{
    private static FileManager fileManager;
    
    private static File dataDirectory;
    
    private final String filenameSeparator;
    
    private final String inputFileEndMarking = "\\[End\\]";
    
    private final String inputFileValuesSectionMarking = "\\[Values\\]";
    
    private final String inputFileAttributesSectionMarking = "\\[Attributes\\]";
    
    private FileManager() 
    {
        this.filenameSeparator = System.getProperty("file.separator");
    }
    
    public static FileManager getInstance() 
    {
        if (fileManager == null) 
        {
            fileManager = new FileManager();
        }
        
        return fileManager;
    }
    
    public static String getDataDirectoryPath() 
    {
        if (dataDirectory == null) 
        {
            throw new IllegalStateException("Cesta k data adresari jeste nebyla nastavena");
        }
        
        return dataDirectory.getAbsolutePath();
    }
    
    public static void setDataDirectory(String directoryFullPath) 
    {
        if (dataDirectory == null) 
        {
           dataDirectory = new File(directoryFullPath); 
           
           if (dataDirectory.exists() == false || dataDirectory.isDirectory() == false) 
           {
               dataDirectory = null;
               throw new IllegalArgumentException("Dany adresar neexistuje nebo se jedna o soubor");
           }
        }
        else 
        {
            throw new IllegalStateException("Cesta k adresari data byla jiz zadana.");
        }
    }
    
    public void saveMoviesIntoTextAndBinary(List<MovieOutput> newOutputMovies) throws IOException, 
            FileNotFoundException
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(dataDirectory.getAbsolutePath() + filenameSeparator + 
                DataStore.getTextOutputMoviesFilename(), false), StandardCharsets.UTF_8));
             RandomAccessFile randomAccess = new RandomAccessFile(dataDirectory.getAbsolutePath() + 
                filenameSeparator + DataStore.getBinaryOutputMoviesFilename(), "rw");
             DataOutputStream dataOutputWriter = new DataOutputStream(
                new FileOutputStream(randomAccess.getFD())))
        {
            StringBuilder generatedMoviesTextRepresentations = 
                    createMoviesTextRepresentation(newOutputMovies);
            
            randomAccess.setLength(0);
            randomAccess.seek(randomAccess.length());
            
            for (MovieOutput m : newOutputMovies) 
            {
                dataOutputWriter.writeInt(m.getId());
                dataOutputWriter.writeLong(m.getRuntimeInSeconds());
                
                for (char c : m.getName().toCharArray()) 
                {
                    dataOutputWriter.writeChar(c);
                }
                
                dataOutputWriter.writeInt(m.getPercentageRating());
                
                for (char c : m.getHyperlinkForContentWatch().toCharArray()) 
                {
                    dataOutputWriter.writeChar(c);
                }
                
                for (char c : m.getShortContentSummary().toCharArray()) 
                {
                    dataOutputWriter.writeChar(c);
                }
                
                dataOutputWriter.writeLong(m.getReleaseDateInEpochSeconds());
                
                for (char c : m.getEra().toCharArray()) 
                {
                    dataOutputWriter.writeChar(c);
                }
            }
                        
            bufferedWriter.write(generatedMoviesTextRepresentations.toString());
        }
    }
    
    public StringBuilder getTextFileContent(String fileName) throws FileNotFoundException, IOException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator +
                        DataStore.getTextInputMoviesFilename()), StandardCharsets.UTF_8))) 
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
        
        try (Scanner sc = new Scanner(text.toString())) 
        {
            if (sc.hasNextLine() == false)
            {
                sc.close();
                //exception
            }
        }
        
        return text;
    }
        
    public StringBuilder getBinaryFileContent(String fileName) throws FileNotFoundException, IOException 
    {
        StringBuilder text = new StringBuilder();
        
        try (BufferedInputStream r = new BufferedInputStream(new FileInputStream
        (dataDirectory.getAbsolutePath() + filenameSeparator + fileName))) 
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
        
        File f = new File(dataDirectory.getAbsolutePath() + filenameSeparator
                + fileName);
        
        if (f.length() == 0) 
        {
            //exception
        }
        
        return text;
    }
    
    public List<MovieInput> loadInputMoviesFromBinary() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        
        try (BufferedInputStream r = new BufferedInputStream(new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputMoviesFilename()))) 
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
        
        File f = new File(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputMoviesFilename());
        
        if (f.length() == 0) 
        {
            //exception
        }
                        
        Class<?> movieInputClass = MovieInput.class;
        Field[] movieInputFields = movieInputClass.getDeclaredFields();
        Map<String, StringBuilder> movieInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> movieInputFieldsIds = new LinkedHashMap<>();
        Field field;
                
        for (int i = 0; i < movieInputFields.length; i++) 
        {
            field = movieInputFields[i];
            movieInputFieldsIds.put(i + 1, field.getName());
            movieInputFieldsValues.put(field.getName(), new StringBuilder());
        }
                
        List<MovieInput> parsedMovies = new ArrayList<>();
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
                    parseMovieInputData(movieInputFieldsValues, parsedMovies, movieInputFields);
                    
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
                    parseMovieInputData(movieInputFieldsValues, parsedMovies,
                            movieInputFields);
                    
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
        
        return parsedMovies;
    }
    
    public List<MovieInput> loadInputMoviesFromText() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator + 
                        DataStore.getTextInputMoviesFilename()), StandardCharsets.UTF_8))) 
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
        
        Class<?> movieInputClass = MovieInput.class;
        Field[] movieInputFields = movieInputClass.getDeclaredFields();
        Map<String, StringBuilder> movieInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> movieInputFieldsIds = new LinkedHashMap<>();
        Field field;
                
        for (int i = 0; i < movieInputFields.length; i++) 
        {
            field = movieInputFields[i];
            movieInputFieldsIds.put(i + 1, field.getName());
            movieInputFieldsValues.put(field.getName(), new StringBuilder());
        }
                
        List<MovieInput> parsedMovies = new ArrayList<>();
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
                    parseMovieInputData(movieInputFieldsValues, parsedMovies, movieInputFields);
                    
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
                    parseMovieInputData(movieInputFieldsValues, parsedMovies,
                            movieInputFields);
                    
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
        
        if (isFileEmpty == true) 
        {
            //exception
        }
        
        return parsedMovies;
    }
    
    public List<TVShowInput> loadInputTVShowsFromBinary() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();

        try ( BufferedInputStream r = new BufferedInputStream(new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputTVShowsFilename()))) 
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
        
        File f = new File(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputTVShowsFilename());
        
        if (f.length() == 0) 
        {
            //exception
        }
        
        Class<?> tvShowInputClass = TVShowInput.class;
        Field[] tvShowInputFields = tvShowInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvShowInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvShowInputFieldsIds = new LinkedHashMap<>();
        Field field;
                
        for (int i = 0; i < tvShowInputFields.length; i++) 
        {
            field = tvShowInputFields[i];
            tvShowInputFieldsIds.put(i + 1, field.getName());
            tvShowInputFieldsValues.put(field.getName(), new StringBuilder());
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
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator + 
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
        Field field;
                
        for (int i = 0; i < tvShowInputFields.length; i++) 
        {
            field = tvShowInputFields[i];
            tvShowInputFieldsIds.put(i + 1, field.getName());
            tvShowInputFieldsValues.put(field.getName(), new StringBuilder());
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
    
    public List<TVSeasonInput> loadInputTVSeasonsFromBinary() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();

        try (BufferedInputStream r = new BufferedInputStream(new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputTVSeasonsFilename()))) 
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
        
        File f = new File(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputTVSeasonsFilename());
        
        if (f.length() == 0) 
        {
            //exception
        }
                
        Class<?> tvSeasonInputClass = TVSeasonInput.class;
        Field[] tvSeasonInputFields = tvSeasonInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvSeasonInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvSeasonInputFieldsIds = new LinkedHashMap<>();
        Field field;
                
        for (int i = 0; i < tvSeasonInputFields.length; i++) 
        {
            field = tvSeasonInputFields[i];
            tvSeasonInputFieldsIds.put(i + 1, field.getName());
            tvSeasonInputFieldsValues.put(field.getName(), new StringBuilder());
        }
                
        List<TVSeasonInput> parsedSeasons = new ArrayList<>();
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
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedSeasons, tvSeasonInputFields);
                    
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
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedSeasons,
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
        
        return parsedSeasons;
    }
    
    public List<TVSeasonInput> loadInputTVSeasonsFromText() throws IOException, 
            FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator +
                        DataStore.getTextInputTVSeasonsFilename()), StandardCharsets.UTF_8))) 
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
            
        Class<?> tvSeasonInputClass = TVSeasonInput.class;
        Field[] tvSeasonInputFields = tvSeasonInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvSeasonInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvSeasonInputFieldsIds = new LinkedHashMap<>();
        Field field;
                
        for (int i = 0; i < tvSeasonInputFields.length; i++) 
        {
            field = tvSeasonInputFields[i];
            tvSeasonInputFieldsIds.put(i + 1, field.getName());
            tvSeasonInputFieldsValues.put(field.getName(), new StringBuilder());
        }
        
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        boolean isFileEmpty = true;
        
        List<TVSeasonInput> parsedTVSeasons = new ArrayList<>();
        
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
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedTVSeasons,
                            tvSeasonInputFields);
                     
                    break;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileValuesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == true)
                {
                    enteredSectionValues = true;
                    continue;
                }
                else if ((textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionValues == true)) 
                {
                    parseTVSeasonInputData(tvSeasonInputFieldsValues, parsedTVSeasons, tvSeasonInputFields);
                    
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
        
        if (isFileEmpty == true) 
        {
            //exception
        }
        
        return parsedTVSeasons;
    }
    
    public List<TVEpisodeInput> loadInputTVEpisodesFromBinary() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        
        try ( BufferedInputStream r = new BufferedInputStream(new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputTVEpisodesFilename()))) 
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
        
        File f = new File(dataDirectory.getAbsolutePath() + filenameSeparator
                + DataStore.getBinaryInputTVEpisodesFilename());
        
        if (f.length() == 0) 
        {
            //exception
        }
                
        Class<?> tvEpisodeInputClass = TVEpisodeInput.class;
        Field[] tvEpisodeInputFields = tvEpisodeInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvEpisodeInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvEpisodeInputFieldsIds = new LinkedHashMap<>();
        Field field;
                
        for (int i = 0; i < tvEpisodeInputFields.length; i++) 
        {
            field = tvEpisodeInputFields[i];
            tvEpisodeInputFieldsIds.put(i + 1, field.getName());
            tvEpisodeInputFieldsValues.put(field.getName(), new StringBuilder());
        }
                
        List<TVEpisodeInput> parsedEpisodes = new ArrayList<>();
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
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes, tvEpisodeInputFields);
                    
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
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes,
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
        
        return parsedEpisodes;
    }
    
    public List<TVEpisodeInput> loadInputTVEpisodesFromText() throws IOException, FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator +
                        DataStore.getTextInputTVEpisodesFilename()), StandardCharsets.UTF_8))) 
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
        
        Class<?> tvEpisodeInputClass = TVEpisodeInput.class;
        Field[] tvEpisodeInputFields = tvEpisodeInputClass.getDeclaredFields();
        Map<String, StringBuilder> tvEpisodeInputFieldsValues = new LinkedHashMap<>();
        Map<Integer, String> tvEpisodeInputFieldsIds = new LinkedHashMap<>();
        Field field;
                
        for (int i = 0; i < tvEpisodeInputFields.length; i++) 
        {
            field = tvEpisodeInputFields[i];
            tvEpisodeInputFieldsIds.put(i + 1, field.getName());
            tvEpisodeInputFieldsValues.put(field.getName(), new StringBuilder());
        }
        
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        boolean isFileEmpty = true;
        
        List<TVEpisodeInput> parsedEpisodes = new ArrayList<>();
        
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
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes,
                            tvEpisodeInputFields);
                     
                    break;
                }
                else if (textLine.matches("^[\\s\t]*" + inputFileValuesSectionMarking +
                        "[\\s\t]*$") && enteredSectionAttributes == true)
                {
                    enteredSectionValues = true;
                    continue;
                }
                else if ((textLine.matches("^[\\s\t]*" + inputFileAttributesSectionMarking +
                        "[\\s\t]*$") && enteredSectionValues == true)) 
                {
                    parseTVEpisodeInputData(tvEpisodeInputFieldsValues, parsedEpisodes,
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

        if (isFileEmpty == true) 
        {
            //exception
        }
        
        return parsedEpisodes;
    }
    
    private void parseTVEpisodeInputData(Map<String, StringBuilder> tvEpisodeInputFieldsValues,
            List<TVEpisodeInput> parsedEpisodes, Field[] tvEpisodeInputFields) 
    {
        try 
        {
            long runtime = Long.parseLong(tvEpisodeInputFieldsValues.get("runtimeInSeconds").toString());
            int percentage = Integer.parseInt(tvEpisodeInputFieldsValues.get("percentageRating").toString());
            int orderInTVShowSeason = Integer.parseInt(tvEpisodeInputFieldsValues.get("orderInTVShowSeason").
                    toString());
                        
            parsedEpisodes.add(new TVEpisodeInput(runtime, tvEpisodeInputFieldsValues.get("name").toString(), 
                    percentage, tvEpisodeInputFieldsValues.get("hyperlinkForContentWatch").toString(), 
                    tvEpisodeInputFieldsValues.get("shortContentSummary").toString(), orderInTVShowSeason));
        }
        catch (NumberFormatException ex) 
        {   
        }
        finally 
        {
            tvEpisodeInputFieldsValues.clear();
            Field field;
            
            for (int i = 0; i < tvEpisodeInputFields.length; i++) 
            {
                field = tvEpisodeInputFields[i];
                tvEpisodeInputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
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
            Field field;
            
            for (int i = 0; i < tvShowInputFields.length; i++) 
            {
                field = tvShowInputFields[i];
                tvShowInputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    private void parseMovieInputData(Map<String, StringBuilder> movieInputFieldsValues,
            List<MovieInput> parsedMovies, Field[] movieInputFields) 
    {        
        try 
        {
            long runtime = Long.parseLong(movieInputFieldsValues.get("runtimeInSeconds").toString());
            int percentage = Integer.parseInt(movieInputFieldsValues.get("percentageRating").toString());
            long epochSeconds = Long.parseLong(movieInputFieldsValues.get("releaseDateInEpochSeconds").
                                toString());
                        
            parsedMovies.add(new MovieInput(runtime, movieInputFieldsValues.get("name").toString(), 
                    percentage, movieInputFieldsValues.get("hyperlinkForContentWatch").toString(), 
                    movieInputFieldsValues.get("shortContentSummary").toString(), 
                    epochSeconds, movieInputFieldsValues.get("era").toString()));
        }
        catch (NumberFormatException ex) 
        {   
        }
        finally 
        {
            movieInputFieldsValues.clear();
            Field field;
            
            for (int i = 0; i < movieInputFields.length; i++) 
            {
                field = movieInputFields[i];
                movieInputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    private void parseTVSeasonInputData(Map<String, StringBuilder> tvSeasonInputFieldsValues,
            List<TVSeasonInput> parsedEpisodes, Field[] tvSeasonInputFields) 
    {
        try 
        {
            int orderInTVShow = Integer.parseInt(tvSeasonInputFieldsValues.
                    get("orderInTVShow").toString());
                        
            parsedEpisodes.add(new TVSeasonInput(orderInTVShow));
        }
        catch (NumberFormatException ex) 
        {   
        }
        finally 
        {
            tvSeasonInputFieldsValues.clear();
            Field field;
            
            for (int i = 0; i < tvSeasonInputFields.length; i++) 
            {
                field = tvSeasonInputFields[i];
                tvSeasonInputFieldsValues.put(field.getName(), new StringBuilder());
            }
        }
    }
    
    private StringBuilder createMoviesTextRepresentation(List<MovieOutput> newOutputMovies) 
    {
        Class<?> movieOutputClass = MovieOutput.class;
        Field[] movieOutputFields = movieOutputClass.getDeclaredFields();
        Map<String, Integer> movieOutputFieldsIds = new LinkedHashMap<>();
        Field field;

        for (int i = 0; i < movieOutputFields.length; i++) 
        {
            field = movieOutputFields[i];
            movieOutputFieldsIds.put(field.getName(), i + 1);
        }

        StringBuilder outputTextData = new StringBuilder();
        String attributesMarking;
        String valuesMarking;

        for (MovieOutput m : newOutputMovies) 
        {
            attributesMarking = inputFileAttributesSectionMarking.replaceAll("\\\\", "");
            outputTextData.append(attributesMarking).append("\n");
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
            outputTextData.append(m.getName()).append(" ").
                    append(movieOutputFieldsIds.get("name")).append("\n");
            outputTextData.append(m.getPercentageRating()).
                    append(" ").append(movieOutputFieldsIds.get("percentageRating")).
                    append("\n");
            outputTextData.append(m.getHyperlinkForContentWatch()).
                    append(" ").append(movieOutputFieldsIds.get("hyperlinkForContentWatch")).
                    append("\n");

            String[] shortContentSummaryLines = m.getShortContentSummary().split("\n");

            for (int i = 0; i < shortContentSummaryLines.length; i++) 
            {
                outputTextData.append(shortContentSummaryLines[i]).append(" ").
                        append(movieOutputFieldsIds.get("shortContentSummary")).append("\n");
            }

            outputTextData.append(m.getReleaseDateInEpochSeconds()).
                    append(" ").append(movieOutputFieldsIds.get("releaseDateInEpochSeconds")).
                    append("\n");
            outputTextData.append(m.getEra()).
                    append(" ").append(movieOutputFieldsIds.get("era")).
                    append("\n").append("\n");
        }

        String endMarking = inputFileEndMarking.replaceAll("\\\\", "");
        outputTextData.append(endMarking).append("\n");
        
        return outputTextData;
    }
}
