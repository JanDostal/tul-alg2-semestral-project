package ui;

import app.logic.controllers.DataSorting;
import app.logic.controllers.DataType;
import app.logic.datastore.DataStore;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import app.models.inputoutput.TVEpisodeInputOutput;
import java.time.Duration;
import java.util.InputMismatchException;
import java.util.List;
import org.apache.commons.mail.EmailException;
import utils.exceptions.DatabaseException;

/**
 * Represents a tv seasons UI submodule, which represents a UI section dealing with tv seasons of chosen TV show.
 * TVSeasonsSubUI is communicating with ConsoleUI to use common methods and access tv episodes controller, 
 * tv episodes UI submodule with scanner
 * @author jan.dostal
 */
public class TVSeasonsSubUI 
{
    private final ConsoleUI consoleUI;
    
    /**
     * Creates a new instance of TVSeasonsSubUI.
     * Uses dependency injection to inject consoleUI ui module.
     * @param consoleUI existing instance of ConsoleUI. 
     * Can be used for using common methods from {@link ConsoleUI} class or access
     * tv episodes controller, tv episodes UI submodule with scanner.
     */
    protected TVSeasonsSubUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
    
    /**
     * Method for handling displaying printed tv seasons of entire chosen TV show
     * ({@link displayPrintChosenTVShowSeasonsSubmenu}). TVShowsSubUI class calls this method.
     * @param chosenTVShow selected tv show from printed tv shows list/view.
     */
    protected void handleDisplayPrintChosenTVShowSeasonsSubmenu(TVShow chosenTVShow) 
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
        System.out.println("1. Načíst z textového souboru");
        System.out.println("2. Načíst z binárního souboru");
        System.out.println("3. Vypsat obsah textového souboru");
        System.out.println("4. Vypsat obsah binárního souboru");
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
        System.out.println("1. Upravit TV sezónu pomocí vstupního textového souboru");
        System.out.println("2. Upravit TV sezónu pomocí vstupního binárního souboru");
        System.out.println("3. Vypsat obsah vstupního textového souboru");
        System.out.println("4. Vypsat obsah vstupního binárního souboru");
                
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
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
                        consoleUI.getTVEpisodesSubUI().deleteChosenTVEpisodes(tvShowSortedTVEpisodes);
                        break;
                    case 2:
                        consoleUI.getTVEpisodesSubUI().handleDisplayDetailAboutTVEpisodeSubmenu(tvShowSortedTVEpisodes);
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
        
        int nameMaxLength = TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH + 3;
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
                            consoleUI.getTVEpisodesSubUI().handleDisplayPrintChosenTVSeasonEpisodesSubmenu(chosenTVSeason);
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
    private boolean editTVSeasonFromInputFile(PrimaryKey existingTVSeasonPrimaryKey, PrimaryKey existingTVShowPrimaryKey, boolean fromBinary) 
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
}
