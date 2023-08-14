package tests.mainmethods;

import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.input.MovieInput;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
import app.models.input.TVShowInput;
import app.models.inputoutput.MovieInputOutput;
import app.models.inputoutput.TVEpisodeInputOutput;
import app.models.inputoutput.TVSeasonInputOutput;
import app.models.inputoutput.TVShowInputOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;

/**
 * Represents a custom unit test class for testing file manager service of file manager module.
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
            
            //saveInputOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveInputOutputDataIntoFiles method:");
            System.out.println();
            
            MovieInputOutput movie1 = new MovieInputOutput(2, 222, "Jeníček", 40, 
                    "https://example2.com", "ahíj\náahoj", 2222, "DAWN_OF_THE_JEDI");
            MovieInputOutput movie2 = new MovieInputOutput(1, 222, "Mařenka", 100, 
                    "https://example.com", "ahoj\nahoj", 2222222, "DAWN_OF_THE_JEDI");
            
            List<MovieInputOutput> list = new ArrayList<>();
            list.add(movie2);
            list.add(movie1);
            
            fileManager.getMoviesFileManager().saveInputOutputDataIntoFiles(list);
            
            StringBuilder binaryTest = fileManager.getMoviesFileManager().getBinaryInputOutputFileContent();
            StringBuilder textTest = fileManager.getMoviesFileManager().getTextInputOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextInputOutputMoviesFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryInputOutputMoviesFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from inputOutputFiles):");
            System.out.println();
            
            fileManager.getMoviesFileManager().transferBetweenInputOutputDataAndCopyFiles(false);
                        
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getMoviesFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            //tryDeleteDataInputOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataInputOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getMoviesFileManager().tryDeleteDataInputOutputFilesCopies();
            
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from text:");
            System.out.println();
            
            List<MovieInputOutput> inputOutputTextMovies = fileManager.getMoviesFileManager().loadInputOutputDataFrom(false);
            
            for (MovieInputOutput m : inputOutputTextMovies) 
            {
                System.out.println(m);
            }
            
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from binary:");
            System.out.println();
            
            List<MovieInputOutput> inputOutputBinaryMovies = fileManager.getMoviesFileManager().loadInputOutputDataFrom(true);
            
            for (MovieInputOutput m : inputOutputBinaryMovies) 
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
            
            //saveInputOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveInputOutputDataIntoFiles method:");
            System.out.println();
            
            TVEpisodeInputOutput tvEpisode1 = new TVEpisodeInputOutput(2, 222, "Jeníček", 40, 
                    "https://example2.com", "ahíj\náahoj", 1, 1);
            TVEpisodeInputOutput tvEpisode2 = new TVEpisodeInputOutput(1, 222, "Mařenka", 100, 
                    "https://example.com", "ahoj\nahoj", 2, 1);
            
            List<TVEpisodeInputOutput> listEpisodes = new ArrayList<>();
            listEpisodes.add(tvEpisode1);
            listEpisodes.add(tvEpisode2);
            
            fileManager.getTVEpisodesFileManager().saveInputOutputDataIntoFiles(listEpisodes);
            
            binaryTest = fileManager.getTVEpisodesFileManager().getBinaryInputOutputFileContent();
            textTest = fileManager.getTVEpisodesFileManager().getTextInputOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextInputOutputTVEpisodesFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryInputOutputTVEpisodesFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from inputOutputFiles):");
            System.out.println();
            
            fileManager.getTVEpisodesFileManager().transferBetweenInputOutputDataAndCopyFiles(false);
                        
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getTVEpisodesFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            //tryDeleteDataInputOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataInputOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getTVEpisodesFileManager().tryDeleteDataInputOutputFilesCopies();
                        
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from text:");
            System.out.println();
            
            List<TVEpisodeInputOutput> inputOutputTextTVEpisodes = fileManager.getTVEpisodesFileManager().loadInputOutputDataFrom(false);
            
            for (TVEpisodeInputOutput m : inputOutputTextTVEpisodes) 
            {
                System.out.println(m);
            }
            
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from binary:");
            System.out.println();
            
            List<TVEpisodeInputOutput> inputOutputBinaryTVEpisodes = fileManager.getTVEpisodesFileManager().loadInputOutputDataFrom(true);
            
            for (TVEpisodeInputOutput m : inputOutputBinaryTVEpisodes) 
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
            
            //saveInputOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveInputOutputDataIntoFiles method:");
            System.out.println();
            
            TVSeasonInputOutput tvSeason1 = new TVSeasonInputOutput(2, 1, 1);
            TVSeasonInputOutput tvSeason2 = new TVSeasonInputOutput(1, 3, 1);
            
            List<TVSeasonInputOutput> listSeasons = new ArrayList<>();
            listSeasons.add(tvSeason1);
            listSeasons.add(tvSeason2);
            
            fileManager.getTVSeasonsFileManager().saveInputOutputDataIntoFiles(listSeasons);
            
            binaryTest = fileManager.getTVSeasonsFileManager().getBinaryInputOutputFileContent();
            textTest = fileManager.getTVSeasonsFileManager().getTextInputOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextInputOutputTVSeasonsFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryInputOutputTVSeasonsFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from inputOutputFiles):");
            System.out.println();
            
            fileManager.getTVSeasonsFileManager().transferBetweenInputOutputDataAndCopyFiles(false);
                        
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getTVSeasonsFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            //tryDeleteDataInputOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataInputOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getTVSeasonsFileManager().tryDeleteDataInputOutputFilesCopies();
            
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from text:");
            System.out.println();
            
            List<TVSeasonInputOutput> inputOutputTextTVSeasons = fileManager.getTVSeasonsFileManager().loadInputOutputDataFrom(false);
            
            for (TVSeasonInputOutput m : inputOutputTextTVSeasons) 
            {
                System.out.println(m);
            }
            
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from binary:");
            System.out.println();
            
            List<TVSeasonInputOutput> inputOutputBinaryTVSeasons = fileManager.getTVSeasonsFileManager().loadInputOutputDataFrom(true);
            
            for (TVSeasonInputOutput m : inputOutputBinaryTVSeasons) 
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
            
            //saveInputOutputDataIntoFiles method
            System.out.println();
            System.out.println("saveInputOutputDataIntoFiles method:");
            System.out.println();
            
            TVShowInputOutput tvShow1 = new TVShowInputOutput(2, "Jeníček", 2, "FALL_OF_THE_JEDI");
            TVShowInputOutput tvShow2 = new TVShowInputOutput(1, "Mařenka", 5, "FALL_OF_THE_JEDI");
            
            List<TVShowInputOutput> listShows = new ArrayList<>();
            listShows.add(tvShow1);
            listShows.add(tvShow2);
            
            fileManager.getTVShowsFileManager().saveInputOutputDataIntoFiles(listShows);
            
            binaryTest = fileManager.getTVShowsFileManager().getBinaryInputOutputFileContent();
            textTest = fileManager.getTVShowsFileManager().getTextInputOutputFileContent();
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getTextInputOutputTVShowsFilename());
            System.out.println();
            
            System.out.println(textTest);
            
            System.out.println();
            System.out.println("Vypis " + DataStore.getBinaryInputOutputTVShowsFilename());
            System.out.println();
            
            System.out.println(binaryTest);
            
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from inputOutputFiles):");
            System.out.println();
            
            fileManager.getTVShowsFileManager().transferBetweenInputOutputDataAndCopyFiles(false);
                        
            //transferBetweenInputOutputDataAndCopyFiles method
            System.out.println();
            System.out.println("transferBetweenInputOutputDataAndCopyFiles method (from copyFiles):");
            System.out.println();
            
            fileManager.getTVShowsFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            //tryDeleteDataInputOutputFilesCopies method
            System.out.println();
            System.out.println("tryDeleteDataInputOutputFilesCopies method:");
            System.out.println();
            
            fileManager.getTVShowsFileManager().tryDeleteDataInputOutputFilesCopies();
            
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from text:");
            System.out.println();
            
            List<TVShowInputOutput> inputOutputTextTVShows = fileManager.getTVShowsFileManager().loadInputOutputDataFrom(false);
            
            for (TVShowInputOutput m : inputOutputTextTVShows) 
            {
                System.out.println(m);
            }
            
            //loadInputOutputDataFrom method
            System.out.println();
            System.out.println("loadInputOutputDataFrom method from binary:");
            System.out.println();
            
            List<TVShowInputOutput> inputOutputBinaryTVShows = fileManager.getTVShowsFileManager().loadInputOutputDataFrom(true);
            
            for (TVShowInputOutput m : inputOutputBinaryTVShows) 
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
