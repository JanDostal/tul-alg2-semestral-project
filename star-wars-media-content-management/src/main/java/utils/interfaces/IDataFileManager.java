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
 * @param <S> Represents a particular data input/output model
 * @author jan.dostal
 */
public interface IDataFileManager<T, S> 
{
    /**
     * Represents a method for getting text input/output data file content
     * (file names defined in {@link DataStore}).
     * Method should just get purest text representation of content, no need for parsing.
     * Encoding of content is expected to be UTF-8.
     * @return file text content for displaying in UI
     * @throws java.io.FileNotFoundException if selected file is not found
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileEmptyException if content of selected file is empty
     */
    StringBuilder getTextInputOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    
    /**
     * Represents a method for getting binary input/output data file content
     * (file names defined in {@link DataStore}).
     * Method should respect a structure of chosen input/output binary file.
     * Read data should be converted into text format in formatted form to ensure better readability.
     * @return file formatted text content for displaying in UI
     * @throws java.io.FileNotFoundException if selected file is not found
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileEmptyException if content of selected file is empty
     */
    StringBuilder getBinaryInputOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    
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
     * Represents a method for loading/parsing input/output data from chosen input/output file
     *(file names defined in {@link DataStore}).
     * If chosen input/output file does not exist, it is automatically created.
     * Method should respect a structure of input/output file if it set as binary.
     * Method should expect an encoding of content to be UTF-8 if input/output file is set as text.
     * @param fromBinary selects if input/output file will be binary or text
     * @return list of parsed input/output data models
     * @throws java.io.IOException if reading from selected file fails
     * @throws utils.exceptions.FileParsingException if structure of file is corrupted, or stored data is corrupted
     * or cannot convert String values to int etc.
     */
    List<S> loadInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException;
    
    /**
     * Represents a method for deleting data input/output files copies 
     * after calling {@link #saveInputOutputDataIntoFiles(java.util.List) saveInputOutputDataIntoFiles} method inside
     * "saving updated database state into input/output files" method.
     * If copies do not exist, nothing happens.
     * <p>
     * Saving updated database state into input/output files method should be defined in data controller and
     * calling this method should happen regardless if calling 
     * {@link #saveInputOutputDataIntoFiles(java.util.List) saveInputOutputDataIntoFiles} method fails or not.
     */
    void tryDeleteDataInputOutputFilesCopies();
    
    /**
     * Represents a method for transfering input/output data between input/output files and copies acting 
     * as the backup in the case {@link #saveInputOutputDataIntoFiles(java.util.List) 
     * saveInputOutputDataIntoFiles} method fails.
     * <p>
     * This method should be called inside 
     * "saving updated database state into input/output files" method, defined in data
     * controller, and before calling 
     * {@link #saveInputOutputDataIntoFiles(java.util.List) saveInputOutputDataIntoFiles} method.
     * <p>
     * When transfering input/output data between input/output text file and copy text file, ensure the
     * encoding of content will be UTF-8.
     * @param fromCopyFiles selects if input/output data will be transfered from 
     * input/output files or their copies.
     * @throws java.io.IOException if transfer fails.
     */
    void transferBetweenInputOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException;
    
    /**
     * Represents a method for saving input/output data into input/output files
     * (file names defined in {@link DataStore}).
     * This method should be called inside "saving updated database state into input/output files" method,
     * defined in data controller.
     * <p>
     * When saving input/output data into input/output binary file, ensure input/output data 
     * string values will have fixed length so reading/parsing from input/output
     * binary file will be possible (more informations in input/output models classes).
     * <p>
     * When saving input/output data into input/output text file, ensure the encoding of
     * content will be UTF-8.
     * @param newInputOutputData list of new input/output data from database
     * @throws java.io.IOException if saving input/output data fails.
     */
    void saveInputOutputDataIntoFiles(List<S> newInputOutputData) throws IOException;
    
    /**
     * Represents a method for loading/parsing input data from chosen input file
     * (file names defined in {@link DataStore}).
     * <p>
     * Because binary input file can be from external source, recommended way is
     * to convert text input file into binary input file. You can use this
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
