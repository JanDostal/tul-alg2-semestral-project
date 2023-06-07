
package ui;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
import app.logic.filemanager.FileManagerAccessor;
import utils.emailsender.EmailSender;

/**
 *
 * @author Admin
 */
public class ApplicationRunner 
{
    public static void main(String[] args) 
    {        
        MoviesController moviesController = MoviesController.getInstance(DataContextAccessor.getInstance(), 
                EmailSender.getInstance(), FileManagerAccessor.getInstance());
        TVEpisodesController tvEpisodesController = TVEpisodesController.getInstance(DataContextAccessor.getInstance(), 
                EmailSender.getInstance(), FileManagerAccessor.getInstance());
        
        ConsoleUI ui = new ConsoleUI(moviesController, tvEpisodesController);
        ui.start();
    }
}
