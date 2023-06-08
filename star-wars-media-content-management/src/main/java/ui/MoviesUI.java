/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import app.logic.controllers.MoviesController;
import app.logic.datastore.DataStore;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class MoviesUI 
{
    private final ConsoleUI consoleUI;
    
    protected MoviesUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
    
    protected void start() 
    {
        boolean returnToMainMenu = false;
        int choice;
        
        while (returnToMainMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayMoviesManagementSubMenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromMenu();
                
                switch (choice) 
                {
                    case 1:
                        consoleUI.addBreadcrumbItem("Načíst filmy");
                        handleMoviesFromInputFileSubMenu();
                        break;
                    case 0:
                        consoleUI.removeLastBreadcrumbItem();
                        returnToMainMenu = true;
                        break;
                    default:
                        consoleUI.displayErrorMessage("Nevalidní číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    private void displayMoviesManagementSubMenu() 
    {
        String menuName = "PODMENU SPRÁVA FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Načíst filmy ze vstupního souboru");
        System.out.println("0. Vrátit se zpět do hlavního menu");
        System.out.println(horizontalLine);
    }
    
    private void displayMoviesFromInputFileSubMenu() 
    {
        String menuName = "PODMENU NAČÍTÁNÍ FILMŮ ZE VSTUPNÍHO SOUBORU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Načíst z textového souboru %s", DataStore.getTextInputMoviesFilename()));
        System.out.println(String.format("2. Načíst z binárního souboru %s", DataStore.getBinaryInputMoviesFilename()));
        System.out.println(String.format("3. Vypsat obsah textového souboru %s", DataStore.getTextInputMoviesFilename()));
        System.out.println(String.format("4. Vypsat obsah binárního souboru %s", DataStore.getBinaryInputMoviesFilename()));
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void loadMoviesFromInputFile(boolean fromBinary) 
    {        
        try 
        {
            StringBuilder infoMessage = consoleUI.getMoviesController().addMoviesFrom(fromBinary);
            consoleUI.displayInfoMessage(infoMessage.toString());
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    private void displayMoviesChosenFileContent(String fileName) 
    {        
        try 
        {
            StringBuilder fileContent = consoleUI.getMoviesController().getMoviesChosenFileContent(fileName);
            
            consoleUI.displayInfoMessage("Výpis obsahu souboru " + fileName);
            System.out.println(fileContent);
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    private void handleMoviesFromInputFileSubMenu() 
    {
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayMoviesFromInputFileSubMenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromMenu();
                
                switch (choice) 
                {
                    case 1:
                        loadMoviesFromInputFile(false);
                        break;
                    case 2:
                        loadMoviesFromInputFile(true);
                        break;
                    case 3:
                        displayMoviesChosenFileContent(DataStore.getTextInputMoviesFilename());
                        break;
                    case 4:
                        displayMoviesChosenFileContent(DataStore.getBinaryInputMoviesFilename());
                        break;
                    case 0:
                        consoleUI.removeLastBreadcrumbItem();
                        returnToParentMenu = true;
                        break;
                    default:
                        consoleUI.displayErrorMessage("Nevalidní číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
}
