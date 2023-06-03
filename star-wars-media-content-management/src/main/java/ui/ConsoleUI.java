
package ui;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
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
}
