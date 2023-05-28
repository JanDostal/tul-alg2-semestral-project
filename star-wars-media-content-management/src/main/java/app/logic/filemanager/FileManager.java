
package app.logic.filemanager;

/**
 *
 * @author Admin
 */
public class FileManager 
{
    private static FileManager fileManager;
    
    private final String filenameSeparator;
    
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
}
