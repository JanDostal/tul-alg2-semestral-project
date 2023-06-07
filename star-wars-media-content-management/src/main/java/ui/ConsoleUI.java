
package ui;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class ConsoleUI 
{
    private static final Scanner scanner = new Scanner(System.in);
    
    private final TVEpisodesController tvEpisodesController;
    
    private final MoviesController moviesController;
    
    public ConsoleUI(MoviesController moviesController, 
            TVEpisodesController tvEpisodesController) 
    {
        this.tvEpisodesController = tvEpisodesController;
        this.moviesController = moviesController;
    }
    
    public void start() 
    {
        boolean isAppRunning = true;
        boolean isDataDirectorySet = false;
  
        displayIntroduction();
        
        while (isDataDirectorySet == false && isAppRunning == true) 
        {
            displayDataDirectoryPathMenu();
            
            try 
            {
                int choice = loadChoiceFromMenu();
                
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

        displayInfoMessage("Děkujeme za použití aplikace. Ukončuji...");
    }
    
    private void displayErrorMessage(String message) 
    {
        System.out.println();
        System.out.println("Chybová zpráva: " + message);
        System.out.println();
    }
    
    private void displayInfoMessage(String message) 
    {
        System.out.println("Informační zpráva: " + message);
    }
    
    private void advanceToNextInput() 
    {
        scanner.nextLine();
    }
    
    private int loadChoiceFromMenu () 
    {
        System.out.println("Zadejte číslo volby z menu: ");
        return scanner.nextInt();
    }
    
    private void displayIntroduction() 
    {
        System.out.println(String.format("=== Vítejte v aplikaci %s ===", DataStore.getAppName()));
        System.out.println();
    }
    
    private boolean setDataDirectoryPath() 
    {
        boolean isDataDirectorySet = false;
       
        String dataDirectoryPath = loadDataDirectoryPath();
        
        try 
        {
            FileManagerAccessor.setDataDirectory(dataDirectoryPath);
            isDataDirectorySet = true;
            System.out.println();
            displayInfoMessage("Cesta k adresáři data úspešně nastavena");
        }
        catch (IllegalArgumentException ex) 
        {
            displayErrorMessage(ex.getMessage());
        }
        
        return isDataDirectorySet;
    }
    private String loadDataDirectoryPath() 
    {
        advanceToNextInput();
        System.out.println();
        System.out.println("Zadejte cestu: ");
        return scanner.nextLine();
    }
    
    private void displayDataDirectoryPathMenu() 
    {
        displayInfoMessage("Nejdříve prosím zadejte cestu k adresáři data (bude obsahovat vstupní a výstupní soubory)");
        System.out.println();
        System.out.println("=============== MENU NASTAVOVÁNÍ ADRESÁŘE DATA ===============");
        System.out.println("1. Zadat cestu k data adresáři");
        System.out.println("0. Ukončit aplikaci");
        System.out.println("--------------------------------------------------------------");
    }
}
