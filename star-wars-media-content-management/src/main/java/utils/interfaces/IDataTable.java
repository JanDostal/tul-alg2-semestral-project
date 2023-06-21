package utils.interfaces;

import app.models.data.DatabaseRecord;
import app.models.data.PrimaryKey;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import utils.exceptions.DatabaseException;
import app.logic.datacontext.DataContextAccessor;

/**
 * Represents an IDataTable interface for implementation in individual data tables.
 * IDataFileManager interface defines functions like creating, deleting, 
 * filtering, sorting, editing, reading.
 * @param <T> Represents a database data model, which has to inherit from 
 * {@link DatabaseRecord}, to ensure it will have primary key attribute
 * @author jan.dostal
 */
public interface IDataTable<T extends DatabaseRecord>
{
    
    /**
     * Represents a method for adding new data into data table.
     * <p>
     * Firstly method checks data for validity, duplicity, 
     * valid foreign key etc.
     * After that it will generate new primary key by
     * {@link DataContextAccessor#generatePrimaryKey(utils.interfaces.IDataTable, 
     * java.util.Random) generatePrimaryKey} method 
     * @param inputData database data model without primary key (model has primary key, 
     * but its value does not matter), 
     * converted to from input data model
     * @throws utils.exceptions.DatabaseException if data validity, data duplicity or foreign 
     * key problem occurs
     */
    void addFrom(T inputData) throws DatabaseException;
    
    /**
     * Represents a method for loading existing data into data table.
     * <p>
     * Method checks data for validity, duplicity, 
     * valid foreign key and primary key, primary key duplicity etc.
     * Method does not generate primary key.
     * @param outputData database data model with primary key, 
     * converted to from output data model
     * @throws utils.exceptions.DatabaseException if data validity, data duplicity, foreign 
     * key or primary key problem occurs
     */
    void loadFrom(T outputData) throws DatabaseException;
    
    /**
     * Represents a method for deleting chosen database data in data table
     * <p>
     * Method checks if chosen database data exists.
     * If data has relations with data from different data table,
     * then it will delete also that data (1:1, 1:N and N:M relations)
     * @param primaryKey chosen database data primary key
     * @throws utils.exceptions.DatabaseException if chosen database data
     * is not found in database by primary key
     */
    void deleteBy(PrimaryKey primaryKey) throws DatabaseException;
    
    /**
     * Represents a method for editing chosen database data in data table with new data
     * <p>
     * Method checks if chosen database data exists.
     * Method checks edited data for validity, duplicity, 
     * valid foreign key etc.
     * @param primaryKey chosen database data primary key
     * @param editedExistingData modified database data model without primary key
     * (model has primary key, 
     * but its value does not matter), 
     * converted to from input data model
     * @return logical value indicating if chosen database data remained without changes or changes occured
     * @throws utils.exceptions.DatabaseException if data validity, data duplicity or foreign 
     * key problem occurs
     */
    boolean editBy(PrimaryKey primaryKey, T editedExistingData) throws DatabaseException;
    
    /**
     * Represents a method for getting chosen existing database data from data table by primary key
     * <p>
     * If chosen existing database data is not found, returns null.
     * @param primaryKey chosen database data primary key
     * @return existing instance of chosen database data or null if requested data is not found
     */
    T getBy(PrimaryKey primaryKey);
    
    /**
     * Represents a method for getting all database data from data table
     * @return a new list of all database data from data table
     */
    List<T> getAll();
    
    /**
     * Represents a method for getting filtered database data from data table by predicate condition
     * @param condition Predicate delegate (returns boolean), which specifies filtering
     * condition (can be specified as lambda expression)
     * @return a new list of filtered data from data table
     */
    List<T> filterBy(Predicate<T> condition);
    
    /**
     * Represents a method for getting sorted source database data list.
     * @param comparator custom comparator for specifying sorting criteria of database data
     * @param sourceList database data list either as 
     * all data from data table or filtered data from data table
     */
    void sortBy(Comparator<T> comparator, List<T> sourceList);
    
    /**
     * Represents a method for getting sorted source database data list by primary key ascendingly 
     * ({@link DatabaseRecord} as database model superclass has defined 
     * compareTo method for sorting)
     * @param sourceList database data list either as 
     * all data from data table or filtered data from data table
     */
    void sortByPrimaryKey(List<T> sourceList);
    
    /**
     * Represents a method for deleting all database data in data table
     */
    void clearData();
}
