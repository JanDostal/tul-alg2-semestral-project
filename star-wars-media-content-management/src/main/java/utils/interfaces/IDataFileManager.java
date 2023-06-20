
package utils.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;

/**
 * Represents an IDataFileManager interface for implementation in individual data file managers
 * IDataFileManager interface defines functions like printing files contents, transfering data, writing data and parsing data
 * @param <T> Represents a particular data input model
 * @param <S> Represents a particular data output model
 * @author jan.dostal
 */
public interface IDataFileManager<T, S> 
{
    StringBuilder getTextOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    StringBuilder getBinaryOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    StringBuilder getBinaryInputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    List<S> loadOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException;
    void tryDeleteDataOutputFilesCopies();
    void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException;
    void saveOutputDataIntoFiles(List<S> newOutputData) throws IOException;
    Map<Integer, T> loadInputDataFrom(boolean fromBinary) throws IOException, FileNotFoundException, FileEmptyException, FileParsingException;
}
