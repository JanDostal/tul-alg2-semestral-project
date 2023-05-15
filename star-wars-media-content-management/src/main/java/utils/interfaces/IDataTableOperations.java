
package utils.interfaces;

import java.util.List;

/**
 *
 * @author jan.dostal
 */
public interface IDataTableOperations<T, S, U, V>
{
    T addInput(S inputData);
    T addOutput(U outputData);
    void delete(V primaryKey);
    boolean edit(T data);
    T getBy(V primaryKey);
    List<T> getAll();
}
