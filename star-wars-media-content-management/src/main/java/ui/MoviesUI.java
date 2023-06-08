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
    private final Scanner scanner;
        
    private final MoviesController moviesController;
    
    protected MoviesUI(MoviesController moviesController, Scanner scanner) 
    {
        this.scanner = scanner;
        this.moviesController = moviesController;
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
                choice = ConsoleUI.loadChoiceFromMenu(scanner);
                
                switch (choice) 
                {
                    case 1:
                        break;
                    case 0:
                        returnToMainMenu = true;
                        break;
                    default:
                        ConsoleUI.displayErrorMessage("Nevalidní číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                ConsoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                ConsoleUI.advanceToNextInput(scanner);
            }
        }
    }
    
    private void displayloadingOutputFilesMenu() 
    {
        String menuName = "PODMENU SPRÁVA FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = ConsoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = ConsoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Načíst z textových souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println("2. Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println("0. Vrátit se do hlavního menu");
        System.out.println(horizontalLine);
    }
}
