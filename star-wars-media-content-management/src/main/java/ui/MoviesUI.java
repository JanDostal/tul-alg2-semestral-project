/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import app.logic.controllers.MoviesController;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.Movie;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import org.apache.commons.mail.EmailException;

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
        consoleUI.addBreadcrumbItem("Správa filmů");
        boolean returnToMainMenu = false;
        int choice;
        
        while (returnToMainMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayMoviesManagementSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handleLoadMoviesFromInputFileSubmenu();
                        break;
                    case 2:
                        handleSendMoviesByEmailSubmenu();
                        break;
                    case 3:
                        handlePrintErasWithAnnouncedMoviesCountSubmenu();
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
    
    private void displayMoviesManagementSubmenu() 
    {
        String menuName = "PODMENU SPRÁVA FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Načíst filmy ze vstupního souboru");
        System.out.println("2. Poslat e-mailem filmy");
        System.out.println("3. Vypsat oznámené filmy v jednotlivých érách");
        System.out.println("0. Vrátit se zpět do hlavního menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintAnnouncedMoviesByEraSubmenu(Era chosenEra) 
    {
        String menuName = "PODMENU VYPSANÝCH OZNÁMENÝCH FILMŮ ÉRY " + chosenEra.getDisplayName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané filmy");
        System.out.println("2. Vypsat detail vybraného filmu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void deleteMoviesInChosenEra(Era chosenEra) 
    {
        List<Movie> announcedMoviesByChosenEra = consoleUI.getMoviesController().getAnnouncedMoviesByEra(chosenEra);
        
        try 
        {
            consoleUI.getMoviesController().deleteMovies(announcedMoviesByChosenEra);
            consoleUI.displayInfoMessage("Filmy v dané éře úspěšně smazány");
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }   
    }
    
    private void printAnnouncedMoviesByEra(Era chosenEra) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("JEDNOTLIVÉ OZNAMENÉ FILMY ÉRY %s (řazeno abecedně)", chosenEra.getDisplayName().toUpperCase()));
        
        StringBuilder dividingLine = consoleUI.createDividingBottomHorizontalLineOf(heading.toString());
        
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println("Počet filmů: " + consoleUI.getMoviesController().getAnnouncedMoviesCountByEra(chosenEra));
        System.out.println();
        System.out.println(dividingLine);
        
        List<Movie> announcedMoviesByChosenEra = consoleUI.getMoviesController().getAnnouncedMoviesByEra(chosenEra);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        
        for (Movie movie : announcedMoviesByChosenEra) 
        {
            counter++;
            
            System.out.println();
            System.out.println(String.format("%-10s%s %-30s%s %s", counter + ".", "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate() == null ? "Není známo" : movie.getReleaseDate().format(formatter)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    private void handlePrintAnnouncedMoviesByEraSubmenu() 
    {
        try 
        {
            int eraOrderFromList = consoleUI.loadChosenEraOrderFromUser();
            Era chosenEra = Era.values()[eraOrderFromList - 1];

            consoleUI.addBreadcrumbItem(String.format("Oznámené filmy éry %s", chosenEra.getDisplayName()));
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                printAnnouncedMoviesByEra(chosenEra);
                consoleUI.displayBreadcrumb();
                displayPrintAnnouncedMoviesByEraSubmenu(chosenEra);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteMoviesInChosenEra(chosenEra);
                            break;
                        case 2:
                            
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
        catch (InputMismatchException ex) 
        {
            consoleUI.displayErrorMessage("Pořadové číslo musí být číslo");
            consoleUI.advanceToNextInput();
        }
        catch (IndexOutOfBoundsException ex) 
        {
            consoleUI.displayErrorMessage("Pořadové číslo neodpovídá žádné z ér");
            consoleUI.advanceToNextInput();
        }
    }
    
    private void displayPrintErasWithAnnouncedMoviesCountSubmenu() 
    {
        String menuName = "PODMENU VYPSANÝCH ÉR S POČTEM OZNÁMENÝCH FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat oznámené filmy vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void printErasWithAnnouncedMoviesCount() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "JEDNOTLIVÉ ÉRY S POČTEM OZNÁMENÝCH FILMŮ (začíná nejstarší érou)");
        
        StringBuilder dividingLine = consoleUI.createDividingBottomHorizontalLineOf(heading.toString());
        
        System.out.println();
        System.out.println(heading);
        
        int counter = 0;
        
        for (Era era : Era.values()) 
        {
            counter++;
            
            System.out.println();
            System.out.println(String.format("%-10s%s %-30s%s %d", counter + ".", "Období:", era.getDisplayName(), 
                    "Počet filmů:", consoleUI.getMoviesController().getAnnouncedMoviesCountByEra(era)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    private void handlePrintErasWithAnnouncedMoviesCountSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Éry s počtem oznámených filmů"); 
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            printErasWithAnnouncedMoviesCount();
            consoleUI.displayBreadcrumb();
            displayPrintErasWithAnnouncedMoviesCountSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handlePrintAnnouncedMoviesByEraSubmenu();
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
    
    private void displaySendMoviesByEmailSubmenu() 
    {
        String menuName = "PODMENU POSÍLÁNÍ FILMŮ E-MAILEM";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Poslat e-mailem nezhlédnuté filmy od nejstaršího");
        System.out.println("2. Poslat e-mailem nezhlédnuté filmy v rámci chronologických ér");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void sendUnwatchedMoviesFromOldestByEmail() 
    {
        String email = consoleUI.loadEmailFromUser();
        
        try 
        {
            consoleUI.getMoviesController().sendUnwatchedOldestMoviesWithHyperlinksByEmail(email);
            consoleUI.displayInfoMessage("E-mail byl úspešně odeslán");
        }
        catch (EmailException ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    private void sendUnwatchedMoviesInChronologicalErasByEmail() 
    {
        String email = consoleUI.loadEmailFromUser();
        
        try 
        {
            consoleUI.getMoviesController().sendUnwatchedMoviesWithHyperlinksInChronologicalErasByEmail(email);
            consoleUI.displayInfoMessage("E-mail byl úspešně odeslán");
        }
        catch (EmailException ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    private void handleSendMoviesByEmailSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Poslat filmy e-mailem");
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displaySendMoviesByEmailSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        sendUnwatchedMoviesFromOldestByEmail();
                        break;
                    case 2:
                        sendUnwatchedMoviesInChronologicalErasByEmail();
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
    
    private void displayLoadMoviesFromInputFileSubmenu() 
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
            
            StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(15, "VÝPIS OBSAHU SOUBORU " + fileName.toUpperCase());
            
            System.out.println();
            System.out.println(heading);
            System.out.println();
            
            System.out.println(fileContent);
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    private void handleLoadMoviesFromInputFileSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Načíst filmy");
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayLoadMoviesFromInputFileSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
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
