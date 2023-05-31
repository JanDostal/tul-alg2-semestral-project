
package tests.mainmethods;

import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.input.MovieInput;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
import app.models.input.TVShowInput;
import app.models.output.MovieOutput;
import static app.models.output.MovieOutput.ATTRIBUTE_NAME_LENGTH;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        FileManagerAccessor fileManager = FileManagerAccessor.getInstance();

        try 
        {
            FileManagerAccessor.setDataDirectory("data");
            String path = FileManagerAccessor.getDataDirectoryPath();
            
            //getDataDirectoryPath method
            System.out.println();
            System.out.println("getDataDirectoryPath method (celá úplná cesta):");
            System.out.println();
                        
            System.out.println(path);
            
            //getBinaryFileContent method
            System.out.println();
            System.out.println("getBinaryFileContent method (obsah input_movies.bin):");
            System.out.println();
            
            StringBuilder moviesBinaryFile = fileManager.getBinaryFileContent(DataStore.getBinaryInputMoviesFilename());
            
            System.out.println(moviesBinaryFile);
            
            //getTextFileContent method
            System.out.println();
            System.out.println("getTextFileContent method (obsah input_movies.txt):");
            System.out.println();
            
            StringBuilder moviesTextFile = fileManager.getTextFileContent(DataStore.getTextInputMoviesFilename());
            
            System.out.println(moviesTextFile);
            
            //loadInputMoviesFromText method
            System.out.println();
            System.out.println("loadInputMoviesFromText method:");
            System.out.println();
            List<MovieInput> a = fileManager.getMoviesFileManager().loadInputMoviesFromText();
        
            for (MovieInput m : a) 
            {
                System.out.println(m);
            }
                        
            //loadInputMoviesFromBinary method
            System.out.println();
            System.out.println("loadInputMoviesFromBinary method:");
            System.out.println();
            List<MovieInput> b = fileManager.getMoviesFileManager().loadInputMoviesFromBinary();
        
            for (MovieInput m : b) 
            {
                System.out.println(m);
            }
        
        
            //loadInputTVShowsFromText method
            System.out.println();
            System.out.println("loadInputTVShowsFromText method:");
            System.out.println();
            List<TVShowInput> c = fileManager.getTVShowsFileManager().loadInputTVShowsFromText();
        
            for (TVShowInput m : c) 
            {
                System.out.println(m);
            }
            
            //loadInputTVShowsFromBinary method
            System.out.println();
            System.out.println("loadInputTVShowsFromBinary method:");
            System.out.println();
            List<TVShowInput> d = fileManager.getTVShowsFileManager().loadInputTVShowsFromBinary();
        
            for (TVShowInput m : d) 
            {
                System.out.println(m);
            }
            
            //loadInputTVSeasonsFromText method
            System.out.println();
            System.out.println("loadInputTVSeasonsFromText method:");
            System.out.println();
            List<TVSeasonInput> e = fileManager.getTVSeasonsFileManager().loadInputTVSeasonsFromText();
        
            for (TVSeasonInput m : e) 
            {
                System.out.println(m);
            }
            
            //loadInputTVSeasonsFromBinary method
            System.out.println();
            System.out.println("loadInputTVSeasonsFromBinary method:");
            System.out.println();
            List<TVSeasonInput> f = fileManager.getTVSeasonsFileManager().loadInputTVSeasonsFromBinary();
        
            for (TVSeasonInput m : f) 
            {
                System.out.println(m);
            }
            
            //loadInputTVEpisodesFromText method
            System.out.println();
            System.out.println("loadInputTVEpisodesFromText method:");
            System.out.println();
            List<TVEpisodeInput> g = fileManager.getTVEpisodesFileManager().loadInputTVEpisodesFromText();
        
            for (TVEpisodeInput m : g) 
            {
                System.out.println(m);
            }
            
            //loadInputTVEpisodesFromBinary method
            System.out.println();
            System.out.println("loadInputTVEpisodesFromBinary method:");
            System.out.println();
            List<TVEpisodeInput> h = fileManager.getTVEpisodesFileManager().loadInputTVEpisodesFromBinary();
        
            for (TVEpisodeInput m : h) 
            {
                System.out.println(m);
            }
            
            //saveMoviesIntoTextAndBinary method
            System.out.println();
            System.out.println("saveMoviesIntoTextAndBinary method:");
            System.out.println();
            
            MovieOutput movie1 = new MovieOutput(2, 222, "Jeníček", 40, 
                    "https://example2.com", "ahíj\náahoj", 2222, "DAWN_OF_THE_JEDI");
            MovieOutput movie2 = new MovieOutput(1, 222, "Mařenka", 100, 
                    "https://example.com", "ahoj\nahoj", 2222222, "DAWN_OF_THE_JEDI");
            
            List<MovieOutput> list = new ArrayList<>();
            list.add(movie2);
            list.add(movie1);
            
            fileManager.getMoviesFileManager().saveMoviesIntoTextAndBinary(list);
            
            StringBuilder binaryTest = fileManager.getBinaryFileContent(DataStore.getBinaryOutputMoviesFilename());
            StringBuilder textTest = fileManager.getTextFileContent(DataStore.getTextOutputMoviesFilename());
            
            System.out.println();
            System.out.println("Vypis output_movies.txt");
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis output_movies.bin");
            System.out.println();
            
            System.out.println(binaryTest);
            
            //makeCopyOfMoviesInTextAndBinary method
            System.out.println();
            System.out.println("makeCopyOfMoviesInTextAndBinary method:");
            System.out.println();
            
            fileManager.getMoviesFileManager().makeCopyOfMoviesInTextAndBinary();
        }
        catch (Exception e) 
        {
            System.out.println("chyba");
        }
    }
}
