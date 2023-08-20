package app.logic.filemanager;

import app.logic.datastore.DataStore;
import app.models.input.MovieInput;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
import app.models.input.TVShowInput;
import app.models.inputoutput.MovieInputOutput;
import app.models.inputoutput.TVEpisodeInputOutput;
import app.models.inputoutput.TVSeasonInputOutput;
import app.models.inputoutput.TVShowInputOutput;
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
    private static final String fileSeparator = System.getProperty("file.separator");;
    
    private static final String textFileEndMarking = "\\[End\\]";
    
    private static final String textFileValuesSectionMarking = "\\[Values\\]";
    
    private static final String textFileAttributesSectionMarking = "\\[Attributes\\]";
    
    private static FileManagerAccessor fileManagerAccessor;
    
    private static File dataDirectory;
    
    private final IDataFileManager<MovieInput, MovieInputOutput> moviesFileManager;
    
    private final IDataFileManager<TVShowInput, TVShowInputOutput> tvShowsFileManager;
    
    private final IDataFileManager<TVSeasonInput, TVSeasonInputOutput> tvSeasonsFileManager;
    
    private final IDataFileManager<TVEpisodeInput, TVEpisodeInputOutput> tvEpisodesFileManager;
    
    /**
     * Creates singleton instance of FileManagerAccessor.
     * When creating instance, all data file managers are also loaded as singleton instances.
     */
    private FileManagerAccessor() 
    {
        this.moviesFileManager = MoviesFileManager.getInstance();
        this.tvShowsFileManager = TVShowsFileManager.getInstance();
        this.tvSeasonsFileManager = TVSeasonsFileManager.getInstance(); 
        this.tvEpisodesFileManager = TVEpisodesFileManager.getInstance();
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
    public IDataFileManager<TVSeasonInput, TVSeasonInputOutput> getTVSeasonsFileManager() 
    {
        return tvSeasonsFileManager;
    }
    
    /**
     * @return tv shows file manager instance as interface
     */
    public IDataFileManager<TVShowInput, TVShowInputOutput> getTVShowsFileManager() 
    {
        return tvShowsFileManager;
    }
    
    /**
     * @return tv episodes file manager instance as interface
     */
    public IDataFileManager<TVEpisodeInput, TVEpisodeInputOutput> getTVEpisodesFileManager() 
    {
        return tvEpisodesFileManager;
    }
    
    /**
     * @return movies file manager instance as interface
     */
    public IDataFileManager<MovieInput, MovieInputOutput> getMoviesFileManager() 
    {
        return moviesFileManager;
    }
    
    /**
     * @return file path separator dependent on application running operating system (usage in data files managers)
     */
    protected static String getFileSeparator() 
    {
        return fileSeparator;
    }
    
    /**
     * @return control string for detecting file end (usage in data files managers)
     */
    protected static String getTextFileEndMarking() 
    {
        return textFileEndMarking;
    }
    
    /**
     * @return control string for detecting values section in file (usage in data files managers)
     */
    protected static String getTextFileValuesSectionMarking() 
    {
        return textFileValuesSectionMarking;
    }
    
    /**
     * @return control string for detecting attributes section in file (usage in data files managers)
     */
    protected static String getTextFileAttributesSectionMarking() 
    {
        return textFileAttributesSectionMarking;
    }
    
    /**
     * Returns Data directory full path from File instance
     * @return data directory absolute path
     * @throws IllegalStateException when File instance was not yet set (data directory should be set before usage of data files managers methods)
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
     * Sets data directory through specified file path (data directory should be set before usage of data files managers methods)
     * @param directoryFullPath file path to existing data directory with data input and input/output files
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
