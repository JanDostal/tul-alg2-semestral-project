package ui;

import app.logic.controllers.DataSorting;
import app.logic.controllers.DataType;
import app.logic.datastore.DataStore;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.inputoutput.TVEpisodeInputOutput;
import java.time.Duration;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Represents a tv episodes UI submodule, which represents a UI section dealing with tv episodes of chosen TV season.
 * TVEpisodesSubUI is communicating with ConsoleUI to use common methods and access tv episodes controller
 * with scanner.
 * @author jan.dostal
 */
public class TVEpisodesSubUI 
{
    private final ConsoleUI consoleUI;
    
    /**
     * Creates a new instance of TVEpisodesSubUI.
     * Uses dependency injection to inject consoleUI ui module.
     * @param consoleUI existing instance of ConsoleUI. 
     * Can be used for using common methods from {@link ConsoleUI} class or access
     * tv episodes controller with scanner.
     */
    protected TVEpisodesSubUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
    
    /**
     * Method for handling displaying printed tv episodes of entire chosen TV season
     * ({@link displayPrintChosenTVSeasonEpisodesSubmenu}). TVSeasonsSubUI class calls this method.
     * @param chosenTVSeason selected tv season from printed tv seasons list/view.
     */
    protected void handleDisplayPrintChosenTVSeasonEpisodesSubmenu(TVSeason chosenTVSeason) 
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
        System.out.println("1. Načíst z textového souboru");
        System.out.println("2. Načíst z binárního souboru");
        System.out.println("3. Vypsat obsah textového souboru");
        System.out.println("4. Vypsat obsah binárního souboru");
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
        System.out.println("1. Upravit TV epizodu pomocí vstupního textového souboru");
        System.out.println("2. Upravit TV epizodu pomocí vstupního binárního souboru");
        System.out.println("3. Vypsat obsah vstupního textového souboru");
        System.out.println("4. Vypsat obsah vstupního binárního souboru");
                
        System.out.println("0. Vrátit se zpět do nadřazeného menu");
        System.out.println(horizontalLine);
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
        
        int nameMaxLength = TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH + 3;
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
     * Method which tries to delete chosen tv episodes from database. TVSeasonsSubUI class calls this method.
     * @param chosenTVEpisodes list of chosen tv episodes
     */
    protected void deleteChosenTVEpisodes(List<TVEpisode> chosenTVEpisodes) 
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
        
        int nameMaxLength = TVEpisodeInputOutput.ATTRIBUTE_NAME_LENGTH + 3;
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
     * Method for handling displaying detail about chosen tv episode submenu
     * ({@link displayDetailAboutTVEpisodeSubmenu}). TVSeasonsSubUI class calls this method.
     * @param chosenTVEpisodes list of chosen tv episodes from which chosen tv episode will
     * be selected by tv episode order from printed tv episodes view/list (not order in TV season).
     */
    protected void handleDisplayDetailAboutTVEpisodeSubmenu(List<TVEpisode> chosenTVEpisodes) 
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
}
