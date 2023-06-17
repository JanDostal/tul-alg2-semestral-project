/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import app.logic.controllers.DataType;
import app.logic.controllers.TVEpisodesController;
import app.logic.datastore.DataStore;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class TVEpisodesUI 
{
    private final ConsoleUI consoleUI;
    
    protected TVEpisodesUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
        
    protected void start() 
    {
        consoleUI.addBreadcrumbItem("Správa TV epizod");
        boolean returnToMainMenu = false;
        int choice;
        
        while (returnToMainMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayTVEpisodesManagementSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handleLoadTVShowsFromInputFileSubmenu();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
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
    
    private void displayTVEpisodesManagementSubmenu() 
    {
        String menuName = "PODMENU SPRÁVA TV EPIZOD";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Přidat TV seriály ze vstupního souboru");
        System.out.println("2. Vyhledat TV seriál podle jména");
        System.out.println("3. Vypsat oznámené TV seriály v jednotlivých érách");
        System.out.println("4. Vypsat vydané TV seriály v jednotlivých érách");
        System.out.println("5. Vypsat nejnovější již vydané TV seriály");
        System.out.println("6. Vypsat obsahy výstupních souborů TV epizod");
        System.out.println("7. Vypsat obsahy výstupních souborů TV sezón");
        System.out.println("8. Vypsat obsahy výstupních souborů TV seriálů");
        System.out.println("0. Vrátit se zpět do hlavního menu");
        System.out.println(horizontalLine);
    }
    
    private void displayLoadTVShowsFromInputFileSubmenu() 
    {
        String menuName = "PODMENU PŘIDÁVÁNÍ TV SERIÁLŮ ZE VSTUPNÍHO SOUBORU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Načíst z textového souboru %s", DataStore.getTextInputTVShowsFilename()));
        System.out.println(String.format("2. Načíst z binárního souboru %s", DataStore.getBinaryInputTVShowsFilename()));
        System.out.println(String.format("3. Vypsat obsah textového souboru %s", DataStore.getTextInputTVShowsFilename()));
        System.out.println(String.format("4. Vypsat obsah binárního souboru %s", DataStore.getBinaryInputTVShowsFilename()));
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void handleLoadTVShowsFromInputFileSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Přidávání TV seriálů ze vstupního souboru");
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayLoadTVShowsFromInputFileSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        loadTVShowsFromInputFile(false);
                        break;
                    case 2:
                        loadTVShowsFromInputFile(true);
                        break;
                    case 3:
                        consoleUI.displayDataChosenFileContent(DataStore.getTextInputTVShowsFilename(), DataType.TV_SHOW);
                        break;
                    case 4:
                        consoleUI.displayDataChosenFileContent(DataStore.getBinaryInputTVShowsFilename(), DataType.TV_SHOW);
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
    
    private void loadTVShowsFromInputFile(boolean fromBinary) 
    {        
        try 
        {
            StringBuilder infoMessage = consoleUI.getTVEpisodesController().addTVShowsFrom(fromBinary);
            consoleUI.displayInfoMessage(infoMessage.toString());
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
}
