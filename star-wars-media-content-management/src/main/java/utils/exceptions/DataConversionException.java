package utils.exceptions;

/**
 * Represents a custom exception class for dealing with checked exceptions during data conversion
 * DataConversionException class is used in data converters
 * @author jan.dostal
 */
public class DataConversionException extends Exception
{
    public DataConversionException(String message) 
    {
        super(message);
    }
    
    public DataConversionException() 
    {
        super();
    }
}
