
package utils.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;

/**
 *
 * @author Admin
 */
public interface IDataFileManager<T, S> 
{
    StringBuilder getTextOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    StringBuilder getBinaryOutputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    StringBuilder getTextInputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    StringBuilder getBinaryInputFileContent() throws FileNotFoundException, IOException, FileEmptyException;
    List<S> loadOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException;
    void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException;
    void saveOutputDataIntoFiles(List<S> newOutputData) throws IOException;
    List<T> loadInputDataFrom(boolean fromBinary) throws IOException, FileNotFoundException, FileEmptyException;
    
}
