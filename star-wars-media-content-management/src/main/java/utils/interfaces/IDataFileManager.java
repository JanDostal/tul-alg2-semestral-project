
package utils.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IDataFileManager<T, S> 
{
    List<S> loadOutputDataFrom(boolean fromBinary) throws IOException, FileNotFoundException;
    void tryDeleteDataOutputFilesCopies();
    void tryCreateDataOutputFiles() throws IOException;
    void transferBetweenOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException, FileNotFoundException;
    void saveOutputDataIntoFiles(List<S> newOutputData) throws IOException, FileNotFoundException;
    List<T> loadInputDataFrom(boolean fromBinary) throws IOException, FileNotFoundException;
    
}
