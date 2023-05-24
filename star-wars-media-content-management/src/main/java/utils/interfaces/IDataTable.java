
package utils.interfaces;

import app.models.data.DatabaseRecord;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author jan.dostal
 */
public interface IDataTable<T extends DatabaseRecord>
{
    T addFrom(T inputData);
    void loadFrom(T outputData);
    void deleteBy(int id);
    boolean editBy(int id, T inputData);
    T getBy(int id);
    List<T> getAll();
    List<T> filterBy(Predicate<T> condition);
    void sortBy(Comparator<T> comparator, List<T> sourceList);
}
