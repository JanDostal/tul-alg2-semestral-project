package ui;

import app.logic.controllers.DataSorting;
import app.logic.controllers.DataType;
import app.logic.controllers.TVEpisodesController;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import app.models.output.TVEpisodeOutput;
import app.models.output.TVShowOutput;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import org.apache.commons.mail.EmailException;
import utils.exceptions.DatabaseException;

/**
 * Represents a tv episodes UI submodule, which represents a UI section dealing with tv shows,
 * tv seasons and tv episodes.
 * TVEpisodesUI is communicating with ConsoleUI to use common methods and access tv episodes controller 
 * with scanner.
 * @author jan.dostal
 */
public class TVEpisodesUI 
{
    private final ConsoleUI consoleUI;
    
    /**
     * Creates a new instance of TVEpisodesUI.
     * Uses dependency injection to inject consoleUI ui module.
     * @param consoleUI existing instance of ConsoleUI. 
     * Can be used for using common methods from {@link ConsoleUI} class or access
     * tv episodes controller with scanner.
     */
    protected TVEpisodesUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
    
    /**
     * Method for starting this UI submodule (ConsoleUI class calls this method)
     */
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
     * Method for displaying this ui submodule submenu with choices for managing tv episodes, tv seasons 
     * and tv shows.
     */
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
        System.out.println(String.format("1. Načíst z textového souboru %s", DataStore.getTextInputTVShowsFilename()));
        System.out.println(String.format("2. Načíst z binárního souboru %s", DataStore.getBinaryInputTVShowsFilename()));
        System.out.println(String.format("3. Vypsat obsah textového souboru %s", DataStore.getTextInputTVShowsFilename()));
        System.out.println(String.format("4. Vypsat obsah binárního souboru %s", DataStore.getBinaryInputTVShowsFilename()));
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
        System.out.println("1. Upravit TV seriál pomocí vstupního textového souboru " + DataStore.getTextInputTVShowsFilename());
        System.out.println("2. Upravit TV seriál pomocí vstupního binárního souboru " + DataStore.getBinaryInputTVShowsFilename());
        System.out.println("3. Vypsat obsah vstupního textového souboru " + DataStore.getTextInputTVShowsFilename());
        System.out.println("4. Vypsat obsah vstupního binárního souboru " + DataStore.getBinaryInputTVShowsFilename());
                
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printed tv seasons of selected tv show
     * @param chosenTVShow selected tv show for which to print its tv seasons
     */
    private void displayPrintChosenTVShowSeasonsSubmenu(TVShow chosenTVShow) 
    {
        String menuName = "PODMENU TV SEZÓN SERIÁLU " + chosenTVShow.getName().toUpperCase();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Přidat TV sezóny ze vstupního souboru");
        System.out.println("2. Smazat aktuálně vypsané TV sezóny");
        System.out.println("3. Poslat e-mailem TV epizody");
        System.out.println("4. Vypsat nejdelší TV epizody tohoto seriálu");
        System.out.println("5. Vypsat nejoblíbenější TV epizody tohoto seriálu");
        System.out.println("6. Vypsat detail vybrané TV sezóny");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for adding tv seasons from tv seasons input file
     */
    private void displayLoadTVSeasonsFromInputFileSubmenu() 
    {
        String menuName = "PODMENU PŘIDÁVÁNÍ TV SEZÓN ZE VSTUPNÍHO SOUBORU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Načíst z textového souboru %s", DataStore.getTextInputTVSeasonsFilename()));
        System.out.println(String.format("2. Načíst z binárního souboru %s", DataStore.getBinaryInputTVSeasonsFilename()));
        System.out.println(String.format("3. Vypsat obsah textového souboru %s", DataStore.getTextInputTVSeasonsFilename()));
        System.out.println(String.format("4. Vypsat obsah binárního souboru %s", DataStore.getBinaryInputTVSeasonsFilename()));
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for sending tv episodes by e-mail
     */
    private void displaySendTVEpisodesByEmailSubmenu() 
    {
        String menuName = "PODMENU POSÍLÁNÍ TV EPIZOD E-MAILEM";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Poslat e-mailem nezhlédnuté TV epizody vybraného seriálu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing sorted tv episodes from
     * entire selected tv show
     * @param chosenTVShow selected tv show for which to print tv episodes
     * @param dataSorting sorting criteria which specifies how the printed tv episodes will be sorted
     */
    private void displayPrintTVShowSortedTVEpisodesSubmenu(TVShow chosenTVShow, DataSorting dataSorting) 
    {
        String menuName = null;
        
        switch (dataSorting) 
        {
            case LONGEST:
                menuName = "PODMENU NEJDELŠÍCH TV EPIZOD SERIÁLU " + chosenTVShow.getName().toUpperCase();
                break;
            case FAVORITE:
                menuName = "PODMENU NEJOBLÍBENĚJŠÍCH TV EPIZOD SERIÁLU " + chosenTVShow.getName().toUpperCase();
                break;
        }
                
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        
        switch (dataSorting) 
        {
            case LONGEST:
                System.out.println("1. Smazat aktuálně vypsané TV epizody");
                System.out.println("2. Vypsat detail vybrané TV epizody");
                break;
            case FAVORITE:
                System.out.println("1. Smazat aktuálně vypsané zhlédnuté TV epizody");
                System.out.println("2. Vypsat detail vybrané zhlédnuté TV epizody");
                break;
        }
        
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing detail about selected tv season
     * @param chosenTVSeason selected tv season for which to display detail informations
     */
    private void displayDetailAboutTVSeasonSubmenu(TVSeason chosenTVSeason) 
    {
        String menuName = "PODMENU DETAILU TV SEZÓNY " + chosenTVSeason.getOrderInTVShow();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat TV sezónu");
        System.out.println("2. Upravit TV sezónu");
        System.out.println("3. Vypsat TV epizody");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for editation of selected tv season
     * @param chosenTVSeason selected movie for which to display this submenu
     */
    private void displayEditChosenTVSeasonSubmenu(TVSeason chosenTVSeason) 
    {
        String menuName = "PODMENU EDITACE TV SEZÓNY " + chosenTVSeason.getOrderInTVShow();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Upravit TV sezónu pomocí vstupního textového souboru " + DataStore.getTextInputTVSeasonsFilename());
        System.out.println("2. Upravit TV sezónu pomocí vstupního binárního souboru " + DataStore.getBinaryInputTVSeasonsFilename());
        System.out.println("3. Vypsat obsah vstupního textového souboru " + DataStore.getTextInputTVSeasonsFilename());
        System.out.println("4. Vypsat obsah vstupního binárního souboru " + DataStore.getBinaryInputTVSeasonsFilename());
                
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing tv episodes from
     * entire selected tv season
     * @param chosenTVSeason selected tv season for which to print tv episodes
     */
    private void displayPrintChosenTVSeasonEpisodesSubmenu(TVSeason chosenTVSeason) 
    {
        String menuName = "PODMENU TV EPIZOD SEZÓNY " + chosenTVSeason.getOrderInTVShow();
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Přidat TV epizody ze vstupního souboru");
        System.out.println("2. Smazat aktuálně vypsané TV epizody");
        System.out.println("3. Vypsat nejdelší TV epizody této sezóny");
        System.out.println("4. Vypsat nejoblíbenější TV epizody této sezóny");
        System.out.println("5. Vypsat detail vybrané TV epizody");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for adding tv episodes from tv episodes input file
     */
    private void displayLoadTVEpisodesFromInputFileSubmenu() 
    {
        String menuName = "PODMENU PŘIDÁVÁNÍ TV EPIZOD ZE VSTUPNÍHO SOUBORU";
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Načíst z textového souboru %s", DataStore.getTextInputTVEpisodesFilename()));
        System.out.println(String.format("2. Načíst z binárního souboru %s", DataStore.getBinaryInputTVEpisodesFilename()));
        System.out.println(String.format("3. Vypsat obsah textového souboru %s", DataStore.getTextInputTVEpisodesFilename()));
        System.out.println(String.format("4. Vypsat obsah binárního souboru %s", DataStore.getBinaryInputTVEpisodesFilename()));
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing sorted tv episodes from
     * entire selected tv season
     * @param chosenTVSeason selected tv season for which to print tv episodes
     * @param dataSorting sorting criteria which specifies how the printed tv episodes will be sorted
     */
    private void displayPrintTVSeasonSortedTVEpisodesSubmenu(TVSeason chosenTVSeason, DataSorting dataSorting) 
    {
        String menuName = null;
        
        switch (dataSorting) 
        {
            case LONGEST:
                menuName = "PODMENU NEJDELŠÍCH TV EPIZOD SEZÓNY " + chosenTVSeason.getOrderInTVShow();
                break;
            case FAVORITE:
                menuName = "PODMENU NEJOBLÍBENĚJŠÍCH TV EPIZOD SEZÓNY " + chosenTVSeason.getOrderInTVShow();
                break;
        }
                
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        
        switch (dataSorting) 
        {
            case LONGEST:
                System.out.println("1. Smazat aktuálně vypsané TV epizody");
                System.out.println("2. Vypsat detail vybrané TV epizody");
                break;
            case FAVORITE:
                System.out.println("1. Smazat aktuálně vypsané zhlédnuté TV epizody");
                System.out.println("2. Vypsat detail vybrané zhlédnuté TV epizody");
                break;
        }
        
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for printing detail about selected tv episode
     * @param chosenTVEpisode selected tv episode for which to display detail informations
     * @param chosenTVEpisodeParentSeason selected tv episode parent season for which also display informations
     */
    private void displayDetailAboutTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason) 
    {
        String menuName = chosenTVEpisode.getWasWatched() == true ? 
                String.format("PODMENU DETAILU ZHLÉDNUTÉ TV EPIZODY %d V SEZÓNĚ %d", 
                        chosenTVEpisode.getOrderInTVShowSeason(), chosenTVEpisodeParentSeason.getOrderInTVShow()) : 
                String.format("PODMENU DETAILU NEZHLÉDNUTÉ TV EPIZODY %d V SEZÓNĚ %d", 
                        chosenTVEpisode.getOrderInTVShowSeason(), chosenTVEpisodeParentSeason.getOrderInTVShow());
                
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Smazat TV epizodu");
        System.out.println("2. Upravit TV epizodu");
        System.out.println("3. Ohodnotit TV epizodu");
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
    }
    
    /**
     * Method for displaying submenu with choices for editation of selected tv episode
     * @param chosenTVEpisode selected tv episode for which to display this submenu
     * @param chosenTVEpisodeParentSeason selected tv episode parent season for which display informations
     */
    private void displayEditChosenTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason) 
    {
        String menuName = chosenTVEpisode.getWasWatched() == true ? 
                String.format("PODMENU EDITACE ZHLÉDNUTÉ TV EPIZODY %d V SEZÓNĚ %d", 
                        chosenTVEpisode.getOrderInTVShowSeason(), chosenTVEpisodeParentSeason.getOrderInTVShow()) : 
                String.format("PODMENU EDITACE NEZHLÉDNUTÉ TV EPIZODY %d V SEZÓNĚ %d", 
                        chosenTVEpisode.getOrderInTVShowSeason(), chosenTVEpisodeParentSeason.getOrderInTVShow());
        
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Upravit TV epizodu pomocí vstupního textového souboru " + DataStore.getTextInputTVEpisodesFilename());
        System.out.println("2. Upravit TV epizodu pomocí vstupního binárního souboru " + DataStore.getBinaryInputTVEpisodesFilename());
        System.out.println("3. Vypsat obsah vstupního textového souboru " + DataStore.getTextInputTVEpisodesFilename());
        System.out.println("4. Vypsat obsah vstupního binárního souboru " + DataStore.getBinaryInputTVEpisodesFilename());
                
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
        String dataTextInputOutputFilename = null;
        String dataBinaryInputOutputFilename = null;
        
        switch (dataType) 
        {
            case TV_EPISODE:
                menuName = "PODMENU VYPISOVÁNÍ OBSAHŮ VSTUPNÍCH/VÝSTUPNÍCH SOUBORŮ TV EPIZOD";
                dataTextInputOutputFilename = DataStore.getTextOutputTVEpisodesFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryOutputTVEpisodesFilename();
                break;
            case TV_SEASON:
                menuName = "PODMENU VYPISOVÁNÍ OBSAHŮ VSTUPNÍCH/VÝSTUPNÍCH SOUBORŮ TV SEZÓN";
                dataTextInputOutputFilename = DataStore.getTextOutputTVSeasonsFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryOutputTVSeasonsFilename();
                break;
            case TV_SHOW:
                menuName = "PODMENU VYPISOVÁNÍ OBSAHŮ VSTUPNÍCH/VÝSTUPNÍCH SOUBORŮ TV SERIÁLŮ";
                dataTextInputOutputFilename = DataStore.getTextOutputTVShowsFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryOutputTVShowsFilename();
                break;
        }
                
        StringBuilder menuNameWithHorizontalLines = consoleUI.createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = consoleUI.createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
                
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Vypsat obsah textového souboru %s", dataTextInputOutputFilename));
        System.out.println(String.format("2. Vypsat obsah binárního souboru %s", dataBinaryInputOutputFilename));
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
        int nameMaxLength = TVShowOutput.ATTRIBUTE_NAME_LENGTH + 3;
        
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
        int nameMaxLength = TVShowOutput.ATTRIBUTE_NAME_LENGTH + 5;
        
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
        int nameMaxLength = TVShowOutput.ATTRIBUTE_NAME_LENGTH + 3;
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
                                handleDisplayPrintChosenTVShowSeasonsSubmenu(chosenTVShow);
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
     * Method for handling displaying printed tv seasons of entire chosen TV show submenu
     * ({@link displayPrintChosenTVShowSeasonsSubmenu})
     * @param chosenTVShow selected tv show from printed tv shows list/view.
     */
    private void handleDisplayPrintChosenTVShowSeasonsSubmenu(TVShow chosenTVShow) 
    {        
        consoleUI.addBreadcrumbItem(String.format("TV sezóny seriálu %s", chosenTVShow.getName()));
        
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            List<TVSeason> chosenTVShowSeasons = consoleUI.getTVEpisodesController().
                    getReleasedTVShowSeasonsByOrder(chosenTVShow.getPrimaryKey());
                              
            printChosenTVShowSeasons(chosenTVShowSeasons, chosenTVShow);
            
            consoleUI.displayInfoMessage("Při smazání TV sezón se smažou i příslušné TV epizody");
            
            consoleUI.displayBreadcrumb();
            displayPrintChosenTVShowSeasonsSubmenu(chosenTVShow);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        handleLoadTVSeasonsFromInputFileSubmenu(chosenTVShow);
                        break;
                    case 2:
                        deleteChosenTVSeasons(chosenTVShowSeasons);
                        break;
                    case 3:
                        handleSendTVEpisodesByEmailSubmenu(chosenTVShow);
                        break;
                    case 4:
                        handleDisplayPrintTVShowSortedTVEpisodesSubmenu(chosenTVShow, DataSorting.LONGEST);
                        break;
                    case 5:
                        handleDisplayPrintTVShowSortedTVEpisodesSubmenu(chosenTVShow, DataSorting.FAVORITE);
                        break;
                    case 6:
                        handleDisplayDetailAboutTVSeasonSubmenu(chosenTVShowSeasons);
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
     * Method which prints tv seasons from entire chosen tv show as view/page.
     * @param chosenTVShowSeasons list of tv seasons from selected tv show
     * @param chosenTVShow selected tv show from printed tv shows list/view.
     */
    private void printChosenTVShowSeasons(List<TVSeason> chosenTVShowSeasons, TVShow chosenTVShow) 
    {        
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("TV SEZÓNY SERIÁLU %s", chosenTVShow.getName().toUpperCase()));

        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
        int showWatchedEpisodesCount = consoleUI.getTVEpisodesController().
                getReleasedTVShowEpisodesCount(chosenTVShow.getPrimaryKey(), true);
        
        int showUnwatchedEpisodesCount = consoleUI.getTVEpisodesController().
                getReleasedTVShowEpisodesCount(chosenTVShow.getPrimaryKey(), false);
        
        int showEpisodesCount = showWatchedEpisodesCount + showUnwatchedEpisodesCount;
                
        Duration showWatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                getTotalRuntimeOfAllReleasedEpisodesInTVShow(chosenTVShow.getPrimaryKey(), true);
        
        Duration showUnwatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                getTotalRuntimeOfAllReleasedEpisodesInTVShow(chosenTVShow.getPrimaryKey(), false);
        
        Duration showEpisodesTotalDuration = showWatchedEpisodesTotalDuration.plus(showUnwatchedEpisodesTotalDuration);
        
        Duration showWatchedEpisodesAverageDuration = consoleUI.getTVEpisodesController().
                getAverageRuntimeOfAllReleasedEpisodesInTVShow(chosenTVShow.getPrimaryKey(), true);
        
        Duration showUnwatchedEpisodesAverageDuration = consoleUI.getTVEpisodesController().
                getAverageRuntimeOfAllReleasedEpisodesInTVShow(chosenTVShow.getPrimaryKey(), false);
        
        
        Duration showEpisodesAverageDuration;
        
        if (showEpisodesCount == 0) 
        {
            showEpisodesAverageDuration = Duration.ZERO;
        }
        else 
        {
            showEpisodesAverageDuration = showWatchedEpisodesAverageDuration.
                    multipliedBy(showWatchedEpisodesCount).
                    plus(showUnwatchedEpisodesAverageDuration.multipliedBy(showUnwatchedEpisodesCount)).
                    dividedBy(showEpisodesCount);
        }
        
        String showUnwatchedEpisodesTotalDurationText = String.format("%02d:%02d:%02d", 
                showUnwatchedEpisodesTotalDuration.toHours(), 
                showUnwatchedEpisodesTotalDuration.toMinutesPart(), 
                showUnwatchedEpisodesTotalDuration.toSecondsPart());
                
        String showEpisodesTotalDurationText = String.format("%02d:%02d:%02d", 
                showEpisodesTotalDuration.toHours(), 
                showEpisodesTotalDuration.toMinutesPart(), 
                showEpisodesTotalDuration.toSecondsPart());
        
        String showUnwatchedEpisodesAverageDurationText = String.format("%02d:%02d:%02d", 
                showUnwatchedEpisodesAverageDuration.toHours(), 
                showUnwatchedEpisodesAverageDuration.toMinutesPart(), 
                showUnwatchedEpisodesAverageDuration.toSecondsPart());
        
        String showEpisodesAverageDurationText = String.format("%02d:%02d:%02d", 
                showEpisodesAverageDuration.toHours(), 
                showEpisodesAverageDuration.toMinutesPart(), 
                showEpisodesAverageDuration.toSecondsPart());
        
        float showAveragePercentageRating = consoleUI.getTVEpisodesController().
                getAverageRatingOfAllReleasedEpisodesInTVShow(chosenTVShow.getPrimaryKey());
        
        System.out.println();
        System.out.println(heading);
        
        System.out.println();
        
        System.out.println(String.format("%-40s%d", "Počet sezón:", chosenTVShowSeasons.size()));
        
        System.out.println();
        
        System.out.println(String.format("%-40s%d", "Počet epizod:", showEpisodesCount));
        System.out.println(String.format("%-40s%s", "Délka epizod:", showEpisodesTotalDurationText));
        System.out.println(String.format("%-40s%s", "Průměrná délka epizod:", showEpisodesAverageDurationText));
        
        System.out.println();
        
        System.out.println(String.format("%-40s%d", "Počet nezhlédnutých epizod:", showUnwatchedEpisodesCount));
        System.out.println(String.format("%-40s%s", "Délka nezhlédnutých epizod:", showUnwatchedEpisodesTotalDurationText));
        System.out.println(String.format("%-40s%s", "Průměrná délka nezhlédnutých epizod:", showUnwatchedEpisodesAverageDurationText));
        
        System.out.println();
        
        System.out.println(String.format("%-40s%d", "Počet zhlédnutých epizod:", showWatchedEpisodesCount));
        System.out.println(String.format("%-40s%.2f %%", "Průměrné hodnocení epizod:", showAveragePercentageRating));
        
        consoleUI.displayInfoMessage("Všechny TV epizody mají vždy nastavenou délku trvání");
        System.out.println();
        System.out.println(dividingLine);
        
        int seasonWatchedEpisodesCount;
        int seasonUnwatchedEpisodesCount;
        int seasonEpisodesCount;
        
        String seasonUnwatchedEpisodesTotalDurationText;
        String seasonEpisodesTotalDurationText;
        
        Duration seasonWatchedEpisodesTotalDuration;
        Duration seasonUnwatchedEpisodesTotalDuration;
        Duration seasonEpisodesTotalDuration;
        
        float seasonAveragePercentageRating;
        
        int counter = 0;
        
        for (TVSeason tvSeason : chosenTVShowSeasons) 
        {
            counter++;
            
            seasonAveragePercentageRating = consoleUI.getTVEpisodesController().
                    getAverageRatingOfAllReleasedEpisodesInTVShowSeason(tvSeason.getPrimaryKey());
            
            seasonWatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowSeasonEpisodesCount(tvSeason.getPrimaryKey(), true);
            
            seasonUnwatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowSeasonEpisodesCount(tvSeason.getPrimaryKey(), false);
            
            seasonEpisodesCount = seasonUnwatchedEpisodesCount + seasonWatchedEpisodesCount;
            
            
            seasonWatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                    getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(tvSeason.getPrimaryKey(), true);
            
            seasonUnwatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                    getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(tvSeason.getPrimaryKey(), false);
            
            seasonEpisodesTotalDuration = seasonWatchedEpisodesTotalDuration.plus(seasonUnwatchedEpisodesTotalDuration);
            
            seasonEpisodesTotalDurationText =  String.format("%02d:%02d:%02d", 
                    seasonEpisodesTotalDuration.toHours(), 
                    seasonEpisodesTotalDuration.toMinutesPart(), 
                    seasonEpisodesTotalDuration.toSecondsPart());
        
            seasonUnwatchedEpisodesTotalDurationText =  String.format("%02d:%02d:%02d", 
                    seasonUnwatchedEpisodesTotalDuration.toHours(), 
                    seasonUnwatchedEpisodesTotalDuration.toMinutesPart(), 
                    seasonUnwatchedEpisodesTotalDuration.toSecondsPart());
            
            System.out.println();
            
            System.out.println(String.format("%-8s%s %-20d%s %-15d%s %-15d%-28s %d", 
                    counter + ".",
                    "Číslo sezóny:", tvSeason.getOrderInTVShow(),
                    "Počet epizod:", seasonEpisodesCount,
                    "Počet nezhlédnutých epizod:", seasonUnwatchedEpisodesCount,
                    "Počet zhlédnutých epizod:", seasonWatchedEpisodesCount));
                                           
            System.out.println(String.format("%55s %-15s%s %-15s%-28s %.2f %%",
                    "Délka epizod:", seasonEpisodesTotalDurationText,
                    "Délka nezhlédnutých epizod:", seasonUnwatchedEpisodesTotalDurationText,
                    "Průměrné hodnocení epizod:", seasonAveragePercentageRating));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling loading tv seasons from input file submenu
     * ({@link displayLoadTVSeasonsFromInputFileSubmenu})
     * @param chosenTVShow selected tv show to which tv seasons will be added
     */
    private void handleLoadTVSeasonsFromInputFileSubmenu(TVShow chosenTVShow) 
    {
        consoleUI.addBreadcrumbItem("Přidávání TV sezón ze vstupního souboru");
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayLoadTVSeasonsFromInputFileSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        loadTVSeasonsFromInputFile(chosenTVShow.getPrimaryKey(), false);
                        break;
                    case 2:
                        loadTVSeasonsFromInputFile(chosenTVShow.getPrimaryKey(), true);
                        break;
                    case 3:
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputTVSeasonsFilename(), DataType.TV_SEASON);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputTVSeasonsFilename(), DataType.TV_SEASON);
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
     * Method which tries to load tv seasons from tv seasons input file
     * @param fromBinary selects if input file is binary or text
     * @param tvShowPrimaryKey primary key of selected tv show to which tv seasons will be added
     */
    private void loadTVSeasonsFromInputFile(PrimaryKey tvShowPrimaryKey, boolean fromBinary) 
    {        
        try 
        {
            StringBuilder infoMessage = consoleUI.getTVEpisodesController().addTVSeasonsFrom(tvShowPrimaryKey, fromBinary);
            consoleUI.displayInfoMessage(infoMessage.toString());
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    /**
     * Method which tries to delete chosen tv seasons from database
     * Method will also delete chosen TV seasons episodes
     * @param chosenTVSeasons represents list of chosen tv seasons to be deleted
     */
    private void deleteChosenTVSeasons(List<TVSeason> chosenTVSeasons) 
    {        
        try 
        {
            consoleUI.getTVEpisodesController().deleteTVSeasons(chosenTVSeasons);
            consoleUI.displayInfoMessage("Vybrané TV sezóny úspěšně smazány");
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }   
    }
    
    /**
     * Method for handling sending tv episodes by email submenu
     * ({@link displaySendTVEpisodesByEmailSubmenu})
     * @param chosenTVShow selected tv Show, which TV episodes should be sent
     */
    private void handleSendTVEpisodesByEmailSubmenu(TVShow chosenTVShow) 
    {
        consoleUI.addBreadcrumbItem("Posílání TV epizod e-mailem");
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displaySendTVEpisodesByEmailSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        sendUnwatchedTVEpisodesInTVShowByEmail(chosenTVShow.getPrimaryKey());
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
     * Method which tries to send unwatched tv episodes of selected TV show by email
     * @param tvShowPrimaryKey chosen tv show primary key, which tv episodes should be sent
     */
    private void sendUnwatchedTVEpisodesInTVShowByEmail(PrimaryKey tvShowPrimaryKey) 
    {
        String email = consoleUI.loadEmailFromUser();
        
        try 
        {
            consoleUI.getTVEpisodesController().sendUnwatchedEpisodesWithHyperlinksInTVShowByEmail(email, tvShowPrimaryKey);
            consoleUI.displayInfoMessage("E-mail byl úspešně odeslán");
        }
        catch (EmailException | DatabaseException ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    /**
     * Method for handling printed sorted tv episodes from entire selected tv show submenu
     * ({@link displayPrintTVShowSortedTVEpisodesSubmenu}).
     * @param chosenTVShow selected TV show, which sorted TV episodes should be printed
     * @param dataSorting sorting criteria specifying how the TV episodes should be sorted
     */
    private void handleDisplayPrintTVShowSortedTVEpisodesSubmenu(TVShow chosenTVShow, DataSorting dataSorting) 
    {
        switch (dataSorting) 
        {
            case LONGEST:
                consoleUI.addBreadcrumbItem("Nejdelší TV epizody seriálu " + chosenTVShow.getName());
                break;
            case FAVORITE:
                consoleUI.addBreadcrumbItem("Nejoblíbenější TV epizody seriálu " + chosenTVShow.getName());
                break;
        }
        
        boolean returnToParentMenu = false;
        int choice;

        while (returnToParentMenu == false) 
        {
            List<TVEpisode> tvShowSortedTVEpisodes = null;
            
            switch (dataSorting) 
            {
                case LONGEST:
                    tvShowSortedTVEpisodes = consoleUI.getTVEpisodesController().
                            getReleasedTVShowLongestEpisodes(chosenTVShow.getPrimaryKey());
                    break;
                case FAVORITE:
                    tvShowSortedTVEpisodes = consoleUI.getTVEpisodesController().
                            getReleasedTVShowFavoriteTVEpisodes(chosenTVShow.getPrimaryKey());
                    break;
            }

            printTVShowSortedTVEpisodes(tvShowSortedTVEpisodes, chosenTVShow, dataSorting);
            consoleUI.displayBreadcrumb();
            displayPrintTVShowSortedTVEpisodesSubmenu(chosenTVShow, dataSorting);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        deleteChosenTVEpisodes(tvShowSortedTVEpisodes);
                        break;
                    case 2:
                        handleDisplayDetailAboutTVEpisodeSubmenu(tvShowSortedTVEpisodes);
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
     * Method prints sorted tv episodes from entire selected tv show as view/page.
     * @param tvShowSortedTVEpisodes list of sorted tv episodes of selected tv show
     * @param chosenTVShow selected TV show, which sorted TV episodes should be printed
     * @param dataSorting sorting criteria specifying how the TV episodes should be sorted
     */
    private void printTVShowSortedTVEpisodes(List<TVEpisode> tvShowSortedTVEpisodes, TVShow chosenTVShow, DataSorting dataSorting) 
    {
        StringBuilder heading = null;
        
        switch (dataSorting) 
        {
            case LONGEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, "NEJDELŠÍ TV EPIZODY SERIÁLU " + chosenTVShow.getName().toUpperCase());
                break;
            case FAVORITE:
                heading = consoleUI.createHeadingWithHorizontalLines(20, "NEJOBLÍBENĚJŠÍ TV EPIZODY SERIÁLU " + chosenTVShow.getName().toUpperCase());
                break;
        }
                
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%s %d", "Počet epizod:", tvShowSortedTVEpisodes.size()));
        
        consoleUI.displayInfoMessage("Všechny TV epizody mají vždy nastavenou délku trvání");
        System.out.println();
        System.out.println(dividingLine);
        
        int nameMaxLength = TVEpisodeOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        String nameText;
        String percentageRatingText;
        TVSeason episodeParentSeason;
        
        int counter = 0;
        
        for (TVEpisode tvEpisode : tvShowSortedTVEpisodes) 
        {
            counter++;
            
            nameText = tvEpisode.getName() == null ? "Neznámý" : tvEpisode.getName();
            
            durationText = String.format("%02d:%02d:%02d", 
                    tvEpisode.getRuntime().toHours(), 
                    tvEpisode.getRuntime().toMinutesPart(), 
                    tvEpisode.getRuntime().toSecondsPart());
            
            percentageRatingText = tvEpisode.getWasWatched() == true ? tvEpisode.getPercentageRating() + " %" : "Nehodnoceno";
            
            episodeParentSeason = consoleUI.getTVEpisodesController().getTVSeasonDetail(tvEpisode.getTVSeasonForeignKey());
            
            System.out.println();
            System.out.println(String.format("%-9s%s %-8d%s %-8d%s %-" + nameMaxLength + "s%s %-12s%s %s",
                    counter + ".",
                    "Číslo sezóny:", episodeParentSeason.getOrderInTVShow(),
                    "Číslo epizody:", tvEpisode.getOrderInTVShowSeason(),
                    "Název:", nameText, 
                    "Délka:", durationText,
                    "Hodnocení:", percentageRatingText));
        }
        
        System.out.println();
        System.out.println(dividingLine);       
    }
    
    /**
     * Method for handling displaying detail about chosen tv season submenu
     * ({@link displayDetailAboutTVSeasonSubmenu})
     * @param chosenTVSeasons list of chosen tv seasons from which chosen tv season will
     * be selected by tv season order from printed tv seasons view/list (not order in TV show).
     */
    private void handleDisplayDetailAboutTVSeasonSubmenu(List<TVSeason> chosenTVSeasons) 
    {
        try 
        {
            int tvSeasonOrderFromList = loadChosenTVSeasonFromUser();            
            TVSeason chosenTVSeason = chosenTVSeasons.get(tvSeasonOrderFromList - 1);

            consoleUI.addBreadcrumbItem(String.format("Detail TV sezóny %d", chosenTVSeason.getOrderInTVShow()));

            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                printTVSeasonDetail(chosenTVSeason, false);

                consoleUI.displayInfoMessage(String.format("Při smazání TV sezóny"
                        + " nebo při reálné úpravě dat TV sezóny "
                        + "dojde k návratu zpátky do daného výpisu s TV sezónami"));

                consoleUI.displayInfoMessage(String.format("Při smazání TV sezóny se smažou"
                        + " i příslušné TV epizody"));

                consoleUI.displayBreadcrumb();
                displayDetailAboutTVSeasonSubmenu(chosenTVSeason);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            returnToParentMenu = deleteChosenTVSeason(chosenTVSeason.getPrimaryKey());

                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }

                            break;
                        case 2:
                            returnToParentMenu = handleDisplayEditChosenTVSeasonSubmenu(chosenTVSeason);

                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }

                            break;
                        case 3:
                            handleDisplayPrintChosenTVSeasonEpisodesSubmenu(chosenTVSeason);
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
            consoleUI.displayErrorMessage("Pořadové číslo sezóny musí být číslo");
            consoleUI.advanceToNextInput();
        } 
        catch (IndexOutOfBoundsException ex) 
        {
            consoleUI.displayErrorMessage("Pořadové číslo sezóny neodpovídá žádné z TV sezón");
            consoleUI.advanceToNextInput();
        }
    }
    
    /**
     * Method which prompts user for tv season order number from printed 
     * tv seasons view/list (not order in TV show)
     * and then returns it.
     * @return int value representing tv season order from tv seasons view. (not order in TV show)
     */
    private int loadChosenTVSeasonFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo TV sezóny: ");
        return consoleUI.getScanner().nextInt();
    }
    
    /**
     * Method which prints detail informations about chosen tv season as view/page.
     * @param chosenTVSeason represents chosen tv season from printed tv seasons list/view.
     * @param isInEditMode selects if tv season informations are displayed in tv season detail mode or edit mode
     * ({@link displayDetailAboutTVSeasonSubmenu}, {@link displayEditChosenTVSeasonSubmenu})
     */
    private void printTVSeasonDetail(TVSeason chosenTVSeason, boolean isInEditMode) 
    {        
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("%s TV SEZÓNY %d", 
                        isInEditMode == true ? "EDITACE" : "DETAIL",
                        chosenTVSeason.getOrderInTVShow()));
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(dividingLine);
        System.out.println();
                        
        System.out.println(String.format("%-40s%d", "Identifikátor:", chosenTVSeason.getPrimaryKey().getId()));
        System.out.println(String.format("%-40s%d", "Identifikátor seriálu:", chosenTVSeason.getTVShowForeignKey().getId()));
        System.out.println(String.format("%-40s%d", "Číslo sezóny v rámci seriálu:", chosenTVSeason.getOrderInTVShow()));
                
        System.out.println();
        System.out.println(dividingLine);  
    }
    
    /**
     * Method which tries to delete chosen tv season from database.
     * Method will also delete chosen tv season episodes
     * @param tvSeasonPrimaryKey represents chosen tv season primary key
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv seasons as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean deleteChosenTVSeason(PrimaryKey tvSeasonPrimaryKey) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            consoleUI.getTVEpisodesController().deleteTVSeasonBy(tvSeasonPrimaryKey);
            consoleUI.displayInfoMessage("Vybraná TV sezóna úspěšně smazána");
            returnToParentMenu = true;
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
    
    /**
     * Method for handling displaying editation of chosen tv season submenu
     * ({@link displayEditChosenTVSeasonSubmenu})
     * @param chosenTVSeason selected tv season from printed tv seasons list/view.
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv season as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean handleDisplayEditChosenTVSeasonSubmenu(TVSeason chosenTVSeason) 
    {
        consoleUI.addBreadcrumbItem(String.format("Editace TV sezóny %d", 
                chosenTVSeason.getOrderInTVShow()));

        boolean returnToParentMenu = false;
        boolean returnToTVSeasonsListMenu = false;
        int choice;

        while (returnToParentMenu == false && returnToTVSeasonsListMenu == false) 
        {
            printTVSeasonDetail(chosenTVSeason, true);
                
            consoleUI.displayInfoMessage(String.format("Při úpravě TV sezóny se používají stejné"
                    + " vstupní soubory jako u přidávání TV sezón"));
            
            consoleUI.displayInfoMessage(String.format("Pro jednoznačnost je nutné mít v daném souboru pouze jednu TV sezónu"));

            consoleUI.displayInfoMessage(String.format("Při úpravě TV sezóny se používá v " 
                    + "souboru úplně totožná struktura dat jako u přidávání TV sezón"));
            
            consoleUI.displayBreadcrumb();
            displayEditChosenTVSeasonSubmenu(chosenTVSeason);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        returnToTVSeasonsListMenu = editTVSeasonFromInputFile(chosenTVSeason.getPrimaryKey(), 
                                chosenTVSeason.getTVShowForeignKey(), false);

                        if (returnToTVSeasonsListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 2:
                        returnToTVSeasonsListMenu = editTVSeasonFromInputFile(chosenTVSeason.getPrimaryKey(), 
                                chosenTVSeason.getTVShowForeignKey(), true);

                        if (returnToTVSeasonsListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 3:
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputTVSeasonsFilename(), DataType.TV_SEASON);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputTVSeasonsFilename(), DataType.TV_SEASON);
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
        
        return returnToTVSeasonsListMenu;
    }
    
    /**
     * Method which tries to edit existing chosen tv season by tv seasons input file
     * @param existingTVSeasonPrimaryKey selected tv season primary key
     * @param existingTVShowPrimaryKey primary key of tv season parent show, 
     * which will be used to link edited tv season from file to correct tv show
     * @param fromBinary selects if input file will be binary or text
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv seasons as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean editTVSeasonFromInputFile(PrimaryKey existingTVSeasonPrimaryKey, PrimaryKey existingTVShowPrimaryKey ,boolean fromBinary) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            boolean wasDataChanged = consoleUI.getTVEpisodesController().editTVSeasonBy(existingTVSeasonPrimaryKey, 
                    existingTVShowPrimaryKey, fromBinary);
             
            if (wasDataChanged == true) 
            {
                consoleUI.displayInfoMessage("Upravená data vybrané TV sezóny se úspěšně uložila");
                returnToParentMenu = true;
            }
            else 
            {
                consoleUI.displayInfoMessage("Nedošlo k žádné změně dat u vybrané TV sezóny");
            }
        }
        catch (Exception ex)
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
    
    /**
     * Method for handling displaying printed tv episodes of entire chosen TV season submenu
     * ({@link displayPrintChosenTVSeasonEpisodesSubmenu})
     * @param chosenTVSeason selected tv season from printed tv seasons list/view.
     */
    private void handleDisplayPrintChosenTVSeasonEpisodesSubmenu(TVSeason chosenTVSeason) 
    {        
        consoleUI.addBreadcrumbItem(String.format("TV epizody sezóny %d", chosenTVSeason.getOrderInTVShow()));
        
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            List<TVEpisode> chosenTVSeasonEpisodes = consoleUI.getTVEpisodesController().
                    getReleasedTVShowSeasonEpisodesByOrder(chosenTVSeason.getPrimaryKey());
                              
            printChosenTVSeasonEpisodes(chosenTVSeasonEpisodes, chosenTVSeason);
                        
            consoleUI.displayBreadcrumb();
            displayPrintChosenTVSeasonEpisodesSubmenu(chosenTVSeason);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        handleLoadTVEpisodesFromInputFileSubmenu(chosenTVSeason);
                        break;
                    case 2:
                        deleteChosenTVEpisodes(chosenTVSeasonEpisodes);
                        break;
                    case 3:
                        handleDisplayPrintTVSeasonSortedTVEpisodesSubmenu(chosenTVSeason, DataSorting.LONGEST);
                        break;
                    case 4:
                        handleDisplayPrintTVSeasonSortedTVEpisodesSubmenu(chosenTVSeason, DataSorting.FAVORITE);
                        break;
                    case 5:
                        handleDisplayDetailAboutTVEpisodeSubmenu(chosenTVSeasonEpisodes);
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
     * Method which prints tv episodes from entire chosen tv season as view/page.
     * @param chosenTVSeasonEpisodes list of tv episodes from selected tv season.
     * @param chosenTVSeason selected tv season from printed tv seasons list/view.
     */
    private void printChosenTVSeasonEpisodes(List<TVEpisode> chosenTVSeasonEpisodes, TVSeason chosenTVSeason) 
    {        
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("TV EPIZODY SEZÓNY %d", chosenTVSeason.getOrderInTVShow()));

        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
        int seasonWatchedEpisodesCount = consoleUI.getTVEpisodesController().
                getReleasedTVShowSeasonEpisodesCount(chosenTVSeason.getPrimaryKey(), true);
        
        int seasonUnwatchedEpisodesCount = consoleUI.getTVEpisodesController().
                getReleasedTVShowSeasonEpisodesCount(chosenTVSeason.getPrimaryKey(), false);
                
        Duration seasonWatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(chosenTVSeason.getPrimaryKey(), true);
        
        Duration seasonUnwatchedEpisodesTotalDuration = consoleUI.getTVEpisodesController().
                getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(chosenTVSeason.getPrimaryKey(), false);
        
        Duration seasonEpisodesTotalDuration = seasonWatchedEpisodesTotalDuration.plus(seasonUnwatchedEpisodesTotalDuration);
        
        Duration seasonWatchedEpisodesAverageDuration = consoleUI.getTVEpisodesController().
                getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason(chosenTVSeason.getPrimaryKey(), true);
        
        Duration seasonUnwatchedEpisodesAverageDuration = consoleUI.getTVEpisodesController().
                getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason(chosenTVSeason.getPrimaryKey(), false);
        
        
        Duration seasonEpisodesAverageDuration;
        
        if (chosenTVSeasonEpisodes.isEmpty()) 
        {
            seasonEpisodesAverageDuration = Duration.ZERO;
        }
        else 
        {
            seasonEpisodesAverageDuration = seasonWatchedEpisodesAverageDuration.
                    multipliedBy(seasonWatchedEpisodesCount).
                    plus(seasonUnwatchedEpisodesAverageDuration.multipliedBy(seasonUnwatchedEpisodesCount)).
                    dividedBy(chosenTVSeasonEpisodes.size());
        }
        
        String seasonUnwatchedEpisodesTotalDurationText = String.format("%02d:%02d:%02d", 
                seasonUnwatchedEpisodesTotalDuration.toHours(), 
                seasonUnwatchedEpisodesTotalDuration.toMinutesPart(), 
                seasonUnwatchedEpisodesTotalDuration.toSecondsPart());
                
        String seasonEpisodesTotalDurationText = String.format("%02d:%02d:%02d", 
                seasonEpisodesTotalDuration.toHours(), 
                seasonEpisodesTotalDuration.toMinutesPart(), 
                seasonEpisodesTotalDuration.toSecondsPart());
        
        String seasonUnwatchedEpisodesAverageDurationText = String.format("%02d:%02d:%02d", 
                seasonUnwatchedEpisodesAverageDuration.toHours(), 
                seasonUnwatchedEpisodesAverageDuration.toMinutesPart(), 
                seasonUnwatchedEpisodesAverageDuration.toSecondsPart());
        
        String seasonEpisodesAverageDurationText = String.format("%02d:%02d:%02d", 
                seasonEpisodesAverageDuration.toHours(), 
                seasonEpisodesAverageDuration.toMinutesPart(), 
                seasonEpisodesAverageDuration.toSecondsPart());
        
        float seasonAveragePercentageRating = consoleUI.getTVEpisodesController().
                getAverageRatingOfAllReleasedEpisodesInTVShowSeason(chosenTVSeason.getPrimaryKey());
        
        System.out.println();
        System.out.println(heading);
                
        System.out.println();
        
        System.out.println(String.format("%-40s%d", "Počet epizod:", chosenTVSeasonEpisodes.size()));
        System.out.println(String.format("%-40s%s", "Délka epizod:", seasonEpisodesTotalDurationText));
        System.out.println(String.format("%-40s%s", "Průměrná délka epizod:", seasonEpisodesAverageDurationText));
        
        System.out.println();
        
        System.out.println(String.format("%-40s%d", "Počet nezhlédnutých epizod:", seasonUnwatchedEpisodesCount));
        System.out.println(String.format("%-40s%s", "Délka nezhlédnutých epizod:", seasonUnwatchedEpisodesTotalDurationText));
        System.out.println(String.format("%-40s%s", "Průměrná délka nezhlédnutých epizod:", seasonUnwatchedEpisodesAverageDurationText));
        
        System.out.println();
        
        System.out.println(String.format("%-40s%d", "Počet zhlédnutých epizod:", seasonWatchedEpisodesCount));
        System.out.println(String.format("%-40s%.2f %%", "Průměrné hodnocení epizod:", seasonAveragePercentageRating));
        
        consoleUI.displayInfoMessage("Všechny TV epizody mají vždy nastavenou délku trvání");
        System.out.println();
        System.out.println(dividingLine);
        
        int nameMaxLength = TVEpisodeOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        String nameText;
        String percentageRatingText;
        
        int counter = 0;
        
        for (TVEpisode tvEpisode : chosenTVSeasonEpisodes) 
        {
            counter++;
            
            nameText = tvEpisode.getName() == null ? "Neznámý" : tvEpisode.getName();
            
            durationText = String.format("%02d:%02d:%02d", 
                    tvEpisode.getRuntime().toHours(), 
                    tvEpisode.getRuntime().toMinutesPart(), 
                    tvEpisode.getRuntime().toSecondsPart());
            
            percentageRatingText = tvEpisode.getWasWatched() == true ? tvEpisode.getPercentageRating() + " %" : "Nehodnoceno";
            
            System.out.println();
            System.out.println(String.format("%-8s%s %-8d%s %-" + nameMaxLength + "s%s %-12s%s %s",
                    counter + ".",
                    "Číslo epizody:", tvEpisode.getOrderInTVShowSeason(),
                    "Název:", nameText, 
                    "Délka:", durationText,
                    "Hodnocení:", percentageRatingText));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
    /**
     * Method for handling loading tv episodes from input file submenu
     * ({@link displayLoadTVEpisodesFromInputFileSubmenu}).
     * @param chosenTVSeason selected tv season to which tv episodes will be added
     */
    private void handleLoadTVEpisodesFromInputFileSubmenu(TVSeason chosenTVSeason) 
    {
        consoleUI.addBreadcrumbItem("Přidávání TV epizod ze vstupního souboru");
        boolean returnToParentMenu = false;
        int choice;
        
        while (returnToParentMenu == false) 
        {
            consoleUI.displayBreadcrumb();
            displayLoadTVEpisodesFromInputFileSubmenu();
            
            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();
                
                switch (choice) 
                {
                    case 1:
                        loadTVEpisodesFromInputFile(chosenTVSeason.getPrimaryKey(), false);
                        break;
                    case 2:
                        loadTVEpisodesFromInputFile(chosenTVSeason.getPrimaryKey(), true);
                        break;
                    case 3:
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputTVEpisodesFilename(), DataType.TV_EPISODE);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputTVEpisodesFilename(), DataType.TV_EPISODE);
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
     * Method which tries to load tv episodes from tv episodes input file
     * @param fromBinary selects if input file is binary or text
     * @param tvSeasonPrimaryKey primary key of selected tv season to which tv episodes will be added
     */
    private void loadTVEpisodesFromInputFile(PrimaryKey tvSeasonPrimaryKey, boolean fromBinary) 
    {        
        try 
        {
            StringBuilder infoMessage = consoleUI.getTVEpisodesController().addTVEpisodesFrom(tvSeasonPrimaryKey, fromBinary);
            consoleUI.displayInfoMessage(infoMessage.toString());
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }        
    }
    
    /**
     * Method which tries to delete chosen tv episodes from database.
     * @param chosenTVEpisodes list of chosen tv episodes
     */
    private void deleteChosenTVEpisodes(List<TVEpisode> chosenTVEpisodes) 
    {        
        try 
        {
            consoleUI.getTVEpisodesController().deleteTVEpisodes(chosenTVEpisodes);
            consoleUI.displayInfoMessage("Vybrané TV epizody úspěšně smazány");
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }   
    }
    
    /**
     * Method for handling printed sorted tv episodes from entire selected tv season submenu
     * ({@link displayPrintTVSeasonSortedTVEpisodesSubmenu})
     * @param chosenTVSeason selected TV season, which sorted TV episodes should be printed
     * @param dataSorting sorting criteria specifying how the TV episodes should be sorted
     */
    private void handleDisplayPrintTVSeasonSortedTVEpisodesSubmenu(TVSeason chosenTVSeason, DataSorting dataSorting) 
    {
        switch (dataSorting) 
        {
            case LONGEST:
                consoleUI.addBreadcrumbItem("Nejdelší TV epizody sezóny " + chosenTVSeason.getOrderInTVShow());
                break;
            case FAVORITE:
                consoleUI.addBreadcrumbItem("Nejoblíbenější TV epizody sezóny " + chosenTVSeason.getOrderInTVShow());
                break;
        }
        
        boolean returnToParentMenu = false;
        int choice;

        while (returnToParentMenu == false) 
        {
            List<TVEpisode> tvSeasonSortedTVEpisodes = null;
            
            switch (dataSorting) 
            {
                case LONGEST:
                    tvSeasonSortedTVEpisodes = consoleUI.getTVEpisodesController().
                            getReleasedTVShowSeasonLongestEpisodes(chosenTVSeason.getPrimaryKey());
                    break;
                case FAVORITE:
                    tvSeasonSortedTVEpisodes = consoleUI.getTVEpisodesController().
                            getReleasedTVShowSeasonFavoriteTVEpisodes(chosenTVSeason.getPrimaryKey());
                    break;
            }

            printTVSeasonSortedTVEpisodes(tvSeasonSortedTVEpisodes, chosenTVSeason, dataSorting);
            consoleUI.displayBreadcrumb();
            displayPrintTVSeasonSortedTVEpisodesSubmenu(chosenTVSeason, dataSorting);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        deleteChosenTVEpisodes(tvSeasonSortedTVEpisodes);
                        break;
                    case 2:
                        handleDisplayDetailAboutTVEpisodeSubmenu(tvSeasonSortedTVEpisodes);
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
     * Method prints sorted tv episodes from entire selected tv season as view/page.
     * @param tvSeasonSortedTVEpisodes list of sorted tv episodes of selected tv season
     * @param chosenTVSeason selected TV season, which sorted TV episodes should be printed
     * @param dataSorting sorting criteria specifying how the TV episodes should be sorted
     */
    private void printTVSeasonSortedTVEpisodes(List<TVEpisode> tvSeasonSortedTVEpisodes, TVSeason chosenTVSeason, DataSorting dataSorting) 
    {
        StringBuilder heading = null;
        
        switch (dataSorting) 
        {
            case LONGEST:
                heading = consoleUI.createHeadingWithHorizontalLines(20, "NEJDELŠÍ TV EPIZODY SEZÓNY " + chosenTVSeason.getOrderInTVShow());
                break;
            case FAVORITE:
                heading = consoleUI.createHeadingWithHorizontalLines(20, "NEJOBLÍBENĚJŠÍ TV EPIZODY SEZÓNY " + chosenTVSeason.getOrderInTVShow());
                break;
        }
                
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                        
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(String.format("%s %d", "Počet epizod:", tvSeasonSortedTVEpisodes.size()));
        
        consoleUI.displayInfoMessage("Všechny TV epizody mají vždy nastavenou délku trvání");
        System.out.println();
        System.out.println(dividingLine);
        
        int nameMaxLength = TVEpisodeOutput.ATTRIBUTE_NAME_LENGTH + 3;
        String durationText;
        String nameText;
        String percentageRatingText;
        
        int counter = 0;
        
        for (TVEpisode tvEpisode : tvSeasonSortedTVEpisodes) 
        {
            counter++;
            
            nameText = tvEpisode.getName() == null ? "Neznámý" : tvEpisode.getName();
            
            durationText = String.format("%02d:%02d:%02d", 
                    tvEpisode.getRuntime().toHours(), 
                    tvEpisode.getRuntime().toMinutesPart(), 
                    tvEpisode.getRuntime().toSecondsPart());
            
            percentageRatingText = tvEpisode.getWasWatched() == true ? tvEpisode.getPercentageRating() + " %" : "Nehodnoceno";
            
            System.out.println();
            System.out.println(String.format("%-8s%s %-8d%s %-" + nameMaxLength + "s%s %-12s%s %s",
                    counter + ".",
                    "Číslo epizody:", tvEpisode.getOrderInTVShowSeason(),
                    "Název:", nameText, 
                    "Délka:", durationText,
                    "Hodnocení:", percentageRatingText));
        }
        
        System.out.println();
        System.out.println(dividingLine);       
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
        int nameMaxLength = TVShowOutput.ATTRIBUTE_NAME_LENGTH + 3;
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
                dataTextInputOutputFilename = DataStore.getTextOutputTVEpisodesFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryOutputTVEpisodesFilename();
                break;
            case TV_SEASON:
                consoleUI.addBreadcrumbItem("Vypisování obsahů vstupních/výstupních souborů TV sezón");
                dataTextInputOutputFilename = DataStore.getTextOutputTVSeasonsFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryOutputTVSeasonsFilename();
                break;
            case TV_SHOW:
                consoleUI.addBreadcrumbItem("Vypisování obsahů vstupních/výstupních souborů TV seriálů");
                dataTextInputOutputFilename = DataStore.getTextOutputTVShowsFilename();
                dataBinaryInputOutputFilename = DataStore.getBinaryOutputTVShowsFilename();
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
     * Method for handling displaying detail about chosen tv episode submenu
     * ({@link displayDetailAboutTVEpisodeSubmenu})
     * @param chosenTVEpisodes list of chosen tv episodes from which chosen tv episode will
     * be selected by tv episode order from printed tv episodes view/list (not order in TV season).
     */
    private void handleDisplayDetailAboutTVEpisodeSubmenu(List<TVEpisode> chosenTVEpisodes) 
    {
        try 
        {            
            int tvEpisodeOrderFromList = loadChosenTVEpisodeFromUser();            
            TVEpisode chosenTVEpisode = chosenTVEpisodes.get(tvEpisodeOrderFromList - 1);
                        
            TVSeason tvEpisodeParentSeason = consoleUI.getTVEpisodesController().getTVSeasonDetail(chosenTVEpisode.getTVSeasonForeignKey());

            consoleUI.addBreadcrumbItem(String.format("Detail %s TV epizody %d v sezóně %d",
                    chosenTVEpisode.getWasWatched() == true ? "zhlédnuté" : "nezhlédnuté",
                    chosenTVEpisode.getOrderInTVShowSeason(),
                    tvEpisodeParentSeason.getOrderInTVShow()));

            boolean returnToParentMenu = false;
            int choice;

            while (returnToParentMenu == false) 
            {
                printTVEpisodeDetail(chosenTVEpisode, tvEpisodeParentSeason, false);
                
                consoleUI.displayInfoMessage(String.format("Po úpravě nebo po ohodnocení se "
                        + "TV epizoda nemusí vypisovat tam, kde se původně vypisovala"));

                consoleUI.displayInfoMessage(String.format("Při smazání TV epizody, "
                        + "ohodnocení TV epizody nebo při reálné úpravě dat TV epizody "
                        + "dojde k návratu zpátky do daného výpisu s TV epizodami"));


                consoleUI.displayBreadcrumb();
                displayDetailAboutTVEpisodeSubmenu(chosenTVEpisode, tvEpisodeParentSeason);

                try 
                {
                    choice = consoleUI.loadChoiceFromSubmenu();

                    switch (choice) 
                    {
                        case 1:
                            returnToParentMenu = deleteChosenTVEpisode(chosenTVEpisode.getPrimaryKey());

                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }

                            break;
                        case 2:
                            returnToParentMenu = handleDisplayEditChosenTVEpisodeSubmenu(chosenTVEpisode, tvEpisodeParentSeason);

                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
                            }

                            break;
                        case 3:
                            returnToParentMenu = rateTVEpisode(chosenTVEpisode);
                            
                            if (returnToParentMenu == true) 
                            {
                                consoleUI.removeLastBreadcrumbItem();
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
            consoleUI.displayErrorMessage("Pořadové číslo epizody musí být číslo");
            consoleUI.advanceToNextInput();
        } 
        catch (IndexOutOfBoundsException ex) 
        {
            consoleUI.displayErrorMessage("Pořadové číslo epizody neodpovídá žádné z TV epizod");
            consoleUI.advanceToNextInput();
        }
    }
    
    /**
     * Method which prompts user for tv episode order number from printed tv episodes view/list
     * and then returns it. (not order in TV season)
     * @return int value representing tv episode order from tv episodes view.
     */
    private int loadChosenTVEpisodeFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo TV epizody: ");
        return consoleUI.getScanner().nextInt();
    }
    
    /**
     * Method which prints detail informations about chosen tv episode as view/page.
     * @param chosenTVEpisode represents chosen tv episode from printed tv episodes list/view.
     * @param chosenTVEpisodeParentSeason represents chosen tv episode parent season which
     * will be used for displaying its informations in tv episode view/page.
     * @param isInEditMode selects if tv episode informations are displayed in tv episode detail mode or 
     * edit mode ({@link displayDetailAboutTVEpisodeSubmenu}, {@link displayEditChosenTVEpisodeSubmenu})
     */
    private void printTVEpisodeDetail(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason, boolean isInEditMode) 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                String.format("%s %s TV EPIZODY %d V SEZÓNĚ %d", 
                        isInEditMode == true ? "EDITACE" : "DETAIL",
                        chosenTVEpisode.getWasWatched() == true ? "ZHLÉDNUTÉ" : "NEZHLÉDNUTÉ",
                        chosenTVEpisode.getOrderInTVShowSeason(),
                        chosenTVEpisodeParentSeason.getOrderInTVShow()));
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
                
        System.out.println();
        System.out.println(heading);
        System.out.println();
        System.out.println(dividingLine);
        System.out.println();
        
        String runtimeText = String.format("%02d:%02d:%02d", 
                chosenTVEpisode.getRuntime().toHours(), 
                chosenTVEpisode.getRuntime().toMinutesPart(), 
                chosenTVEpisode.getRuntime().toSecondsPart());
                        
        System.out.println(String.format("%-40s%d", "Identifikátor:", chosenTVEpisode.getPrimaryKey().getId()));
        System.out.println(String.format("%-40s%d", "Identifikátor sezóny:", chosenTVEpisode.getTVSeasonForeignKey().getId()));
        System.out.println(String.format("%-40s%d", "Číslo epizody v rámci sezóny:", chosenTVEpisode.getOrderInTVShowSeason()));
        System.out.println(String.format("%-40s%s", "Délka epizody:", runtimeText));
        System.out.println(String.format("%-40s%s", "Název:", chosenTVEpisode.getName() == null ? 
                "Neznámý" : chosenTVEpisode.getName()));
        
        System.out.println(String.format("%-40s%s", "Procentuální ohodnocení:", 
                chosenTVEpisode.getWasWatched() == true ? chosenTVEpisode.getPercentageRating() + " %" : "Nehodnoceno"));
        
        System.out.println(String.format("%-40s%s", "Odkaz ke zhlédnutí:", 
                chosenTVEpisode.getHyperlinkForContentWatch() == null ? "Neznámý" : chosenTVEpisode.getHyperlinkForContentWatch()));
            
        String shortContentSummaryText = chosenTVEpisode.getShortContentSummary() == null ? 
                String.format("%-40s%s", "Krátké shrnutí obsahu:", "Neznámé") : 
                String.format("Krátké shrnutí obsahu:%n%n%s", chosenTVEpisode.getShortContentSummary());
            
        System.out.println(shortContentSummaryText);
                
        System.out.println();
        System.out.println(dividingLine);  
    }
    
    /**
     * Method which tries to delete chosen tv episode from database
     * @param tvEpisodePrimaryKey represents chosen tv episode primary key
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv episodes as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean deleteChosenTVEpisode(PrimaryKey tvEpisodePrimaryKey) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            consoleUI.getTVEpisodesController().deleteTVEpisodeBy(tvEpisodePrimaryKey);
            consoleUI.displayInfoMessage("Vybraná TV epizoda úspěšně smazána");
            returnToParentMenu = true;
        }
        catch (Exception ex) 
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
    
    /**
     * Method for handling displaying editation of chosen tv episode submenu
     * ({@link displayEditChosenTVEpisodeSubmenu})
     * @param chosenTVEpisode selected tv episode from printed tv episodes list/view.
     * @param chosenTVEpisodeParentSeason represents chosen tv episode parent season which
     * will be used for displaying its informations in tv episode view/page.
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv episodes as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean handleDisplayEditChosenTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason) 
    {
        consoleUI.addBreadcrumbItem(String.format("Editace %s TV epizody %d v sezóně %d", 
                chosenTVEpisode.getWasWatched() == true ? "zhlédnuté" : "nezhlédnuté",
                chosenTVEpisode.getOrderInTVShowSeason(),
                chosenTVEpisodeParentSeason.getOrderInTVShow()));

        boolean returnToParentMenu = false;
        boolean returnToTVEpisodesListMenu = false;
        int choice;

        while (returnToParentMenu == false && returnToTVEpisodesListMenu == false) 
        {
            printTVEpisodeDetail(chosenTVEpisode, chosenTVEpisodeParentSeason, true);
                
            consoleUI.displayInfoMessage(String.format("Při úpravě TV epizody se používají stejné"
                    + " vstupní soubory jako u přidávání TV epizod"));
            
            consoleUI.displayInfoMessage(String.format("Pro jednoznačnost je nutné mít v daném souboru pouze jednu TV epizodu"));

            consoleUI.displayInfoMessage(String.format("Při úpravě TV epizody se používá v " 
                    + "souboru úplně totožná struktura dat jako u přidávání TV epizod"));
            
            consoleUI.displayBreadcrumb();
            displayEditChosenTVEpisodeSubmenu(chosenTVEpisode, chosenTVEpisodeParentSeason);

            try 
            {
                choice = consoleUI.loadChoiceFromSubmenu();

                switch (choice) 
                {
                    case 1:
                        returnToTVEpisodesListMenu = editTVEpisodeFromInputFile(chosenTVEpisode.getPrimaryKey(), 
                                chosenTVEpisode.getTVSeasonForeignKey(), false);

                        if (returnToTVEpisodesListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 2:
                        returnToTVEpisodesListMenu = editTVEpisodeFromInputFile(chosenTVEpisode.getPrimaryKey(), 
                                chosenTVEpisode.getTVSeasonForeignKey(), true);

                        if (returnToTVEpisodesListMenu == true) 
                        {
                            consoleUI.removeLastBreadcrumbItem();
                        }

                        break;
                    case 3:
                        consoleUI.displayChosenDataFileContent(DataStore.getTextInputTVEpisodesFilename(), DataType.TV_EPISODE);
                        break;
                    case 4:
                        consoleUI.displayChosenDataFileContent(DataStore.getBinaryInputTVEpisodesFilename(), DataType.TV_EPISODE);
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
        
        return returnToTVEpisodesListMenu;
    }
    
    /**
     * Method which tries to edit existing chosen tv episode by tv episodes input file
     * @param existingTVEpisodePrimaryKey selected tv episode primary key
     * @param existingTVSeasonPrimaryKey primary key of tv episode parent season, 
     * which will be used to link edited tv episode from file to correct tv season
     * @param fromBinary selects if input file will be binary or text
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv episodes as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean editTVEpisodeFromInputFile(PrimaryKey existingTVEpisodePrimaryKey, 
            PrimaryKey existingTVSeasonPrimaryKey ,boolean fromBinary) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            boolean wasDataChanged = consoleUI.getTVEpisodesController().editTVEpisodeBy(existingTVEpisodePrimaryKey, 
                    existingTVSeasonPrimaryKey, fromBinary);
             
            if (wasDataChanged == true) 
            {
                consoleUI.displayInfoMessage("Upravená data vybrané TV epizody se úspěšně uložila");
                returnToParentMenu = true;
            }
            else 
            {
                consoleUI.displayInfoMessage("Nedošlo k žádné změně dat u vybrané TV epizody");
            }
        }
        catch (Exception ex)
        {
            consoleUI.displayErrorMessage(ex.getMessage());
        }
        
        return returnToParentMenu;
    }
    
    /**
     * Method which tries to rate existing chosen tv episode in percents (0 to 100)
     * @param chosenTVEpisode selected tv episode, which percentage rating is to be modified.
     * @return logical value, if true, then current breadcrumb level 
     * will return to parent level with printed tv episodes as view/page, 
     * else nothing happens (will stay at current breadcrumb level)
     */
    private boolean rateTVEpisode(TVEpisode chosenTVEpisode) 
    {
        boolean returnToParentMenu = false;
        
        try 
        {
            int percentageRating = loadTVEpisodePercentageRatingFromUser();
            
            boolean wasDataChanged = consoleUI.getTVEpisodesController().rateTVEpisode(chosenTVEpisode, percentageRating);
             
            if (wasDataChanged == true && chosenTVEpisode.getWasWatched() == false) 
            {
                consoleUI.displayInfoMessage("Vybraná epizoda byla ohodnocena úspěšně a označena jako zhlédnutá");
                returnToParentMenu = true;
            }
            else if (wasDataChanged == true && chosenTVEpisode.getWasWatched() == true) 
            {
                consoleUI.displayInfoMessage("Vybraná epizoda byla ohodnocena úspěšně a jedná se o opakované ohodnocení");
                returnToParentMenu = true;
            }
            else 
            {
                consoleUI.displayInfoMessage("Ohodnocení epizody zůstalo beze změny");
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
     * Method which prompts user for entering chosen tv episode percentage rating in procents (0 to 100)
     * and then returns it.
     * @return int value representing percentage rating 
     */
    private int loadTVEpisodePercentageRatingFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte hodnocení TV epizody jako procenta od 0 až do 100: ");
        return consoleUI.getScanner().nextInt();
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
