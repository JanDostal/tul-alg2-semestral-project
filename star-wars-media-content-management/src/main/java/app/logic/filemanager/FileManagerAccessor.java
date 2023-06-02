
package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.MovieInput;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
import app.models.input.TVShowInput;
import app.models.output.MovieOutput;
import app.models.output.TVEpisodeOutput;
import app.models.output.TVSeasonOutput;
import app.models.output.TVShowOutput;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import utils.interfaces.IDataFileManager;

/**
 *
 * @author Admin
 */
public class FileManagerAccessor 
{
    private static FileManagerAccessor fileManagerAccessor;
    
    private static File dataDirectory;
    
    private IDataFileManager<MovieInput, MovieOutput> moviesFileManager;
    
    private IDataFileManager<TVShowInput, TVShowOutput> tvShowsFileManager;
    
    private IDataFileManager<TVSeasonInput, TVSeasonOutput> tvSeasonsFileManager;
    
    private IDataFileManager<TVEpisodeInput, TVEpisodeOutput> tvEpisodesFileManager;
    
    private final String filenameSeparator = System.getProperty("file.separator");;
    
    private final String inputFileEndMarking = "\\[End\\]";
    
    private final String inputFileValuesSectionMarking = "\\[Values\\]";
    
    private final String inputFileAttributesSectionMarking = "\\[Attributes\\]";

    private FileManagerAccessor() 
    {
        this.moviesFileManager = MoviesFileManager.getInstance(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking);
        this.tvShowsFileManager = TVShowsFileManager.getInstance(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking);
        this.tvSeasonsFileManager = TVSeasonsFileManager.getInstance(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking); 
        this.tvEpisodesFileManager = TVEpisodesFileManager.getInstance(filenameSeparator,
            inputFileEndMarking, inputFileValuesSectionMarking, inputFileAttributesSectionMarking);
    }
    
        
    public static FileManagerAccessor getInstance() 
    {
        if (fileManagerAccessor == null) 
        {
            fileManagerAccessor = new FileManagerAccessor();
        }
        
        return fileManagerAccessor;
    }

    public IDataFileManager<TVSeasonInput, TVSeasonOutput> getTVSeasonsFileManager() 
    {
        return tvSeasonsFileManager;
    }

    public IDataFileManager<TVShowInput, TVShowOutput> getTVShowsFileManager() 
    {
        return tvShowsFileManager;
    }
    
    public IDataFileManager<TVEpisodeInput, TVEpisodeOutput> getTVEpisodesFileManager() 
    {
        return tvEpisodesFileManager;
    }
    
    public IDataFileManager<MovieInput, MovieOutput> getMoviesFileManager() 
    {
        return moviesFileManager;
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
           else if (dataDirectory.getName().equals(DataStore.getDataDirectoryName()) == false) 
           {
               dataDirectory = null;
               throw new IllegalArgumentException("Zadaný adresar neni pojmenovany jako data");
           }
        }
        else 
        {
            throw new IllegalStateException("Cesta k adresari data byla jiz zadana.");
        }
    }
    
    public StringBuilder getTextFileContent(String fileName) throws FileNotFoundException, IOException 
    {
        StringBuilder text = new StringBuilder();
                
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(dataDirectory.getAbsolutePath() + filenameSeparator + fileName), StandardCharsets.UTF_8))) 
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
                //exception
            }
        }
        
        return text;
    }
        
    public StringBuilder getBinaryFileContent(String fileName) throws FileNotFoundException, IOException 
    {
        StringBuilder text = new StringBuilder();
        
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream
        (dataDirectory.getAbsolutePath() + filenameSeparator + fileName))) 
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
        
        File binaryFile = new File(dataDirectory.getAbsolutePath() + filenameSeparator
                + fileName);
        
        if (binaryFile.length() == 0) 
        {
            //exception
        }
        
        return text;
    }   
}
