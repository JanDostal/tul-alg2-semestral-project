/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import app.logic.controllers.MoviesController;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.output.MovieOutput;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
                        
                        break;
                    case 3:
                        handleSendMoviesByEmailSubmenu();
                        break;
                    case 4:
                        handlePrintErasWithAnnouncedMoviesSubmenu();
                        break;
                    case 5:
                        
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
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
        System.out.println("2. Vyhledat film podle jména");
        System.out.println("3. Poslat e-mailem filmy");
        System.out.println("4. Vypsat oznámené filmy v jednotlivých érách");
        System.out.println("5. Vypsat vydané nezhlédnuté filmy v jednotlivých érách");
        System.out.println("6. Vypsat vydané zhlédnuté filmy v jednotlivých érách");
        System.out.println("7. Vypsat nejoblíbenější filmy");
        System.out.println("8. Vypsat nejnovější vydané filmy");
        System.out.println("9. Vypsat obsahy výstupních souborů filmů");
        System.out.println("0. Vrátit se zpět do hlavního menu");
        System.out.println(horizontalLine);
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
    
    private void displayPrintErasWithUnwatchedMoviesSubmenu() 
    {
        String menuName = "PODMENU ÉR S NEZHLÉDNUTÝMI FILMY";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat nezhlédnuté filmy filmy vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintErasWithAnnouncedMoviesSubmenu() 
    {
        String menuName = "PODMENU ÉR S OZNÁMENÝMI FILMY";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat oznámené filmy vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
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
    
    private void displayDetailAboutAnnouncedMovieSubmenu(Movie chosenMovie) 
    {
        String menuName = "PODMENU DETAILU OZNÁMENÉHO FILMU " + chosenMovie.getName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat film");
        System.out.println("2. Upravit film pomocí vstupního textového souboru " + DataStore.getTextInputMoviesFilename());
        System.out.println("3. Upravit film pomocí vstupního binárního souboru " + DataStore.getBinaryInputMoviesFilename());
        System.out.println("4. Vypsat obsah vstupního textového souboru " + DataStore.getTextInputMoviesFilename());
        System.out.println("5. Vypsat obsah vstupního binárního souboru " + DataStore.getBinaryInputMoviesFilename());
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
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
    
    private void handlePrintErasWithUnwatchedMoviesSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Éry s nezhlédnutými filmy"); 
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            printErasWithAnnouncedMovies();
            consoleUI.displayBreadcrumb();
            displayPrintErasWithUnwatchedMoviesSubmenu();
            
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
    
    private void printErasWithUnwatchedMovies() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "ÉRY S NEZHLÉDNUTÝMI FILMY (začíná nejstarší érou)");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
        System.out.println();
        System.out.println(heading);
        
        int counter = 0;
        int eraMaxLength = MovieOutput.ATTRIBUTE_ERA_LENGTH + 5;
        
        Map<Integer, Duration> eraUnwatchedMoviesTotalDuration;
        Map<Integer, Duration> eraUnwatchedMoviesAverageDuration;
        
        int eraUnwatchedMoviesWithDurationSetCount;
        
        for (Era era : Era.values()) 
        {
            counter++;
            
            eraUnwatchedMoviesTotalDuration = consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(era, false);
            eraUnwatchedMoviesAverageDuration = consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(era, false);
            eraUnwatchedMoviesWithDurationSetCount = eraUnwatchedMoviesTotalDuration.keySet().iterator().next();
            15
            System.out.println();            
            System.out.println(String.format("%-10s%s %-" + eraMaxLength + "s%s %d", 
                    counter + ".", 
                    "Období:", era.getDisplayName(), 
                    "Celkový počet filmů:", consoleUI.getMoviesController().getReleasedMoviesCountByEra(era, false),
                    "Celková doba filmů se zadanou dobou:", eraUnwatchedMoviesTotalDuration.get(eraUnwatchedMoviesWithDurationSetCount),
                    "Průměrná doba filmů se zadanou dobou:", eraUnwatchedMoviesAverageDuration.get(eraUnwatchedMoviesWithDurationSetCount),
                    "Počet filmů se zadanou dobou:", consoleUI.getMoviesController().g));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    private void handlePrintErasWithAnnouncedMoviesSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Éry s oznámenými filmy"); 
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            printErasWithAnnouncedMovies();
            consoleUI.displayBreadcrumb();
            displayPrintErasWithAnnouncedMoviesSubmenu();
            
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
    
    private void printErasWithAnnouncedMovies() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "ÉRY S OZNÁMENÝMI FILMY (začíná nejstarší érou)");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
        System.out.println();
        System.out.println(heading);
        
        int counter = 0;
        int eraMaxLength = MovieOutput.ATTRIBUTE_ERA_LENGTH + 5;
        
        for (Era era : Era.values()) 
        {
            counter++;
                        
            System.out.println();
            System.out.println(String.format("%-10s%s %-" + eraMaxLength + "s%s %d", 
                    counter + ".", "Období:", era.getDisplayName(), 
                    "Celkový počet filmů:", consoleUI.getMoviesController().getAnnouncedMoviesCountByEra(era)));
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
                            handleDisplayDetailAboutAnnouncedMovieSubmenu(announcedMoviesByChosenEra);
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
    
    private void printAnnouncedMoviesByEra(List<Movie> announcedMoviesByChosenEra, Era chosenEra) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("OZNAMENÉ FILMY ÉRY %s (řazeno abecedně)", chosenEra.getDisplayName().toUpperCase()));
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println("Celkový počet filmů: " + announcedMoviesByChosenEra.size());
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = MovieOutput.ATTRIBUTE_NAME_LENGTH + 5;
        
        for (Movie movie : announcedMoviesByChosenEra) 
        {
            counter++;
                    
            System.out.println();
            System.out.println(String.format("%-15s%s %-" + nameMaxLength + "s%s %s", 
                    counter + ".", "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate() == null ? "Není známo" : movie.getReleaseDate().format(dateFormatter)));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
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
                printAnnouncedMovieDetail(chosenMovie);
                
                consoleUI.displayInfoMessage(String.format("Při úpravě filmu se používají stejné"
                        + " vstupní soubory jako u přidávání filmů.%nPro jednoznačnost je nutné mít v daném souboru pouze jeden film."));
                
                consoleUI.displayInfoMessage(String.format("Při úpravě filmu se používá v "
                        + "souboru úplně totožná struktura dat jako u přidávání filmů.%nPo úpravě se film nemusí vypisovat "
                        + "tam, kde se původně vypisoval.%nPři smazání filmu nebo při reálné úpravě dat filmu "
                        + "dojde k návratu zpátky do nadřazeného menu.%n"));
 
                consoleUI.displayBreadcrumb();
                displayDetailAboutAnnouncedMovieSubmenu(chosenMovie);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            returnToParentMenu = deleteChosenMovie(chosenMovie.getPrimaryKey());
                            
                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }
                            
                            break;
                        case 2:
                            returnToParentMenu = editMovieFromInputFile(chosenMovie.getPrimaryKey(), false);    
                            
                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }
                            
                            break;
                        case 3:
                            returnToParentMenu = editMovieFromInputFile(chosenMovie.getPrimaryKey(), true);
                            
                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }
                            
                            break;
                        case 4:
                            consoleUI.displayMoviesChosenFileContent(DataStore.getTextInputMoviesFilename());
                            break;
                        case 5:
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
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        String runtimeText = chosenMovie.getRuntime() == null ? "Není známa" :
                String.format("%02d:%02d:%02d", chosenMovie.getRuntime().toHours(), 
                        chosenMovie.getRuntime().toMinutesPart(), chosenMovie.getRuntime().toSecondsPart());
        
        System.out.println(String.format("%-30s%d", "Identifikátor:", chosenMovie.getPrimaryKey().getId()));
        System.out.println(String.format("%-30s%s", "Název:", chosenMovie.getName()));
        System.out.println(String.format("%-30s%s", "Chronologická éra:", chosenMovie.getEra().getDisplayName()));
        System.out.println(String.format("%-30s%s", "Délka filmu:", runtimeText));
        System.out.println(String.format("%-30s%s", "Datum uvedení:", chosenMovie.getReleaseDate() == null ? 
                "Není známo" : chosenMovie.getReleaseDate().format(dateFormatter)));
        
        System.out.println();
        System.out.println(dividingLine);  
    }
    
    private boolean deleteChosenMovie(PrimaryKey moviePrimaryKey) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            consoleUI.getMoviesController().deleteMovieBy(moviePrimaryKey);
            consoleUI.displayInfoMessage("Vybraný film úspěšně smazán");
            returnToParentMenu = true;
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
    
    private boolean editMovieFromInputFile(PrimaryKey existingMoviePrimaryKey, boolean fromBinary) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            boolean wasDataChanged = consoleUI.getMoviesController().editMovieBy(existingMoviePrimaryKey, fromBinary);
             
            if (wasDataChanged == true) 
            {
                consoleUI.displayInfoMessage("Upravená data vybraného filmu se úspěšně uložila");
                returnToParentMenu = true;
            }
            else 
            {
                consoleUI.displayInfoMessage("Nedošlo k žádné změně dat u vybraného filmu");
            }
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
}
