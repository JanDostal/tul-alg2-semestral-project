
package app.logic.controllers;

/**
 *
 * @author Admin
 */
public class MovieController 
{
    private static MovieController movieController;
    
    private MovieController() 
    {
    }
    
    public static MovieController getInstance() 
    {
        if (movieController == null) 
        {
            movieController = new MovieController();
        }
        
        return movieController;
    }
}
