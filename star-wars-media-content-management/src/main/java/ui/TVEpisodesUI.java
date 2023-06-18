/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import app.logic.controllers.DataSorting;
import app.logic.controllers.DataType;
import app.logic.controllers.TVEpisodesController;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.TVShow;
import app.models.output.TVShowOutput;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class TVEpisodesUI 
{
    private final ConsoleUI consoleUI;
    
    protected TVEpisodesUI(ConsoleUI consoleUI) 
    {
        this.consoleUI = consoleUI;
    }
        
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
                        break;
                    case 3:
                        handlePrintErasWithAnnouncedTVShowsSubmenu();
                        break;
                    case 4:
                        handlePrintErasWithReleasedTVShowsSubmenu();
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
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
                        consoleUI.displayDataChosenFileContent(DataStore.getTextInputTVShowsFilename(), DataType.TV_SHOW);
                        break;
                    case 4:
                        consoleUI.displayDataChosenFileContent(DataStore.getBinaryInputTVShowsFilename(), DataType.TV_SHOW);
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
            System.out.println(String.format("%-10s%s %-25s%s %d", 
                    counter + ".", "Období:", era.getDisplayName(), 
                    "Počet seriálů:", consoleUI.getTVEpisodesController().getAnnouncedTVShowsCountByEra(era)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
    
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
            System.out.println(String.format("%-15s%s %-" + nameMaxLength + "s%s %s", 
                    counter + ".", "Název:", tvShow.getName(), 
                    "Datum vydání:", tvShow.getReleaseDate() == null ? "Neznámé" : tvShow.getReleaseDate().format(dateFormatter)));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
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
     
     private void printErasWithReleasedTVShows() 
    {
        StringBuilder heading = consoleUI.createHeadingWithHorizontalLines(20, 
                "ÉRY S VYDANÝMI TV SERIÁLY (začíná nejstarší érou)");
        
        StringBuilder dividingLine = consoleUI.createDividingHorizontalLineOf(heading.toString());
        
 
        System.out.println();
        System.out.println(dividingLine);
        
        int counter = 0;
                
        for (Era era : Era.values()) 
        {
            counter++;
                        
            System.out.println();            
            System.out.println(String.format("%-10s%s %-25s%s %d", 
                    counter + ".", 
                    "Období:", era.getDisplayName(), 
                    "Počet seriálů:", consoleUI.getTVEpisodesController().getReleasedTVShowsCountByEra(era)));
        }
        
        System.out.println();
        System.out.println(dividingLine);
    }
     
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
        consoleUI.displayInfoMessage("Všechny TV epizody mají vždy nastavenou délku trvání");
        
        System.out.println();
        System.out.println(dividingLine);
        System.out.println();
        
        System.out.println("Počet seriálů: " + releasedTVShowsByChosenEra.size());
        System.out.println();
        System.out.println(dividingLine);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.forLanguageTag("cs-CZ"));
        
        int counter = 0;
        int nameMaxLength = TVShowOutput.ATTRIBUTE_NAME_LENGTH + 3;
        
        int showWatchedEpisodesCount;
        int showUnwatchedEpisodesCount;
        int showEpisodesCount;
        
        String showWatchedEpisodesTotalDurationText;
        String showUnwatchedEpisodesTotalDurationText;
        String showEpisodesTotalDurationText;
        
        Duration showWatchedEpisodesTotalDuration;
        Duration showUnwatchedEpisodesTotalDuration;
        Duration showEpisodesTotalDuration;
        
        float averagePercentageRating;
        
        for (TVShow show : releasedTVShowsByChosenEra) 
        {
            counter++;
            
            averagePercentageRating = consoleUI.getTVEpisodesController().
                    getAverageRatingOfAllReleasedEpisodesInTVShow(show.getPrimaryKey());
            
            showWatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowEpisodesCount(show.getPrimaryKey(), true);
            
            showUnwatchedEpisodesCount = consoleUI.getTVEpisodesController().
                    getReleasedTVShowEpisodesCount(show.getPrimaryKey(), false);
            
            showEpisodesCount = showUnwatchedEpisodesCount + showWatchedEpisodesCount;
            
            
            showWatchedEpisodesTotalDuration = 
            
                    
            System.out.println();
            System.out.println(String.format("%-15s%s %-" + nameMaxLength + "s%s %-16s%s %-14s%s %d %%", 
                    counter + ".", "Název:", show.getName(), 
                    "Datum vydání:", show.getReleaseDate().format(dateFormatter),
                    "Délka:", durationText,
                    "Hodnocení:", movie.getPercentageRating()));
        }
        
        System.out.println();
        System.out.println(dividingLine);        
    }
    
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
