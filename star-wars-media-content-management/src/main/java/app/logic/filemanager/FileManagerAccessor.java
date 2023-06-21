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
import java.io.File;
import utils.interfaces.IDataFileManager;

/**
 * Represents a file manager access layer service for working with data files.
 * File manager accessor data files managers are made available through accessor.
 * File manager accessor data files managers are using UTF-8 to encode or decode text files.
 * @author jan.dostal
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
    
    
    /**
     * Creates singleton instance of FileManagerAccessor.
     * When creating instance, all data file managers are also loaded as singleton instances.
     */
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
    
    /**
     * Represents a factory method for creating singleton instance.
     * @return singleton instance of FileManagerAccessor class
     */
    public static FileManagerAccessor getInstance() 
    {
        if (fileManagerAccessor == null) 
        {
            fileManagerAccessor = new FileManagerAccessor();
        }
        
        return fileManagerAccessor;
    }
    
    /**
     * @return tv seasons file manager instance as interface
     */
    public IDataFileManager<TVSeasonInput, TVSeasonOutput> getTVSeasonsFileManager() 
    {
        return tvSeasonsFileManager;
    }
    
    /**
     * @return tv shows file manager instance as interface
     */
    public IDataFileManager<TVShowInput, TVShowOutput> getTVShowsFileManager() 
    {
        return tvShowsFileManager;
    }
    
    /**
     * @return tv episodes file manager instance as interface
     */
    public IDataFileManager<TVEpisodeInput, TVEpisodeOutput> getTVEpisodesFileManager() 
    {
        return tvEpisodesFileManager;
    }
    
    /**
     * @return movies file manager instance as interface
     */
    public IDataFileManager<MovieInput, MovieOutput> getMoviesFileManager() 
    {
        return moviesFileManager;
    }
    
    /**
     * Returns Data directory full path from File instance
     * @return data directory absolute path
     * @throws IllegalStateException when File instance was not yet set
     */
    public static String getDataDirectoryPath()
    {
        if (dataDirectory == null) 
        {
            throw new IllegalStateException("Cesta k adresáři " + DataStore.getDataDirectoryName() + " ještě nebyla nastavena");
        }
        
        return dataDirectory.getAbsolutePath();
    }
    
    /**
     * Sets data directory through specified file path
     * @param directoryFullPath file path to existing data directory with data input and output files
     * @throws IllegalStateException when File instance was already set
     * @throws IllegalArgumentException when directory on 
     * specified path does not exist, is not directory or is not named accordingly.
     */
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
            throw new IllegalStateException("Cesta k adresáři " + DataStore.getDataDirectoryName() + " byla již zadána");
        }
    }
}
