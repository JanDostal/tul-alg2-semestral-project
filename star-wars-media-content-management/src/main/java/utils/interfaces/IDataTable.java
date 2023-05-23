
package utils.interfaces;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author jan.dostal
 */
public interface IDataTable<T extends Comparable<T>, Object, S extends Object, U extends Object, V>
{
    T addFrom(S inputData);
    T loadFrom(U outputData);
    void deleteBy(V primaryKey);
    boolean editBy(V primaryKey, S inputData);
    T getBy(V primaryKey);
    List<T> getAll();
    List<T> filterBy(Predicate<T> condition, List<T> sourceList);
    void sortBy(Comparator<T> comparator, List<T> sourceList);
    void sortByPrimaryKey(List<T> sourceList);
}
