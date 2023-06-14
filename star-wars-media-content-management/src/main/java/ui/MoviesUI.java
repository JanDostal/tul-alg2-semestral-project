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
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Přidat filmy ze vstupního souboru");
        System.out.println("2. Poslat e-mailem filmy");
        System.out.println("3. Vypsat oznámené filmy v jednotlivých érách");
        System.out.println("0. Vrátit se zpět do hlavního menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintAnnouncedMoviesByEraSubmenu(Era chosenEra) 
    {
        String menuName = "PODMENU OZNÁMENÝCH FILMŮ ÉRY " + chosenEra.getDisplayName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané oznámené filmy");
        System.out.println("2. Vypsat detail vybraného oznámeného filmu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    
    private int loadChosenMovieFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo filmu: ");
        return consoleUI.getScanner().nextInt();
    }
    
    private void printAnnouncedMovieDetail(Movie chosenMovie) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("DETAIL OZNÁMENÉHO FILMU %s", chosenMovie.getName().toUpperCase()));
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(dividingLine);
        System.out.println();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        String runtimeText = String.format("%d h %d m %d s", chosenMovie.getRuntime().toHours(), 
                chosenMovie.getRuntime().toMinutesPart(), chosenMovie.getRuntime().toSecondsPart());
        
        System.out.println(String.format("%-30s%d", "Identifikátor:", chosenMovie.getPrimaryKey().getId()));
        System.out.println();
        System.out.println(String.format("%-30s%s", "Název:", chosenMovie.getName()));
        System.out.println();
        System.out.println(String.format("%-30s%s", "Chronologická éra:", chosenMovie.getEra().getDisplayName()));
        System.out.println();
        System.out.println(String.format("%-30s%s", "Délka filmu:", chosenMovie.getEra().getDisplayName()));
        
        System.out.println();
        System.out.println(dividingLine);  
    }
    
    private void handleDisplayDetailAboutAnnouncedMovieSubmenu(List<Movie> announcedMoviesByChosenEra) 
    {
        try
        {
            int movieOrderFromList = loadChosenMovieFromUser();
            Movie chosenMovie = announcedMoviesByChosenEra.get(movieOrderFromList - 1);

            consoleUI.addBreadcrumbItem(String.format("Detail oznámeného filmu %s", chosenMovie.getName()));
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

                    switch (choice) {
                        case 1:
                            handlePrintAnnouncedMoviesByEraSubmenu();
                            chosenMovie = consoleUI.getMoviesController().getMovieDetail(chosenMovie.getPrimaryKey());
                            break;
                        case 0:
                            consoleUI.removeLastBreadcrumbItem();
                            returnToParentMenu = true;
                            break;
                        default:
                            consoleUI.displayErrorMessage("Nevalidní číslo volby z podmenu");
                    }
                } catch (InputMismatchException ex) {
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
            consoleUI.displayErrorMessage("Pořadové číslo neodpovídá žádnému z filmů");
            consoleUI.advanceToNextInput();
        }
    }
    
    private void deleteAnnouncedMoviesInChosenEra(Era chosenEra) 
    {
        List<Movie> announcedMoviesByChosenEra = consoleUI.getMoviesController().getAnnouncedMoviesByEra(chosenEra);
        
        try 
        {
            consoleUI.getMoviesController().deleteMovies(announcedMoviesByChosenEra);
            consoleUI.displayInfoMessage("Oznámené filmy v dané éře úspěšně smazány");
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }   
    }
    
    private void printAnnouncedMoviesByEra(List<Movie> announcedMoviesByChosenEra, Era chosenEra) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("OZNAMENÉ FILMY ÉRY %s (řazeno abecedně)", chosenEra.getDisplayName().toUpperCase()));
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println("Počet filmů: " + announcedMoviesByChosenEra.size());
        System.out.println();
        System.out.println(dividingLine);
        
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
                List<Movie> announcedMoviesByChosenEra = consoleUI.getMoviesController().getAnnouncedMoviesByEra(chosenEra);
                printAnnouncedMoviesByEra(announcedMoviesByChosenEra, chosenEra);
                consoleUI.displayBreadcrumb();
                displayPrintAnnouncedMoviesByEraSubmenu(chosenEra);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteAnnouncedMoviesInChosenEra(chosenEra);
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
        String menuName = "PODMENU ÉR S POČTEM OZNÁMENÝCH FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat oznámené filmy vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void printErasWithAnnouncedMoviesCount() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "ÉRY S POČTEM OZNÁMENÝCH FILMŮ (začíná nejstarší érou)");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
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
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
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
        consoleUI.addBreadcrumbItem("Posílání filmů e-mailem");
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
        String menuName = "PODMENU PŘIDÁVÁNÍ FILMŮ ZE VSTUPNÍHO SOUBORU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
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
        
    private void handleLoadMoviesFromInputFileSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Přidávání filmů ze vstupního souboru");
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
                        consoleUI.displayMoviesChosenFileContent(DataStore.getTextInputMoviesFilename());
                        break;
                    case 4:
                        consoleUI.displayMoviesChosenFileContent(DataStore.getBinaryInputMoviesFilename());
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
