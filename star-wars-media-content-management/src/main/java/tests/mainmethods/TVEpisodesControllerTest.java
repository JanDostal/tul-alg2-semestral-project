
package tests.mainmethods;

import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utils.interfaces.IDataTable;

/**
 *
 * @author Admin
 */
public class TVEpisodesControllerTest 
{
    public static void main(String[] args) 
    {
        DataContextAccessor dbContext = DataContextAccessor.getInstance();
        IDataTable<TVShow> showsTable = dbContext.getTVShowsTable();
        IDataTable<TVSeason> seasonsTable = dbContext.getTVSeasonsTable();
        IDataTable<TVEpisode> episodesTable = dbContext.getTVEpisodesTable();
        
        TVEpisodesController controller = TVEpisodesController.getInstance(dbContext);
        
        TVShow show = new TVShow(new PrimaryKey(1), "show", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        
        TVShow show2 = new TVShow(new PrimaryKey(2), "show2", LocalDate.parse("2023-05-20", 
                DateTimeFormatter.ISO_LOCAL_DATE), Era.AGE_OF_THE_REBELLION);
        
        TVSeason season = new TVSeason(new PrimaryKey(1), 2, show.getPrimaryKey());
        TVSeason season2 = new TVSeason(new PrimaryKey(2), 3, show.getPrimaryKey());
        TVSeason season3 = new TVSeason(new PrimaryKey(3), 3, show2.getPrimaryKey());
        
        
        TVEpisode episodeA = new TVEpisode(new PrimaryKey(3), Duration.ofMinutes(50), "episodeA", 
                60, true, "https://www.example02.com", "Velmi špatná epizoda", 1, season.getPrimaryKey());
        
        TVEpisode episodeB = new TVEpisode(new PrimaryKey(4), Duration.ofMinutes(80), "episodeB", 
                60, true, "https://www.example03.com", "adas", 2, season.getPrimaryKey());
        
        TVEpisode episodeC = new TVEpisode(new PrimaryKey(5), Duration.ofMinutes(100), "episodeC", 
                60, true, "https://www.example04.com", "adasasd", 1, season2.getPrimaryKey());
        
        TVEpisode episodeD = new TVEpisode(new PrimaryKey(6), Duration.ofMinutes(900), "episodeD", 
                60, true, "https://www.example05.com", "sada", 1, season3.getPrimaryKey());
        
        showsTable.loadFrom(show);
        showsTable.loadFrom(show2);
        seasonsTable.loadFrom(season);
        seasonsTable.loadFrom(season2);
        seasonsTable.loadFrom(season3);
        episodesTable.loadFrom(episodeA);
        episodesTable.loadFrom(episodeB);
        episodesTable.loadFrom(episodeC);
        episodesTable.loadFrom(episodeD);
        
        System.out.println();
        System.out.println("Kontrolni vypis:");
        System.out.println();
        
        List<TVShow> shows = showsTable.getAll();
        List<TVSeason> seasons = seasonsTable.getAll();
        List<TVEpisode> episodes = episodesTable.getAll();
        
        for (TVShow s : shows) 
        {
            System.out.println(s);
        }
        
        for (TVSeason s : seasons) 
        {
            System.out.println(s);
        }
        
        for (TVEpisode s : episodes) 
        {
            System.out.println(s);
        }
        
        //getTVShowSeasonLongestEpisodes method
        
        List<TVEpisode> getTVShowSeasonLongestEpisodes_result = 
                controller.getTVShowSeasonLongestEpisodes(season.getPrimaryKey());
        
        System.out.println();
        System.out.println("getTVShowSeasonLongestEpisodes method:");
        System.out.println();
        
        for (TVEpisode m : getTVShowSeasonLongestEpisodes_result) 
        {
            System.out.println(m);
        }
       
                        
        //getTVShowLongestEpisodes method
        
        List<TVEpisode> getTVShowLongestEpisodes_result = 
                controller.getTVShowLongestEpisodes(show.getPrimaryKey());
        
        System.out.println();
        System.out.println("getTVShowLongestEpisodes method:");
        System.out.println();
        
        for (TVEpisode m : getTVShowLongestEpisodes_result) 
        {
            System.out.println(m);
        }
        
        //getLongestTVShowsByEra method
        
        List<TVShow> getLongestTVShowsByEra_result = 
                controller.getLongestTVShowsByEra(Era.AGE_OF_THE_REBELLION);
        
        System.out.println();
        System.out.println("getLongestTVShowsByEra method:");
        System.out.println();
        
        for (TVShow m : getLongestTVShowsByEra_result) 
        {
            System.out.println(m);
        }
                
    }
}
