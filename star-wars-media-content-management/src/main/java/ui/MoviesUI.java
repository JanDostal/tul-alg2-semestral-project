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
import org.apache.commons.mail.EmailException;

/**
 * Represents a movies UI submodule, which represents a UI section dealing with movies data.
 * MoviesUI is communicating with ConsoleUI to use common methods and access movies controller with scanner.
 * @author jan.dostal
 */
public class MoviesUI 
{
    private final ConsoleUI consoleUI;
    
    /**
     * Creates a new instance of MoviesUI.
     * Uses dependency injection to inject consoleUI ui module.
     * @param consoleUI existing instance of ConsoleUI. 
     * Can be used for using common methods from {@link ConsoleUI} class or access
     * movies controller with scanner.
     */
    protected MoviesUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
    
    /**
     * Method for starting this UI submodule (ConsoleUI class calls this method)
     */
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
                        handlePrintFoundMoviesByNameSubmenu();
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
                        handlePrintReleasedFavoriteMoviesOfAllTimeSubmenu();
                        break;
                    case 8:
                        handlePrintReleasedNewestMoviesSubmenu();
                        break;
                    case 9:
                        handlePrintMoviesInputOutputFilesContentsSubmenu();
                        break;
                    case 0:
                        consoleUI.removeLastBreadcrumbItem();
                        returnToMainMenu = true;
                        break;
                    default:
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method for displaying this ui submodule submenu with choices for managing movies
     */
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
        System.out.println("8. Vypsat nejnovější již vydané filmy");
        System.out.println("9. Vypsat obsahy výstupních souborů filmů");
        System.out.println("0. Vrátit se zpět do hlavního menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for adding movies from movies input file
     */
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
    
    /**
     * Method for displaying submenu with choices for found movies by their name
     */
    private void displayPrintFoundMoviesByNameSubmenu() 
    {
        String menuName = "PODMENU NALEZENÝCH FILMŮ PODLE NÁZVU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané nalezené filmy");
        System.out.println("2. Vypsat detail vybraného nalezeného filmu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for sending movies by e-mail
     */
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
    
    /**
     * Method for displaying submenu with choices for printed star wars eras list with annnounced movies
     */
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
    
    /**
     * Method for displaying submenu with choices for printed announced movies, 
     * sorted alphabetically by name, of selected star wars era.
     * @param chosenEra selected star wars era for which to print announced movies
     */
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
    
    /**
     * Method for displaying submenu with choices for printed star wars eras list with unwatched movies
     */
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
    
    /**
     * Method for displaying submenu with choices for printed unwatched movies of selected star wars era
     * @param chosenEra selected star wars era for which to print unwatched movies
     */
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
    
    /**
     * Method for displaying submenu with choices for printed star wars eras list with watched movies
     */
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
    
    /**
     * Method for displaying submenu with choices for printed watched movies of selected star wars era
     * @param chosenEra selected star wars era for which to print watched movies
     */
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
    
    /**
     * Method for displaying submenu with choices for printed favorite movies of all time
     */
    private void displayPrintReleasedFavoriteMoviesOfAllTimeSubmenu() 
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
    
    /**
     * Method for displaying submenu with choices for printed newest released movies
     */
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
    
    /**
     * Method for displaying submenu with choices for printing movies input/output files contents
     */
    private void displayPrintMoviesInputOutputFilesContentsSubmenu() 
    {
        String menuName = "PODMENU VYPISOVÁNÍ OBSAHŮ VSTUPNÍCH/VÝSTUPNÍCH SOUBORŮ FILMŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Vypsat obsah textového souboru %s", DataStore.getTextOutputMoviesFilename()));
        System.out.println(String.format("2. Vypsat obsah binárního souboru %s", DataStore.getBinaryOutputMoviesFilename()));
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing detail about selected movie
     * @param chosenMovie selected movie for which to display detail informations
     */
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
        System.out.println("2. Upravit film");
        
        if (wasReleased == true) System.out.println("3. Ohodnotit film");
        
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for editation of selected movie
     * @param chosenMovie selected movie for which to display this submenu
     */
    private void displayEditChosenMovieSubmenu(Movie chosenMovie) 
    {
        boolean wasReleased = false;
        
        if (chosenMovie.getReleaseDate() != null && 
                chosenMovie.getReleaseDate().compareTo(MoviesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }
        
        String menuName = wasReleased == true ? chosenMovie.getWasWatched() == true ? 
                "PODMENU EDITACE ZHLÉDNUTÉHO FILMU " + chosenMovie.getName().toUpperCase() : 
                "PODMENU EDITACE NEZHLÉDNUTÉHO FILMU " + chosenMovie.getName().toUpperCase() : 
                "PODMENU EDITACE OZNÁMENÉHO FILMU " + chosenMovie.getName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Upravit film pomocí vstupního textového souboru " + DataStore.getTextInputMoviesFilename());
        System.out.println("2. Upravit film pomocí vstupního binárního souboru " + DataStore.getBinaryInputMoviesFilename());
        System.out.println("3. Vypsat obsah vstupního textového souboru " + DataStore.getTextInputMoviesFilename());
        System.out.println("4. Vypsat obsah vstupního binárního souboru " + DataStore.getBinaryInputMoviesFilename());
                
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for handling loading movies from input file submenu 
     * ({@link displayLoadMoviesFromInputFileSubmenu})
     */
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
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 0:
                        consoleUI.removeLastBreadcrumbItem();
                        returnToParentMenu = true;
                        break;
                    default:
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method which tries to load movies from movies input file
     * @param fromBinary selects if input file is binary or text
     */
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
    
    /**
     * Method for handling printing found movies by name submenu
     * ({@link displayPrintFoundMoviesByNameSubmenu})
     */
    private void handlePrintFoundMoviesByNameSubmenu() 
    {
        try 
        {
            String movieQueriedName = loadMovieNameFromUser();
            List<Movie> foundMoviesByName = consoleUI.getMoviesController().searchForMovie(movieQueriedName);

            consoleUI.addBreadcrumbItem("Nalezené filmy podle názvu");

            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                foundMoviesByName = consoleUI.getMoviesController().searchForMovie(movieQueriedName);

                printFoundMoviesByName(foundMoviesByName, movieQueriedName);
                consoleUI.displayBreadcrumb();
                displayPrintFoundMoviesByNameSubmenu();

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteChosenMovies(foundMoviesByName);
                            break;
                        case 2:
                            handleDisplayDetailAboutMovieSubmenu(foundMoviesByName);
                            break;
                        case 0:
                            consoleUI.removeLastBreadcrumbItem();
                            returnToParentMenu = true;
                            break;
                        default:
                            consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                    }
                } 
                catch (InputMismatchException ex) 
                {
                    consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                    consoleUI.advanceToNextInput();
                }
            }
        }
        catch (IllegalArgumentException ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
    }
    
    /**
     * Method which prompts user for entering movie name and then returns it.
     * @return string value representing movie name
     */
    private String loadMovieNameFromUser() 
    {
        consoleUI.advanceToNextInput();
        System.out.println();
        System.out.println("Zadejte název filmu: ");
        return consoleUI.getScanner().nextLine();
    }
    
    /**
     * Method which prints found movies by name as view/page.
     * @param foundMovies list of found movies by name
     * @param queriedName queried movie name used for searching movies by name
     */
    private void printFoundMoviesByName(List<Movie> foundMovies, String queriedName) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, "NALEZENÉ FILMY PODLE NÁZVU");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%-20s%d", "Počet filmů:", foundMovies.size()));
        System.out.println(String.format("%-20s%s", "Hledaný název:", queriedName));
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = MovieOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        String percentageRatingText;
        String releaseDateText;
        
        for (Movie movie : foundMovies) 
        {
            counter++;

            percentageRatingText = movie.getWasWatched() == true ? movie.getPercentageRating() + " %" : "Nehodnoceno";
            
            durationText = movie.getRuntime() == null ? "Neznámá" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
            
            releaseDateText =  movie.getReleaseDate() == null ? "Neznámé" : movie.getReleaseDate().format(dateFormatter);
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %-14s%s %-13s%s %s", 
                    counter + ".", 
                    "Název:", movie.getName(), 
                    "Datum vydání:", releaseDateText,
                    "Délka:", durationText,
                    "Hodnocení:", percentageRatingText));
        }
        
        System.out.println();
        System.out.println(dividingLine);  
    }
    
    /**
     * Method for handling sending movies by email submenu
     * ({@link displaySendMoviesByEmailSubmenu})
     */
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
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method which tries to send unwatched movies, sorted from oldest, by email
     */
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
    
    /**
     * Method which tries to send unwatched movies, categorized into star wars eras, by email
     */
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
    
    /**
     * Method for handling printing star wars eras with annnounced movies submenu
     * ({@link displayPrintErasWithAnnouncedMoviesSubmenu})
     */
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
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    } 
    
    /**
     * Method for printing star wars eras with announced movies as view/page.
     */
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
            System.out.println(String.format("%-7s%s %-28s%s %d", 
                    counter + ".", 
                    "Období:", era.getDisplayName(), 
                    "Počet filmů:", consoleUI.getMoviesController().getAnnouncedMoviesCountByEra(era)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    /**
     * Method for handling printing annnounced movies in alphabetical order by era submenu
     * ({@link displayPrintAnnouncedMoviesInAlphabeticalOrderByEraSubmenu})
     */
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
                            consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
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
    
    /**
     * Method for printing annnounced movies in alphabetical order by selected era as view/page.
     * @param announcedMoviesByChosenEra list of announced movies in selected era
     * @param chosenEra selected star wars era from eras list
     */
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
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %s", 
                    counter + ".", 
                    "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate() == null ? "Neznámé" : movie.getReleaseDate().format(dateFormatter)));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling printing eras with unwatched movies submenu
     * ({@link displayPrintErasWithUnwatchedMoviesSubmenu})
     */
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
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method for printing star wars eras with unwatched movies as view/page.
     */
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
        
        Duration eraUnwatchedMoviesTotalDuration;
        Duration eraUnwatchedMoviesAverageDuration;
        
        int eraUnwatchedMoviesWithDurationSetCount;
        
        String totalDurationText;
        String averageDurationText;
        
        for (Era era : Era.values()) 
        {
            counter++;
            
            eraUnwatchedMoviesTotalDuration = consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(era, false);
            eraUnwatchedMoviesAverageDuration = consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(era, false);
            
            eraUnwatchedMoviesWithDurationSetCount = consoleUI.getMoviesController().getReleasedMoviesWithRuntimeSetCountByEra(era, false);
                        
            totalDurationText =  String.format("%02d:%02d:%02d", eraUnwatchedMoviesTotalDuration.toHours(), 
                    eraUnwatchedMoviesTotalDuration.toMinutesPart(), 
                    eraUnwatchedMoviesTotalDuration.toSecondsPart());
            
            averageDurationText =  String.format("%02d:%02d:%02d", eraUnwatchedMoviesAverageDuration.toHours(), 
                    eraUnwatchedMoviesAverageDuration.toMinutesPart(), 
                    eraUnwatchedMoviesAverageDuration.toSecondsPart());

            System.out.println();            
            System.out.println(String.format("%-7s%s %-28s%s %-7d%s %-7d%s %-13s%s %s", 
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
    
    /**
     * Method for handling printing unwatched movies by selected era submenu
     * ({@link displayPrintUnwatchedMoviesByEraSubmenu})
     * @param dataSorting chooses sorting criteria for unwatched movies to specify
     * how they should sort
     */
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
                            consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
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
    
    /**
     * Method for printing unwatched movies by selected star wars era as view/page.
     * @param unwatchedMoviesByChosenEra list of unwatched movies by selected era
     * @param chosenEra selected star wars era from eras list
     * @param dataSorting specifies how the printed movies should be sorted
     */
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
        
        Duration unwatchedMoviesTotalDuration = consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(chosenEra, false);
        Duration unwatchedMoviesAverageDuration = consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(chosenEra, false);
            
        int unwatchedMoviesWithDurationSetCount = consoleUI.getMoviesController().getReleasedMoviesWithRuntimeSetCountByEra(chosenEra, false);
                        
        String totalDurationText =  String.format("%02d:%02d:%02d", unwatchedMoviesTotalDuration.toHours(), 
                unwatchedMoviesTotalDuration.toMinutesPart(), 
                unwatchedMoviesTotalDuration.toSecondsPart());
        
        String averageDurationText =  String.format("%02d:%02d:%02d", unwatchedMoviesAverageDuration.toHours(), 
                unwatchedMoviesAverageDuration.toMinutesPart(), 
                unwatchedMoviesAverageDuration.toSecondsPart());
                
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
            
            durationText = movie.getRuntime() == null ? "Neznámá" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %-14s%s %s", 
                    counter + ".", 
                    "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling printing eras with watched movies submenu
     * ({@link displayPrintErasWithWatchedMoviesSubmenu})
     */
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
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method for printing eras with watched movies as view/page.
     */
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
        
        Duration eraWatchedMoviesTotalDuration;
        Duration eraWatchedMoviesAverageDuration;
        
        int eraWatchedMoviesWithRuntimeSetCount;
        
        String totalDurationText;
        String averageDurationText;
        float averagePercentageRating;
        
        for (Era era : Era.values()) 
        {
            counter++;
            
            eraWatchedMoviesTotalDuration = 
                    consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(era, true);
            eraWatchedMoviesAverageDuration = 
                    consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(era, true);
            
            averagePercentageRating = consoleUI.getMoviesController().getAverageRatingOfAllReleasedMoviesByEra(era);
            
            eraWatchedMoviesWithRuntimeSetCount = 
                    consoleUI.getMoviesController().getReleasedMoviesWithRuntimeSetCountByEra(era, true);
                        
            totalDurationText =  String.format("%02d:%02d:%02d", eraWatchedMoviesTotalDuration.toHours(), 
                    eraWatchedMoviesTotalDuration.toMinutesPart(), 
                    eraWatchedMoviesTotalDuration.toSecondsPart());
            
            averageDurationText =  String.format("%02d:%02d:%02d", eraWatchedMoviesAverageDuration.toHours(), 
                    eraWatchedMoviesAverageDuration.toMinutesPart(), 
                    eraWatchedMoviesAverageDuration.toSecondsPart());

            System.out.println();            
            System.out.println(String.format("%-7s%s %-28s%s %-7d%s %-12s%s %-7d%s %-13s%s %s", 
                    counter + ".", 
                    "Období:", era.getDisplayName(), 
                    "Počet filmů:", consoleUI.getMoviesController().getReleasedMoviesCountByEra(era, true),
                    "Průměrné hodnocení filmů:", String.format("%.2f %%", averagePercentageRating),
                    "Počet filmů s délkou:", eraWatchedMoviesWithRuntimeSetCount,
                    "Délka filmů:", totalDurationText,
                    "Průměrná délka filmů:", averageDurationText));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    /**
     * Method for handling printing watched movies by era submenu
     * ({@link displayPrintWatchedMoviesByEraSubmenu})
     * @param dataSorting sorting criteria which specifies how the printed movies should be sorted
     */
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
                                consoleUI.getMoviesController().getReleasedFavoriteMoviesByEra(chosenEra);
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
                            consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
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
    
    /**
     * Method for printing watched movies in selected star wars era as view/page.
     * @param watchedMoviesByChosenEra list of watched movies in selected star wars era
     * @param chosenEra selected star wars era from eras list
     * @param dataSorting sorting criteria which specifies how the printed movies should be sort
     */
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
        
        Duration watchedMoviesTotalDuration = consoleUI.getMoviesController().getTotalRuntimeOfAllReleasedMoviesByEra(chosenEra, true);
        Duration watchedMoviesAverageDuration = consoleUI.getMoviesController().getAverageRuntimeOfAllReleasedMoviesByEra(chosenEra, true);
        
        float averagePercentageRating = consoleUI.getMoviesController().getAverageRatingOfAllReleasedMoviesByEra(chosenEra);
            
        int watchedMoviesWithRuntimeSetCount = consoleUI.
                getMoviesController().getReleasedMoviesWithRuntimeSetCountByEra(chosenEra, true);
                        
        String totalDurationText =  String.format("%02d:%02d:%02d", watchedMoviesTotalDuration.toHours(), 
                watchedMoviesTotalDuration.toMinutesPart(), 
                watchedMoviesTotalDuration.toSecondsPart());
        
        String averageDurationText =  String.format("%02d:%02d:%02d", watchedMoviesAverageDuration.toHours(), 
                watchedMoviesAverageDuration.toMinutesPart(), 
                watchedMoviesAverageDuration.toSecondsPart());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%-70s%d", "Počet filmů:", watchedMoviesByChosenEra.size()));
        System.out.println(String.format("%-70s%.2f %%", "Průměrné hodnocení filmů:", 
                averagePercentageRating));
        
        System.out.println();
        
        System.out.println(String.format("%-70s%d", "Počet filmů s délkou (zahrnuty pouze filmy s nastavenou délkou):", 
                watchedMoviesWithRuntimeSetCount));
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
        
        for (Movie movie : watchedMoviesByChosenEra) 
        {
            counter++;
            
            durationText = movie.getRuntime() == null ? "Neznámá" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %-14s%s %-12s%s %d %%", 
                    counter + ".", 
                    "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText,
                    "Hodnocení:", movie.getPercentageRating()));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling printing released favorite movies of all time submenu
     * ({@link displayPrintReleasedFavoriteMoviesOfAllTimeSubmenu})
     */
    private void handlePrintReleasedFavoriteMoviesOfAllTimeSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Nejoblíbenější filmy");

        boolean returnToParentMenu = false;
        int choice;

        while (returnToParentMenu == false) 
        {
            List<Movie> favoriteMoviesOfAllTime = consoleUI.getMoviesController().getReleasedFavoriteMoviesOfAllTime();

            printReleasedFavoriteMoviesOfAllTime(favoriteMoviesOfAllTime);
            consoleUI.displayBreadcrumb();
            displayPrintReleasedFavoriteMoviesOfAllTimeSubmenu();

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
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            } 
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method for printing released favorite movies of all time as view/page.
     * @param favoriteMovies list of favorite movies
     */
    private void printReleasedFavoriteMoviesOfAllTime(List<Movie> favoriteMovies) 
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
            
            durationText = movie.getRuntime() == null ? "Neznámá" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %-14s%s %-12s%s %d %%", 
                    counter + ".", 
                    "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText,
                    "Hodnocení:", movie.getPercentageRating()));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling printing released newest movies submenu
     * ({@link displayPrintReleasedNewestMoviesSubmenu})
     */
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
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            } 
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method for printing released newets movies as view/page.
     * @param releasedNewestMovies list of released newest movies
     */
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
                        
            percentageRatingText = movie.getWasWatched() == true ? movie.getPercentageRating() + " %" : "Nehodnoceno";
            
            durationText = movie.getRuntime() == null ? "Neznámá" : String.format("%02d:%02d:%02d", movie.getRuntime().toHours(), 
                    movie.getRuntime().toMinutesPart(), movie.getRuntime().toSecondsPart());
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %-14s%s %-12s%s %s", 
                    counter + ".", 
                    "Název:", movie.getName(), 
                    "Datum vydání:", movie.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText,
                    "Hodnocení:", percentageRatingText));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling printing movies input/output files contents submenu
     * ({@link displayPrintMoviesInputOutputFilesContentsSubmenu})
     */
    private void handlePrintMoviesInputOutputFilesContentsSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Vypisování obsahů vstupních/výstupních souborů filmů");
        
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayPrintMoviesInputOutputFilesContentsSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        consoleUI.displayChosenDataFileContent(DataStore.getTextOutputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 2:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryOutputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 0:
                        consoleUI.removeLastBreadcrumbItem();
                        returnToParentMenu = true;
                        break;
                    default:
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            }
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
    }
    
    /**
     * Method for handling displaying detail about chosen movie submenu
     * ({@link displayDetailAboutMovieSubmenu})
     * @param chosenMovies list of chosen movies from which chosen movie will
     * be selected by movie order from printed movies view/list.
     */
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
                printMovieDetail(chosenMovie, false);
                                
                consoleUI.displayInfoMessage(String.format("Po úpravě"
                        + (wasReleased == true ? " nebo po ohodnocení" : "") + " se "
                        + "film nemusí vypisovat tam, kde se původně vypisoval"));
                                
                consoleUI.displayInfoMessage(String.format("Při smazání filmu" 
                        + (wasReleased == true ? ", ohodnocení filmu" : "") 
                        + " nebo při reálné úpravě dat filmu "
                        + "dojde k návratu zpátky do daného výpisu s filmy"));            
 
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
                            returnToParentMenu = handleDisplayEditChosenMovieSubmenu(chosenMovie); 
                            
                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }
                            
                            break;
                        case 3:
                            
                            if (wasReleased == true) 
                            {
                                returnToParentMenu = rateMovie(chosenMovie);
                            
                                if (returnToParentMenu == true) 
                                {
                                    consoleUI.removeLastBreadcrumbItem();
                                }
                            }
                            else 
                            {
                                consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                            }
                            
                            break;
                        case 0:
                            consoleUI.removeLastBreadcrumbItem();
                            returnToParentMenu = true;
                            break;
                        default:
                            consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
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
    
    /**
     * Method which prompts user for movie order number from printed movies view/list 
     * and then returns it.
     * @return int value representing movie order from movies view.
     */
    private int loadChosenMovieFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo filmu: ");
        return consoleUI.getScanner().nextInt();
    }
    
    /**
     * Method which prints detail informations about chosen movie as view/page.
     * @param chosenMovie represents chosen movie from printed movies list/view.
     * @param isInEditMode selects if movie informations are displayed in movie detail mode or edit mode
     * ({@link displayDetailAboutMovieSubmenu}, {@link displayEditChosenMovieSubmenu})
     */
    private void printMovieDetail(Movie chosenMovie, boolean isInEditMode) 
    {
        boolean wasReleased = false;
        
        if(chosenMovie.getReleaseDate() != null && 
                    chosenMovie.getReleaseDate().compareTo(MoviesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }
        
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("%s %s FILMU %s",
                        isInEditMode == true ? "EDITACE" : "DETAIL",
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
        
        String runtimeText = chosenMovie.getRuntime() == null ? "Neznámá" :
                String.format("%02d:%02d:%02d", chosenMovie.getRuntime().toHours(), 
                        chosenMovie.getRuntime().toMinutesPart(), chosenMovie.getRuntime().toSecondsPart());
        
        System.out.println(String.format("%-30s%d", "Identifikátor:", chosenMovie.getPrimaryKey().getId()));
        System.out.println(String.format("%-30s%s", "Název:", chosenMovie.getName()));
        System.out.println(String.format("%-30s%s", "Chronologická éra:", chosenMovie.getEra().getDisplayName()));
        System.out.println(String.format("%-30s%s", "Datum uvedení:", chosenMovie.getReleaseDate() == null ? 
                "Neznámé" : chosenMovie.getReleaseDate().format(dateFormatter)));    
        System.out.println(String.format("%-30s%s", "Délka filmu:", runtimeText));
        
        if (wasReleased == true) 
        {            
            System.out.println(String.format("%-30s%s", "Procentuální ohodnocení:", 
                    chosenMovie.getWasWatched() == true ? chosenMovie.getPercentageRating() + " %" : "Nehodnoceno"));
            System.out.println(String.format("%-30s%s", "Odkaz ke zhlédnutí:", 
                    chosenMovie.getHyperlinkForContentWatch() == null ? "Neznámý" : chosenMovie.getHyperlinkForContentWatch()));
            
            String shortContentSummaryText = chosenMovie.getShortContentSummary() == null ? 
                    String.format("%-30s%s", "Krátké shrnutí obsahu:", "Neznámé") : 
                    String.format("Krátké shrnutí obsahu:%n%n%s", chosenMovie.getShortContentSummary());
            
            System.out.println(shortContentSummaryText);
        }
                
        System.out.println();
        System.out.println(dividingLine);  
    }
    
    /**
     * Method which tries to delete chosen movie from database
     * @param moviePrimaryKey represents chosen movie primary key
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed movies as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
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
    
    /**
     * Method for handling displaying editation of chosen movie submenu
     * ({@link displayEditChosenMovieSubmenu})
     * @param chosenMovie selected movie from printed movies list/view.
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed movies as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean handleDisplayEditChosenMovieSubmenu(Movie chosenMovie) 
    {
        boolean wasReleased = false;

        if (chosenMovie.getReleaseDate() != null && 
                chosenMovie.getReleaseDate().compareTo(MoviesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }

        consoleUI.addBreadcrumbItem(String.format("Editace %s filmu %s", 
                wasReleased == true ? chosenMovie.getWasWatched() == true ? 
                        "zhlédnutého" : "nezhlédnutého" : "oznámeného", chosenMovie.getName()));

        boolean returnToParentMenu = false;
        boolean returnToMoviesListMenu = false;
        int choice;

        while (returnToParentMenu == false && returnToMoviesListMenu == false) 
        {
            printMovieDetail(chosenMovie, true);
                
            consoleUI.displayInfoMessage(String.format("Při úpravě filmu se používají stejné"
                    + " vstupní soubory jako u přidávání filmů"));
            
            consoleUI.displayInfoMessage(String.format("Pro jednoznačnost je nutné mít v daném souboru pouze jeden film"));

            consoleUI.displayInfoMessage(String.format("Při úpravě filmu se používá v " 
                    + "souboru úplně totožná struktura dat jako u přidávání filmů"));
            
            consoleUI.displayBreadcrumb();
            displayEditChosenMovieSubmenu(chosenMovie);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        returnToMoviesListMenu = editMovieFromInputFile(chosenMovie.getPrimaryKey(), false);

                        if (returnToMoviesListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 2:
                        returnToMoviesListMenu = editMovieFromInputFile(chosenMovie.getPrimaryKey(), true);

                        if (returnToMoviesListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 3:
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 0:
                        consoleUI.removeLastBreadcrumbItem();
                        returnToParentMenu = true;
                        break;
                    default:
                        consoleUI.displayErrorMessage("Neplatné číslo volby z podmenu");
                }
            } 
            catch (InputMismatchException ex) 
            {
                consoleUI.displayErrorMessage("Volba musí být vybrána pomocí čísla");
                consoleUI.advanceToNextInput();
            }
        }
        
        return returnToMoviesListMenu;
    }
    
    /**
     * Method which tries to edit existing chosen movie by movies input file
     * @param existingMoviePrimaryKey selected movie primary key
     * @param fromBinary selects if input file will be binary or text
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed movies as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
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
    
    /**
     * Method which tries to rate existing chosen movie in percents (0 to 100)
     * @param chosenMovie selected movie, which percentage rating is to be modified.
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed movies as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
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
    
    /**
     * Method which prompts user for entering chosen movie percentage rating in procents (0 to 100)
     * and then returns it.
     * @return int value representing percentage rating 
     */
    private int loadMoviePercentageRatingFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte hodnocení filmu jako procenta od 0 až do 100: ");
        return consoleUI.getScanner().nextInt();
    }
    
    /**
     * Method which tries to delete chosen movies from database
     * @param chosenMovies represents list of chosen movies to be deleted
     */
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
}
