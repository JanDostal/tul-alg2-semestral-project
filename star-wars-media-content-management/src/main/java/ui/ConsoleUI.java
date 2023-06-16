
package ui;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import java.nio.charset.StandardCharsets;
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
    private static boolean isDataDirectorySet = false;
    
    private static boolean isDatabaseFromFilesLoaded = false;
    
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    
    private boolean wasInitialized = false;
    
    private final List<String> breadcrumbItems = new ArrayList<>(); 
    
    private final TVEpisodesController tvEpisodesController;
    
    private final MoviesController moviesController;
    
    private MoviesUI moviesUI;
    
    private TVEpisodesUI tvEpisodesUI;
   
    public ConsoleUI(MoviesController moviesController, TVEpisodesController tvEpisodesController) 
    {
        this.tvEpisodesController = tvEpisodesController;
        this.moviesController = moviesController;
    }
    
    private void initializeConsoleUI() 
    {
        wasInitialized = true;
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
        if (wasInitialized == false) 
        {
            initializeConsoleUI();
        }
        
        boolean isConsoleRunning = true;
        int choice;
  
        while (isDataDirectorySet == false && isConsoleRunning == true) 
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
                        setDataDirectoryPath();
                        break;
                    case 0:
                        isConsoleRunning = false;
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
        
        while (isDatabaseFromFilesLoaded == false && isConsoleRunning == true) 
        {
            displayInfoMessage("Dále prosím vyberte typ výstupních souborů a to buďto\n" + 
                    String.format("textové (%s, %s, %s, %s)%n", DataStore.getTextOutputMoviesFilename(), 
                            DataStore.getTextOutputTVShowsFilename(), DataStore.getTextOutputTVSeasonsFilename(), 
                            DataStore.getTextOutputTVEpisodesFilename()) +
                    String.format("nebo binární (%s, %s, %s, %s)%n", DataStore.getBinaryOutputMoviesFilename(), 
                            DataStore.getBinaryOutputTVShowsFilename(), DataStore.getBinaryOutputTVSeasonsFilename(), 
                            DataStore.getBinaryOutputTVEpisodesFilename())
                    + "pro načtení existujících dat z daných souborů");
            
            displayLoadingOutputFilesMenu();
            
            try 
            {
                choice = loadChoiceFromMenu();
                
                switch (choice) 
                {
                    case 1:
                        loadAllOutputDataFrom(false);
                        break;
                    case 2:
                        loadAllOutputDataFrom(true);
                        break;
                    case 3:
                        displayMoviesChosenFileContent(DataStore.getTextOutputMoviesFilename());
                        break;
                    case 4:
                        displayTVShowsChosenFileContent(DataStore.getTextOutputTVShowsFilename());
                        break;
                    case 5:
                        displayTVSeasonsChosenFileContent(DataStore.getTextOutputTVSeasonsFilename());
                        break;
                    case 6:
                        displayTVEpisodesChosenFileContent(DataStore.getTextOutputTVEpisodesFilename());
                        break;
                    case 7:
                        displayMoviesChosenFileContent(DataStore.getBinaryOutputMoviesFilename());
                        break;
                    case 8:
                        displayTVShowsChosenFileContent(DataStore.getBinaryOutputTVShowsFilename());
                        break;
                    case 9:
                        displayTVSeasonsChosenFileContent(DataStore.getBinaryOutputTVSeasonsFilename());
                        break;
                    case 10:
                        displayTVEpisodesChosenFileContent(DataStore.getTextOutputTVEpisodesFilename());
                        break;
                    case 0:
                        isConsoleRunning = false;
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
        
        if (isConsoleRunning == true) 
        {
            displayIntroduction();
            displayInfoMessage("Tato aplikace slouží jako evidence mediálního obsahu (seriály, filmy)" 
                    + " v rámci výhradně Star Wars univerza. Nechť vás provází síla.");
        
            addBreadcrumbItem("Hlavní menu");
        }
        
        while (isConsoleRunning == true) 
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
                        moviesUI.start();
                        break;
                    case 3:
                        tvEpisodesUI.start();
                        break;
                    case 0:
                        isConsoleRunning = false;
                        removeLastBreadcrumbItem();
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
    
    private void displayDataDirectoryPathMenu()
    {
        String menuName = String.format("MENU NASTAVOVÁNÍ ADRESÁŘE %s", DataStore.getDataDirectoryName().toUpperCase());
        
        StringBuilder menuNameWithHorizontalLines = createMenuNameWithHorizontalLines(10, menuName);
        StringBuilder horizontalLine = createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("1. Zadat cestu k %s adresáři", DataStore.getDataDirectoryName()));
        System.out.println("0. Ukončit aplikaci");
        System.out.println(horizontalLine);
    }
    
    private void displayLoadingOutputFilesMenu() 
    {
        String menuName = "MENU NAČÍTÁNÍ VÝSTUPNÍCH SOUBORŮ";
        
        StringBuilder menuNameWithHorizontalLines = createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1.  Načíst z textových souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println("2.  Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)");
        System.out.println(String.format("3.  Vypsat obsah textového souboru %s (diagnostika chyby při načítání)", 
                DataStore.getTextOutputMoviesFilename()));
        System.out.println(String.format("4.  Vypsat obsah textového souboru %s (diagnostika chyby při načítání)", 
                DataStore.getTextOutputTVShowsFilename()));
        System.out.println(String.format("5.  Vypsat obsah textového souboru %s (diagnostika chyby při načítání)", 
                DataStore.getTextOutputTVSeasonsFilename()));
        System.out.println(String.format("6.  Vypsat obsah textového souboru %s (ddiagnostika chyby při načítání)", 
                DataStore.getTextOutputTVEpisodesFilename()));
        System.out.println(String.format("7.  Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", 
                DataStore.getBinaryOutputMoviesFilename()));
        System.out.println(String.format("8.  Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", 
                DataStore.getBinaryOutputTVShowsFilename()));
        System.out.println(String.format("9.  Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", 
                DataStore.getBinaryOutputTVSeasonsFilename()));
        System.out.println(String.format("10. Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", 
                DataStore.getBinaryOutputTVEpisodesFilename()));
        System.out.println("0.  Ukončit aplikaci");
        System.out.println(horizontalLine);        
    }
    
    private void displayIntroduction() 
    {
        String introductionHeading = String.format("VÍTEJTE V APLIKACI %s", DataStore.getAppName().toUpperCase());
        
        StringBuilder introductionWithHorizontalLines = createHeadingWithHorizontalLines(20, introductionHeading);
        
        System.out.println();
        System.out.println(introductionWithHorizontalLines);
    }
    
    private void displayMainMenu() 
    {
        String menuName = "HLAVNÍ MENU";
        
        StringBuilder menuNameWithHorizontalLines = createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println("1. Vypsat informace o chronologických érách");
        System.out.println("2. Spravovat filmy");
        System.out.println("3. Spravovat TV epizody");
        System.out.println("0. Ukončit aplikaci");
        System.out.println(horizontalLine);
    }
    
    protected StringBuilder createDividingHorizontalLineOf(String heading) 
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
        
        StringBuilder breadcrumbWithHorizontalLines = createMenuNameWithHorizontalLines(15, breadcrumb.toString());
        
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
    
    protected int loadChosenEraOrderFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo éry: ");
        return scanner.nextInt();
    }
    
    protected String loadEmailFromUser() 
    {
        advanceToNextInput();
        System.out.println();
        System.out.println(String.format("Zadejte e-mailovou adresu příjemce (odesílatel bude %s / tvůrce této aplikace): ", 
                DataStore.getAppCreator()));
        return scanner.nextLine();
    }
    
    protected int loadChoiceFromSubmenu() 
    {
        System.out.println("Zadejte číslo volby z podmenu: ");
        return scanner.nextInt();
    }
    
    private int loadChoiceFromMenu () 
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
    
    private void setDataDirectoryPath() 
    {       
        String dataDirectoryPath = loadDataDirectoryPath();
        
        try 
        {
            FileManagerAccessor.setDataDirectory(dataDirectoryPath);
            isDataDirectorySet = true;
            displayInfoMessage(String.format("Cesta k adresáři %s úspešně nastavena a je specifikovaná jako%n%s", 
                    DataStore.getDataDirectoryName(), FileManagerAccessor.getDataDirectoryPath()));
        }
        catch (IllegalArgumentException | IllegalStateException ex) 
        {
            displayErrorMessage(ex.getMessage());
        }        
    }
    
    private void loadAllOutputDataFrom(boolean fromBinary) 
    {        
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
    }
    
    protected void displayMoviesChosenFileContent(String fileName) 
    {        
        try 
        {
            StringBuilder fileContent = getMoviesController().getMoviesChosenFileContent(fileName);
            
            StringBuilder heading = createHeadingWithHorizontalLines(15, "VÝPIS OBSAHU SOUBORU " + fileName.toUpperCase());
            StringBuilder dividingLine = createDividingHorizontalLineOf(heading.toString());
            
            System.out.println();
            System.out.println(heading);
            System.out.println();
            System.out.println(dividingLine);
            
            System.out.println(fileContent);
            
            System.out.println(dividingLine);
        }
        catch (Exception ex) 
        {
            displayErrorMessage(ex.getMessage());
        }        
    }
    
    protected void displayTVShowsChosenFileContent(String fileName) 
    {        
        try 
        {
            StringBuilder fileContent = getTVEpisodesController().getTVShowsChosenFileContent(fileName);
            
            StringBuilder heading = createHeadingWithHorizontalLines(15, "VÝPIS OBSAHU SOUBORU " + fileName.toUpperCase());
            StringBuilder dividingLine = createDividingHorizontalLineOf(heading.toString());
            
            System.out.println();
            System.out.println(heading);
            System.out.println();         
            System.out.println(dividingLine);
            
            System.out.println(fileContent);
            
            System.out.println(dividingLine);
        }
        catch (Exception ex) 
        {
            displayErrorMessage(ex.getMessage());
        }        
    }
    
    protected void displayTVSeasonsChosenFileContent(String fileName) 
    {        
        try 
        {
            StringBuilder fileContent = getTVEpisodesController().getTVSeasonsChosenFileContent(fileName);
            
            StringBuilder heading = createHeadingWithHorizontalLines(15, "VÝPIS OBSAHU SOUBORU " + fileName.toUpperCase());
            StringBuilder dividingLine = createDividingHorizontalLineOf(heading.toString());
            
            System.out.println();
            System.out.println(heading);
            System.out.println();
            System.out.println(dividingLine);
            
            System.out.println(fileContent);
            
            System.out.println(dividingLine);
        }
        catch (Exception ex) 
        {
            displayErrorMessage(ex.getMessage());
        }        
    }
    
    protected void displayTVEpisodesChosenFileContent(String fileName) 
    {        
        try 
        {
            StringBuilder fileContent = getTVEpisodesController().getTVEpisodesChosenFileContent(fileName);
            
            StringBuilder heading = createHeadingWithHorizontalLines(15, "VÝPIS OBSAHU SOUBORU " + fileName.toUpperCase());
            StringBuilder dividingLine = createDividingHorizontalLineOf(heading.toString());
            
            System.out.println();
            System.out.println(heading);
            System.out.println();
            System.out.println(dividingLine);
            
            System.out.println(fileContent);
            
            System.out.println(dividingLine);
        }
        catch (Exception ex) 
        {
            displayErrorMessage(ex.getMessage());
        }        
    }
        
    private void printInformationsAboutChronologicalEras() 
    {
        String heading = "CHRONOLOGICKÉ ÉRY STAR WARS UNIVERZA (začíná nejstarší érou)";
                
        StringBuilder headingWithHorizontalLines = createHeadingWithHorizontalLines(30, heading);
        StringBuilder horizontalLine = createDividingHorizontalLineOf(headingWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(headingWithHorizontalLines);
                
        for (Era era : Era.values()) 
        {
            System.out.println();
            System.out.println("Název: " + era.getDisplayName());
            System.out.println();
            System.out.println("Popis:");
            System.out.println(era.getDescription());
            System.out.println(horizontalLine);
        }
    }
}
