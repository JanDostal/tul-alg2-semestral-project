
package utils.interfaces;

import java.util.List;

/**
 *
 * @author jan.dostal
 */
public interface IDataTable<T, S, U, V>
{
    T addInput(S inputData);
    T addOutput(U outputData);
    void delete(V primaryKey);
    boolean edit(V primaryKey, S inputData);
    T getBy(V primaryKey);
    List<T> getAll();
}
