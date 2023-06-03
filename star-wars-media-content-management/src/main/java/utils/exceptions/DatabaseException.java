/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.exceptions;

/**
 *
 * @author Admin
 */
public class DatabaseException extends Exception
{
    public DatabaseException(String message) 
    {
        super(message);
    }
    
    public DatabaseException() 
    {
        super();
    }
}
