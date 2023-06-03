
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
        DataContextAccessor dataContextAccessor = DataContextAccessor.getInstance();
        EmailSender emailSender = EmailSender.getInstance();
        FileManagerAccessor fileManagerAccessor = FileManagerAccessor.getInstance();
        
        MoviesController moviesController = MoviesController.getInstance(dataContextAccessor, 
                emailSender, fileManagerAccessor);
        TVEpisodesController tvEpisodesController = TVEpisodesController.getInstance(dataContextAccessor, 
                emailSender, fileManagerAccessor);
        
        ConsoleUI ui = new ConsoleUI(moviesController, tvEpisodesController);
    }
}
