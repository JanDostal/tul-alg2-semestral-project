
package ui;

import app.logic.controllers.DataSorting;
import app.logic.controllers.DataType;
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
                        handlePrintErasWithUnwatchedMoviesSubmenu();
                        break;
                    case 6:
                        handlePrintErasWithWatchedMoviesSubmenu();
                        break;
                    case 7:
                        handlePrintFavoriteMoviesOfAllTimeSubmenu();
                        break;
                    case 8:
                        handlePrintReleasedNewestMoviesSubmenu();
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
    
    private void displayPrintFavoriteMoviesOfAllTimeSubmenu() 
    {
        String menuName = "PODMENU NEJOBLÍBENĚJŠÍCH FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané nejoblíbenější filmy");
        System.out.println("2. Vypsat detail vybraného zhlédnutého filmu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintReleasedNewestMoviesSubmenu() 
    {
        String menuName = "PODMENU NEJNOVĚJŠÍCH JIŽ VYDANÝCH FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané nejnovější již vydané filmy");
        System.out.println("2. Vypsat detail vybraného vydaného filmu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
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
        System.out.println("1. Vypsat abecedně nezhlédnuté filmy vybrané éry");
        System.out.println("2. Vypsat nejnovější nezhlédnuté filmy vybrané éry");
        System.out.println("3. Vypsat nejdelší nezhlédnuté filmy vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintUnwatchedMoviesByEraSubmenu(Era chosenEra) 
    {
        String menuName = "PODMENU NEZHLÉDNUTÝCH FILMŮ ÉRY " + chosenEra.getDisplayName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané nezhlédnuté filmy");
        System.out.println("2. Vypsat detail vybraného nezhlédnutého filmu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintErasWithWatchedMoviesSubmenu() 
    {
        String menuName = "PODMENU ÉR SE ZHLÉDNUTÝMI FILMY";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat abecedně zhlédnuté filmy vybrané éry");
        System.out.println("2. Vypsat nejnovější zhlédnuté filmy vybrané éry");
        System.out.println("3. Vypsat nejdelší zhlédnuté filmy vybrané éry");
        System.out.println("4. Vypsat nejoblíbenější zhlédnuté filmy vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintWatchedMoviesByEraSubmenu(Era chosenEra) 
    {
        String menuName = "PODMENU ZHLÉDNUTÝCH FILMŮ ÉRY " + chosenEra.getDisplayName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané zhlédnuté filmy");
        System.out.println("2. Vypsat detail vybraného zhlédnutého filmu");
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
        System.out.println("1. Vypsat abecedně oznámené filmy vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void displayPrintAnnouncedMoviesInAlphabeticalOrderByEraSubmenu(Era chosenEra) 
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
    
    private void displayDetailAboutMovieSubmenu(Movie chosenMovie) 
    {
        boolean wasReleased = false;
        
        if (chosenMovie.getReleaseDate() != null && 
                chosenMovie.getReleaseDate().compareTo(MoviesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }
        
        String menuName = wasReleased == true ? chosenMovie.getWasWatched() == true ? 
                "PODMENU DETAILU ZHLÉDNUTÉHO FILMU " + chosenMovie.getName().toUpperCase() : 
                "PODMENU DETAILU NEZHLÉDNUTÉHO FILMU " + chosenMovie.getName().toUpperCase() : 
                "PODMENU DETAILU OZNÁMENÉHO FILMU " + chosenMovie.getName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat film");
        System.out.println("2. Upravit film pomocí vstupního textového souboru " + DataStore.getTextInputMoviesFilename());
        System.out.println("3. Upravit film pomocí vstupního binárního souboru " + DataStore.getBinaryInputMoviesFilename());
        System.out.println("4. Vypsat obsah vstupního textového souboru " + DataStore.getTextInputMoviesFilename());
        System.out.println("5. Vypsat obsah vstupního binárního souboru " + DataStore.getBinaryInputMoviesFilename());
        
        if (wasReleased == true) System.out.println("6. Ohodnotit film");
        
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    private void handlePrintReleasedNewestMoviesSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Nejnovější již vydané filmy");

        boolean returnToParentMenu = false;
        int choice;

        while (returnToParentMenu == false) 
        {
            List<Movie> releasedNewestMovies = consoleUI.getMoviesController().getReleasedNewestMovies();

            printReleasedNewestMovies(releasedNewestMovies);
            consoleUI.displayBreadcrumb();
            displayPrintReleasedNewestMoviesSubmenu();

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        deleteChosenMovies(releasedNewestMovies);
                        break;
                    case 2:
                        handleDisplayDetailAboutMovieSubmenu(releasedNewestMovies);
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
    
    private void printReleasedNewestMovies(List<Movie> releasedNewestMovies) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, "NEJNOVĚJŠÍ JIŽ VYDANÉ FILMY");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%s %d", "Počet filmů:", releasedNewestMovies.size()));
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = MovieOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        String percentageRatingText;
        
        for (Movie movie : releasedNewestMovies) 
        {
            counter++;
                        
            percentageRatingText = movie.getWasWatched() == true ? movie.getPercentageRating() + " %" : "Není ohodnoceno";
            
            durationText = movie.getRuntime() == null ? "Není známa" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-15s%s %-" + nameMaxLength + "s%s %-16s%s %-14s%s %s", 
                    counter + ".", "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText,
                    "Hodnocení:", percentageRatingText));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    private void handlePrintFavoriteMoviesOfAllTimeSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Nejoblíbenější filmy");

        boolean returnToParentMenu = false;
        int choice;

        while (returnToParentMenu == false) 
        {
            List<Movie> favoriteMoviesOfAllTime = consoleUI.getMoviesController().getFavoriteMoviesOfAllTime();

            printFavoriteMoviesOfAllTime(favoriteMoviesOfAllTime);
            consoleUI.displayBreadcrumb();
            displayPrintFavoriteMoviesOfAllTimeSubmenu();

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        deleteChosenMovies(favoriteMoviesOfAllTime);
                        break;
                    case 2:
                        handleDisplayDetailAboutMovieSubmenu(favoriteMoviesOfAllTime);
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
    
    private void printFavoriteMoviesOfAllTime(List<Movie> favoriteMovies) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, "NEJOBLÍBENĚJŠÍ FILMY");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%s %d", "Počet filmů:", favoriteMovies.size()));
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = MovieOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        
        for (Movie movie : favoriteMovies) 
        {
            counter++;
            
            durationText = movie.getRuntime() == null ? "Není známa" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-15s%s %-" + nameMaxLength + "s%s %-16s%s %-14s%s %d %%", 
                    counter + ".", "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText,
                    "Hodnocení:", movie.getPercentageRating()));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
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
                        consoleUI.displayDataChosenFileContent(DataStore.getTextInputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 4:
                        consoleUI.displayDataChosenFileContent(DataStore.getBinaryInputMoviesFilename(), DataType.MOVIE);
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
            printErasWithUnwatchedMovies();
            consoleUI.displayBreadcrumb();
            displayPrintErasWithUnwatchedMoviesSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handlePrintUnwatchedMoviesByEraSubmenu(DataSorting.BY_NAME);
                        break;
                    case 2:
                        handlePrintUnwatchedMoviesByEraSubmenu(DataSorting.NEWEST);
                        break;
                    case 3:
                        handlePrintUnwatchedMoviesByEraSubmenu(DataSorting.LONGEST);
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
        consoleUI.displayInfoMessage("Počet filmů s délkou jsou filmy, které mají nastavenou dobu/délku");
        consoleUI.displayInfoMessage("Délka filmů zahrnuje pouze filmy s nastavenou dobou/délkou");
        consoleUI.displayInfoMessage("Průměrná délka filmů zahrnuje pouze filmy s nastavenou dobou/délkou");
        System.out.println();
        System.out.println(dividingLine);
        
        int counter = 0;
        
        Map<Integer, Duration> eraUnwatchedMoviesTotalDurationWithCount;
        Map<Integer, Duration> eraUnwatchedMoviesAverageDurationWithCount;
        
        int eraUnwatchedMoviesWithDurationSetCount;
        Duration totalDuration;
        Duration averageDuration;
        
        String totalDurationText;
        String averageDurationText;
        
        for (Era era : Era.values()) 
        {
            counter++;
            
            eraUnwatchedMoviesTotalDurationWithCount = 
                    consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(era, false);
            eraUnwatchedMoviesAverageDurationWithCount = 
                    consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(era, false);
            
            eraUnwatchedMoviesWithDurationSetCount = eraUnwatchedMoviesTotalDurationWithCount.keySet().iterator().next();
            
            totalDuration = eraUnwatchedMoviesTotalDurationWithCount.get(eraUnwatchedMoviesWithDurationSetCount);
            averageDuration = eraUnwatchedMoviesAverageDurationWithCount.get(eraUnwatchedMoviesWithDurationSetCount);
            
            totalDurationText =  String.format("%02d:%02d:%02d", totalDuration.toHours(), totalDuration.toMinutesPart(), 
                    totalDuration.toSecondsPart());
            averageDurationText =  String.format("%02d:%02d:%02d", averageDuration.toHours(), averageDuration.toMinutesPart(), 
                    averageDuration.toSecondsPart());

            System.out.println();            
            System.out.println(String.format("%-6s%s %-25s%s %-14d%s %-14d%s %-14s%s %s", 
                    counter + ".", 
                    "Období:", era.getDisplayName(), 
                    "Počet filmů:", consoleUI.getMoviesController().getReleasedMoviesCountByEra(era, false),
                    "Počet filmů s délkou:", eraUnwatchedMoviesWithDurationSetCount,
                    "Délka filmů:", totalDurationText,
                    "Průměrná délka filmů:", averageDurationText));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    private void handlePrintUnwatchedMoviesByEraSubmenu(DataSorting dataSorting) 
    {
        try 
        {
            int eraOrderFromList = consoleUI.loadChosenEraOrderFromUser();
            Era chosenEra = Era.values()[eraOrderFromList - 1];
            
            switch (dataSorting) 
            {
                case BY_NAME:
                    consoleUI.addBreadcrumbItem(String.format("Nezhlédnuté filmy éry %s (řazeno abecedně)", chosenEra.getDisplayName()));
                    break;
                case NEWEST:
                    consoleUI.addBreadcrumbItem(String.format("Nezhlédnuté filmy éry %s (řazeno podle data uvedení)", 
                            chosenEra.getDisplayName()));
                    break;
                case LONGEST:
                    consoleUI.addBreadcrumbItem(String.format("Nezhlédnuté filmy éry %s (řazeno podle délky filmu)", 
                            chosenEra.getDisplayName()));
                    break;
            }
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                List<Movie> unwatchedMoviesByChosenEra = null;
                
                switch (dataSorting) 
                {
                    case BY_NAME:
                        unwatchedMoviesByChosenEra = 
                                consoleUI.getMoviesController().getReleasedMoviesInAlphabeticalOrderByEra(chosenEra, false);
                        break;
                    case NEWEST:
                        unwatchedMoviesByChosenEra = 
                                consoleUI.getMoviesController().getReleasedNewestMoviesByEra(chosenEra, false);
                        break;
                    case LONGEST:
                        unwatchedMoviesByChosenEra = 
                                consoleUI.getMoviesController().getReleasedLongestMoviesByEra(chosenEra, false);
                        break;
                }
                                
                printUnwatchedMoviesByEra(unwatchedMoviesByChosenEra, chosenEra, dataSorting);
                consoleUI.displayBreadcrumb();
                displayPrintUnwatchedMoviesByEraSubmenu(chosenEra);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteChosenMovies(unwatchedMoviesByChosenEra);
                            break;
                        case 2:
                            handleDisplayDetailAboutMovieSubmenu(unwatchedMoviesByChosenEra);
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
    
    private void printUnwatchedMoviesByEra(List<Movie> unwatchedMoviesByChosenEra, Era chosenEra, DataSorting dataSorting) 
    {
        StringBuilder heading = null;
        
        switch (dataSorting) 
        {
            case BY_NAME:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("NEZHLÉDNUTÉ FILMY ÉRY %s (řazeno abecedně)", chosenEra.getDisplayName().toUpperCase()));
                break;
            case NEWEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("NEZHLÉDNUTÉ FILMY ÉRY %s (řazeno podle data uvedení)", chosenEra.getDisplayName().toUpperCase()));
                break;
            case LONGEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("NEZHLÉDNUTÉ FILMY ÉRY %s (řazeno podle délky filmu)", chosenEra.getDisplayName().toUpperCase()));
                break;
        }
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
        
        Map<Integer, Duration> unwatchedMoviesTotalDurationWithCount = 
                consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(chosenEra, false);
        Map<Integer, Duration> unwatchedMoviesAverageDurationWithCount = 
                consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(chosenEra, false);
            
        int unwatchedMoviesWithDurationSetCount = unwatchedMoviesTotalDurationWithCount.keySet().iterator().next();
            
        Duration totalDuration = unwatchedMoviesTotalDurationWithCount.get(unwatchedMoviesWithDurationSetCount);
        Duration averageDuration = unwatchedMoviesAverageDurationWithCount.get(unwatchedMoviesWithDurationSetCount);
            
        String totalDurationText =  String.format("%02d:%02d:%02d", totalDuration.toHours(), totalDuration.toMinutesPart(), 
                totalDuration.toSecondsPart());
        String averageDurationText =  String.format("%02d:%02d:%02d", averageDuration.toHours(), averageDuration.toMinutesPart(), 
                averageDuration.toSecondsPart());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%-70s%d", "Počet filmů:", unwatchedMoviesByChosenEra.size()));
        System.out.println(String.format("%-70s%d", "Počet filmů s délkou (zahrnuty pouze filmy s nastavenou délkou):", 
                unwatchedMoviesWithDurationSetCount));
        System.out.println(String.format("%-70s%s", "Délka filmů (zahrnuty pouze filmy s nastavenou délkou):", 
                totalDurationText));
        System.out.println(String.format("%-70s%s", "Průměrná délka filmů (zahrnuty pouze filmy s nastavenou délkou):", 
                averageDurationText));
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = MovieOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        
        for (Movie movie : unwatchedMoviesByChosenEra) 
        {
            counter++;
            
            durationText = movie.getRuntime() == null ? "Není známa" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-15s%s %-" + nameMaxLength + "s%s %-16s%s %s", 
                    counter + ".", "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
     private void handlePrintErasWithWatchedMoviesSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Éry se zhlédnutými filmy"); 
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            printErasWithWatchedMovies();
            consoleUI.displayBreadcrumb();
            displayPrintErasWithWatchedMoviesSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handlePrintWatchedMoviesByEraSubmenu(DataSorting.BY_NAME);
                        break;
                    case 2:
                        handlePrintWatchedMoviesByEraSubmenu(DataSorting.NEWEST);
                        break;
                    case 3:
                        handlePrintWatchedMoviesByEraSubmenu(DataSorting.LONGEST);
                        break;
                    case 4:
                        handlePrintWatchedMoviesByEraSubmenu(DataSorting.FAVORITE);
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
     
     private void printErasWithWatchedMovies() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "ÉRY SE ZHLÉDNUTÝMI FILMY (začíná nejstarší érou)");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
        System.out.println();
        System.out.println(heading);
        consoleUI.displayInfoMessage("Počet filmů s délkou jsou filmy, které mají nastavenou dobu/délku");
        consoleUI.displayInfoMessage("Délka filmů zahrnuje pouze filmy s nastavenou dobou/délkou");
        consoleUI.displayInfoMessage("Průměrná délka filmů zahrnuje pouze filmy s nastavenou dobou/délkou");
        System.out.println();
        System.out.println(dividingLine);
        
        int counter = 0;
        
        Map<Integer, Duration> eraWatchedMoviesTotalDurationWithCount;
        Map<Integer, Duration> eraWatchedMoviesAverageDurationWithCount;
        
        int eraWatchedMoviesWithDurationSetCount;
        Duration totalDuration;
        Duration averageDuration;
        
        String totalDurationText;
        String averageDurationText;
        float averagePercentageRating;
        
        for (Era era : Era.values()) 
        {
            counter++;
            
            eraWatchedMoviesTotalDurationWithCount = 
                    consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(era, true);
            eraWatchedMoviesAverageDurationWithCount = 
                    consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(era, true);
            
            averagePercentageRating = consoleUI.getMoviesController().getAverageRatingOfAllReleasedMoviesByEra(era);
            
            eraWatchedMoviesWithDurationSetCount = eraWatchedMoviesTotalDurationWithCount.keySet().iterator().next();
            
            totalDuration = eraWatchedMoviesTotalDurationWithCount.get(eraWatchedMoviesWithDurationSetCount);
            averageDuration = eraWatchedMoviesAverageDurationWithCount.get(eraWatchedMoviesWithDurationSetCount);
            
            totalDurationText =  String.format("%02d:%02d:%02d", totalDuration.toHours(), totalDuration.toMinutesPart(), 
                    totalDuration.toSecondsPart());
            averageDurationText =  String.format("%02d:%02d:%02d", averageDuration.toHours(), averageDuration.toMinutesPart(), 
                    averageDuration.toSecondsPart());

            System.out.println();            
            System.out.println(String.format("%-4s%s %-25s%s %-14d%s %-14d%s %-14s%s %-14s%s %.2f %%", 
                    counter + ".", 
                    "Období:", era.getDisplayName(), 
                    "Počet filmů:", consoleUI.getMoviesController().getReleasedMoviesCountByEra(era, true),
                    "Počet filmů s délkou:", eraWatchedMoviesWithDurationSetCount,
                    "Délka filmů:", totalDurationText,
                    "Průměrná délka filmů:", averageDurationText,
                    "Průměrné hodnocení filmů:", averagePercentageRating));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
     
    private void handlePrintWatchedMoviesByEraSubmenu(DataSorting dataSorting) 
    {
        try 
        {
            int eraOrderFromList = consoleUI.loadChosenEraOrderFromUser();
            Era chosenEra = Era.values()[eraOrderFromList - 1];
            
            switch (dataSorting) 
            {
                case BY_NAME:
                    consoleUI.addBreadcrumbItem(String.format("Zhlédnuté filmy éry %s (řazeno abecedně)", chosenEra.getDisplayName()));
                    break;
                case NEWEST:
                    consoleUI.addBreadcrumbItem(String.format("Zhlédnuté filmy éry %s (řazeno podle data uvedení)", 
                            chosenEra.getDisplayName()));
                    break;
                case LONGEST:
                    consoleUI.addBreadcrumbItem(String.format("Zhlédnuté filmy éry %s (řazeno podle délky filmu)", 
                            chosenEra.getDisplayName()));
                    break;
                case FAVORITE:
                    consoleUI.addBreadcrumbItem(String.format("Zhlédnuté filmy éry %s (řazeno podle procentuálního hodnocení)", 
                            chosenEra.getDisplayName()));
                    break;
            }
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                List<Movie> watchedMoviesByChosenEra = null;
                
                switch (dataSorting) 
                {
                    case BY_NAME:
                        watchedMoviesByChosenEra = 
                                consoleUI.getMoviesController().getReleasedMoviesInAlphabeticalOrderByEra(chosenEra, true);
                        break;
                    case NEWEST:
                        watchedMoviesByChosenEra = 
                                consoleUI.getMoviesController().getReleasedNewestMoviesByEra(chosenEra, true);
                        break;
                    case LONGEST:
                        watchedMoviesByChosenEra = 
                                consoleUI.getMoviesController().getReleasedLongestMoviesByEra(chosenEra, true);
                        break;
                    case FAVORITE:
                        watchedMoviesByChosenEra = 
                                consoleUI.getMoviesController().getFavoriteMoviesByEra(chosenEra);
                        break;
                }
                                
                printWatchedMoviesByEra(watchedMoviesByChosenEra, chosenEra, dataSorting);
                consoleUI.displayBreadcrumb();
                displayPrintWatchedMoviesByEraSubmenu(chosenEra);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteChosenMovies(watchedMoviesByChosenEra);
                            break;
                        case 2:
                            handleDisplayDetailAboutMovieSubmenu(watchedMoviesByChosenEra);
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
    
    private void printWatchedMoviesByEra(List<Movie> watchedMoviesByChosenEra, Era chosenEra, DataSorting dataSorting) 
    {
        StringBuilder heading = null;
        
        switch (dataSorting) 
        {
            case BY_NAME:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("ZHLÉDNUTÉ FILMY ÉRY %s (řazeno abecedně)", chosenEra.getDisplayName().toUpperCase()));
                break;
            case NEWEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("ZHLÉDNUTÉ FILMY ÉRY %s (řazeno podle data uvedení)", chosenEra.getDisplayName().toUpperCase()));
                break;
            case LONGEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("ZHLÉDNUTÉ FILMY ÉRY %s (řazeno podle délky filmu)", chosenEra.getDisplayName().toUpperCase()));
                break;        
            case FAVORITE:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("ZHLÉDNUTÉ FILMY ÉRY %s (řazeno podle procentuálního hodnocení)", chosenEra.getDisplayName().toUpperCase()));
                break;
        }
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
        
        Map<Integer, Duration> watchedMoviesTotalDurationWithCount = 
                consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(chosenEra, true);
        Map<Integer, Duration> watchedMoviesAverageDurationWithCount = 
                consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(chosenEra, true);
        
        float averagePercentageRating = consoleUI.getMoviesController().getAverageRatingOfAllReleasedMoviesByEra(chosenEra);
            
        int watchedMoviesWithDurationSetCount = watchedMoviesTotalDurationWithCount.keySet().iterator().next();
            
        Duration totalDuration = watchedMoviesTotalDurationWithCount.get(watchedMoviesWithDurationSetCount);
        Duration averageDuration = watchedMoviesAverageDurationWithCount.get(watchedMoviesWithDurationSetCount);
            
        String totalDurationText =  String.format("%02d:%02d:%02d", totalDuration.toHours(), totalDuration.toMinutesPart(), 
                totalDuration.toSecondsPart());
        String averageDurationText =  String.format("%02d:%02d:%02d", averageDuration.toHours(), averageDuration.toMinutesPart(), 
                averageDuration.toSecondsPart());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%-70s%d", "Počet filmů:", watchedMoviesByChosenEra.size()));
        System.out.println(String.format("%-70s%d", "Počet filmů s délkou (zahrnuty pouze filmy s nastavenou délkou):", 
                watchedMoviesWithDurationSetCount));
        System.out.println(String.format("%-70s%s", "Délka filmů (zahrnuty pouze filmy s nastavenou délkou):", 
                totalDurationText));
        System.out.println(String.format("%-70s%s", "Průměrná délka filmů (zahrnuty pouze filmy s nastavenou délkou):", 
                averageDurationText));
        System.out.println(String.format("%-70s%.2f %%", "Průměrné hodnocení filmů:", 
                averagePercentageRating));
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = MovieOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        
        for (Movie movie : watchedMoviesByChosenEra) 
        {
            counter++;
            
            durationText = movie.getRuntime() == null ? "Není známa" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-15s%s %-" + nameMaxLength + "s%s %-16s%s %-14s%s %d %%", 
                    counter + ".", "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText,
                    "Hodnocení:", movie.getPercentageRating()));
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
                        handlePrintAnnouncedMoviesInAlphabeticalOrderByEraSubmenu();
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
        
        for (Era era : Era.values()) 
        {
            counter++;
                        
            System.out.println();
            System.out.println(String.format("%-10s%s %-25s%s %d", 
                    counter + ".", "Období:", era.getDisplayName(), 
                    "Počet filmů:", consoleUI.getMoviesController().getAnnouncedMoviesCountByEra(era)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    private void handlePrintAnnouncedMoviesInAlphabeticalOrderByEraSubmenu() 
    {
        try 
        {
            int eraOrderFromList = consoleUI.loadChosenEraOrderFromUser();
            Era chosenEra = Era.values()[eraOrderFromList - 1];

            consoleUI.addBreadcrumbItem(String.format("Oznámené filmy éry %s (řazeno abecedně)", chosenEra.getDisplayName()));
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                List<Movie> announcedMoviesByChosenEra = consoleUI.getMoviesController().getAnnouncedMoviesInAlphabeticalOrderByEra(chosenEra);
                printAnnouncedMoviesInAlphabeticalOrderByEra(announcedMoviesByChosenEra, chosenEra);
                consoleUI.displayBreadcrumb();
                displayPrintAnnouncedMoviesInAlphabeticalOrderByEraSubmenu(chosenEra);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteChosenMovies(announcedMoviesByChosenEra);
                            break;
                        case 2:
                            handleDisplayDetailAboutMovieSubmenu(announcedMoviesByChosenEra);
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
    
    private void printAnnouncedMoviesInAlphabeticalOrderByEra(List<Movie> announcedMoviesByChosenEra, Era chosenEra) 
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
    
    private void handleDisplayDetailAboutMovieSubmenu(List<Movie> chosenMovies) 
    {
        try
        {
            int movieOrderFromList = loadChosenMovieFromUser();
            Movie chosenMovie = chosenMovies.get(movieOrderFromList - 1);
            
            boolean wasReleased = false;
            
            if(chosenMovie.getReleaseDate() != null && 
                    chosenMovie.getReleaseDate().compareTo(MoviesController.getCurrentDate()) <= 0) 
            {
                wasReleased = true;
            }
            
            consoleUI.addBreadcrumbItem(String.format("Detail %s filmu %s", 
                    wasReleased == true ? chosenMovie.getWasWatched() == true ? 
                    "zhlédnutého" : "nezhlédnutého" : "oznámeného", chosenMovie.getName()));
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                printMovieDetail(chosenMovie);
                
                consoleUI.displayInfoMessage(String.format("Při úpravě filmu se používají stejné"
                        + " vstupní soubory jako u přidávání filmů.%nPro jednoznačnost je nutné mít v daném souboru pouze jeden film."));
                
                consoleUI.displayInfoMessage(String.format("Při úpravě filmu se používá v "
                        + "souboru úplně totožná struktura dat jako u přidávání filmů.%nPo úpravě"
                        + (wasReleased == true ? " nebo po ohodnocení" : "") + " se "
                        + "film nemusí vypisovat tam, kde se původně vypisoval.%n"
                        + "Při smazání filmu" + (wasReleased == true ? ", ohodnocení filmu" : "") + " nebo při reálné úpravě dat filmu "
                        + "dojde k návratu zpátky do nadřazeného menu."));
 
                consoleUI.displayBreadcrumb();
                displayDetailAboutMovieSubmenu(chosenMovie);

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
                            consoleUI.displayDataChosenFileContent(DataStore.getTextInputMoviesFilename(), DataType.MOVIE);
                            break;
                        case 5:
                            consoleUI.displayDataChosenFileContent(DataStore.getBinaryInputMoviesFilename(), DataType.MOVIE);
                            break;
                        case 6:
                            
                            if (wasReleased == true) 
                            {
                                returnToParentMenu = rateMovie(chosenMovie);
                            
                                if (returnToParentMenu == true) 
                                {
                                    consoleUI.removeLastBreadcrumbItem();
                                }
                            }
                            
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
    
    private int loadMoviePercentageRatingFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte hodnocení filmu jako procenta od 0 až do 100: ");
        return consoleUI.getScanner().nextInt();
    }
    
    private boolean rateMovie(Movie chosenMovie) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            int percentageRating = loadMoviePercentageRatingFromUser();
            
            boolean wasDataChanged = consoleUI.getMoviesController().rateMovie(chosenMovie, percentageRating);
             
            if (wasDataChanged == true && chosenMovie.getWasWatched() == false) 
            {
                consoleUI.displayInfoMessage("Vybraný film byl ohodnocen úspěšně a označen jako zhlédnutý");
                returnToParentMenu = true;
            }
            else if (wasDataChanged == true && chosenMovie.getWasWatched() == true) 
            {
                consoleUI.displayInfoMessage("Vybraný film byl ohodnocen úspěšně a jedná se o opakované ohodnocení");
                returnToParentMenu = true;
            }
            else 
            {
                consoleUI.displayInfoMessage("Ohodnocení filmu zůstalo beze změny");
            }
        }
        catch (InputMismatchException ex) 
        {
            consoleUI.displayErrorMessage("Procentuální hodnocení musí být celé číslo");
            consoleUI.advanceToNextInput();
        }
        catch (IllegalArgumentException ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
        
    private int loadChosenMovieFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo filmu: ");
        return consoleUI.getScanner().nextInt();
    }
    
     private void printMovieDetail(Movie chosenMovie) 
    {
        boolean wasReleased = false;
        
        if(chosenMovie.getReleaseDate() != null && 
                    chosenMovie.getReleaseDate().compareTo(MoviesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }
        
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("DETAIL %s FILMU %s", 
                        wasReleased == true ? chosenMovie.getWasWatched() == true ? 
                        "ZHLÉDNUTÉHO" : "NEZHLÉDNUTÉHO" : "OZNÁMENÉHO", 
                        chosenMovie.getName().toUpperCase()));
        
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
        System.out.println(String.format("%-30s%s", "Datum uvedení:", chosenMovie.getReleaseDate() == null ? 
                "Není známo" : chosenMovie.getReleaseDate().format(dateFormatter)));    
        System.out.println(String.format("%-30s%s", "Délka filmu:", runtimeText));
        
        if (wasReleased == true) 
        {
            String shortContentSummaryText;
            
            System.out.println(String.format("%-30s%s", "Procentualní ohodnocení:", 
                    chosenMovie.getWasWatched() == true ? chosenMovie.getPercentageRating() + " %" : "Není ohodnoceno"));
            System.out.println(String.format("%-30s%s", "Odkaz ke zhlédnutí:", 
                    chosenMovie.getHyperlinkForContentWatch() == null ? "Není znám" : chosenMovie.getHyperlinkForContentWatch()));
            
            shortContentSummaryText = chosenMovie.getShortContentSummary() == null ? 
                    String.format("%-30s%s", "Krátké shrnutí obsahu:", "Není známo") : 
                    String.format("Krátké shrnutí obsahu:%n%n%s", chosenMovie.getShortContentSummary());
            
            System.out.println(shortContentSummaryText);
        }
                
        System.out.println();
        System.out.println(dividingLine);  
    }
     
    private void deleteChosenMovies(List<Movie> chosenMovies) 
    {        
        try 
        {
            consoleUI.getMoviesController().deleteMovies(chosenMovies);
            consoleUI.displayInfoMessage("Vybrané filmy úspěšně smazány");
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }   
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
