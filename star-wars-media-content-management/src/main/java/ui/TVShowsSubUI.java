package ui;

import app.logic.controllers.DataSorting;
import app.logic.controllers.DataType;
import app.logic.controllers.TVEpisodesController;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.PrimaryKey;
import app.models.data.TVShow;
import app.models.inputoutput.TVShowInputOutput;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;

/**
 * Represents a tv shows UI submodule, which represents a UI section dealing with tv shows data.
 * TVShowsSubUI is communicating with ConsoleUI to use common methods and access tv episodes controller, 
 * tv seasons UI submodule with scanner.
 * @author jan.dostal
 */
public class TVShowsSubUI 
{
    private final ConsoleUI consoleUI;
    
    /**
     * Creates a new instance of TVShowsSubUI.
     * Uses dependency injection to inject consoleUI ui module.
     * @param consoleUI existing instance of ConsoleUI. 
     * Can be used for using common methods from {@link ConsoleUI} class or access
     * tv episodes controller, tv seasons UI submodule with scanner.
     */
    protected TVShowsSubUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
    
    /**
     * Method for handling displaying tv shows management submenu
     * ({@link displayTVShowsManagementSubmenu}). ConsoleUI class calls this method.
     */
    protected void handleDisplayTVShowsManagementSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Správa TV seriálů");
        
        boolean returnToMainMenu = false;
        int choice;
        
        while (returnToMainMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayTVShowsManagementSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handleLoadTVShowsFromInputFileSubmenu();
                        break;
                    case 2:
                        handlePrintFoundTVShowsByNameSubmenu();
                        break;
                    case 3:
                        handlePrintErasWithAnnouncedTVShowsSubmenu();
                        break;
                    case 4:
                        handlePrintErasWithReleasedTVShowsSubmenu();
                        break;
                    case 5:
                        handleDisplayPrintReleasedNewestTVShowsSubmenu();
                        break;
                    case 6:
                        handlePrintDataInputOutputFilesContentsSubmenu(DataType.TV_EPISODE);
                        break;
                    case 7:
                        handlePrintDataInputOutputFilesContentsSubmenu(DataType.TV_SEASON);
                        break;
                    case 8:
                        handlePrintDataInputOutputFilesContentsSubmenu(DataType.TV_SHOW);
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
     * Method for displaying submenu with choices for managing tv shows.
     */
    private void displayTVShowsManagementSubmenu() 
    {
        String menuName = "PODMENU SPRÁVA TV SERIÁLŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Přidat TV seriály ze vstupního souboru");
        System.out.println("2. Vyhledat TV seriál podle jména");
        System.out.println("3. Vypsat oznámené TV seriály v jednotlivých érách");
        System.out.println("4. Vypsat vydané TV seriály v jednotlivých érách");
        System.out.println("5. Vypsat nejnovější již vydané TV seriály");
        System.out.println("6. Vypsat obsahy vstupních/výstupních souborů TV epizod");
        System.out.println("7. Vypsat obsahy vstupních/výstupních souborů TV sezón");
        System.out.println("8. Vypsat obsahy vstupních/výstupních souborů TV seriálů");
        System.out.println("0. Vrátit se zpět do hlavního menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for adding tv shows from tv shows input file
     */
    private void displayLoadTVShowsFromInputFileSubmenu() 
    {
        String menuName = "PODMENU PŘIDÁVÁNÍ TV SERIÁLŮ ZE VSTUPNÍHO SOUBORU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Načíst z textového souboru");
        System.out.println("2. Načíst z binárního souboru");
        System.out.println("3. Vypsat obsah textového souboru");
        System.out.println("4. Vypsat obsah binárního souboru");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for found tv shows by their name
     */
    private void displayPrintFoundTVShowsByNameSubmenu() 
    {
        String menuName = "PODMENU NALEZENÝCH TV SERIÁLŮ PODLE NÁZVU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané nalezené TV seriály");
        System.out.println("2. Vypsat detail vybraného nalezeného TV seriálu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printed star wars eras list with annnounced tv shows
     */
    private void displayPrintErasWithAnnouncedTVShowsSubmenu() 
    {
        String menuName = "PODMENU ÉR S OZNÁMENÝMI TV SERIÁLY";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat abecedně oznámené TV seriály vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printed announced tv shows, 
     * sorted alphabetically by name, of selected star wars era.
     * @param chosenEra selected star wars era for which to print announced tv shows
     */
    private void displayPrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu(Era chosenEra) 
    {
        String menuName = "PODMENU OZNÁMENÝCH TV SERIÁLŮ ÉRY " + chosenEra.getDisplayName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané oznámené TV seriály");
        System.out.println("2. Vypsat detail vybraného oznámeného TV seriálu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printed star wars eras list with released tv shows
     */
    private void displayPrintErasWithReleasedTVShowsSubmenu() 
    {
        String menuName = "PODMENU ÉR S VYDANÝMI TV SERIÁLY";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat abecedně vydané TV seriály vybrané éry");
        System.out.println("2. Vypsat nejnovější vydané TV seriály vybrané éry");
        System.out.println("3. Vypsat nejdelší vydané TV seriály vybrané éry");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printed released tv shows of selected star wars era
     * @param chosenEra selected star wars era for which to print released tv shows
     */
    private void displayPrintReleasedTVShowsByEraSubmenu(Era chosenEra) 
    {
        String menuName = "PODMENU VYDANÝCH TV SERIÁLŮ ÉRY " + chosenEra.getDisplayName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané vydané TV seriály");
        System.out.println("2. Vypsat detail vybraného vydaného TV seriálu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing detail about selected tv show
     * @param chosenTVShow selected tv show for which to display detail informations
     */
    private void displayDetailAboutTVShowSubmenu(TVShow chosenTVShow) 
    {
        boolean wasReleased = false;
        
        if (chosenTVShow.getReleaseDate() != null && 
                chosenTVShow.getReleaseDate().compareTo(TVEpisodesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }
        
        String menuName = wasReleased == true ? "PODMENU DETAILU VYDANÉHO TV SERIÁLU " + chosenTVShow.getName().toUpperCase() : 
                "PODMENU DETAILU OZNÁMENÉHO TV SERIÁLU " + chosenTVShow.getName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat TV seriál");
        System.out.println("2. Upravit TV seriál");
        
        if (wasReleased == true) 
        {
            System.out.println("3. Vypsat TV sezóny");
        }
        
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for editation of selected tv show
     * @param chosenTVShow selected tv show for which to display this submenu
     */
    private void displayEditChosenTVShowSubmenu(TVShow chosenTVShow) 
    {
        boolean wasReleased = false;
        
        if (chosenTVShow.getReleaseDate() != null && 
                chosenTVShow.getReleaseDate().compareTo(TVEpisodesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }
        
        String menuName = wasReleased == true ? "PODMENU EDITACE VYDANÉHO TV SERIÁLU " + chosenTVShow.getName().toUpperCase() : 
                "PODMENU EDITACE OZNÁMENÉHO TV SERIÁLU " + chosenTVShow.getName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Upravit TV seriál pomocí vstupního textového souboru");
        System.out.println("2. Upravit TV seriál pomocí vstupního binárního souboru");
        System.out.println("3. Vypsat obsah vstupního textového souboru");
        System.out.println("4. Vypsat obsah vstupního binárního souboru");
                
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printed released newest tv shows
     */
    private void displayPrintReleasedNewestTVShowsSubmenu() 
    {
        String menuName = "PODMENU NEJNOVĚJŠÍCH JIŽ VYDANÝCH TV SERIÁLŮ";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat aktuálně vypsané nejnovější již vydané TV seriály");
        System.out.println("2. Vypsat detail vybraného vydaného TV seriálu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing tv episodes, 
     * tv seasons and tv shows input/output files contents
     * @param dataType specifies for which data to display submenu and its choices
     */
    private void displayPrintDataInputOutputFilesContentsSubmenu(DataType dataType) 
    {
        String menuName = null;
        
        switch (dataType) 
        {
            case TV_EPISODE:
                menuName = "PODMENU VYPISOVÁNÍ OBSAHŮ VSTUPNÍCH/VÝSTUPNÍCH SOUBORŮ TV EPIZOD";
                break;
            case TV_SEASON:
                menuName = "PODMENU VYPISOVÁNÍ OBSAHŮ VSTUPNÍCH/VÝSTUPNÍCH SOUBORŮ TV SEZÓN";
                break;
            case TV_SHOW:
                menuName = "PODMENU VYPISOVÁNÍ OBSAHŮ VSTUPNÍCH/VÝSTUPNÍCH SOUBORŮ TV SERIÁLŮ";
                break;
        }
                
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat obsah textového souboru");
        System.out.println("2. Vypsat obsah binárního souboru");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for handling loading tv shows from input file submenu 
     * ({@link displayLoadTVShowsFromInputFileSubmenu})
     */
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
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputTVShowsFilename(), DataType.TV_SHOW);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputTVShowsFilename(), DataType.TV_SHOW);
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
     * Method which tries to load tv shows from tv shows input file
     * @param fromBinary selects if input file is binary or text
     */
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
    
    /**
     * Method for handling printing found tv shows by name submenu
     * ({@link displayPrintFoundTVShowsByNameSubmenu})
     */
    private void handlePrintFoundTVShowsByNameSubmenu() 
    {
        try 
        {
            String tvShowQueriedName = loadTVShowNameFromUser();
            List<TVShow> foundTVShowsByName = consoleUI.getTVEpisodesController().searchForTVShow(tvShowQueriedName);

            consoleUI.addBreadcrumbItem("Nalezené TV seriály podle názvu");

            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                foundTVShowsByName = consoleUI.getTVEpisodesController().searchForTVShow(tvShowQueriedName);

                printFoundTVShowsByName(foundTVShowsByName, tvShowQueriedName);
                consoleUI.displayBreadcrumb();
                displayPrintFoundTVShowsByNameSubmenu();

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteChosenTVShows(foundTVShowsByName);
                            break;
                        case 2:
                            handleDisplayDetailAboutTVShowSubmenu(foundTVShowsByName);
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
     * Method which prompts user for entering tv show name and then returns it.
     * @return string value representing tv show name
     */
    private String loadTVShowNameFromUser() 
    {
        consoleUI.advanceToNextInput();
        System.out.println();
        System.out.println("Zadejte název TV seriálu: ");
        return consoleUI.getScanner().nextLine();
    }
    
    /**
     * Method which prints found tv shows by name as view/page.
     * @param foundTVShows list of found tv shows by name
     * @param queriedName queried tv show name used for searching tv shows by name
     */
    private void printFoundTVShowsByName(List<TVShow> foundTVShows, String queriedName) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, "NALEZENÉ TV SERIÁLY PODLE NÁZVU");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%-20s%d", "Počet seriálů:", foundTVShows.size()));
        System.out.println(String.format("%-20s%s", "Hledaný název:", queriedName));
        
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = TVShowInputOutput.ATTRIBUTE_NAME_LENGTH + 3;
        
        for (TVShow show : foundTVShows) 
        {
            counter++;
                     
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %s", 
                    counter + ".", 
                    "Název:", show.getName(), 
                    "Datum vydání:", show.getReleaseDate() == null ? "Neznámé" : show.getReleaseDate().format(dateFormatter)));            
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    /**
     * Method for handling printing star wars eras with annnounced tv shows submenu
     * ({@link displayPrintErasWithAnnouncedTVShowsSubmenu})
     */
    private void handlePrintErasWithAnnouncedTVShowsSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Éry s oznámenými TV seriály"); 
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            printErasWithAnnouncedTVShows();
            consoleUI.displayBreadcrumb();
            displayPrintErasWithAnnouncedTVShowsSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handlePrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu();
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
     * Method for printing star wars eras with announced tv shows as view/page.
     */
    private void printErasWithAnnouncedTVShows() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "ÉRY S OZNÁMENÝMI TV SERIÁLY (začíná nejstarší érou)");
        
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
                    "Počet seriálů:", consoleUI.getTVEpisodesController().getAnnouncedTVShowsCountByEra(era)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    /**
     * Method for handling printing annnounced tv shows in alphabetical order by era submenu
     * ({@link displayPrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu})
     */
    private void handlePrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu() 
    {
        try 
        {
            int eraOrderFromList = consoleUI.loadChosenEraOrderFromUser();
            Era chosenEra = Era.values()[eraOrderFromList - 1];

            consoleUI.addBreadcrumbItem(String.format("Oznámené TV seriály éry %s (řazeno abecedně)", chosenEra.getDisplayName()));
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                List<TVShow> announcedTVShowsByChosenEra = consoleUI.getTVEpisodesController().
                        getAnnouncedTVShowsInAlphabeticalOrderByEra(chosenEra);
                
                printAnnouncedTVShowsInAlphabeticalOrderByEra(announcedTVShowsByChosenEra, chosenEra);
                consoleUI.displayBreadcrumb();
                displayPrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu(chosenEra);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteChosenTVShows(announcedTVShowsByChosenEra);
                            break;
                        case 2:
                            handleDisplayDetailAboutTVShowSubmenu(announcedTVShowsByChosenEra);
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
     * Method for printing annnounced tv shows in alphabetical order by selected era as view/page.
     * @param announcedTVShowsByChosenEra list of announced tv shows in selected era
     * @param chosenEra selected star wars era from eras list
     */
    private void printAnnouncedTVShowsInAlphabeticalOrderByEra(List<TVShow> announcedTVShowsByChosenEra, Era chosenEra) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("OZNAMENÉ TV SERIÁLY ÉRY %s (řazeno abecedně)", chosenEra.getDisplayName().toUpperCase()));
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println("Počet seriálů: " + announcedTVShowsByChosenEra.size());
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = TVShowInputOutput.ATTRIBUTE_NAME_LENGTH + 5;
        
        for (TVShow tvShow : announcedTVShowsByChosenEra) 
        {
            counter++;
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %s", 
                    counter + ".", 
                    "Název:", tvShow.getName(), 
                    "Datum vydání:", tvShow.getReleaseDate() == null ? "Neznámé" : tvShow.getReleaseDate().format(dateFormatter)));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    
    /**
     * Method for handling printing eras with released tv shows submenu
     * ({@link displayPrintErasWithReleasedTVShowsSubmenu})
     */
    private void handlePrintErasWithReleasedTVShowsSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Éry s vydanými TV seriály"); 
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            printErasWithReleasedTVShows();
            consoleUI.displayBreadcrumb();
            displayPrintErasWithReleasedTVShowsSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        handlePrintReleasedTVShowsByEraSubmenu(DataSorting.BY_NAME);
                        break;
                    case 2:
                        handlePrintReleasedTVShowsByEraSubmenu(DataSorting.NEWEST);
                        break;
                    case 3:
                        handlePrintReleasedTVShowsByEraSubmenu(DataSorting.LONGEST);
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
     * Method for printing star wars eras with released tv shows as view/page.
     */
    private void printErasWithReleasedTVShows() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "ÉRY S VYDANÝMI TV SERIÁLY (začíná nejstarší érou)");
        
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
                    "Počet seriálů:", consoleUI.getTVEpisodesController().getReleasedTVShowsCountByEra(era)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
    /**
     * Method for handling printing released tv shows by selected era submenu
     * ({@link displayPrintReleasedTVShowsByEraSubmenu})
     * @param dataSorting chooses sorting criteria for released tv shows to specify
     * how they should sort
     */
    private void handlePrintReleasedTVShowsByEraSubmenu(DataSorting dataSorting) 
    {
        try 
        {
            int eraOrderFromList = consoleUI.loadChosenEraOrderFromUser();
            Era chosenEra = Era.values()[eraOrderFromList - 1];
            
            switch (dataSorting) 
            {
                case BY_NAME:
                    consoleUI.addBreadcrumbItem(String.format("Vydané TV seriály éry %s (řazeno abecedně)", 
                            chosenEra.getDisplayName()));
                    break;
                case NEWEST:
                    consoleUI.addBreadcrumbItem(String.format("Vydané TV seriály éry %s (řazeno podle data uvedení)", 
                            chosenEra.getDisplayName()));
                    break;
                case LONGEST:
                    consoleUI.addBreadcrumbItem(String.format("Vydané TV seriály éry %s (řazeno podle celkové délky epizod)", 
                            chosenEra.getDisplayName()));
                    break;
            }
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                List<TVShow> releasedTVShowsByChosenEra = null;
                
                switch (dataSorting) 
                {
                    case BY_NAME:
                        releasedTVShowsByChosenEra = 
                                consoleUI.getTVEpisodesController().getReleasedTVShowsInAlphabeticalOrderByEra(chosenEra);
                        break;
                    case NEWEST:
                        releasedTVShowsByChosenEra = 
                                consoleUI.getTVEpisodesController().getReleasedNewestTVShowsByEra(chosenEra);
                        break;
                    case LONGEST:
                        releasedTVShowsByChosenEra = 
                                consoleUI.getTVEpisodesController().getReleasedLongestTVShowsByEra(chosenEra);
                        break;
                }
                                
                printReleasedTVShowsByEra(releasedTVShowsByChosenEra, chosenEra, dataSorting);
                
                consoleUI.displayInfoMessage("Při smazání vydaných TV seriálů se smažou i příslušné TV sezóny a TV epizody");
                
                consoleUI.displayBreadcrumb();
                displayPrintReleasedTVShowsByEraSubmenu(chosenEra);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            deleteChosenTVShows(releasedTVShowsByChosenEra);
                            break;
                        case 2:
                            handleDisplayDetailAboutTVShowSubmenu(releasedTVShowsByChosenEra);
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
     * Method for printing released tv shows by selected star wars era as view/page.
     * @param releasedTVShowsByChosenEra list of released tv shows by selected era
     * @param chosenEra selected star wars era from eras list
     * @param dataSorting specifies how the printed tv shows should be sorted
     */
    private void printReleasedTVShowsByEra(List<TVShow> releasedTVShowsByChosenEra, Era chosenEra, DataSorting dataSorting) 
    {
        StringBuilder heading = null;
        
        switch (dataSorting) 
        {
            case BY_NAME:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("VYDANÉ TV SERIÁLY ÉRY %s (řazeno abecedně)", chosenEra.getDisplayName().toUpperCase()));
                break;
            case NEWEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("VYDANÉ TV SERIÁLY ÉRY %s (řazeno podle data uvedení)", 
                            chosenEra.getDisplayName().toUpperCase()));
                break;
            case LONGEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, 
                    String.format("VYDANÉ TV SERIÁLY ÉRY %s (řazeno podle celkové délky epizod)", 
                            chosenEra.getDisplayName().toUpperCase()));
                break;
        }
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        
        System.out.println();
        
        System.out.println("Počet seriálů: " + releasedTVShowsByChosenEra.size());
        
        consoleUI.displayInfoMessage("Všechny TV epizody mají vždy nastavenou délku trvání");
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = TVShowInputOutput.ATTRIBUTE_NAME_LENGTH + 3;
        int statisticInformationsIndentationWidth = nameMaxLength + 28;
        
        int showWatchedEpisodesCount;
        int showUnwatchedEpisodesCount;
        int showEpisodesCount;
        
        String showUnwatchedEpisodesTotalDurationText;
        String showEpisodesTotalDurationText;
        
        Duration showWatchedEpisodesTotalDuration;
        Duration showUnwatchedEpisodesTotalDuration;
        Duration showEpisodesTotalDuration;
        
        float showAveragePercentageRating;
        
        for (TVShow show : releasedTVShowsByChosenEra) 
        {
            counter++;
            
            showAveragePercentageRating = consoleUI.getTVEpisodesController().
                    getAverageRatingOfAllReleasedEpisodesInTVShow(show.getPrimaryKey());
            
            showWatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowEpisodesCount(show.getPrimaryKey(), true);
            
            showUnwatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowEpisodesCount(show.getPrimaryKey(), false);
            
            showEpisodesCount = showUnwatchedEpisodesCount + showWatchedEpisodesCount;
            
            
            showWatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                    getTotalRuntimeOfAllReleasedEpisodesInTVShow(show.getPrimaryKey(), true);
            
            showUnwatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                    getTotalRuntimeOfAllReleasedEpisodesInTVShow(show.getPrimaryKey(), false);
            
            showEpisodesTotalDuration = showWatchedEpisodesTotalDuration.plus(showUnwatchedEpisodesTotalDuration);
            
            showEpisodesTotalDurationText =  String.format("%02d:%02d:%02d", showEpisodesTotalDuration.toHours(), 
                showEpisodesTotalDuration.toMinutesPart(), 
                showEpisodesTotalDuration.toSecondsPart());
        
            showUnwatchedEpisodesTotalDurationText =  String.format("%02d:%02d:%02d", showUnwatchedEpisodesTotalDuration.toHours(), 
                showUnwatchedEpisodesTotalDuration.toMinutesPart(), 
                showUnwatchedEpisodesTotalDuration.toSecondsPart());
            
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %s", 
                    counter + ".", 
                    "Název:", show.getName(), 
                    "Datum vydání:", show.getReleaseDate().format(dateFormatter)));
              
            System.out.println(String.format("%" + statisticInformationsIndentationWidth + "s %-15d%s %-15d%-28s %d",
                    "Počet epizod:", showEpisodesCount,
                    "Počet nezhlédnutých epizod:", showUnwatchedEpisodesCount,
                    "Počet zhlédnutých epizod:", showWatchedEpisodesCount));
            
            System.out.println(String.format("%" + statisticInformationsIndentationWidth + "s %-15s%s %-15s%-28s %.2f %%",
                    "Délka epizod:", showEpisodesTotalDurationText,
                    "Délka nezhlédnutých epizod:", showUnwatchedEpisodesTotalDurationText,
                    "Průměrné hodnocení epizod:", showAveragePercentageRating));
                        
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling displaying detail about chosen tv show submenu
     * ({@link displayDetailAboutTVShowSubmenu})
     * @param chosenTVShows list of chosen tv shows from which chosen tv show will
     * be selected by tv show order from printed tv shows view/list.
     */
    private void handleDisplayDetailAboutTVShowSubmenu(List<TVShow> chosenTVShows) 
    {
        try
        {
            int TVShowOrderFromList = loadChosenTVShowFromUser();
            TVShow chosenTVShow = chosenTVShows.get(TVShowOrderFromList - 1);
            
            boolean wasReleased = false;
            
            if(chosenTVShow.getReleaseDate() != null && 
                    chosenTVShow.getReleaseDate().compareTo(TVEpisodesController.getCurrentDate()) <= 0) 
            {
                wasReleased = true;
            }
            
            consoleUI.addBreadcrumbItem(String.format("Detail %s TV seriálu %s", 
                    wasReleased == true ? "vydaného" : "oznámeného", chosenTVShow.getName()));
            
            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                printTVShowDetail(chosenTVShow, false);
                                
                consoleUI.displayInfoMessage(String.format("Po úpravě se "
                        + "TV seriál nemusí vypisovat tam, kde se původně vypisoval"));
                                
                consoleUI.displayInfoMessage(String.format("Při smazání TV seriálu"
                        + " nebo při reálné úpravě dat TV seriálu "
                        + "dojde k návratu zpátky do daného výpisu s TV seriály"));
                
                if (wasReleased == true) consoleUI.displayInfoMessage(String.format("Při smazání vydaného TV seriálu se smažou"
                        + " i příslušné TV sezóny a TV epizody"));
 
                consoleUI.displayBreadcrumb();
                displayDetailAboutTVShowSubmenu(chosenTVShow);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            returnToParentMenu = deleteChosenTVShow(chosenTVShow.getPrimaryKey());
                            
                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }
                            
                            break;
                        case 2:
                            returnToParentMenu = handleDisplayEditChosenTVShowSubmenu(chosenTVShow); 
                            
                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }
                            
                            break;
                        case 3:
                            
                            if (wasReleased == true) 
                            {
                                consoleUI.getTVSeasonsSubUI().handleDisplayPrintChosenTVShowSeasonsSubmenu(chosenTVShow); ////asdasddsadsa
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
            consoleUI.displayErrorMessage("Pořadové číslo neodpovídá žádnému z TV seriálů");
            consoleUI.advanceToNextInput();
        }
    }
    
    /**
     * Method which prompts user for tv show order number from printed tv shows view/list 
     * and then returns it.
     * @return int value representing tv show order from tv shows view.
     */
    private int loadChosenTVShowFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo TV seriálu: ");
        return consoleUI.getScanner().nextInt();
    }
    
    /**
     * Method which prints detail informations about chosen tv show as view/page.
     * @param chosenTVShow represents chosen tv show from printed tv shows list/view.
     * @param isInEditMode selects if tv show informations are displayed in tv show detail mode or edit mode
     * ({@link displayDetailAboutTVShowSubmenu}, {@link displayEditChosenTVShowSubmenu})
     */
    private void printTVShowDetail(TVShow chosenTVShow, boolean isInEditMode) 
    {
        boolean wasReleased = false;
        
        if(chosenTVShow.getReleaseDate() != null && 
                    chosenTVShow.getReleaseDate().compareTo(TVEpisodesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }
        
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("%s %s TV SERIÁLU %s",
                        isInEditMode == true ? "EDITACE" : "DETAIL",
                        wasReleased == true ? "VYDANÉHO" : "OZNÁMENÉHO", 
                        chosenTVShow.getName().toUpperCase()));
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(dividingLine);
        System.out.println();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
                
        System.out.println(String.format("%-30s%d", "Identifikátor:", chosenTVShow.getPrimaryKey().getId()));
        System.out.println(String.format("%-30s%s", "Název:", chosenTVShow.getName()));
        System.out.println(String.format("%-30s%s", "Chronologická éra:", chosenTVShow.getEra().getDisplayName()));
        System.out.println(String.format("%-30s%s", "Datum uvedení:", chosenTVShow.getReleaseDate() == null ? 
                "Neznámé" : chosenTVShow.getReleaseDate().format(dateFormatter)));
                
        System.out.println();
        System.out.println(dividingLine);  
    }
    
    /**
     * Method which tries to delete chosen tv show from database.
     * Method will also delete chosen TV show seasons and episodes.
     * @param tvShowPrimaryKey represents chosen tv show primary key
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv shows as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean deleteChosenTVShow(PrimaryKey tvShowPrimaryKey) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            consoleUI.getTVEpisodesController().deleteTVShowBy(tvShowPrimaryKey);
            consoleUI.displayInfoMessage("Vybraný TV seriál úspěšně smazán");
            returnToParentMenu = true;
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
    
    /**
     * Method for handling displaying editation of chosen tv show submenu
     * ({@link displayEditChosenTVShowSubmenu})
     * @param chosenTVShow selected tv show from printed tv shows list/view.
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv shows as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean handleDisplayEditChosenTVShowSubmenu(TVShow chosenTVShow) 
    {
        boolean wasReleased = false;

        if (chosenTVShow.getReleaseDate() != null && 
                chosenTVShow.getReleaseDate().compareTo(TVEpisodesController.getCurrentDate()) <= 0) 
        {
            wasReleased = true;
        }

        consoleUI.addBreadcrumbItem(String.format("Editace %s TV seriálu %s", 
                wasReleased == true ? "vydaného" : "oznámeného", 
                chosenTVShow.getName()));

        boolean returnToParentMenu = false;
        boolean returnToTVShowsListMenu = false;
        int choice;

        while (returnToParentMenu == false && returnToTVShowsListMenu == false) 
        {
            printTVShowDetail(chosenTVShow, true);
                
            consoleUI.displayInfoMessage(String.format("Při úpravě TV seriálu se používají stejné"
                    + " vstupní soubory jako u přidávání TV seriálů"));
            
            consoleUI.displayInfoMessage(String.format("Pro jednoznačnost je nutné mít v daném souboru pouze jeden TV seriál"));

            consoleUI.displayInfoMessage(String.format("Při úpravě TV seriálu se používá v " 
                    + "souboru úplně totožná struktura dat jako u přidávání TV seriálů"));
            
            consoleUI.displayBreadcrumb();
            displayEditChosenTVShowSubmenu(chosenTVShow);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        returnToTVShowsListMenu = editTVShowFromInputFile(chosenTVShow.getPrimaryKey(), false);

                        if (returnToTVShowsListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 2:
                        returnToTVShowsListMenu = editTVShowFromInputFile(chosenTVShow.getPrimaryKey(), true);

                        if (returnToTVShowsListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 3:
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputTVShowsFilename(), DataType.TV_SHOW);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputTVShowsFilename(), DataType.TV_SHOW);
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
        
        return returnToTVShowsListMenu;
    }
    
    /**
     * Method which tries to edit existing chosen tv show by tv shows input file
     * @param existingTVShowPrimaryKey selected tv show primary key
     * @param fromBinary selects if input file will be binary or text
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv shows as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean editTVShowFromInputFile(PrimaryKey existingTVShowPrimaryKey, boolean fromBinary) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            boolean wasDataChanged = consoleUI.getTVEpisodesController().editTVShowBy(existingTVShowPrimaryKey, fromBinary);
             
            if (wasDataChanged == true) 
            {
                consoleUI.displayInfoMessage("Upravená data vybraného TV seriálu se úspěšně uložila");
                returnToParentMenu = true;
            }
            else 
            {
                consoleUI.displayInfoMessage("Nedošlo k žádné změně dat u vybraného TV seriálu");
            }
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
    
    /**
     * Method for handling printing released newest tv shows submenu
     * ({@link displayPrintReleasedNewestTVShowsSubmenu})
     */
    private void handleDisplayPrintReleasedNewestTVShowsSubmenu() 
    {
        consoleUI.addBreadcrumbItem("Nejnovější již vydané TV seriály");

        boolean returnToParentMenu = false;
        int choice;

        while (returnToParentMenu == false) 
        {
            List<TVShow> releasedNewestTVShows = consoleUI.getTVEpisodesController().getReleasedNewestTVShows();

            printReleasedNewestTVShows(releasedNewestTVShows);
            consoleUI.displayBreadcrumb();
            displayPrintReleasedNewestTVShowsSubmenu();

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        deleteChosenTVShows(releasedNewestTVShows);
                        break;
                    case 2:
                        handleDisplayDetailAboutTVShowSubmenu(releasedNewestTVShows);
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
     * Method for printing released newest tv shows as view/page.
     * @param releasedNewestTVShows list of released newest shows
     */
    private void printReleasedNewestTVShows(List<TVShow> releasedNewestTVShows) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, "NEJNOVĚJŠÍ JIŽ VYDANÉ TV SERIÁLY");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        
        System.out.println();
        
        System.out.println("Počet seriálů: " + releasedNewestTVShows.size());
        
        consoleUI.displayInfoMessage("Všechny TV epizody mají vždy nastavenou délku trvání");
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = TVShowInputOutput.ATTRIBUTE_NAME_LENGTH + 3;
        int statisticInformationsIndentationWidth = nameMaxLength + 28;
        
        int showWatchedEpisodesCount;
        int showUnwatchedEpisodesCount;
        int showEpisodesCount;
        
        String showUnwatchedEpisodesTotalDurationText;
        String showEpisodesTotalDurationText;
        
        Duration showWatchedEpisodesTotalDuration;
        Duration showUnwatchedEpisodesTotalDuration;
        Duration showEpisodesTotalDuration;
        
        float showAveragePercentageRating;
        
        for (TVShow show : releasedNewestTVShows) 
        {
            counter++;
            
            showAveragePercentageRating = consoleUI.getTVEpisodesController().
                    getAverageRatingOfAllReleasedEpisodesInTVShow(show.getPrimaryKey());
            
            showWatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowEpisodesCount(show.getPrimaryKey(), true);
            
            showUnwatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowEpisodesCount(show.getPrimaryKey(), false);
            
            showEpisodesCount = showUnwatchedEpisodesCount + showWatchedEpisodesCount;
            
            showWatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                    getTotalRuntimeOfAllReleasedEpisodesInTVShow(show.getPrimaryKey(), true);
            
            showUnwatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                    getTotalRuntimeOfAllReleasedEpisodesInTVShow(show.getPrimaryKey(), false);
            
            showEpisodesTotalDuration = showWatchedEpisodesTotalDuration.plus(showUnwatchedEpisodesTotalDuration);
            
            showEpisodesTotalDurationText =  String.format("%02d:%02d:%02d", showEpisodesTotalDuration.toHours(), 
                showEpisodesTotalDuration.toMinutesPart(), 
                showEpisodesTotalDuration.toSecondsPart());
        
            showUnwatchedEpisodesTotalDurationText =  String.format("%02d:%02d:%02d", showUnwatchedEpisodesTotalDuration.toHours(), 
                showUnwatchedEpisodesTotalDuration.toMinutesPart(), 
                showUnwatchedEpisodesTotalDuration.toSecondsPart());
            
                    
            System.out.println();
            System.out.println(String.format("%-8s%s %-" + nameMaxLength + "s%s %s", 
                    counter + ".", 
                    "Název:", show.getName(), 
                    "Datum vydání:", show.getReleaseDate().format(dateFormatter)));
              
            System.out.println(String.format("%" + statisticInformationsIndentationWidth + "s %-15d%s %-15d%-28s %d",
                    "Počet epizod:", showEpisodesCount,
                    "Počet nezhlédnutých epizod:", showUnwatchedEpisodesCount,
                    "Počet zhlédnutých epizod:", showWatchedEpisodesCount));
            
            System.out.println(String.format("%" + statisticInformationsIndentationWidth + "s %-15s%s %-15s%-28s %.2f %%",
                    "Délka epizod:", showEpisodesTotalDurationText,
                    "Délka nezhlédnutých epizod:", showUnwatchedEpisodesTotalDurationText,
                    "Průměrné hodnocení epizod:", showAveragePercentageRating));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling printing tv episodes, tv seasons and tv shows input/output files contents submenu
     * ({@link displayPrintDataInputOutputFilesContentsSubmenu})
     * @param dataType specifies for which data to display submenu and its choices
     */
    private void handlePrintDataInputOutputFilesContentsSubmenu(DataType dataType) 
    {
        String dataTextInputOutputFilename = null;
        String dataBinaryInputOutputFilename = null;
        
        switch (dataType) 
        {
            case TV_EPISODE:
                consoleUI.addBreadcrumbItem("Vypisování obsahů vstupních/výstupních souborů TV epizod");
                dataTextInputOutputFilename = DataStore.getTextInputOutputTVEpisodesFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryInputOutputTVEpisodesFilename();
                break;
            case TV_SEASON:
                consoleUI.addBreadcrumbItem("Vypisování obsahů vstupních/výstupních souborů TV sezón");
                dataTextInputOutputFilename = DataStore.getTextInputOutputTVSeasonsFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryInputOutputTVSeasonsFilename();
                break;
            case TV_SHOW:
                consoleUI.addBreadcrumbItem("Vypisování obsahů vstupních/výstupních souborů TV seriálů");
                dataTextInputOutputFilename = DataStore.getTextInputOutputTVShowsFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryInputOutputTVShowsFilename();
                break;
        }
        
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayPrintDataInputOutputFilesContentsSubmenu(dataType);
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        consoleUI.displayChosenDataFileContent(dataTextInputOutputFilename, dataType);
                        break;
                    case 2:
                        consoleUI.displayChosenDataFileContent(dataBinaryInputOutputFilename, dataType);
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
     * Method which tries to delete chosen tv shows from database.
     * Method will also delete chosen TV shows seasons and episodes.
     * @param chosenTVShows represents list of chosen tv shows to be deleted
     */
    private void deleteChosenTVShows(List<TVShow> chosenTVShows) 
    {        
        try 
        {
            consoleUI.getTVEpisodesController().deleteTVShows(chosenTVShows);
            consoleUI.displayInfoMessage("Vybrané TV seriály úspěšně smazány");
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }   
    }
}
