/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import app.logic.controllers.MoviesController;
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
            displayloadingOutputFilesMenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromMenu();
                
                switch (choice) 
                {
                    case 1:
                        break;
                    case 0:
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
    
    private void displayloadingOutputFilesMenu() 
    {
        String menuName = "PODMENU SPRÁVA FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Načíst filmy ze souboru");
        System.out.println("2. Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println("0. Vrátit se do hlavního menu");
        System.out.println(horizontalLine);
    }
    
    private void loadMoviesFromFile() 
    {
        String menuName = "PODMENU SPRÁVA FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Načíst filmy ze souboru");
        System.out.println("2. Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println("0. Vrátit se do hlavního menu");
        System.out.println(horizontalLine);
    }
}
