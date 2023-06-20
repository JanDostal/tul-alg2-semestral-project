package tests.mainmethods;

import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.input.MovieInput;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
import app.models.input.TVShowInput;
import app.models.output.MovieOutput;
import app.models.output.TVEpisodeOutput;
import app.models.output.TVSeasonOutput;
import app.models.output.TVShowOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;

/**
 * Represents a custom unit test class for testing file manager service of file manager module
 * @author jan.dostal
 */
public class FileManagerAccessorTest 
{
    //testing main
    public static void main(String[] args)
    {
        FileManagerAccessor fileManager = FileManagerAccessor.getInstance();

        try 
        {
            FileManagerAccessor.setDataDirectory(DataStore.getDataDirectoryName());
            String path = FileManagerAccessor.getDataDirectoryPath();
            
            //getDataDirectoryPath method
            System.out.println();
            System.out.println("getDataDirectoryPath method (celá úplná cesta):");
            System.out.println();
                        
            System.out.println(path);
            
            //getBinaryFileContent method
            System.out.println();
            System.out.println("getBinaryFileContent method (obsah " + 
                    DataStore.getBinaryInputMoviesFilename() + "):");
            System.out.println();
            
            StringBuilder moviesBinaryFile = fileManager.getMoviesFileManager().getBinaryInputFileContent();
            
            System.out.println(moviesBinaryFile);
            
            //getTextFileContent method
            System.out.println();
            System.out.println("getTextFileContent method (obsah " + 
                    DataStore.getTextInputMoviesFilename() + "):");
            System.out.println();
            
            StringBuilder moviesTextFile = fileManager.getMoviesFileManager().getTextInputFileContent();
            
            System.out.println(moviesTextFile);
            
            //movies category
            System.out.println();
            System.out.println("movies category:");
            System.out.println();
            
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from text:");
            System.out.println();
            Map<Integer, MovieInput> a = fileManager.getMoviesFileManager().loadInputDataFrom(false);
        
            for (Map.Entry<Integer, MovieInput> m : a.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
                        
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from binary:");
            System.out.println();
            Map<Integer, MovieInput> b = fileManager.getMoviesFileManager().loadInputDataFrom(true);
        
            for (Map.Entry<Integer, MovieInput> m : b.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
            
            //saveOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveOutputDataIntoFiles method:");
            System.out.println();
            
            MovieOutput movie1 = new MovieOutput(2, 222, "Jeníček", 40, 
                    "https://example2.com", "ahíj\náahoj", 2222, "DAWN_OF_THE_JEDI");
            MovieOutput movie2 = new MovieOutput(1, 222, "Mařenka", 100, 
                    "https://example.com", "ahoj\nahoj", 2222222, "DAWN_OF_THE_JEDI");
            
            List<MovieOutput> list = new ArrayList<>();
            list.add(movie2);
            list.add(movie1);
            
            fileManager.getMoviesFileManager().saveOutputDataIntoFiles(list);
            
            StringBuilder binaryTest = fileManager.getMoviesFileManager().getBinaryOutputFileContent();
            StringBuilder textTest = fileManager.getMoviesFileManager().getTextOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextOutputMoviesFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryOutputMoviesFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from outputFiles):");
            System.out.println();
            
            fileManager.getMoviesFileManager().transferBetweenOutputDataAndCopyFiles(false);
                        
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getMoviesFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            //tryDeleteDataOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getMoviesFileManager().tryDeleteDataOutputFilesCopies();
            
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from text:");
            System.out.println();
            
            List<MovieOutput> outputTextMovies = fileManager.getMoviesFileManager().loadOutputDataFrom(false);
            
            for (MovieOutput m : outputTextMovies) 
            {
                System.out.println(m);
            }
            
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from binary:");
            System.out.println();
            
            List<MovieOutput> outputBinaryMovies = fileManager.getMoviesFileManager().loadOutputDataFrom(true);
            
            for (MovieOutput m : outputBinaryMovies) 
            {
                System.out.println(m);
            }
            
            //tvEpisodes category
            System.out.println();
            System.out.println("tvEpisodes category:");
            System.out.println();
            
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from text:");
            System.out.println();
            Map<Integer, TVEpisodeInput> ooo = fileManager.getTVEpisodesFileManager().loadInputDataFrom(false);
        
            for (Map.Entry<Integer, TVEpisodeInput> m : ooo.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
                        
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from binary:");
            System.out.println();
            Map<Integer, TVEpisodeInput> ooo2 = fileManager.getTVEpisodesFileManager().loadInputDataFrom(true);
        
            for (Map.Entry<Integer, TVEpisodeInput> m : ooo2.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
            
            //saveOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveOutputDataIntoFiles method:");
            System.out.println();
            
            TVEpisodeOutput tvEpisode1 = new TVEpisodeOutput(2, 222, "Jeníček", 40, 
                    "https://example2.com", "ahíj\náahoj", 1, 1);
            TVEpisodeOutput tvEpisode2 = new TVEpisodeOutput(1, 222, "Mařenka", 100, 
                    "https://example.com", "ahoj\nahoj", 2, 1);
            
            List<TVEpisodeOutput> listEpisodes = new ArrayList<>();
            listEpisodes.add(tvEpisode1);
            listEpisodes.add(tvEpisode2);
            
            fileManager.getTVEpisodesFileManager().saveOutputDataIntoFiles(listEpisodes);
            
            binaryTest = fileManager.getTVEpisodesFileManager().getBinaryOutputFileContent();
            textTest = fileManager.getTVEpisodesFileManager().getTextOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextOutputTVEpisodesFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryOutputTVEpisodesFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from outputFiles):");
            System.out.println();
            
            fileManager.getTVEpisodesFileManager().transferBetweenOutputDataAndCopyFiles(false);
                        
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getTVEpisodesFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            //tryDeleteDataOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getTVEpisodesFileManager().tryDeleteDataOutputFilesCopies();
                        
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from text:");
            System.out.println();
            
            List<TVEpisodeOutput> outputTextTVEpisodes = fileManager.getTVEpisodesFileManager().loadOutputDataFrom(false);
            
            for (TVEpisodeOutput m : outputTextTVEpisodes) 
            {
                System.out.println(m);
            }
            
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from binary:");
            System.out.println();
            
            List<TVEpisodeOutput> outputBinaryTVEpisodes = fileManager.getTVEpisodesFileManager().loadOutputDataFrom(true);
            
            for (TVEpisodeOutput m : outputBinaryTVEpisodes) 
            {
                System.out.println(m);
            }
            
            //tvSeasons category
            System.out.println();
            System.out.println("tvSeasons category:");
            System.out.println();
            
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from text:");
            System.out.println();
            Map<Integer, TVSeasonInput> ooo3 = fileManager.getTVSeasonsFileManager().loadInputDataFrom(false);
        
            for (Map.Entry<Integer, TVSeasonInput> m : ooo3.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
                        
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from binary:");
            System.out.println();
            Map<Integer, TVSeasonInput> ooo4 = fileManager.getTVSeasonsFileManager().loadInputDataFrom(true);
        
            for (Map.Entry<Integer, TVSeasonInput> m : ooo4.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
            
            //saveOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveOutputDataIntoFiles method:");
            System.out.println();
            
            TVSeasonOutput tvSeason1 = new TVSeasonOutput(2, 1, 1);
            TVSeasonOutput tvSeason2 = new TVSeasonOutput(1, 3, 1);
            
            List<TVSeasonOutput> listSeasons = new ArrayList<>();
            listSeasons.add(tvSeason1);
            listSeasons.add(tvSeason2);
            
            fileManager.getTVSeasonsFileManager().saveOutputDataIntoFiles(listSeasons);
            
            binaryTest = fileManager.getTVSeasonsFileManager().getBinaryOutputFileContent();
            textTest = fileManager.getTVSeasonsFileManager().getTextOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextOutputTVSeasonsFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryOutputTVSeasonsFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from outputFiles):");
            System.out.println();
            
            fileManager.getTVSeasonsFileManager().transferBetweenOutputDataAndCopyFiles(false);
                        
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getTVSeasonsFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            //tryDeleteDataOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getTVSeasonsFileManager().tryDeleteDataOutputFilesCopies();
            
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from text:");
            System.out.println();
            
            List<TVSeasonOutput> outputTextTVSeasons = fileManager.getTVSeasonsFileManager().loadOutputDataFrom(false);
            
            for (TVSeasonOutput m : outputTextTVSeasons) 
            {
                System.out.println(m);
            }
            
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from binary:");
            System.out.println();
            
            List<TVSeasonOutput> outputBinaryTVSeasons = fileManager.getTVSeasonsFileManager().loadOutputDataFrom(true);
            
            for (TVSeasonOutput m : outputBinaryTVSeasons) 
            {
                System.out.println(m);
            }
            
            //tvShows category
            System.out.println();
            System.out.println("tvShows category:");
            System.out.println();
            
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from text:");
            System.out.println();
            Map<Integer, TVShowInput> ooo5 = fileManager.getTVShowsFileManager().loadInputDataFrom(false);
        
            for (Map.Entry<Integer, TVShowInput> m : ooo5.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
                        
            //loadInputDataFrom method
            System.out.println();
            System.out.println("loadInputDataFrom method from binary:");
            System.out.println();
            Map<Integer, TVShowInput> ooo6 = fileManager.getTVShowsFileManager().loadInputDataFrom(true);
        
            for (Map.Entry<Integer, TVShowInput> m : ooo6.entrySet()) 
            {
                System.out.println("Pořadí v souboru: " + m.getKey() + " " + m.getValue());
            }
            
            //saveOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveOutputDataIntoFiles method:");
            System.out.println();
            
            TVShowOutput tvShow1 = new TVShowOutput(2, "Jeníček", 2, "FALL_OF_THE_JEDI");
            TVShowOutput tvShow2 = new TVShowOutput(1, "Mařenka", 5, "FALL_OF_THE_JEDI");
            
            List<TVShowOutput> listShows = new ArrayList<>();
            listShows.add(tvShow1);
            listShows.add(tvShow2);
            
            fileManager.getTVShowsFileManager().saveOutputDataIntoFiles(listShows);
            
            binaryTest = fileManager.getTVShowsFileManager().getBinaryOutputFileContent();
            textTest = fileManager.getTVShowsFileManager().getTextOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextOutputTVShowsFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryOutputTVShowsFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from outputFiles):");
            System.out.println();
            
            fileManager.getTVShowsFileManager().transferBetweenOutputDataAndCopyFiles(false);
                        
            //transferBetweenOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getTVShowsFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            //tryDeleteDataOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getTVShowsFileManager().tryDeleteDataOutputFilesCopies();
            
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from text:");
            System.out.println();
            
            List<TVShowOutput> outputTextTVShows = fileManager.getTVShowsFileManager().loadOutputDataFrom(false);
            
            for (TVShowOutput m : outputTextTVShows) 
            {
                System.out.println(m);
            }
            
            //loadOutputDataFrom method
            System.out.println();
            System.out.println("loadOutputDataFrom method from binary:");
            System.out.println();
            
            List<TVShowOutput> outputBinaryTVShows = fileManager.getTVShowsFileManager().loadOutputDataFrom(true);
            
            for (TVShowOutput m : outputBinaryTVShows) 
            {
                System.out.println(m);
            }
            
        }
        catch (FileNotFoundException o) 
        {
            System.out.println(o.getMessage());
        }
        catch (IOException | FileEmptyException | FileParsingException | IllegalArgumentException | IllegalStateException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
