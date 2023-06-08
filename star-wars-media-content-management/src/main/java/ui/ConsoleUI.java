
package ui;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class ConsoleUI 
{
    private final Scanner scanner = new Scanner(System.in);
    
    private final TVEpisodesController tvEpisodesController;
    
    private final MoviesController moviesController;
    
    private final MoviesUI moviesUI;
    
    private final TVEpisodesUI tvEpisodesUI;
    
    private final List<String> breadcrumbItems = new ArrayList<>(); 
    
    public ConsoleUI(MoviesController moviesController, TVEpisodesController tvEpisodesController) 
    {
        this.tvEpisodesController = tvEpisodesController;
        this.moviesController = moviesController;
        this.moviesUI = new MoviesUI(this);
        this.tvEpisodesUI = new TVEpisodesUI(this);
    }
    
    protected Scanner getScanner() 
    {
        return scanner;
    }
    
    protected MoviesController getMoviesController() 
    {
        return moviesController;
    }
    
    protected TVEpisodesController getTVEpisodesController() 
    {
        return tvEpisodesController;
    }
    
    public void start() 
    {
        boolean isAppRunning = true;
        boolean isDataDirectorySet = false;
        boolean isDatabaseFromFilesLoaded = false;
        int choice;
  
        while (isDataDirectorySet == false && isAppRunning == true) 
        {
            displayInfoMessage(String.format("Nejdříve prosím zadejte cestu k adresáři %s (bude obsahovat vstupní a výstupní soubory)", 
                    DataStore.getDataDirectoryName()));
            
            displayDataDirectoryPathMenu();
            
            try 
            {
                choice = loadChoiceFromMenu();
                
                switch (choice) 
                {
                    case 1:
                        isDataDirectorySet = setDataDirectoryPath();
                        break;
                    case 0:
                        isAppRunning = false;
                        break;
                    default:
                        displayErrorMessage("Nevalidní číslo volby z menu");
                }
            }
            catch (InputMismatchException ex) 
            {
                displayErrorMessage("Volba musí být vybrána pomocí čísla");
                advanceToNextInput();
            }
        }
        
        while (isDatabaseFromFilesLoaded == false && isAppRunning == true) 
        {
            displayInfoMessage("Dále prosím vyberte typ výstupních souborů a to buďto\n" + 
                    String.format("textové (%s, %s, %s, %s)%n", DataStore.getTextOutputMoviesFilename(), 
                            DataStore.getTextOutputTVShowsFilename(), DataStore.getTextOutputTVSeasonsFilename(), 
                            DataStore.getTextOutputTVEpisodesFilename()) +
                    String.format("nebo binární (%s, %s, %s, %s)%n", DataStore.getBinaryOutputMoviesFilename(), 
                            DataStore.getBinaryOutputTVShowsFilename(), DataStore.getBinaryOutputTVSeasonsFilename(), 
                            DataStore.getBinaryOutputTVEpisodesFilename())
                    + "pro načtení existujících dat z daných souborů");
            
            displayloadingOutputFilesMenu();
            
            try 
            {
                choice = loadChoiceFromMenu();
                
                switch (choice) 
                {
                    case 1:
                        isDatabaseFromFilesLoaded = loadAllOutputDataFrom(false);
                        break;
                    case 2:
                        isDatabaseFromFilesLoaded = loadAllOutputDataFrom(true);
                        break;
                    case 0:
                        isAppRunning = false;
                        break;
                    default:
                        displayErrorMessage("Nevalidní číslo volby z menu");
                }
            }
            catch (InputMismatchException ex) 
            {
                displayErrorMessage("Volba musí být vybrána pomocí čísla");
                advanceToNextInput();
            }
        }
        
        displayIntroduction();
        displayInfoMessage("Tato aplikace slouží jako evidence mediálního obsahu (seriály, filmy)"
                + " v rámci výhradně Star Wars univerza. Nechť vás provází síla.");
        
        addBreadcrumbItem("Hlavní menu");
        
        while (isAppRunning == true) 
        {
            displayBreadcrumb();
            displayMainMenu();
            
            try 
            {
                choice = loadChoiceFromMenu();
                
                switch (choice) 
                {
                    case 1:
                        printInformationsAboutChronologicalEras();
                        break;
                    case 2:
                        addBreadcrumbItem("Správa filmů");
                        moviesUI.start();
                        break;
                    case 3:
                        addBreadcrumbItem("Správa TV epizod");
                        tvEpisodesUI.start();
                        break;
                    case 0:
                        isAppRunning = false;
                        break;
                    default:
                        displayErrorMessage("Nevalidní číslo volby z menu");
                }
            }
            catch (InputMismatchException ex) 
            {
                displayErrorMessage("Volba musí být vybrána pomocí čísla");
                advanceToNextInput();
            }
        }

        displayInfoMessage("Děkujeme za použití aplikace. Ukončuji...");
    }
    
    protected void addBreadcrumbItem(String title) 
    {
        breadcrumbItems.add(title);
    }
    
    protected void removeLastBreadcrumbItem() 
    {
        breadcrumbItems.remove(breadcrumbItems.size() - 1);
    }
    
    protected void displayBreadcrumb()
    {
        StringBuilder breadcrumb = new StringBuilder("Aktuální cesta v navigaci:");
        
        for (String title : breadcrumbItems) 
        {
            breadcrumb.append(String.format(" %s /", title));
        }
        
        StringBuilder breadcrumbWithHorizontalLines = createMenuNameWithHorizontalLines(20, breadcrumb.toString());
        
        System.out.println();
        System.out.println(breadcrumbWithHorizontalLines);
        
    }
    
    protected void displayErrorMessage(String message) 
    {
        System.out.println();
        System.out.println("Chybová zpráva: " + message);
    }
    
    protected void displayInfoMessage(String message) 
    {
        System.out.println();
        System.out.println("Informační zpráva: " + message);
    }
    
    protected void advanceToNextInput() 
    {
        scanner.nextLine();
    }
    
    protected int loadChoiceFromMenu () 
    {
        System.out.println("Zadejte číslo volby z menu: ");
        return scanner.nextInt();
    }
    
    private String loadDataDirectoryPath() 
    {
        advanceToNextInput();
        System.out.println();
        System.out.println("Zadejte cestu (může být absolutní i relativní, automatické rozpoznání používaného operačního systému): ");
        return scanner.nextLine();
    }
    
    protected StringBuilder createDividingBottomHorizontalLineOf(String heading) 
    {
        StringBuilder horizontalLine = new StringBuilder();
        
        for (char c : heading.toCharArray()) 
        {
            horizontalLine.append("-");
        }
        
        return horizontalLine;
    }
    
    protected StringBuilder createHeadingWithHorizontalLines(int size, String heading) 
    {
        StringBuilder headingWithHorizontalLines = new StringBuilder();
        
        for (int i = 0; i < size; i++) 
        {
            headingWithHorizontalLines.append("#");
        }
        
        headingWithHorizontalLines.append(" ").append(heading).append(" ");
        
        for (int i = 0; i < size; i++) 
        {
            headingWithHorizontalLines.append("#");
        }
        
        return headingWithHorizontalLines;
    }
    
    protected StringBuilder createMenuNameWithHorizontalLines(int size, String menuName) 
    {
        StringBuilder menuNameWithHorizontalLines = new StringBuilder();
        
        for (int i = 0; i < size; i++) 
        {
            menuNameWithHorizontalLines.append("=");
        }
        
        menuNameWithHorizontalLines.append(" ").append(menuName).append(" ");
        
        for (int i = 0; i < size; i++) 
        {
            menuNameWithHorizontalLines.append("=");
        }
        
        return menuNameWithHorizontalLines;
    }
    
    private void displayIntroduction() 
    {
        String introductionHeading = String.format("VÍTEJTE V APLIKACI %s", DataStore.getAppName().toUpperCase());
        
        StringBuilder introductionWithHorizontalLines = createHeadingWithHorizontalLines(10, introductionHeading);
        
        System.out.println();
        System.out.println(introductionWithHorizontalLines);
    }
    
    private void displayMainMenu() 
    {
        String menuName = "HLAVNÍ MENU";
        
        StringBuilder menuNameWithHorizontalLines = createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat informace o chronologických érách");
        System.out.println("2. Správa filmů");
        System.out.println("3. Správa TV epizod");
        System.out.println("0. Ukončit aplikaci");
        System.out.println(horizontalLine);
    }
    
    private void displayDataDirectoryPathMenu()
    {
        String menuName = String.format("MENU NASTAVOVÁNÍ ADRESÁŘE %s", DataStore.getDataDirectoryName().toUpperCase());
        
        StringBuilder menuNameWithHorizontalLines = createMenuNameWithHorizontalLines(10, menuName);
        StringBuilder horizontalLine = createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Zadat cestu k %s adresáři", DataStore.getDataDirectoryName()));
        System.out.println("0. Ukončit aplikaci");
        System.out.println(horizontalLine);
    }
    
    private void displayloadingOutputFilesMenu() 
    {
        String menuName = "MENU NAČÍTÁNÍ VÝSTUPNÍCH SOUBORŮ";
        
        StringBuilder menuNameWithHorizontalLines = createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = createDividingBottomHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Načíst z textových souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println("2. Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println("0. Ukončit aplikaci");
        System.out.println(horizontalLine);
    }
        
    private void printInformationsAboutChronologicalEras() 
    {
        String heading = "CHRONOLOGICKÉ ÉRY STAR WARS UNIVERZA (začíná nejstarší érou)";
                
        StringBuilder headingWithHorizontalLines = createHeadingWithHorizontalLines(30, heading);
        StringBuilder horizontalLine = createDividingBottomHorizontalLineOf(headingWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(headingWithHorizontalLines);
                
        for (Era era : Era.values()) 
        {
            System.out.println();
            System.out.println("Název éry: " + era.getDisplayName());
            System.out.println();
            System.out.println("Popis:");
            System.out.println(era.getDescription());
            System.out.println(horizontalLine);
        }
    }
    
    private boolean setDataDirectoryPath() 
    {
        boolean isDataDirectorySet = false;
       
        String dataDirectoryPath = loadDataDirectoryPath();
        
        try 
        {
            FileManagerAccessor.setDataDirectory(dataDirectoryPath);
            isDataDirectorySet = true;
            displayInfoMessage(String.format("Cesta k adresáři %s úspešně nastavena a je specifikovaná jako%n%s", 
                    DataStore.getDataDirectoryName(), FileManagerAccessor.getDataDirectoryPath()));
        }
        catch (IllegalArgumentException ex) 
        {
            displayErrorMessage(ex.getMessage());
        }
        
        return isDataDirectorySet;
    }
    
    private boolean loadAllOutputDataFrom(boolean fromBinary) 
    {
        boolean isDatabaseFromFilesLoaded = false;
        
        try 
        {
            moviesController.loadAllOutputDataFrom(fromBinary);
            tvEpisodesController.loadAllOutputDataFrom(fromBinary);
            isDatabaseFromFilesLoaded = true;
            displayInfoMessage("Existující data z výstupních souborů úspěšně načtena");
        }
        catch (Exception ex) 
        {
            displayErrorMessage(ex.getMessage());
        }
        
        return isDatabaseFromFilesLoaded;
    }
}
