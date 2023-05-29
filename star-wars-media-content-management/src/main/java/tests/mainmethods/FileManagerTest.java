
package tests.mainmethods;

import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManager;
import app.models.input.MovieInput;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
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
            FileManager.setDataDirectory("data");
            String path = FileManager.getDataDirectoryPath();
            
            StringBuilder moviesBinaryFile = fileManager.getBinaryFileContent(DataStore.getBinaryInputMoviesFilename());
            
            System.out.println(moviesBinaryFile);
            
            //loadInputMoviesFromText method
            System.out.println();
            System.out.println("loadInputMoviesFromText method:");
            System.out.println();
            List<MovieInput> a = fileManager.loadInputMoviesFromText();
        
            for (MovieInput m : a) 
            {
                System.out.println(m);
            }
                        
            //loadInputMoviesFromBinary method
            System.out.println();
            System.out.println("loadInputMoviesFromBinary method:");
            System.out.println();
            List<MovieInput> b = fileManager.loadInputMoviesFromBinary();
        
            for (MovieInput m : b) 
            {
                System.out.println(m);
            }
        
        
            //loadInputTVShowsFromText method
            System.out.println();
            System.out.println("loadInputTVShowsFromText method:");
            System.out.println();
            List<TVShowInput> c = fileManager.loadInputTVShowsFromText();
        
            for (TVShowInput m : c) 
            {
                System.out.println(m);
            }
            
            //loadInputTVShowsFromBinary method
            System.out.println();
            System.out.println("loadInputTVShowsFromBinary method:");
            System.out.println();
            List<TVShowInput> d = fileManager.loadInputTVShowsFromBinary();
        
            for (TVShowInput m : d) 
            {
                System.out.println(m);
            }
            
            //loadInputTVSeasonsFromText method
            System.out.println();
            System.out.println("loadInputTVSeasonsFromText method:");
            System.out.println();
            List<TVSeasonInput> e = fileManager.loadInputTVSeasonsFromText();
        
            for (TVSeasonInput m : e) 
            {
                System.out.println(m);
            }
            
            //loadInputTVSeasonsFromBinary method
            System.out.println();
            System.out.println("loadInputTVSeasonsFromBinary method:");
            System.out.println();
            List<TVSeasonInput> f = fileManager.loadInputTVSeasonsFromBinary();
        
            for (TVSeasonInput m : f) 
            {
                System.out.println(m);
            }
            
            //loadInputTVEpisodesFromText method
            System.out.println();
            System.out.println("loadInputTVEpisodesFromText method:");
            System.out.println();
            List<TVEpisodeInput> g = fileManager.loadInputTVEpisodesFromText();
        
            for (TVEpisodeInput m : g) 
            {
                System.out.println(m);
            }
            
            //loadInputTVEpisodesFromBinary method
            System.out.println();
            System.out.println("loadInputTVEpisodesFromBinary method:");
            System.out.println();
            List<TVEpisodeInput> h = fileManager.loadInputTVEpisodesFromBinary();
        
            for (TVEpisodeInput m : h) 
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
