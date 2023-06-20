package utils.interfaces;

import app.models.data.DatabaseRecord;
import app.models.data.PrimaryKey;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import utils.exceptions.DatabaseException;

/**
 * Represents an IDataTable interface for implementation in individual data tables
 * IDataFileManager interface defines functions like creating, deleting, filtering, sorting, editing, reading
 * @param <T> Represents a data model, which has to inherit from DatabaseRecord, to ensure it will have primary key attribute
 * @author jan.dostal
 */
public interface IDataTable<T extends DatabaseRecord>
{
    void addFrom(T inputData) throws DatabaseException;
    void loadFrom(T outputData) throws DatabaseException;
    void deleteBy(PrimaryKey primaryKey) throws DatabaseException;
    boolean editBy(PrimaryKey primaryKey, T editedExistingData) throws DatabaseException;
    T getBy(PrimaryKey primaryKey);
    List<T> getAll();
    List<T> filterBy(Predicate<T> condition);
    void sortBy(Comparator<T> comparator, List<T> sourceList);
    void sortByPrimaryKey(List<T> sourceList);
    void clearData();
}
