package ui;

import app.logic.controllers.DataType;
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
 * Represents a common UI module for all data UI submodules (submodules are at disposal in this class).
 * ConsoleUI class offers breadcrumb for easier navigation in UI.
 * ConsoleUI class is communicating with business logic through Movies and TVEpisodes controllers.
 * @author jan.dostal
 */
public class ConsoleUI 
{
    private static boolean isDataDirectorySet = false;
    
    private static boolean isDatabaseFromFilesLoaded = false;
    
    private final Scanner scanner = new Scanner(System.in);
    
    private boolean wasInitialized = false;
    
    private final List<String> breadcrumbItems = new ArrayList<>(); 
    
    private final TVEpisodesController tvEpisodesController;
    
    private final MoviesController moviesController;
    
    private MoviesUI moviesUI;
    
    private TVEpisodesUI tvEpisodesUI;
   
    /**
     * Creates a new instance of ConsoleUI.
     * Uses dependency injection to inject data controllers.
     * @param moviesController singleton instance of movies data controller.
     * @param tvEpisodesController singleton instance of tv episodes data controller.
     */
    public ConsoleUI(MoviesController moviesController, TVEpisodesController tvEpisodesController) 
    {
        this.tvEpisodesController = tvEpisodesController;
        this.moviesController = moviesController;
    }
    
    /**
     * Initializes ConsoleUI after instance creation, 
     * specifically loads all data UI submodules as new instances
     */
    private void initializeConsoleUI() 
    {
        wasInitialized = true;
        this.moviesUI = new MoviesUI(this);
        this.tvEpisodesUI = new TVEpisodesUI(this);
    }
    
    /**
     * @return Scanner instance (usage in data UI submodules)
     */
    protected Scanner getScanner() 
    {
        return scanner;
    }
    
    /**
     * @return Movies controller instance (usage in data UI submodules)
     */
    protected MoviesController getMoviesController() 
    {
        return moviesController;
    }
    
    /**
     * @return TV episodes controller instance (usage in data UI submodules)
     */
    protected TVEpisodesController getTVEpisodesController() 
    {
        return tvEpisodesController;
    }
    
    /**
     * starts UI in console.
     * <p>
     * If ConsoleUI instance was just created, 
     * it will call {@link initializeConsoleUI} method on start of this method, after that it will not 
     * call that method for this instance ever again.
     * <p>
     * If console was just started, 
     * it will set data directory and load selected output data files into database.
     * If console will be run by multiple ConsoleUI instances, it will skip set data directory 
     * and load selected output data files into database stages after calling 
     * this method on first ConsoleUI instance.
     */
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
                        displayErrorMessage("Neplatné číslo volby z menu");
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
                        displayDataChosenFileContent(DataStore.getTextOutputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 4:
                        displayDataChosenFileContent(DataStore.getTextOutputTVShowsFilename(), DataType.TV_SHOW);
                        break;
                    case 5:
                        displayDataChosenFileContent(DataStore.getTextOutputTVSeasonsFilename(), DataType.TV_SEASON);
                        break;
                    case 6:
                        displayDataChosenFileContent(DataStore.getTextOutputTVEpisodesFilename(), DataType.TV_EPISODE);
                        break;
                    case 7:
                        displayDataChosenFileContent(DataStore.getBinaryOutputMoviesFilename(), DataType.MOVIE);
                        break;
                    case 8:
                        displayDataChosenFileContent(DataStore.getBinaryOutputTVShowsFilename(), DataType.TV_SHOW);
                        break;
                    case 9:
                        displayDataChosenFileContent(DataStore.getBinaryOutputTVSeasonsFilename(), DataType.TV_SEASON);
                        break;
                    case 10:
                        displayDataChosenFileContent(DataStore.getTextOutputTVEpisodesFilename(), DataType.TV_EPISODE);
                        break;
                    case 0:
                        isConsoleRunning = false;
                        break;
                    default:
                        displayErrorMessage("Neplatné číslo volby z menu");
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
                        displayErrorMessage("Neplatné číslo volby z menu");
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
    
    /**
     * Displays menu with choices for set data directory stage
     * (is skipped if multiple instances of ConsoleUI are run).
     */
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
    
    /**
     * Displays menu with choices for load selected data output files into database stage
     * (is skipped if multiple instances of ConsoleUI are run).
     */
    private void displayLoadingOutputFilesMenu() 
    {
        String menuName = "MENU NAČÍTÁNÍ VÝSTUPNÍCH SOUBORŮ";
        
        StringBuilder menuNameWithHorizontalLines = createMenuNameWithHorizontalLines(30, menuName);
        StringBuilder horizontalLine = createDividingHorizontalLineOf(menuNameWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(menuNameWithHorizontalLines);
        System.out.println(String.format("%-4s Načíst z textových souborů (dojde případně k automatickému vytvoření daných souborů)", "1."));
        System.out.println(String.format("%-4s Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)", "2."));
        System.out.println(String.format("%-4s Vypsat obsah textového souboru %s (diagnostika chyby při načítání)", "3.", 
                DataStore.getTextOutputMoviesFilename()));
        System.out.println(String.format("%-4s Vypsat obsah textového souboru %s (diagnostika chyby při načítání)", "4.",
                DataStore.getTextOutputTVShowsFilename()));
        System.out.println(String.format("%-4s Vypsat obsah textového souboru %s (diagnostika chyby při načítání)", "5.",
                DataStore.getTextOutputTVSeasonsFilename()));
        System.out.println(String.format("%-4s Vypsat obsah textového souboru %s (ddiagnostika chyby při načítání)", "6.",
                DataStore.getTextOutputTVEpisodesFilename()));
        System.out.println(String.format("%-4s Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", "7.",
                DataStore.getBinaryOutputMoviesFilename()));
        System.out.println(String.format("%-4s Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", "8.",
                DataStore.getBinaryOutputTVShowsFilename()));
        System.out.println(String.format("%-4s Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", "9.",
                DataStore.getBinaryOutputTVSeasonsFilename()));
        System.out.println(String.format("%-4s Vypsat obsah binárního souboru %s (diagnostika chyby při načítání)", "10.",
                DataStore.getBinaryOutputTVEpisodesFilename()));
        System.out.println(String.format("%-4s Ukončit aplikaci", "0."));
        System.out.println(horizontalLine);        
    }
    
    /**
     * Displays a welcoming introduction for users of this application
     */
    private void displayIntroduction() 
    {
        String introductionHeading = String.format("VÍTEJTE V APLIKACI %s", DataStore.getAppName().toUpperCase());
        
        StringBuilder introductionWithHorizontalLines = createHeadingWithHorizontalLines(20, introductionHeading);
        
        System.out.println();
        System.out.println(introductionWithHorizontalLines);
    }
    
    /**
     * Displays a main menu of application with choices
     */
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
    
    /**
     * Method creates and then returns horizontal line with same width as heading 
     * (usage also in data UI submodules)
     * @param heading represents a heading for particular page/view
     * @return horizontal line made of '-' with width set as heading width
     */
    protected StringBuilder createDividingHorizontalLineOf(String heading) 
    {
        StringBuilder horizontalLine = new StringBuilder();
        
        for (char c : heading.toCharArray()) 
        {
            horizontalLine.append("-");
        }
        
        return horizontalLine;
    }
    
    /**
     * Method creates and then returns heading, which has horizontal lines on left and right sides
     * (usage also in data UI submodules).
     * @param size represents width of one horizontal line
     * @param heading represents text heading for particular view/page
     * @return heading with horizontal lines on left and right (made of #)
     */
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
    
    /**
     * Method creates and then returns menu name, which has horizontal lines on left and right sides
     * (usage also in data UI submodules).
     * @param size represents width of one horizontal line
     * @param menuName represents text menu name for particular navigation level
     * @return heading with horizontal lines on left and right (made of =)
     */
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
    
    /**
     * Method adds new navigation level (going into sublevel) 
     * into breadcrumb (collection of navigation levels).
     * Usage of this method is also in data UI submodules.
     * @param title represents a title of particular navigation level
     */
    protected void addBreadcrumbItem(String title) 
    {
        breadcrumbItems.add(title);
    }
    
    /**
     * Method removes last navigation level (returning to parent level) 
     * from breadcrumb (collection of navigation levels).
     * Usage of this method is also in data UI submodules.
     */
    protected void removeLastBreadcrumbItem() 
    {
        breadcrumbItems.remove(breadcrumbItems.size() - 1);
    }
    
    /**
     * Method displays current breadcrumb (current hiearchy of levels).
     * Usage of this method is also in data UI submodules.
     */
    protected void displayBreadcrumb()
    {
        StringBuilder breadcrumb = new StringBuilder("Aktuální cesta v navigaci: ");
        String breadcrumbDivider = " / ";
        
        int count = 0;
        boolean setNewLine = false;
        
        for (String title : breadcrumbItems) 
        {
            count++;
            
            if (setNewLine == true) 
            {
                breadcrumb.append("\n");
                setNewLine = false;
            }
            
            breadcrumb.append(String.format("%s%s", title, breadcrumbDivider));
            
            if (count % 3 == 0) setNewLine = true;
        }
        
        breadcrumb.delete(breadcrumb.length() - breadcrumbDivider.length(), breadcrumb.length());
        
        StringBuilder breadcrumbWithHorizontalLines = createMenuNameWithHorizontalLines(15, breadcrumb.toString());
        
        System.out.println();
        System.out.println(breadcrumbWithHorizontalLines);
        
    }
    
    /**
     * Method displays message as error message (formatting)
     * Usage of this method is also in data UI submodules.
     * @param message error message to be formatted
     */
    protected void displayErrorMessage(String message) 
    {
        System.out.println();
        System.out.println("Chybová zpráva: " + message);
    }
    
    /**
     * Method displays message as info message (formatting)
     * Usage of this method is also in data UI submodules.
     * @param message info message to be formatted
     */
    protected void displayInfoMessage(String message) 
    {
        System.out.println();
        System.out.println("Informační zpráva: " + message);
    }
    
    /**
     * Method for skipping last input in scanner (when InputMismatchException occurs etc.)
     * Usage of this method is also in data UI submodules.
     */
    protected void advanceToNextInput() 
    {
        scanner.nextLine();
    }
    
    /**
     * Method which prompts user for star wars era order number from printed eras view/list 
     * and then returns it.
     * Usage of this method is also in data UI submodules.
     * @return int value representing era order from view list
     */
    protected int loadChosenEraOrderFromUser() 
    {
        System.out.println();
        System.out.println("Zadejte pořadové číslo éry: ");
        return scanner.nextInt();
    }
    
    /**
     * Method which prompts user for email and then returns it.
     * Usage of this method is also in data UI submodules.
     * @return String value representing email
     */
    protected String loadEmailFromUser() 
    {
        advanceToNextInput();
        System.out.println();
        System.out.println(String.format("Zadejte e-mailovou adresu příjemce (odesílatel bude %s / tvůrce této aplikace): ", 
                DataStore.getAppCreator()));
        return scanner.nextLine();
    }
    
    /**
     * Method which prompts user for entering choice number from current displayed submenu 
     * and then returns it.
     * Usage of this method is in data UI submodules.
     * @return int value representing choice from submenu
     */
    protected int loadChoiceFromSubmenu() 
    {
        System.out.println("Zadejte číslo volby z podmenu: ");
        return scanner.nextInt();
    }
    
    /**
     * Method which prompts user for entering choice number from current displayed menu
     * and then returns it.
     * @return int value representing choice from menu
     */
    private int loadChoiceFromMenu () 
    {
        System.out.println("Zadejte číslo volby z menu: ");
        return scanner.nextInt();
    }
    
    /**
     * Method which tries to set data directory path to data directory (contains input files, output files)
     */
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
    
    /**
     * Method which prompts user for entering path to data directory
     * and then returns it.
     * @return String values representing path to data directory
     */
    private String loadDataDirectoryPath() 
    {
        advanceToNextInput();
        System.out.println();
        System.out.println("Zadejte cestu (může být absolutní i relativní, automatické rozpoznání používaného operačního systému): ");
        return scanner.nextLine();
    }
    
    /**
     * Method which tries to load all output data from output data files (text or binary) into database
     * @param fromBinary selects if output data files will be binary or text
     */
    private void loadAllOutputDataFrom(boolean fromBinary) 
    {        
        try 
        {
            moviesController.loadAllOutputDataFrom(fromBinary);
            tvEpisodesController.loadAllOutputDataFrom(fromBinary);
            isDatabaseFromFilesLoaded = true;
            displayInfoMessage("Existující data z " + (fromBinary == true ? 
                    "binárních" : "textových") + " výstupních souborů úspěšně načtena");
        }
        catch (Exception ex) 
        {
            displayErrorMessage(ex.getMessage());
        }
    }
    
    /**
     * Method which tries to display content of chosen data file as view/page.
     * Usage of this method is also in data UI submodules.
     * @param fileName specifies name of the chosen file (files names defined in {@link DataStore}).
     * @param dataType specifies type of data of the chosen file (types of data defined in {@link DataType}).
     */
    protected void displayDataChosenFileContent(String fileName, DataType dataType) 
    {   
        try 
        {
            StringBuilder fileContent = null;
            
            switch (dataType) 
            {
                case MOVIE:
                    fileContent = getMoviesController().getMoviesChosenFileContent(fileName);
                    break;
                case TV_SHOW:
                    fileContent = getTVEpisodesController().getTVShowsChosenFileContent(fileName);
                    break;
                case TV_SEASON:
                    fileContent = getTVEpisodesController().getTVSeasonsChosenFileContent(fileName);
                    break;
                case TV_EPISODE:
                    fileContent = getTVEpisodesController().getTVEpisodesChosenFileContent(fileName);
                    break; 
            }
            
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
    
    /**
     * Method for printing informations about Star Wars chronological eras as view/page.
     */
    private void printInformationsAboutChronologicalEras() 
    {
        String heading = "CHRONOLOGICKÉ ÉRY STAR WARS UNIVERZA (začíná nejstarší érou)";
                
        StringBuilder headingWithHorizontalLines = createHeadingWithHorizontalLines(30, heading);
        StringBuilder horizontalLine = createDividingHorizontalLineOf(headingWithHorizontalLines.toString());
        
        System.out.println();
        System.out.println(headingWithHorizontalLines);
        
        displayInfoMessage("Kódová označení se používají jako hodnoty atributu era ve vstupních a výstupních souborech filmů a TV seriálů");
        System.out.println(horizontalLine);
                
        for (Era era : Era.values()) 
        {
            System.out.println();
            System.out.println(String.format("%-20s%s", "Název:", era.getDisplayName()));
            System.out.println();
            System.out.println(String.format("%-20s%s", "Kódové označení:", era.toString()));
            System.out.println();
            System.out.println("Popis:");
            System.out.println();
            System.out.println(era.getDescription());
            System.out.println(horizontalLine);
        }
    }
}
