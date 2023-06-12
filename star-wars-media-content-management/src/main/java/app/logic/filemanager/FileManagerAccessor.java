
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
    
    private final IDataFileManager<MovieInput, MovieOutput> moviesFileManager;
    
    private final IDataFileManager<TVShowInput, TVShowOutput> tvShowsFileManager;
    
    private final IDataFileManager<TVSeasonInput, TVSeasonOutput> tvSeasonsFileManager;
    
    private final IDataFileManager<TVEpisodeInput, TVEpisodeOutput> tvEpisodesFileManager;
    
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
            throw new IllegalArgumentException("Cesta k adresáři " + DataStore.getDataDirectoryName() + " ještě nebyla nastavena");
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
               throw new IllegalArgumentException("Zadaný adresář neexistuje nebo se jedná o soubor");
           }
           else if (dataDirectory.getName().equals(DataStore.getDataDirectoryName()) == false) 
           {
               dataDirectory = null;
               throw new IllegalArgumentException("Zadaný adresář není pojmenovaný jako " + DataStore.getDataDirectoryName());
           }
        }
        else 
        {
            throw new IllegalArgumentException("Cesta k adresáři " + DataStore.getDataDirectoryName() + " byla již zadána");
        }
    }
}
