package ui;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
import app.logic.filemanager.FileManagerAccessor;
import utils.emailsender.EmailSender;

/**
 * Application for managing, reviewing and organizing star wars media content (movies, TV episodes).
 * @author jan.dostal
 * @version 1.0 06/20/23
 */
public class ApplicationRunner 
{
    //application initialization
    public static void main(String[] args) 
    {        
        MoviesController moviesController = MoviesController.getInstance(DataContextAccessor.getInstance(), 
                EmailSender.getInstance(), FileManagerAccessor.getInstance());
        TVEpisodesController tvEpisodesController = TVEpisodesController.getInstance(DataContextAccessor.getInstance(), 
                EmailSender.getInstance(), FileManagerAccessor.getInstance());
        
        ConsoleUI consoleTerminal = ConsoleUI.getInstance(moviesController, tvEpisodesController);
        consoleTerminal.start();
    }
}
