package utils.exceptions;

/**
 * Represents a custom exception class for dealing with checked exceptions during manipulation of data in database
 * DatabaseException class is used in data context data tables
 * DatabaseException class is used for dealing with data duplicity, validity, duplicate or invalid primary key etc.
 * @author jan.dostal
 */
public class DatabaseException extends Exception
{
    public DatabaseException(String message) 
    {
        super(message);
    }
    
    public DatabaseException() 
    {
        super();
    }
}
