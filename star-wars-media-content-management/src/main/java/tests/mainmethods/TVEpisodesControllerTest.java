
package tests.mainmethods;

import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utils.emailsender.EmailSender;
import utils.exceptions.DataConversionException;
import utils.exceptions.DatabaseException;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;
import utils.interfaces.IDataTable;

/**
 * Represents a custom unit test class for testing TVEpisodes controller of controllers module.
 * @author jan.dostal
 */
public class TVEpisodesControllerTest 
{
    //testing main
    public static void main(String[] args) 
    {
//        DataContextAccessor dbContext = DataContextAccessor.getInstance();
//        IDataTable<TVShow> showsTable = dbContext.getTVShowsTable();
//        IDataTable<TVSeason> seasonsTable = dbContext.getTVSeasonsTable();
//        IDataTable<TVEpisode> episodesTable = dbContext.getTVEpisodesTable();
//        
//        EmailSender emailSender = EmailSender.getInstance();
//        FileManagerAccessor fileManager = FileManagerAccessor.getInstance();
//        
//        try 
//        {
//            FileManagerAccessor.setDataDirectory(DataStore.getDataDirectoryName());
//        }
//        catch (IllegalArgumentException | IllegalStateException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        TVEpisodesController controller = TVEpisodesController.getInstance(dbContext, emailSender, fileManager);
//        
//        TVShow show = new TVShow(new PrimaryKey(1), "show", LocalDate.parse("2023-05-23", 
//                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
//        
//        TVShow show2 = new TVShow(new PrimaryKey(2), "show2", LocalDate.parse("2023-05-20", 
//                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
//        
//        TVShow show3 = new TVShow(new PrimaryKey(3), "show3", LocalDate.parse("2023-05-25", 
//                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
//        
//        TVSeason season = new TVSeason(new PrimaryKey(1), 2, show.getPrimaryKey());
//        TVSeason season2 = new TVSeason(new PrimaryKey(2), 3, show.getPrimaryKey());
//        TVSeason season3 = new TVSeason(new PrimaryKey(3), 3, show2.getPrimaryKey());
//        TVSeason season4 = new TVSeason(new PrimaryKey(4), 3, show3.getPrimaryKey());
//        
//        
//        TVEpisode episodeA = new TVEpisode(new PrimaryKey(3), Duration.ofMinutes(50), "episodeA", 
//                60, true, "https://www.example02.com", "very bad episode", 1, season.getPrimaryKey());
//        
//        TVEpisode episodeB = new TVEpisode(new PrimaryKey(4), Duration.ofMinutes(80), "episodeB", 
//                50, true, "https://www.example03.com", "adas", 2, season.getPrimaryKey());
//        
//        TVEpisode episodeC = new TVEpisode(new PrimaryKey(5), Duration.ofMinutes(100), "episodeC", 
//                40, true, "https://www.example04.com", "adasasd", 1, season2.getPrimaryKey());
//        
//        TVEpisode episodeD = new TVEpisode(new PrimaryKey(6), Duration.ofMinutes(900), "episodeD", 
//                60, true, "https://www.example05.com", "sada", 1, season3.getPrimaryKey());
//        
//        TVEpisode episodeE = new TVEpisode(new PrimaryKey(7), Duration.ofMinutes(500), "episodeE", 
//                50, true, "https://www.example06.com", "sadasdssadssadaadsasd", 1, season4.getPrimaryKey());
//        
//        try 
//        {
//            showsTable.loadFrom(show2);
//            showsTable.loadFrom(show);
//            showsTable.loadFrom(show3);
//            seasonsTable.loadFrom(season);
//            seasonsTable.loadFrom(season3);
//            seasonsTable.loadFrom(season4);
//            seasonsTable.loadFrom(season2);
//            episodesTable.loadFrom(episodeB);
//            episodesTable.loadFrom(episodeC);
//            episodesTable.loadFrom(episodeE);
//            episodesTable.loadFrom(episodeA);
//            episodesTable.loadFrom(episodeD);
//        }
//        catch (DatabaseException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        System.out.println();
//        System.out.println("Printed results for checking correctness:");
//        System.out.println();
//        
//        List<TVShow> shows = showsTable.getAll();
//        List<TVSeason> seasons = seasonsTable.getAll();
//        List<TVEpisode> episodes = episodesTable.getAll();
//        
//        for (TVShow s : shows) 
//        {
//            System.out.println(s);
//        }
//        
//        for (TVSeason s : seasons) 
//        {
//            System.out.println(s);
//        }
//        
//        for (TVEpisode s : episodes) 
//        {
//            System.out.println(s);
//        }
//        
//        //getReleasedTVShowSeasonLongestEpisodes method
//        
//        List<TVEpisode> getTVShowSeasonLongestEpisodes_result = 
//                controller.getReleasedTVShowSeasonLongestEpisodes(season.getPrimaryKey());
//        
//        System.out.println();
//        System.out.println("getReleasedTVShowSeasonLongestEpisodes method:");
//        System.out.println();
//        
//        for (TVEpisode m : getTVShowSeasonLongestEpisodes_result) 
//        {
//            System.out.println(m);
//        }
//      
//        //getReleasedTVShowLongestEpisodes method
//        
//        List<TVEpisode> getTVShowLongestEpisodes_result = 
//                controller.getReleasedTVShowLongestEpisodes(show.getPrimaryKey());
//        
//        System.out.println();
//        System.out.println("getReleasedTVShowLongestEpisodes method:");
//        System.out.println();
//        
//        for (TVEpisode m : getTVShowLongestEpisodes_result) 
//        {
//            System.out.println(m);
//        }
//        
//        //getReleasedNewestTVShows method
//        
//        List<TVShow> getNewestReleasedTVShows_result = 
//                controller.getReleasedNewestTVShows();
//        
//        System.out.println();
//        System.out.println("getReleasedNewestTVShows method:");
//        System.out.println();
//                
//        for (TVShow s : getNewestReleasedTVShows_result) 
//        {
//            System.out.println(s);
//        }
//                
//        //getReleasedLongestTVShowsByEra method
//        
//        List<TVShow> getLongestTVShowsByEra_result = 
//                controller.getReleasedLongestTVShowsByEra(Era.AGE_OF_REBELLION);
//        
//        System.out.println();
//        System.out.println("getReleasedLongestTVShowsByEra method:");
//        System.out.println();
//                
//        for (TVShow s : getLongestTVShowsByEra_result) 
//        {
//            System.out.println(s);
//        }
//        
//        //getTotalRuntimeOfAllReleasedEpisodesInTVShow method
//        
//        Duration getTotalRuntimeOfAllEpisodesInTVShow_result = 
//                controller.getTotalRuntimeOfAllReleasedEpisodesInTVShow(show.getPrimaryKey(), true);
//        
//        System.out.println();
//        System.out.println("getTotalRuntimeOfAllReleasedEpisodesInTVShow method:");
//        System.out.println();
//        
//        int watchedShowEpisodesCount_total = controller.getReleasedTVShowEpisodesCount(show.getPrimaryKey(), true);
//        
//        System.out.println("Total runtime of watched content in minutes: " 
//                + getTotalRuntimeOfAllEpisodesInTVShow_result.toMinutes());
//        System.out.println("Count of watched TV episodes: "
//                + watchedShowEpisodesCount_total);
//        
//        //getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason method
//        
//        Duration getTotalRuntimeOfAllEpisodesInTVShowSeason_result = 
//                controller.getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(season.getPrimaryKey(), true);
//        
//        System.out.println();
//        System.out.println("getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason method:");
//        System.out.println();
//        
//        int watchedSeasonEpisodesCount_total = 
//                controller.getReleasedTVShowSeasonEpisodesCount(season.getPrimaryKey(), true);
//        
//        System.out.println("Total runtime of watched content in minutes: " + 
//                getTotalRuntimeOfAllEpisodesInTVShowSeason_result.toMinutes());
//        System.out.println("Count of watched TV episodes: "
//                + watchedSeasonEpisodesCount_total);
//        
//        //getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason method
//        
//        Duration getAverageRuntimeOfAllEpisodesInTVShowSeason_result = 
//                controller.getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason(season.getPrimaryKey(), true);
//        
//        System.out.println();
//        System.out.println("getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason method:");
//        System.out.println();
//        
//        int watchedSeasonEpisodesCount_average = 
//                controller.getReleasedTVShowSeasonEpisodesCount(season.getPrimaryKey(), true);
//        
//        System.out.println("Average runtime of watched content in minutes: " + 
//                getAverageRuntimeOfAllEpisodesInTVShowSeason_result.toMinutes());
//        System.out.println("Count of watched TV episodes: "
//                + watchedSeasonEpisodesCount_average);
//        
//        //getAverageRuntimeOfAllReleasedEpisodesInTVShow method
//        
//        Duration getAverageRuntimeOfAllEpisodesInTVShow_result = 
//                controller.getAverageRuntimeOfAllReleasedEpisodesInTVShow(show.getPrimaryKey(), true);
//        
//        System.out.println();
//        System.out.println("getAverageRuntimeOfAllReleasedEpisodesInTVShow method:");
//        System.out.println();
//        
//        int watchedShowEpisodesCount_average = controller.getReleasedTVShowEpisodesCount(show.getPrimaryKey(), true);
//        
//        System.out.println("Average runtime of watched content in minutes: " + 
//                getAverageRuntimeOfAllEpisodesInTVShow_result.toMinutes());
//        System.out.println("Count of watched TV episodes: "
//                + watchedShowEpisodesCount_average);
//        
//        //getAverageRatingOfAllReleasedEpisodesInTVShow method
//        
//        float getAverageRatingOfAllEpisodesInTVShow_result = 
//                controller.getAverageRatingOfAllReleasedEpisodesInTVShow(show.getPrimaryKey());
//        
//        System.out.println();
//        System.out.println("getAverageRatingOfAllReleasedEpisodesInTVShow method:");
//        System.out.println();
//        
//        System.out.println("Average rating in percents: " + 
//                getAverageRatingOfAllEpisodesInTVShow_result);
//        System.out.println("Count of rated TV episodes: " + 
//                controller.getReleasedTVShowEpisodesCount(show.getPrimaryKey(), true));
//        
//        //getAverageRatingOfAllReleasedEpisodesInTVShowSeason method
//        
//        float getAverageRatingOfAllEpisodesInTVShowSeason_result = 
//                controller.getAverageRatingOfAllReleasedEpisodesInTVShowSeason(season.getPrimaryKey());
//        
//        System.out.println();
//        System.out.println("getAverageRatingOfAllReleasedEpisodesInTVShowSeason method:");
//        System.out.println();
//        
//        System.out.println("Average rating in percents: " + 
//                getAverageRatingOfAllEpisodesInTVShowSeason_result);
//        System.out.println("Count of rated TV episodes: " + 
//                controller.getReleasedTVShowSeasonEpisodesCount(season.getPrimaryKey(), true));
//        
//        //addTVShowsFrom
//        System.out.println();
//        System.out.println("addTVShowsFrom method (text):");
//        System.out.println();
//        
//        try 
//        {            
//            StringBuilder message = controller.addTVShowsFrom(false);
//            
//            System.out.println(message.toString());
//            
//            List<TVShow> moviesList = showsTable.getAll();
//            
//            for (TVShow m : moviesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException l) 
//        {
//            System.out.println(l.getMessage());
//        }
//        catch (IOException | FileEmptyException | FileParsingException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //addTVSeasonsFrom
//        System.out.println();
//        System.out.println("addTVSeasonsFrom method (text):");
//        System.out.println();
//        
//        try 
//        {            
//            StringBuilder message = controller.addTVSeasonsFrom(show.getPrimaryKey(), false);
//            
//            System.out.println(message.toString());
//            
//            List<TVSeason> seasonsList = seasonsTable.getAll();
//            
//            for (TVSeason m : seasonsList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException l) 
//        {
//            System.out.println(l.getMessage());
//        }
//        catch (IOException | FileEmptyException | FileParsingException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //addTVEpisodesFrom
//        System.out.println();
//        System.out.println("addTVEpisodesFrom method (text):");
//        System.out.println();
//        
//        try 
//        {
//            StringBuilder message = controller.addTVEpisodesFrom(season3.getPrimaryKey(), false);
//            
//            System.out.println(message.toString());
//            
//            List<TVEpisode> episodesList = episodesTable.getAll();
//            
//            for (TVEpisode m : episodesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException l) 
//        {
//            System.out.println(l.getMessage());
//        }
//        catch (IOException | FileEmptyException | FileParsingException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //deleteTVShowBy
//        System.out.println();
//        System.out.println("deleteTVShowBy method :");
//        System.out.println();
//        
//        try 
//        {
//            controller.deleteTVShowBy(show.getPrimaryKey());
//            
//            List<TVShow> showsList = showsTable.getAll();
//            List<TVSeason> seasonsList = seasonsTable.getAll();
//            List<TVEpisode> episodesList = episodesTable.getAll();
//            
//            for (TVShow m : showsList) 
//            {
//                System.out.println(m);
//            }
//            
//            for (TVSeason m : seasonsList) 
//            {
//                System.out.println(m);
//            }
//            
//            for (TVEpisode m : episodesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (IOException | DatabaseException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //deleteTVShows
//        System.out.println();
//        System.out.println("deleteTVShows method :");
//        System.out.println();
//        
//        try 
//        {
//            controller.deleteTVShows(showsTable.getAll());
//            
//            List<TVShow> showsList = showsTable.getAll();
//            List<TVSeason> seasonsList = seasonsTable.getAll();
//            List<TVEpisode> episodesList = episodesTable.getAll();
//            
//            for (TVShow m : showsList) 
//            {
//                System.out.println(m);
//            }
//            
//            for (TVSeason m : seasonsList) 
//            {
//                System.out.println(m);
//            }
//            
//            for (TVEpisode m : episodesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (IOException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //editTVEpisodeBy
//        System.out.println();
//        System.out.println("editTVEpisodeBy method (text):");
//        System.out.println();
//        
//        TVShow showEdit = new TVShow(new PrimaryKey(3), "show33", LocalDate.parse("2023-05-20", 
//                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_REBELLION);
//        TVSeason seasonEdit = new TVSeason(new PrimaryKey(222), 10, showEdit.getPrimaryKey());
//        TVEpisode episodeForEdit = new TVEpisode(new PrimaryKey(21111), Duration.ofMinutes(900), "hellohello", 
//                2, false, null, null, 
//                55, seasonEdit.getPrimaryKey());
//              
//        try 
//        {
//            showsTable.loadFrom(showEdit);
//            seasonsTable.loadFrom(seasonEdit);
//            episodesTable.loadFrom(episodeForEdit);
//            
//            System.out.println(episodeForEdit);
//                                
//            boolean wasDataChangedForEdit = controller.editTVEpisodeBy(episodeForEdit.getPrimaryKey(), seasonEdit.getPrimaryKey(), false);
//            
//            System.out.println(wasDataChangedForEdit);
//            
//            List<TVEpisode> episodesList = episodesTable.getAll();
//            
//            for (TVEpisode m : episodesList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException l) 
//        {
//            System.out.println(l.getMessage());
//        }
//        catch (IOException | FileEmptyException | DatabaseException | FileParsingException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //editTVSeasonBy
//        System.out.println();
//        System.out.println("editTVSeasonBy method (text):");
//        System.out.println();
//        
//        try 
//        {
//            System.out.println(seasonEdit);
//            
//            boolean wasDataChangedForEdit = controller.editTVSeasonBy(seasonEdit.getPrimaryKey(), seasonEdit.getTVShowForeignKey(), false);
//            
//            System.out.println(wasDataChangedForEdit);
//            
//            List<TVSeason> seasonsList = seasonsTable.getAll();
//            
//            for (TVSeason m : seasonsList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException l) 
//        {
//            System.out.println(l.getMessage());
//        }
//        catch (IOException | FileEmptyException | DatabaseException | FileParsingException e) 
//        {
//            System.out.println(e.getMessage());
//        }
//        
//        //editTVShowBy
//        System.out.println();
//        System.out.println("editTVShowBy method (text):");
//        System.out.println();
//        
//        try 
//        {
//            System.out.println(showEdit);
//            
//            boolean wasDataChangedForEdit = controller.editTVShowBy(showEdit.getPrimaryKey(), false);
//            
//            System.out.println(wasDataChangedForEdit);
//            
//            List<TVShow> showsList = showsTable.getAll();
//            
//            for (TVShow m : showsList) 
//            {
//                System.out.println(m);
//            }
//        }
//        catch (FileNotFoundException l) 
//        {
//            System.out.println(l.getMessage());
//        }
//        catch (IOException | FileEmptyException | DatabaseException | FileParsingException | DataConversionException e) 
//        {
//            System.out.println(e.getMessage());
//        }
    }
}
