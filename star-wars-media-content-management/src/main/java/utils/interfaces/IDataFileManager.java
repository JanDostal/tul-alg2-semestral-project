package utils.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;
import app.logic.datastore.DataStore;

/**
 * Represents an IDataFileManager interface for implementation in individual data file managers.
 * IDataFileManager interface defines functions like printing files contents, transfering data, 
 * writing data and parsing data.
 * @param <T> Represents a particular data input model
 * @param <S> Represents a particular data output model
 * @author jan.dostal
 */
public interface IDataFileManager<T, S> 
{
    /**
     * Represents a method for getting text output data file content
     * (file names defined in {@link DataStore}).
     * Method should just get purest text representation of content, no need for parsing.
     * Encoding of content is expected to be UTF-8.
     * @return file text content for displaying in UI
     * @throws java.io.FileNotFoundException if selected file is not found
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileEmptyException if content of selected file is empty
     */
    StringBuilder getTextOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    
    /**
     * Represents a method for getting binary output data file content
     * (file names defined in {@link DataStore}).
     * Method should respect a structure of chosen output binary file.
     * Read data should be converted into text format in formatted form to ensure better readability.
     * @return file formatted text content for displaying in UI
     * @throws java.io.FileNotFoundException if selected file is not found
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileEmptyException if content of selected file is empty
     */
    StringBuilder getBinaryOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    
    /**
     * Represents a method for getting text input data file content
     * (file names defined in {@link DataStore}).
     * Method should just get purest text representation of content, no need for parsing.
     * Encoding of content is expected to be UTF-8.
     * @return file text content for displaying in UI
     * @throws java.io.FileNotFoundException if selected file is not found
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileEmptyException if content of selected file is empty
     */
    StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    
    /**
     * Represents a method for getting binary input data file content
     * (file names defined in {@link DataStore}).
     * Because binary input file can be from external source, recommended way is
     * to convert text input file into binary input file. You can use this link
     * <a href="https://www.rapidtables.com/convert/number/ascii-to-binary.html">Text to binary converter</a> 
     * to do it and please set character encoding as UTF-8.
     * <p>
     * Method should just get purest text representation of content, no need for parsing.
     * Encoding of content is expected to be UTF-8.
     * @return file text content for displaying in UI
     * @throws java.io.FileNotFoundException if selected file is not found
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileEmptyException if content of selected file is empty
     */
    StringBuilder getBinaryInputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    
    /**
     * Represents a method for loading/parsing output data from chosen output file
     *(file names defined in {@link DataStore}).
     * If chosen output file does not exist, it is automatically created.
     * Method should respect a structure of output file if it set as binary.
     * Method should expect an encoding of content to be UTF-8 if output file is set as text.
     * @param fromBinary selects if output file will be binary or text
     * @return list of parsed output data models
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileParsingException if structure of file is corrupted, or stored data is corrupted
     * or cannot convert String values to int etc.
     */
    List<S> loadOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException;
    
    /**
     * Represents a method for deleting data output files copies 
     * after calling {@link #saveOutputDataIntoFiles(java.util.List) saveOutputDataIntoFiles} method inside
     * saving updated database state into output files method.
     * If copies do not exist, nothing happens.
     * <p>
     * Saving updated database state into output files method should be defined in data controller and
     * calling this method should happen regardless if calling 
     * {@link #saveOutputDataIntoFiles(java.util.List) saveOutputDataIntoFiles} method fails or not.
     */
    void tryDeleteDataOutputFilesCopies();
    
    /**
     * Represents a method for transfering output data between output files and copies acting 
     * as the backup in the case {@link #saveOutputDataIntoFiles(java.util.List) 
     * saveOutputDataIntoFiles} method fails.
     * <p>
     * This method should be called inside 
     * saving updated database state into output files method, defined in data
     * controller, and before calling 
     * {@link #saveOutputDataIntoFiles(java.util.List) saveOutputDataIntoFiles} method.
     * @param fromCopyFiles selects if output data will be transfered from 
     * output files or their copies.
     * @throws java.io.IOException if transfer fails.
     */
    void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException;
    
    /**
     * Represents a method for saving output data into output files
     * (file names defined in {@link DataStore}).
     * This method should be called inside saving updated database state into output files method,
     * defined in data controller.
     * <p>
     * When saving output data into output binary file, ensure output data 
     * string values will have fixed length so reading/parsing from output
     * binary file will be possible (more informations in output models classes).
     * @param newOutputData list of new output data from database
     * @throws java.io.IOException if saving output data fails.
     */
    void saveOutputDataIntoFiles(List<S> newOutputData) throws IOException;
    
    /**
     * Represents a method for loading/parsing input data from chosen input file
     * (file names defined in {@link DataStore}).
     * <p>
     * Because binary input file can be from external source, recommended way is
     * to convert text input file into binary input file. You can use this link
     * <a href="https://www.rapidtables.com/convert/number/ascii-to-binary.html">Text to binary converter</a> 
     * to do it and please set character encoding as UTF-8.
     * <p>
     * Expected encoding of file content is UTF-8.
     * @param fromBinary selects if input file will be binary or text
     * @return list of parsed input data models with orders
     * @throws java.io.IOException if reading from selected file fails
     * @throws java.io.FileNotFoundException if selected file does not exist
     * @throws utils.exceptions.FileEmptyException if selected file content is empty
     * @throws utils.exceptions.FileParsingException if parsed list of input data is empty and file content is not empty
     */
    Map<Integer, T> loadInputDataFrom(boolean fromBinary) throws IOException, FileNotFoundException, FileEmptyException, FileParsingException;
}
