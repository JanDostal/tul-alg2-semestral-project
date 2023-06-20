package utils.exceptions;

/**
 * Represents a custom exception class for dealing with checked exceptions during parsing data files
 * FileParsingException class is used in data file managers
 * FileParsingException class is used when issue occurs with parsing (cannot convert String to int, etc.)
 * @author jan.dostal
 */
public class FileParsingException extends Exception
{
    public FileParsingException(String message) 
    {
        super(message);
    }
    
    public FileParsingException() 
    {
        super();
    }
}
