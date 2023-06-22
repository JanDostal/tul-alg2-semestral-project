package utils.exceptions;

/**
 * Represents a custom checked exception class for dealing with problems during printing or parsing data files.
 * FileEmptyException class is used in data file managers.
 * FileEmptyException class is used for checking if file has any content.
 * @author jan.dostal
 */
public class FileEmptyException extends Exception
{
    public FileEmptyException(String message) 
    {
        super(message);
    }
    
    public FileEmptyException() 
    {
        super();
    }
}
