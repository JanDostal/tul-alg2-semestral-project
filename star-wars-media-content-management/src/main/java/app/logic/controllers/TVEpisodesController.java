
package app.logic.controllers;

import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.models.data.Era;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.text.Collator;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class TVEpisodesController 
{
    private static TVEpisodesController tvEpisodesController;
    
    private final DataContextAccessor dbContext;
    
    private final Collator czechCollator = DataStore.loadCzechCollator();
    
    private final Comparator<TVEpisode> BY_LONGEST_DURATION_EPISODE = (TVEpisode m1, TVEpisode m2) -> 
    {
        if (m1.getRuntime() == null && m2.getRuntime() == null) 
        {
            return 0;
        } 
        else if (m1.getRuntime() == null) 
        {
            return 1;
        } 
        else if (m2.getRuntime() == null) 
        {
            return -1;
        } 
        
        return m2.getRuntime().compareTo(m1.getRuntime());
    };

    private final Comparator<TVShow> BY_NAME_ALPHABETICALLY_SHOW = (TVShow m1, TVShow m2) -> 
            czechCollator.compare(m1.getName(), m2.getName());
    
    private final Comparator<TVEpisode> 
            BY_PERCENTAGE_RATING_HIGHEST_EPISODE = (TVEpisode m1, TVEpisode m2) -> 
    {        
        return m2.getPercentageRating() - m1.getPercentageRating();
    };
    
    private final Comparator<TVEpisode> 
            BY_ORDER_ASCENDING_EPISODE = (TVEpisode m1, TVEpisode m2) -> 
    {        
        return m1.getOrderInTVShowSeason() - m2.getOrderInTVShowSeason();
    };
    
    private final Comparator<TVSeason> 
            BY_ORDER_ASCENDING_SEASON = (TVSeason m1, TVSeason m2) -> 
    {        
        return m1.getOrderInTVShow()- m2.getOrderInTVShow();
    };
    
    private TVEpisodesController(DataContextAccessor dbContext) 
    {
        this.dbContext = dbContext;
    }
    
    public static TVEpisodesController getInstance(DataContextAccessor dbContext) 
    {
        if (tvEpisodesController == null) 
        {
            tvEpisodesController = new TVEpisodesController(dbContext);
        }
        
        return tvEpisodesController;
    }
    
    //statistic method
    public Duration getTotalRuntimeOfAllEpisodesInTVShow(PrimaryKey tvShowPrimaryKey) 
    {
        Duration duration = Duration.ZERO;
        
        List<TVEpisode> seasonEpisodes;
        
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        for (TVSeason season : showSeasons) 
        {
            seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(season.getPrimaryKey()));
            
            for (TVEpisode e : seasonEpisodes) 
            {
                if (e.getRuntime() != null) 
                {
                    duration = duration.plus(e.getRuntime());
                }
            }
        }
        
        return duration;
    }
    
    //statistic method
    public Duration getTotalRuntimeOfAllEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey) 
    {
        Duration duration = Duration.ZERO;
        
        List<TVEpisode> seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(s -> s.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        for (TVEpisode e : seasonEpisodes) 
        {
            if (e.getRuntime() != null) 
            {
                duration = duration.plus(e.getRuntime());
            }
        }
        
        return duration;
    }
    
    //statistic method
    public Duration getAverageRuntimeOfAllEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey) 
    {        
        Duration duration = Duration.ZERO;
        long averageSeconds;
        int durationsCount = 0;
        
        List<TVEpisode> seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(s -> s.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        for (TVEpisode e : seasonEpisodes) 
        {
            if (e.getRuntime() != null) 
            {
                durationsCount++;
                duration = duration.plus(e.getRuntime());
            }
        }
        
        averageSeconds = duration.toSeconds() / durationsCount;
        
        return Duration.ofSeconds(averageSeconds);
    }
    
    //statistic method
    public Duration getAverageRuntimeOfAllEpisodesInTVShow(PrimaryKey tvShowPrimaryKey) 
    {        
        Duration duration = Duration.ZERO;
        long averageSeconds;
        int durationsCount = 0;
        
        List<TVEpisode> seasonEpisodes;
        
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        for (TVSeason season : showSeasons) 
        {
            seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(season.getPrimaryKey()));
            
            for (TVEpisode e : seasonEpisodes) 
            {
                if (e.getRuntime() != null) 
                {
                    durationsCount++;
                    duration = duration.plus(e.getRuntime());
                }
            }
        }
        
        averageSeconds = duration.toSeconds() / durationsCount;
        
        return Duration.ofSeconds(averageSeconds);
    }
    
    //statistic method
    public float getAverageRatingOfAllEpisodesInTVShow(PrimaryKey tvShowPrimaryKey) 
    {        
        float averageRating;
        long totalRating = 0;
        int ratingsCount = 0;
        
        List<TVEpisode> seasonEpisodes;
        
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        for (TVSeason season : showSeasons) 
        {
            seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(season.getPrimaryKey()));
            
            for (TVEpisode e : seasonEpisodes) 
            {
                ratingsCount++;
                totalRating += e.getPercentageRating();
            }
        }
        
        averageRating = totalRating / (float) ratingsCount;
              
        return averageRating;
    }
    
    //statistic method
    public float getAverageRatingOfAllEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey) 
    {        
        float averageRating;
        long totalRating = 0;
                
        List<TVEpisode> seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        for (TVEpisode e : seasonEpisodes) 
        {
            totalRating += e.getPercentageRating();
        }
        
        averageRating = totalRating / (float) seasonEpisodes.size();
              
        return averageRating;
    }
    
    public Map<TVShow, Duration> getLongestTVShowsByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        List<TVSeason> showSeasons;
        List<TVEpisode> seasonEpisodes;
        Map<TVShow, Duration> filteredShowsWithDurations = new LinkedHashMap<>();
        
        List<TVShow> filteredShows = dbContext.getTVShowsTable().filterBy(s -> 
                s.getEra() == era && s.getReleaseDate() != null && 
                        s.getReleaseDate().compareTo(currentDate) <= 0);
        
        List<Duration> tvShowsDurations = new ArrayList<>();
        Duration showDuration;
        
        for (TVShow show : filteredShows) 
        {
            showDuration = Duration.ZERO;
            
            showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(show.getPrimaryKey()));
            
            for (TVSeason season : showSeasons) 
            {
                seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(s -> s.getTVSeasonForeignKey().equals(season.getPrimaryKey()));
                
                for (TVEpisode episode : seasonEpisodes) 
                {
                    if (episode.getRuntime() != null) 
                    {
                        showDuration = showDuration.plus(episode.getRuntime());
                    }
                }                
            }
            
            tvShowsDurations.add(showDuration);
        }
        
        Comparator<Object> s = Comparator.comparingLong(tvShow -> {
            int index = filteredShows.indexOf(tvShow);
            if (index >= 0 && index < tvShowsDurations.size()) {
                Duration duration = tvShowsDurations.get(index);
                return duration.toSeconds();
            }
            return 0;
        }).reversed();
        
        Collections.sort(filteredShows, s);
        Collections.sort(tvShowsDurations, Comparator.comparingLong(Duration::toSeconds).reversed());
        
        for (int i = 0; i < filteredShows.size(); i++) 
        {
            filteredShowsWithDurations.put(filteredShows.get(i), tvShowsDurations.get(i));
        }
        
        return filteredShowsWithDurations;
    }
    
    public List<TVEpisode> getTVShowLongestEpisodes(PrimaryKey tvShowPrimaryKey) 
    {
        List<TVEpisode> foundEpisodes = new ArrayList<>();
        List<TVEpisode> seasonEpisodes;
        
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        for (TVSeason season : showSeasons) 
        {
            seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(season.getPrimaryKey()));
            foundEpisodes.addAll(seasonEpisodes);
        }
        
        dbContext.getTVEpisodesTable().sortBy(BY_LONGEST_DURATION_EPISODE, foundEpisodes);

        return foundEpisodes;
    }
    
    public List<TVEpisode> getTVShowSeasonLongestEpisodes(PrimaryKey tvShowSeasonPrimaryKey) 
    {
        List<TVEpisode> seasonEpisodes;
        
        seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        dbContext.getTVEpisodesTable().sortBy(BY_LONGEST_DURATION_EPISODE, seasonEpisodes);

        return seasonEpisodes;
    }
    
    public List<TVShow> getAnnouncedTVShows(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<TVShow> filteredTVShows = dbContext.getTVShowsTable().filterBy(s -> 
                s.getEra() == era && (s.getReleaseDate() == null || 
                        s.getReleaseDate().compareTo(currentDate) > 0));
        
        dbContext.getTVShowsTable().sortBy(BY_NAME_ALPHABETICALLY_SHOW, filteredTVShows);
        
        return filteredTVShows;
    }
    
    public int getAnnouncedTVShowsCountByEra(Era era) 
    {        
        List<TVShow> filteredTVShows = getAnnouncedTVShows(era);
                
        return filteredTVShows.size();
    }
    
    public List<TVShow> searchForTVShow(String name) 
    {
        String normalizedName = Normalizer.normalize(name, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase();
        
        String regex = normalizedName;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("");
        
        List<TVShow> foundShows = dbContext.getTVShowsTable().filterBy(show -> 
        {
            String normalizedShowName = Normalizer.normalize(show.getName(), Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase();
            matcher.reset(normalizedShowName);
            return matcher.find();
        });
        
        return foundShows;
    }
    
    public List<TVEpisode> getFavoriteEpisodesFromEntireTVShow(PrimaryKey tvShowPrimaryKey) 
    {
        List<TVEpisode> filteredEpisodes = new ArrayList<>();
        List<TVEpisode> seasonEpisodes;
        
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        for (TVSeason season : showSeasons) 
        {
            seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> 
                        e.getTVSeasonForeignKey().equals(season.getPrimaryKey()) &&
                                e.getWasWatched() == true);
            filteredEpisodes.addAll(seasonEpisodes);
        }
                
        dbContext.getTVEpisodesTable().sortBy(BY_PERCENTAGE_RATING_HIGHEST_EPISODE, filteredEpisodes);
        
        return filteredEpisodes;
    }
    
    public List<TVEpisode> getTVShowSeasonEpisodesByOrder(PrimaryKey tvShowSeasonPrimaryKey) 
    {
        List<TVEpisode> seasonEpisodes;
        
        seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        dbContext.getTVEpisodesTable().sortBy(BY_ORDER_ASCENDING_EPISODE, seasonEpisodes);
        
        return seasonEpisodes;
    }
    
    public List<TVSeason> getTVShowSeasonsByOrder(PrimaryKey tvShowPrimaryKey) 
    {
        List<TVSeason> showSeasons;
        
        showSeasons = dbContext.
                getTVSeasonsTable().filterBy(e -> e.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        dbContext.getTVSeasonsTable().sortBy(BY_ORDER_ASCENDING_SEASON, showSeasons);
        
        return showSeasons;
    }
    
    public boolean rateEpisode(TVEpisode existingEpisode, int percentageRating)
    {
        TVEpisode newData = new TVEpisode(existingEpisode.getPrimaryKey(), 
                    existingEpisode.getRuntime(), 
                    existingEpisode.getName(), 
                    percentageRating, 
                    true, 
                    existingEpisode.getHyperlinkForContentWatch(),
                    existingEpisode.getShortContentSummary(),
                    existingEpisode.getOrderInTVShowSeason(),
                    existingEpisode.getTVSeasonForeignKey());
        
        boolean wasDataChanged = dbContext.getTVEpisodesTable().editBy(existingEpisode.getPrimaryKey(), 
                newData);
                
        return wasDataChanged;
    }
    
    private static LocalDate getCurrentDate() 
    {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
