
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.MovieInput;
import app.models.input.TVShowInput;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FileManager 
{
    private static FileManager fileManager;
    
    private final String filenameSeparator;
    
    private File dataDirectory;
    
    private final String textFileEndMarking = "\\[End\\]";
    
    private final String textFileValuesSectionMarking = "\\[Values\\]";
    
    private final String textFileAttributesSectionMarking = "\\[Attributes\\]";
    
    private FileManager() 
    {
        this.filenameSeparator = System.getProperty("file.separator");
    }
    
    public void setDataDirectory(String directoryFullPath) 
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
    
    public List<MovieInput> addMoviesFromText() throws IOException, FileNotFoundException
    {   
        StringBuilder[] values = new StringBuilder[7];
        for (int i = 0; i < values.length; i++) 
        {
            values[i] = new StringBuilder();
        }
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        
        List<MovieInput> parsedMovies = new ArrayList<>();
        
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator +
                        DataStore.getMoviesTextFileAddFilename()), StandardCharsets.UTF_8))) 
        {
            String text;
            
            while ((text = r.readLine()) != null) 
            {
                try 
                {
                    if (text.matches("^$") || text.matches("^\\s+$")) 
                    {
                        continue;
                    }
                    else if (text.matches(textFileEndMarking) && enteredSectionValues == true) 
                    {                        
                        long runtime = Long.parseLong(values[0].toString());
                        int percentage = Integer.parseInt(values[2].toString());
                        long epochSeconds = Long.parseLong(values[5].toString());
                        
                        parsedMovies.add(new MovieInput(runtime, values[1].toString(),
                        percentage, values[3].toString(), values[4].toString(),
                        epochSeconds, values[6].toString()));
                        
                        enteredSectionAttributes = true;
                        enteredSectionValues = false;
                        continue;
                    }
                    else if (text.matches(textFileValuesSectionMarking) && enteredSectionAttributes == true) 
                    {                        
                        enteredSectionValues = true;
                        continue;
                    }
                    else if ((text.matches(textFileAttributesSectionMarking) && enteredSectionValues == true)) 
                    {
                        long runtime = Long.parseLong(values[0].toString());
                        int percentage = Integer.parseInt(values[2].toString());
                        long epochSeconds = Long.parseLong(values[5].toString());
                        
                        parsedMovies.add(new MovieInput(runtime, values[1].toString(),
                        percentage, values[3].toString(), values[4].toString(),
                        epochSeconds, values[6].toString()));
                        
                        enteredSectionAttributes = true;
                        enteredSectionValues = false;
                        
                        int valuesLength = values.length;
                        values = new StringBuilder[valuesLength];
                        for (int i = 0; i < values.length; i++) 
                        {
                            values[i] = new StringBuilder();
                        }
                        continue;
                    }
                    else if (text.matches(textFileAttributesSectionMarking) && enteredSectionAttributes == false) 
                    {
                        enteredSectionAttributes = true;
                        continue;
                    }
                    
                    
                    if (enteredSectionValues == true) 
                    {
                        String[] parts = text.split(" (?=[^ ]+$)");
                        
                        int linkedId = Integer.parseInt(parts[parts.length - 1]);
                        switch (linkedId) 
                        {
                            case 1:
                                values[0].append(parts[0]);
                                break;
                            case 2:
                                values[1].append(parts[0]);
                                break;
                            case 3:
                                values[2].append(parts[0]);
                                break;
                            case 4:
                                values[3].append(parts[0]);
                                break;
                            case 5:
                                values[4].append(parts[0]).append("\n");
                                break;
                            case 6:
                                values[5].append(parts[0]);
                                break;
                            case 7:
                                values[6].append(parts[0]);
                                break;
                        }
                    }
                }
                catch (NumberFormatException ex) 
                {
                } 
            }

        }
        
        return parsedMovies;
    }
    
    public List<TVShowInput> addTVShowsFromText() throws IOException, FileNotFoundException
    {   
        StringBuilder[] values = new StringBuilder[3];
        for (int i = 0; i < values.length; i++) 
        {
            values[i] = new StringBuilder();
        }
        boolean enteredSectionAttributes = false;
        boolean enteredSectionValues = false;
        
        List<TVShowInput> parsedTVShows = new ArrayList<>();
        
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator +
                        DataStore.getTvShowsTextFileAddFilename()), StandardCharsets.UTF_8))) 
        {
            String text;
            
            while ((text = r.readLine()) != null) 
            {
                try 
                {
                    if (text.matches("^$") || text.matches("^\\s+$")) 
                    {
                        continue;
                    }
                    else if (text.matches(textFileEndMarking) && enteredSectionValues == true) 
                    {                        
                        long epochSeconds = Long.parseLong(values[1].toString());
                        
                        parsedTVShows.add(new TVShowInput(values[0].toString(),epochSeconds,
                        values[2].toString()));
                        
                        enteredSectionAttributes = true;
                        enteredSectionValues = false;
                        continue;
                    }
                    else if (text.matches(textFileValuesSectionMarking) && enteredSectionAttributes == true) 
                    {                        
                        enteredSectionValues = true;
                        continue;
                    }
                    else if ((text.matches(textFileAttributesSectionMarking) && enteredSectionValues == true)) 
                    {
                        long epochSeconds = Long.parseLong(values[1].toString());
                        
                        parsedTVShows.add(new TVShowInput(values[0].toString(),epochSeconds,
                        values[2].toString()));
                        
                        enteredSectionAttributes = true;
                        enteredSectionValues = false;
                        
                        int valuesLength = values.length;
                        values = new StringBuilder[valuesLength];
                        for (int i = 0; i < values.length; i++) 
                        {
                            values[i] = new StringBuilder();
                        }
                        continue;
                    }
                    else if (text.matches(textFileAttributesSectionMarking) && enteredSectionAttributes == false) 
                    {
                        enteredSectionAttributes = true;
                        continue;
                    }
                    
                    
                    if (enteredSectionValues == true) 
                    {
                        String[] parts = text.split(" (?=[^ ]+$)");
                        
                        int linkedId = Integer.parseInt(parts[parts.length - 1]);
                        switch (linkedId) 
                        {
                            case 1:
                                values[0].append(parts[0]);
                                break;
                            case 2:
                                values[1].append(parts[0]);
                                break;
                            case 3:
                                values[2].append(parts[0]);
                                break;
                        }
                    }
                }
                catch (NumberFormatException ex) 
                {
                } 
            }

        }
        
        return parsedTVShows;
    }
    
    public static FileManager getInstance() 
    {
        if (fileManager == null) 
        {
            fileManager = new FileManager();
        }
        
        return fileManager;
    }
}
