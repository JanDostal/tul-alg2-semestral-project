package tests.mainmethods;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
import app.logic.filemanager.FileManagerAccessor;
import ui.ConsoleUI;
import utils.emailsender.EmailSender;

/**
 * Represents a custom unit test class for testing ConsoleUI instances of UI module.
 * @author jan.dostal
 */
public class ConsoleUITest 
{
    //testing main
    public static void main(String[] args) 
    {
        /* testing multi console terminals functionality, specifically
           only one-time request for entering data folder location and
           loading existing data from input/output files
        */        
        MoviesController moviesController = MoviesController.getInstance(DataContextAccessor.getInstance(), 
                EmailSender.getInstance(), FileManagerAccessor.getInstance());
        TVEpisodesController tvEpisodesController = TVEpisodesController.getInstance(DataContextAccessor.getInstance(), 
                EmailSender.getInstance(), FileManagerAccessor.getInstance());
        
        ConsoleUI ui = new ConsoleUI(moviesController, tvEpisodesController);
        ConsoleUI ui2 = new ConsoleUI(moviesController, tvEpisodesController);
        ConsoleUI ui3 = new ConsoleUI(moviesController, tvEpisodesController);
        
        ui.start();
        ui2.start();
        ui3.start();
    }
}
