
package tests.mainmethods;

import app.logic.filemanager.FileManager;
import app.models.input.MovieInput;
import app.models.input.TVShowInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FileManagerTest 
{
    public static void main(String[] args)
    {
        FileManager fileManager = FileManager.getInstance();

        try 
        {
            fileManager.setDataDirectory("data");
            
            //addMoviesFromText method
            System.out.println();
            System.out.println("addMoviesFromText method:");
            System.out.println();
            List<MovieInput> a = fileManager.addMoviesFromText();
        
            for (MovieInput m : a) 
            {
                System.out.println(m);
            }
        
        
            //addTVShowsFromText method
            System.out.println();
            System.out.println("addTVShowsFromText method:");
            System.out.println();
            List<TVShowInput> b = fileManager.addTVShowsFromText();
        
            for (TVShowInput m : b) 
            {
                System.out.println(m);
            }   
        }
        catch (Exception e) 
        {
            System.out.println("chyba");
        }
    }
}
