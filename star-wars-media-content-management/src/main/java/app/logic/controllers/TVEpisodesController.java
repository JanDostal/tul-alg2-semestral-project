package app.logic.controllers;

import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import app.models.input.TVEpisodeInput;
import app.models.input.TVSeasonInput;
import app.models.input.TVShowInput;
import app.models.inputoutput.TVEpisodeInputOutput;
import app.models.inputoutput.TVSeasonInputOutput;
import app.models.inputoutput.TVShowInputOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Collator;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.mail.EmailException;
import utils.emailsender.EmailSender;
import utils.exceptions.DataConversionException;
import utils.exceptions.DatabaseException;
import utils.exceptions.FileEmptyException;
import utils.exceptions.FileParsingException;
import utils.helpers.TVEpisodeDataConverter;
import utils.helpers.TVSeasonDataConverter;
import utils.helpers.TVShowDataConverter;
import utils.interfaces.IDataFileManager;

/**
 * Represents a TV episodes controller for acting as business logic for application.
 * TV episodes controller works with TV episode, TV season and TV show data types.
 * TV episodes controller uses services like file manager, email service and database access layer.
 * @author jan.dostal
 */
public class TVEpisodesController 
{
    private static TVEpisodesController tvEpisodesController;
    
    private final DataContextAccessor dbContext;
    
    private final EmailSender emailSender;
    
    private final FileManagerAccessor fileManagerAccessor;
    
    private final Collator czechCollator = DataStore.loadCzechCollator();
    
    /**
     * Compares two tv episodes by their runtime atrribute and sorts them from longest.
     * @return int value indicating if first tv episode runtime is greater or equal than second tv episode runtime
     */
    private final Comparator<TVEpisode> BY_LONGEST_DURATION_EPISODE = (TVEpisode m1, TVEpisode m2) -> 
    {        
        return m2.getRuntime().compareTo(m1.getRuntime());
    };
    
    /**
     * Compares two tv shows by their name attribute and sorts them alphabetically.
     * @return int value indicating if first tv show name is alphabetically greater or equal than second tv show name
     */
    private final Comparator<TVShow> BY_NAME_ALPHABETICALLY_SHOW = (TVShow m1, TVShow m2) -> 
            czechCollator.compare(m1.getName(), m2.getName());
    
    /**
     * Compares two tv episodes by their percentage rating attribute and sorts them from highest rating.
     * @return int value indicating if first tv episode rating is greater or equal than second tv episode rating
     */
    private final Comparator<TVEpisode> 
            BY_PERCENTAGE_RATING_HIGHEST_EPISODE = (TVEpisode m1, TVEpisode m2) -> 
    {        
        return m2.getPercentageRating() - m1.getPercentageRating();
    };
    
    /**
     * Compares two tv episodes by their order in tv season attribute and sorts them ascendingly.
     * @return int value indicating if first tv episode order is greater or equal than second tv episode order
     */
    private final Comparator<TVEpisode> 
            BY_ORDER_ASCENDING_EPISODE = (TVEpisode m1, TVEpisode m2) -> 
    {        
        return m1.getOrderInTVShowSeason() - m2.getOrderInTVShowSeason();
    };
    
    /**
     * Compares two tv seasons by their order in tv show attribute and sorts them ascendingly.
     * @return int value indicating if first tv season order is greater or equal than second tv season order
     */
    private final Comparator<TVSeason> 
            BY_ORDER_ASCENDING_SEASON = (TVSeason m1, TVSeason m2) -> 
    {        
        return m1.getOrderInTVShow()- m2.getOrderInTVShow();
    };
    
    /**
     * Compares two tv shows by their date attribute and sorts them from newest.
     * Also if date attribute is null, still continues comparison.
     * @return int value indicating if first tv show date is newer or equal than second tv show date
     */
    private final Comparator<TVShow> BY_DATE_NEWEST_SHOW = (TVShow m1, TVShow m2) -> 
    {
        if (m1.getReleaseDate() == null && m2.getReleaseDate() == null) 
        {
            return 0;
        } 
        else if (m1.getReleaseDate() == null) 
        {
            return 1;
        } 
        else if (m2.getReleaseDate() == null) 
        {
            return -1;
        }
        
        return m2.getReleaseDate().compareTo(m1.getReleaseDate());
    };
    
    /**
     * Creates singleton instance of TVEpisodesController.
     * Uses dependency injection to inject data context, email sender and file manager services.
     * @param dbContext singleton instance of data context accessor 
     * @param emailSender singleton instance of email sender 
     * @param fileManagerAccessor singleton instance of file manager accessor 
     */
    private TVEpisodesController(DataContextAccessor dbContext, EmailSender emailSender, 
            FileManagerAccessor fileManagerAccessor) 
    {
        this.dbContext = dbContext;
        this.emailSender = emailSender;
        this.fileManagerAccessor = fileManagerAccessor;
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @param dbContext singleton instance of data context accessor 
     * @param emailSender singleton instance of email sender 
     * @param fileManagerAccessor singleton instance of file manager accessor 
     * @return singleton instance of TVEpisodesController class
     */
    public static TVEpisodesController getInstance(DataContextAccessor dbContext, EmailSender emailSender, FileManagerAccessor
            fileManagerAccessor) 
    {
        if (tvEpisodesController == null) 
        {
            tvEpisodesController = new TVEpisodesController(dbContext, emailSender, fileManagerAccessor);
        }
        
        return tvEpisodesController;
    }
    
    /**
     * Represents an email method for sending e-mail with HTML encoded unwatched TV episodes with hyperlinks 
     * in selected TV show.
     * @param recipientEmailAddress entered recipient e-mail address from user
     * @param tvShowPrimaryKey represent a identificator of existing tv show from database
     * @throws org.apache.commons.mail.EmailException if recipientEmailAddress is invalid or network error occures
     * @throws utils.exceptions.DatabaseException if chosen tv show does not exist or is in announced state
     */
    public void sendUnwatchedEpisodesWithHyperlinksInTVShowByEmail(String recipientEmailAddress, PrimaryKey tvShowPrimaryKey) 
            throws EmailException, DatabaseException
    {
        TVShow foundTVShow = dbContext.getTVShowsTable().getBy(tvShowPrimaryKey);
        LocalDate currentDate = getCurrentDate();
        
        if (foundTVShow == null) 
        {
            throw new DatabaseException("Seriál vybraný pro odeslání e-mailu nebyl nalezen");
        }
        
        if (foundTVShow.getReleaseDate() == null || foundTVShow.getReleaseDate().compareTo(currentDate) > 0)
        {
            throw new DatabaseException("Seriál vybraný pro odeslání e-mailu ještě nebyl vydán");
        }
        
        List<TVEpisode> seasonEpisodes;
       
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        dbContext.getTVSeasonsTable().sortBy(BY_ORDER_ASCENDING_SEASON, showSeasons);
        
        DateTimeFormatter emailDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.forLanguageTag("cs-CZ"));
        
        String subject = String.format("%s - Nezhlédnuté epizody - Seriál %s - Období %s - Datum uvedení seriálu %s", 
                DataStore.getAppName(), foundTVShow.getName(), foundTVShow.getEra().getDisplayName(), 
                foundTVShow.getReleaseDate().format(emailDateFormatter));
        
        StringBuilder message = new StringBuilder();
        String durationText;
        String nameText;
        String hyperlinkText;
        
        message.append("<html>");
        message.append("<h1>");
        message.append(String.format("Nezhlédnuté epizody seriálu %s", foundTVShow.getName()));
        message.append("</h1>");
        
        if(showSeasons.isEmpty()) 
        {
            message.append("<br>");
            message.append("<p style=\"color:red\">Žádné sezóny</p>");
            message.append("<br>");
        }
        else 
        {
            for (TVSeason season : showSeasons) 
            {
                message.append("<h2>");
                message.append(String.format("Sezóna %d", season.getOrderInTVShow()));
                message.append("</h2>");

                seasonEpisodes = dbContext.
                        getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(season.getPrimaryKey())
                        && e.getWasWatched() == false);

                dbContext.getTVEpisodesTable().sortBy(BY_ORDER_ASCENDING_EPISODE, seasonEpisodes);

                if (seasonEpisodes.isEmpty()) 
                {
                    message.append("<br>");
                    message.append("<p style=\"color:red\">Žádné epizody</p>");
                    message.append("<br>");
                } 
                else 
                {
                    message.append("<ul>");

                    for (TVEpisode e : seasonEpisodes) 
                    {
                        durationText = String.format("%02d:%02d:%02d", 
                                e.getRuntime().toHoursPart(), 
                                e.getRuntime().toMinutesPart(),
                                e.getRuntime().toSecondsPart());
                        
                        nameText = e.getName() == null ? "<span style=\"color:red\">Neznámý</span>" : e.getName();
                        
                        hyperlinkText = e.getHyperlinkForContentWatch() == null ? "<span style=\"color:red\">Neuveden</span>" : 
                                String.format("<a href=\"%s\">Zhlédnout</a>", e.getHyperlinkForContentWatch());

                        message.append("<li>");
                        message.append("<h3>");
                        message.append(String.format("Epizoda %d", e.getOrderInTVShowSeason()));
                        message.append("</h3>");
                        message.append("<p>");
                        message.append(String.format("Název: %s", nameText));
                        message.append("</p>");
                        message.append("<p>");
                        message.append(String.format("Délka epizody: %s", durationText));
                        message.append("</p>");
                        message.append("<p>");
                        message.append(String.format("Odkaz ke zhlédnutí: %s", hyperlinkText));
                        message.append("</p>");
                        message.append("</li>");
                    }

                    message.append("</ul>");
                }
            }
        }
        
        message.append("</html>");

        emailSender.sendEmail(recipientEmailAddress, subject, message);
    }
    
    /**
     * Represents a statistic method for calculating total runtime of unwatched/watched
     * tv episodes in selected tv show.
     * @param tvShowPrimaryKey chosen tv show identificator in database
     * @param onlyWatched selects if tv episodes filtered will be unwatched or watched
     * @return total runtime of all watched/unwatched tv episodes in tv show
     */
    public Duration getTotalRuntimeOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) 
    {
        Duration totalDuration = Duration.ZERO;
        
        List<TVEpisode> seasonEpisodes;
        
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        for (TVSeason season : showSeasons) 
        {
            seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(season.getPrimaryKey()) &&
                        e.getWasWatched() == onlyWatched);
            
            for (TVEpisode e : seasonEpisodes) 
            {
                totalDuration = totalDuration.plus(e.getRuntime());
            }
        }
                
        return totalDuration;
    }
    
    /**
     * Represents a statistic method for calculating total runtime of unwatched/watched
     * tv episodes in selected tv season.
     * @param tvShowSeasonPrimaryKey chosen tv season identificator in database
     * @param onlyWatched selects if tv episodes filtered will be unwatched or watched
     * @return total runtime of all watched/unwatched tv episodes in tv season
     */
    public Duration getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) 
    {
        Duration totalDuration = Duration.ZERO;
        
        List<TVEpisode> seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(s -> s.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey) &&
                        s.getWasWatched() == onlyWatched);
        
        for (TVEpisode e : seasonEpisodes) 
        {
            totalDuration = totalDuration.plus(e.getRuntime());
        }
                              
        return totalDuration;
    }
    
    /**
     * Represents a statistic method for calculating average runtime of unwatched/watched
     * tv episodes in selected tv show.
     * @param tvShowPrimaryKey chosen tv show identificator in database
     * @param onlyWatched selects if tv episodes filtered will be unwatched or watched
     * @return average runtime of all watched/unwatched tv episodes in tv show
     */
    public Duration getAverageRuntimeOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) 
    {        
        long averageSeconds;
        
        Duration totalRuntimeOfAllReleasedEpisodesInTVShow = getTotalRuntimeOfAllReleasedEpisodesInTVShow(tvShowPrimaryKey, onlyWatched);
        
        int durationsCount = getReleasedTVShowEpisodesCount(tvShowPrimaryKey, onlyWatched);
        
        if (durationsCount == 0) 
        {
            averageSeconds = 0;
        }
        else 
        {
            averageSeconds = totalRuntimeOfAllReleasedEpisodesInTVShow.toSeconds() / durationsCount;
        }
        
        Duration averageDuration = Duration.ofSeconds(averageSeconds);
                      
        return averageDuration;
    }
    
    /**
     * Represents a statistic method for calculating average runtime of unwatched/watched
     * tv episodes in selected tv season.
     * @param tvShowSeasonPrimaryKey chosen tv season identificator in database
     * @param onlyWatched selects if tv episodes filtered will be unwatched or watched
     * @return average runtime of all watched/unwatched tv episodes in tv season
     */
    public Duration getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) 
    {        
        long averageSeconds;
        
        Duration totalRuntimeOfAllReleasedEpisodesInTVShowSeason = 
                getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(tvShowSeasonPrimaryKey, onlyWatched);
        
        int durationsCount = getReleasedTVShowSeasonEpisodesCount(tvShowSeasonPrimaryKey, onlyWatched);
        
        if (durationsCount == 0) 
        {
            averageSeconds = 0;
        }
        else 
        {
            averageSeconds = totalRuntimeOfAllReleasedEpisodesInTVShowSeason.toSeconds() / durationsCount;
        }
        
        Duration averageDuration = Duration.ofSeconds(averageSeconds);
        
        return averageDuration;
    }
    
    /**
     * Represents a statistic method for calculating average percentage rating (0 - 100) of watched
     * tv episodes in selected tv show.
     * @param tvShowPrimaryKey chosen tv show identificator in database
     * @return average percentage rating of all watched tv episodes in tv show
     */
    public float getAverageRatingOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey) 
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
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(season.getPrimaryKey())
                && e.getWasWatched() == true);
            
            for (TVEpisode e : seasonEpisodes) 
            {
                ratingsCount++;
                totalRating += e.getPercentageRating();
            }
        }
        
        if (ratingsCount == 0) 
        {
            averageRating = 0;
        }
        else 
        {
            averageRating = totalRating / (float) ratingsCount;
        }
                      
        return averageRating;
    }
    
    /**
     * Represents a statistic method for calculating average percentage rating (0 - 100) of watched
     * tv episodes in selected tv season.
     * @param tvShowSeasonPrimaryKey chosen tv season identificator in database
     * @return average percentage rating of all watched tv episodes in tv season
     */
    public float getAverageRatingOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey) 
    {        
        float averageRating;
        long totalRating = 0;
                
        List<TVEpisode> seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey) 
                && e.getWasWatched() == true);
        
        for (TVEpisode e : seasonEpisodes) 
        {
            totalRating += e.getPercentageRating();
        }
        
        if (seasonEpisodes.isEmpty() == true) 
        {
            averageRating = 0;
        }
        else 
        {
            averageRating = totalRating / (float) seasonEpisodes.size();
        }
                      
        return averageRating;
    }
    
    /**
     * Represents a statistic method for calculating announced tv shows count
     * in selected era.
     * @param era chosen era in which to operate
     * @return int value indicating total count of announced tv shows
     */
    public int getAnnouncedTVShowsCountByEra(Era era) 
    {        
        List<TVShow> filteredTVShows = getAnnouncedTVShowsInAlphabeticalOrderByEra(era);
                
        return filteredTVShows.size();
    }
    
    /**
     * Represents a statistic method for calculating a total count of released 
     * tv shows in selected era.
     * @param era chosen era in which to operate
     * @return int value indicating total count of released tv shows
     */
    public int getReleasedTVShowsCountByEra(Era era) 
    {        
        List<TVShow> filteredShows = getReleasedNewestTVShowsByEra(era);
                
        return filteredShows.size();
    }
    
    /**
     * Represents a statistic method for calculating a total count of unwatched/watched 
     * tv episodes in selected tv show.
     * @param tvShowPrimaryKey chosen tv show identificator in database
     * @param onlyWatched selects if tv episodes filtered will be unwatched or watched
     * @return int value indicating total count of unwatched/watched tv episodes in tv show
     */
    public int getReleasedTVShowEpisodesCount(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) 
    {
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        int totalCount = 0;
        
        for (TVSeason season : showSeasons) 
        {
            totalCount += getReleasedTVShowSeasonEpisodesCount(season.getPrimaryKey(), onlyWatched);
        }
                
        return totalCount;
    }
        
    /**
     * Represents a statistic method for calculating a total count of unwatched/watched 
     * tv episodes in selected tv season.
     * @param tvShowSeasonPrimaryKey chosen tv season identificator in database
     * @param onlyWatched selects if tv episodes filtered will be unwatched or watched
     * @return int value indicating total count of unwatched/watched tv episodes in tv season
     */
    public int getReleasedTVShowSeasonEpisodesCount(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) 
    {
        List<TVEpisode> filteredEpisodes = dbContext.getTVEpisodesTable().filterBy(e -> 
                e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey) && 
                        e.getWasWatched() == onlyWatched);
        
        return filteredEpisodes.size();
    }
    
    /**
     * Represents a method for getting released tv shows, sorted alphabetically,
     * in selected era.
     * @param era chosen era in which to operate.
     * @return list of filtered and sorted released tv shows
     */
    public List<TVShow> getReleasedTVShowsInAlphabeticalOrderByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<TVShow> filteredShows = dbContext.getTVShowsTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getTVShowsTable().sortBy(BY_NAME_ALPHABETICALLY_SHOW, filteredShows);
        
        return filteredShows;
    }
    
    /**
     * Represents a method for getting released tv shows, sorted from newest,
     * in selected era.
     * @param era chosen era in which to operate.
     * @return list of filtered and sorted released tv shows
     */
    public List<TVShow> getReleasedNewestTVShowsByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<TVShow> filteredShows = dbContext.getTVShowsTable().filterBy(m -> 
                m.getEra() == era && m.getReleaseDate() != null && 
                        m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getTVShowsTable().sortBy(BY_DATE_NEWEST_SHOW, filteredShows);
        
        return filteredShows;
    }
    
     /**
     * Represents a method for getting released tv shows, sorted from total runtime of all tv episodes,
     * in selected era.
     * @param era chosen era in which to operate.
     * @return list of filtered and sorted released tv shows
     */
    public List<TVShow> getReleasedLongestTVShowsByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        List<TVSeason> showSeasons;
        List<TVEpisode> seasonEpisodes;
        
        List<TVShow> filteredShows = dbContext.getTVShowsTable().filterBy(s -> 
                s.getEra() == era && s.getReleaseDate() != null && 
                        s.getReleaseDate().compareTo(currentDate) <= 0);
        
        List<Duration> tvShowsTotalDurations = new ArrayList<>();
        
        Duration showTotalDuration;
        
        for (TVShow show : filteredShows) 
        {
            showTotalDuration = Duration.ZERO;
            
            showSeasons = dbContext.
                    getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(show.getPrimaryKey()));
            
            for (TVSeason season : showSeasons) 
            {
                seasonEpisodes = dbContext.
                        getTVEpisodesTable().filterBy(s -> s.getTVSeasonForeignKey().equals(season.getPrimaryKey()));
                
                for (TVEpisode episode : seasonEpisodes) 
                {
                    showTotalDuration = showTotalDuration.plus(episode.getRuntime());
                }                
            }
            
            tvShowsTotalDurations.add(showTotalDuration);
        }
        
        Comparator<Object> byLongestTotalDurationTVShow = Comparator.comparingLong(tvShow -> 
        {
            int index = filteredShows.indexOf(tvShow);
            
            if (index >= 0 && index < tvShowsTotalDurations.size()) 
            {
                Duration duration = tvShowsTotalDurations.get(index);
                return duration.toSeconds();
            }
            
            return 0;
            
        }).reversed();
        
        Collections.sort(filteredShows, byLongestTotalDurationTVShow);
        
        return filteredShows;
    }
    
    /**
     * Represents a method for getting released tv episodes, sorted from longest,
     * in selected tv show.
     * @param tvShowPrimaryKey chosen tv show identificator in database.
     * @return list of filtered and sorted released tv episodes
     */
    public List<TVEpisode> getReleasedTVShowLongestEpisodes(PrimaryKey tvShowPrimaryKey) 
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
    
    /**
     * Represents a method for getting released tv episodes, sorted from longest,
     * in selected tv season.
     * @param tvShowSeasonPrimaryKey chosen tv season identificator in database.
     * @return list of filtered and sorted released tv episodes
     */
    public List<TVEpisode> getReleasedTVShowSeasonLongestEpisodes(PrimaryKey tvShowSeasonPrimaryKey) 
    {
        List<TVEpisode> seasonEpisodes;
        
        seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        dbContext.getTVEpisodesTable().sortBy(BY_LONGEST_DURATION_EPISODE, seasonEpisodes);

        return seasonEpisodes;
    }
    
    /**
     * Represents a method for getting watched tv episodes, sorted from highest percentage rating,
     * in selected tv show.
     * @param tvShowPrimaryKey chosen tv show identificator in database.
     * @return list of filtered and sorted watched tv episodes
     */
    public List<TVEpisode> getReleasedTVShowFavoriteTVEpisodes(PrimaryKey tvShowPrimaryKey) 
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
    
    /**
     * Represents a method for getting watched tv episodes, sorted from highest percentage rating,
     * in selected tv season.
     * @param tvShowSeasonPrimaryKey chosen tv season identificator in database.
     * @return list of filtered and sorted watched tv episodes
     */
    public List<TVEpisode> getReleasedTVShowSeasonFavoriteTVEpisodes(PrimaryKey tvShowSeasonPrimaryKey) 
    {        
        List<TVEpisode> seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e -> 
                e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey) && 
                        e.getWasWatched() == true);
                
        dbContext.getTVEpisodesTable().sortBy(BY_PERCENTAGE_RATING_HIGHEST_EPISODE, seasonEpisodes);
        
        return seasonEpisodes;
    }
    
    /**
     * Represents a method for getting released tv seasons, sorted by order in tv show,
     * in selected tv show.
     * @param tvShowPrimaryKey chosen tv show identificator in database.
     * @return list of filtered and sorted released tv seasons
     */
    public List<TVSeason> getReleasedTVShowSeasonsByOrder(PrimaryKey tvShowPrimaryKey) 
    {        
        List<TVSeason> showSeasons = dbContext.getTVSeasonsTable().filterBy(e -> e.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        dbContext.getTVSeasonsTable().sortBy(BY_ORDER_ASCENDING_SEASON, showSeasons);
        
        return showSeasons;
    }
    
    /**
     * Represents a method for getting released tv episodes, sorted by order in tv season,
     * in selected tv season.
     * @param tvShowSeasonPrimaryKey chosen tv season identificator in database.
     * @return list of filtered and sorted released tv episodes
     */
    public List<TVEpisode> getReleasedTVShowSeasonEpisodesByOrder(PrimaryKey tvShowSeasonPrimaryKey) 
    {        
        List<TVEpisode> seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        dbContext.getTVEpisodesTable().sortBy(BY_ORDER_ASCENDING_EPISODE, seasonEpisodes);
        
        return seasonEpisodes;
    }
    
    /**
     * Represents a method for detail detail/instance of selected tv season from database.
     * @param chosenTVSeasonPrimaryKey chosen tv season identificator in database.
     * @return instance of found tv season in database
     */
    public TVSeason getTVSeasonDetail(PrimaryKey chosenTVSeasonPrimaryKey) 
    {
        TVSeason foundTVSeason = dbContext.getTVSeasonsTable().getBy(chosenTVSeasonPrimaryKey);
        
        return foundTVSeason;
    }
    
    /**
     * Represents a method for getting announced tv shows, sorted alphabetically,
     * in selected era.
     * @param era chosen era in which to operate.
     * @return list of filtered and sorted announced tv shows
     */
    public List<TVShow> getAnnouncedTVShowsInAlphabeticalOrderByEra(Era era) 
    {
        LocalDate currentDate = getCurrentDate();
        
        List<TVShow> filteredTVShows = dbContext.getTVShowsTable().filterBy(s -> 
                s.getEra() == era && (s.getReleaseDate() == null || 
                        s.getReleaseDate().compareTo(currentDate) > 0));
        
        dbContext.getTVShowsTable().sortBy(BY_NAME_ALPHABETICALLY_SHOW, filteredTVShows);
        
        return filteredTVShows;
    }
    
    /**
     * Represents a method for getting released tv shows, sorted from newest.
     * @return list of filtered and sorted released tv shows
     */
    public List<TVShow> getReleasedNewestTVShows()
    {
        LocalDate currentDate = getCurrentDate();
        
        List<TVShow> filteredTVShows = dbContext.getTVShowsTable().filterBy(m -> 
                m.getReleaseDate() != null && 
                m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getTVShowsTable().sortBy(BY_DATE_NEWEST_SHOW, filteredTVShows);
                
        return filteredTVShows;
    }
    
    /**
     * Represents a method for rating a selected tv episode with new percentage rating.
     * @param existingEpisode tv episode data model instance which rating wants to be changed
     * @param percentageRating percentage rating in range 0 - 100 indicating likability of tv episode
     * @return logical value indicating if percentage rating changed or remained unchanged
     * @throws utils.exceptions.DatabaseException if percentage rating value is invalid
     * @throws java.io.IOException if updating tv episodes input/output data files with new data failed
     * @throws IllegalArgumentException if percentageRating is negative number
     */
    public boolean rateTVEpisode(TVEpisode existingEpisode, int percentageRating) throws DatabaseException, IOException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        
        if (percentageRating < 0) 
        {
            throw new IllegalArgumentException("Procentuální ohodnocení TV epizody nesmí být záporné");
        }
        
        TVEpisode newData = new TVEpisode(existingEpisode.getPrimaryKey(), 
                    existingEpisode.getRuntime(), 
                    existingEpisode.getName(), 
                    percentageRating, 
                    true, 
                    existingEpisode.getHyperlinkForContentWatch(),
                    existingEpisode.getShortContentSummary(),
                    existingEpisode.getOrderInTVShowSeason(),
                    existingEpisode.getTVSeasonForeignKey());
        
        boolean wasDataChanged = dbContext.getTVEpisodesTable().editBy(existingEpisode.getPrimaryKey(), newData);
        
        if (wasDataChanged == true) 
        {
            updateTVEpisodesInputOutputFilesWithNewChanges();
        }
                
        return wasDataChanged;
    }
    
    /**
     * Represents a method for searching in tv shows data table by tv show name.
     * Searching uses regular expression to achieve it.
     * @param name queried tv show name entered from user
     * @return list of zero to N tv shows which meet best with queried tv show name
     * @throws IllegalArgumentException if entered tv show name is empty
     */
    public List<TVShow> searchForTVShow(String name)
    {
        if (name.isEmpty() || name.isBlank()) 
        {
            throw new IllegalArgumentException("Hledaný název TV seriálu nemůže být prázdný");
        }
        
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
    
    /**
     * Represents a method for getting tv shows chosen file (binary or text, input or input/output) content.
     * @param fileName name of the chosen file (not file path)
     * @return stringbuilder which contains file content as string
     * @throws java.io.IOException when reading from chosen file fails
     * @throws java.io.FileNotFoundException when chosen file does not exist
     * @throws utils.exceptions.FileEmptyException when chosen file content is empty
     */
    public StringBuilder getChosenTVShowsFileContent(String fileName) throws IOException, FileNotFoundException, FileEmptyException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryInputOutputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getBinaryInputOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputOutputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getTextInputOutputFileContent();
        }
        
        return content;
    }
    
    /**
     * Represents a method for getting chosen tv seasons file (binary or text, input or input/output) content.
     * @param fileName name of the chosen file (not file path)
     * @return stringbuilder which contains file content as string
     * @throws java.io.IOException when reading from chosen file fails
     * @throws java.io.FileNotFoundException when chosen file does not exist
     * @throws utils.exceptions.FileEmptyException when chosen file content is empty
     */
    public StringBuilder getChosenTVSeasonsFileContent(String fileName) throws IOException, FileNotFoundException, FileEmptyException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryInputOutputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getBinaryInputOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputOutputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getTextInputOutputFileContent();
        }
        
        return content;
    }
    
    /**
     * Represents a method for getting chosen tv episodes file (binary or text, input or input/output) content.
     * @param fileName name of the chosen file (not file path)
     * @return stringbuilder which contains file content as string
     * @throws java.io.IOException when reading from chosen file fails
     * @throws java.io.FileNotFoundException when chosen file does not exist
     * @throws utils.exceptions.FileEmptyException when chosen file content is empty
     */
    public StringBuilder getChosenTVEpisodesFileContent(String fileName) throws IOException, FileNotFoundException, FileEmptyException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryInputOutputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getBinaryInputOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputOutputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getTextInputOutputFileContent();
        }
        
        return content;
    }
    
    /**
     * Represents a method for parsing tv episodes, tv seasons and tv shows input/output data from binary or text files
     * @param fromBinary selects if input/output files will be binary or text
     * @throws java.io.IOException when reading from tv shows or tv seasons or tv episodes input/output files fails
     * @throws utils.exceptions.FileParsingException when parsing from input/output files fails because of corrupted data
     * @throws utils.exceptions.DataConversionException when parsed input/output data cannot be converted to database model data
     * @throws utils.exceptions.DatabaseException when database model data have invalid data, duplicity etc.
     */
    public void loadAllInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException, 
            DataConversionException, DatabaseException, Exception 
    {
        try 
        {
            List<TVShowInputOutput> inputOutputTVShows = fileManagerAccessor.getTVShowsFileManager().
                    loadInputOutputDataFrom(fromBinary);
            List<TVSeasonInputOutput> inputOutputTVSeasons = fileManagerAccessor.getTVSeasonsFileManager().
                    loadInputOutputDataFrom(fromBinary);
            List<TVEpisodeInputOutput> inputOutputTVEpisodes = fileManagerAccessor.getTVEpisodesFileManager().
                    loadInputOutputDataFrom(fromBinary);
        
            TVShow convertedInputOutputTVShow;
            TVSeason convertedInputOutputTVSeason;
            TVEpisode convertedInputOutputTVEpisode;
        
            for (TVShowInputOutput m : inputOutputTVShows) 
            {
                convertedInputOutputTVShow = TVShowDataConverter.convertToDataFrom(m);
                dbContext.getTVShowsTable().loadFrom(convertedInputOutputTVShow);
            }

            for (TVSeasonInputOutput m : inputOutputTVSeasons) 
            {
                convertedInputOutputTVSeason = TVSeasonDataConverter.convertToDataFrom(m);
                dbContext.getTVSeasonsTable().loadFrom(convertedInputOutputTVSeason);
            }

            for (TVEpisodeInputOutput m : inputOutputTVEpisodes) 
            {
                convertedInputOutputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(m);
                dbContext.getTVEpisodesTable().loadFrom(convertedInputOutputTVEpisode);
            }
        }
        catch (Exception ex) 
        {
            dbContext.getMoviesTable().clearData();
            dbContext.getTVShowsTable().clearData();
            dbContext.getTVSeasonsTable().clearData();
            dbContext.getTVEpisodesTable().clearData();
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * Represents a method for parsing tv shows input data from binary or text file
     * @param fromBinary selects if input file will be binary or text
     * @return stringbuilder which contains message log informing about occured errors and parsed tv shows
     * @throws java.io.IOException when reading from tv shows input file fails or when updating tv shows input/output files with new data fails
     * @throws java.io.FileNotFoundException when input file does not exist
     * @throws utils.exceptions.FileEmptyException when input file is empty
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public StringBuilder addTVShowsFrom(boolean fromBinary) throws IOException, FileNotFoundException, FileEmptyException, FileParsingException 
    {
        updateTVShowsInputOutputFilesWithExistingData();
        
        Map<Integer, TVShowInput> inputTVShows = fileManagerAccessor.getTVShowsFileManager().loadInputDataFrom(fromBinary);
        
        StringBuilder message = new StringBuilder();
        StringBuilder moviesErrorMessages = new StringBuilder();
        
        TVShow convertedInputTVShow;
        int errorCounter = 0;

        for (Map.Entry<Integer, TVShowInput> inputTVShow : inputTVShows.entrySet()) 
        {
            try 
            {
                convertedInputTVShow = TVShowDataConverter.convertToDataFrom(inputTVShow.getValue());
                dbContext.getTVShowsTable().addFrom(convertedInputTVShow);
            } 
            catch (DatabaseException | DataConversionException e) 
            {
                errorCounter++;
                moviesErrorMessages.append(String.format("Chybový stav seriálu s pořadím %d v souboru %s: %s", 
                        inputTVShow.getKey(), fromBinary == true ? DataStore.getBinaryInputTVShowsFilename() : 
                                DataStore.getTextInputTVShowsFilename(), e.getMessage())).append("\n");
            }
        }

        int successfullyUploadedTVShowsCount = inputTVShows.size() - errorCounter;
        message.append(String.format("Celkově se podařilo nahrát %d seriálů do databáze a naopak se nepodařilo nahrát %d seriálů", 
                successfullyUploadedTVShowsCount, errorCounter)).append("\n");
        message.append(moviesErrorMessages);

        updateTVShowsInputOutputFilesWithNewChanges();

        return message;
    }
    
    /**
     * Represents a method for parsing tv seasons input data from binary or text file
     * @param chosenTVShowPrimaryKey chosen tv show to which parsed tv seasons will be added/linked
     * @param fromBinary selects if input file will be binary or text
     * @return stringbuilder which contains message log informing about occured errors and parsed tv seasons
     * @throws java.io.IOException when reading from tv seasons input file fails or when updating tv seasons input/output files with new data fails
     * @throws java.io.FileNotFoundException when input file does not exist
     * @throws utils.exceptions.FileEmptyException when input file is empty
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public StringBuilder addTVSeasonsFrom(PrimaryKey chosenTVShowPrimaryKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
    {
        updateTVSeasonsInputOutputFilesWithExistingData();
        
        Map<Integer, TVSeasonInput> inputTVSeasons = fileManagerAccessor.getTVSeasonsFileManager().loadInputDataFrom(fromBinary);
        
        StringBuilder message = new StringBuilder();
        StringBuilder moviesErrorMessages = new StringBuilder();
        
        TVSeason convertedInputTVSeason;
        int errorCounter = 0;

        for (Map.Entry<Integer, TVSeasonInput> inputTVSeason : inputTVSeasons.entrySet()) 
        {
            try 
            {
                convertedInputTVSeason = TVSeasonDataConverter.convertToDataFrom(inputTVSeason.getValue(), chosenTVShowPrimaryKey);
                dbContext.getTVSeasonsTable().addFrom(convertedInputTVSeason);
            } 
            catch (DatabaseException e) 
            {
                errorCounter++;
                moviesErrorMessages.append(String.format("Chybový stav sezóny vybraného seriálu s pořadím %d v souboru %s: %s", 
                        inputTVSeason.getKey(), fromBinary == true ? DataStore.getBinaryInputTVSeasonsFilename() : 
                                DataStore.getTextInputTVSeasonsFilename(), e.getMessage())).append("\n");
            }
        }

        int successfullyUploadedTVSeasonsCount = inputTVSeasons.size() - errorCounter;
        message.append(String.format("Celkově se podařilo nahrát %d sezón vybraného seriálu "
                + "do databáze a naopak se nepodařilo nahrát %d sezón vybraného seriálu",
                successfullyUploadedTVSeasonsCount, errorCounter)).append("\n");
        message.append(moviesErrorMessages);

        updateTVSeasonsInputOutputFilesWithNewChanges();

        return message;
    }
    
    /**
     * Represents a method for parsing tv episodes input data from binary or text file
     * @param chosenTVSeasonPrimaryKey chosen tv season to which parsed tv episodes will be added/linked
     * @param fromBinary selects if input file will be binary or text
     * @return stringbuilder which contains message log informing about occured errors and parsed tv episodes
     * @throws java.io.IOException when reading from tv episodes input file fails or when updating tv episodes input/output files with new data fails
     * @throws java.io.FileNotFoundException when input file does not exist
     * @throws utils.exceptions.FileEmptyException when input file is empty
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public StringBuilder addTVEpisodesFrom(PrimaryKey chosenTVSeasonPrimaryKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        
        Map<Integer, TVEpisodeInput> inputTVEpisodes = fileManagerAccessor.getTVEpisodesFileManager().loadInputDataFrom(fromBinary);
        
        StringBuilder message = new StringBuilder();
        StringBuilder moviesErrorMessages = new StringBuilder();
        
        TVEpisode convertedInputTVEpisode;
        int errorCounter = 0;

        for (Map.Entry<Integer, TVEpisodeInput> inputTVEpisode : inputTVEpisodes.entrySet()) 
        {
            try 
            {
                convertedInputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(inputTVEpisode.getValue(), chosenTVSeasonPrimaryKey);
                dbContext.getTVEpisodesTable().addFrom(convertedInputTVEpisode);
            } 
            catch (DatabaseException e) 
            {
                errorCounter++;
                moviesErrorMessages.append(String.format("Chybový stav epizody vybrané sezóny s pořadím %d v souboru %s: %s", 
                        inputTVEpisode.getKey(), fromBinary == true ? DataStore.getBinaryInputTVEpisodesFilename() : 
                                DataStore.getTextInputTVEpisodesFilename(), e.getMessage())).append("\n");
            }
        }

        int successfullyUploadedTVEpisodesCount = inputTVEpisodes.size() - errorCounter;
        message.append(String.format("Celkově se podařilo nahrát %d epizod vybrané sezóny do databáze" 
                + " a naopak se nepodařilo nahrát %d epizod vybrané sezóny", 
                successfullyUploadedTVEpisodesCount, errorCounter)).append("\n");
        message.append(moviesErrorMessages);

        updateTVEpisodesInputOutputFilesWithNewChanges();

        return message;
    }
    
    /**
     * Represents a method for deleting chosen data model tv show by its primary key 
     * (will delete tv show seasons and episodes if they exist).
     * @param tvShowPrimaryKey represents a tv show identificator in database
     * @throws java.io.IOException when updating tv shows, tv seasons, and tv episodes input/output files with new data fails
     * @throws utils.exceptions.DatabaseException when chosen tv show does not exist
     */
    public void deleteTVShowBy(PrimaryKey tvShowPrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        updateTVSeasonsInputOutputFilesWithExistingData();
        updateTVShowsInputOutputFilesWithExistingData();
               
        dbContext.getTVShowsTable().deleteBy(tvShowPrimaryKey);
        
        updateTVEpisodesInputOutputFilesWithNewChanges();
        updateTVSeasonsInputOutputFilesWithNewChanges();
        updateTVShowsInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for deleting chosen data model tv season by its primary key 
     * (will delete tv season episodes if they exist).
     * @param tvSeasonPrimaryKey represents a tv season identificator in database
     * @throws java.io.IOException when updating tv seasons, and tv episodes input/output files with new data fails
     * @throws utils.exceptions.DatabaseException when chosen tv season does not exist
     */
    public void deleteTVSeasonBy(PrimaryKey tvSeasonPrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        updateTVSeasonsInputOutputFilesWithExistingData();

        dbContext.getTVSeasonsTable().deleteBy(tvSeasonPrimaryKey);
        
        updateTVEpisodesInputOutputFilesWithNewChanges();
        updateTVSeasonsInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for deleting chosen data model tv episode by its primary key
     * @param tvEpisodePrimaryKey represents a tv episode identificator in database
     * @throws java.io.IOException when updating tv episodes input/output files with new data fails
     * @throws utils.exceptions.DatabaseException when chosen tv episode does not exist
     */
    public void deleteTVEpisodeBy(PrimaryKey tvEpisodePrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();

        dbContext.getTVEpisodesTable().deleteBy(tvEpisodePrimaryKey);

        updateTVEpisodesInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for deleting chosen list of tv shows
     * (will delete tv shows seasons and episodes if they exist)
     * @param chosenTVShows list of chosen tv shows originating from database
     * @throws java.io.IOException when updating tv shows, tv seasons and tv episodes input/output files with new data fails
     */
    public void deleteTVShows(List<TVShow> chosenTVShows) throws IOException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        updateTVSeasonsInputOutputFilesWithExistingData();
        updateTVShowsInputOutputFilesWithExistingData();
                        
        for (TVShow m : chosenTVShows) 
        {
            try 
            {
                dbContext.getTVShowsTable().deleteBy(m.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }
        
        updateTVEpisodesInputOutputFilesWithNewChanges();
        updateTVSeasonsInputOutputFilesWithNewChanges();
        updateTVShowsInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for deleting chosen list of tv seasons
     * (will delete tv seasons episodes if they exist)
     * @param chosenTVSeasons list of chosen tv seasons originating from database
     * @throws java.io.IOException when updating tv seasons and tv episodes input/output files with new data fails
     */
    public void deleteTVSeasons(List<TVSeason> chosenTVSeasons) throws IOException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        updateTVSeasonsInputOutputFilesWithExistingData();
                        
        for (TVSeason m : chosenTVSeasons) 
        {
            try 
            {
                dbContext.getTVSeasonsTable().deleteBy(m.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }
        
        updateTVEpisodesInputOutputFilesWithNewChanges();
        updateTVSeasonsInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for deleting chosen list of tv episodes
     * @param chosenTVEpisodes list of chosen tv episodes originating from database
     * @throws java.io.IOException when updating tv episodes input/output files with new data fails
     */
    public void deleteTVEpisodes(List<TVEpisode> chosenTVEpisodes) throws IOException
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        
        for (TVEpisode m : chosenTVEpisodes) 
        {
            try 
            {
                dbContext.getTVEpisodesTable().deleteBy(m.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }

        updateTVEpisodesInputOutputFilesWithNewChanges();
    }
    
    /**
     * Represents a method for editing chosen tv show by its primary key and using tv shows input file
     * @param existingTVShowPrimaryKey represents an existing tv show identificator in database
     * @param fromBinary selects if parsing of new data for existing tv show will be from text or binary input file
     * @return logical value indicating if existing tv show data was changed or remained same
     * @throws java.io.IOException if reading from tv shows input file fails or when updating tv shows input/output files with new data fails
     * @throws java.io.FileNotFoundException if input file is not found
     * @throws utils.exceptions.FileEmptyException if input file is empty
     * @throws utils.exceptions.DataConversionException if tv show input data cannot be converted to tv show database data model
     * @throws utils.exceptions.DatabaseException if tv show database data are invalid, duplicity etc.
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public boolean editTVShowBy(PrimaryKey existingTVShowPrimaryKey, boolean fromBinary) throws IOException, 
            FileNotFoundException, FileEmptyException, DataConversionException, DatabaseException, FileParsingException 
    {
        updateTVShowsInputOutputFilesWithExistingData();
        
        Map<Integer, TVShowInput> editedTVShow = fileManagerAccessor.getTVShowsFileManager().loadInputDataFrom(fromBinary);
        
        String filename = fromBinary == true ? DataStore.getBinaryInputTVShowsFilename() : DataStore.getTextInputTVShowsFilename();
                
        if (editedTVShow.size() > 1 || editedTVShow.get(1) == null) 
        {
            throw new FileParsingException("Soubor " + 
                    filename + " musí obsahovat právě jeden seriál vybraný pro editaci");
        }
        
        TVShow convertedInputTVShow = TVShowDataConverter.convertToDataFrom(editedTVShow.get(1));

        boolean wasDataChanged = dbContext.getTVShowsTable().editBy(existingTVShowPrimaryKey, convertedInputTVShow);

        if (wasDataChanged == true) 
        {
            updateTVShowsInputOutputFilesWithNewChanges();
        }
        
        return wasDataChanged; 
    }
    
    /**
     * Represents a method for editing chosen tv season by its primary key and using tv seasons input file
     * @param existingTVSeasonPrimaryKey represents an existing tv season identificator in database
     * @param tvShowForeignKey represents an existing tv show identificator in database 
     * (to link tv season new data with this tv show).
     * @param fromBinary selects if parsing of new data for existing tv season will be from text or binary input file
     * @return logical value indicating if existing tv season data was changed or remained same
     * @throws java.io.IOException if reading from tv seasons input file fails or when updating tv seasons input/output files with new data fails
     * @throws java.io.FileNotFoundException if input file is not found
     * @throws utils.exceptions.FileEmptyException if input file is empty
     * @throws utils.exceptions.DatabaseException if tv season database data are invalid, duplicity etc.
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public boolean editTVSeasonBy(PrimaryKey existingTVSeasonPrimaryKey, PrimaryKey tvShowForeignKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, DatabaseException, FileParsingException 
    {
        updateTVSeasonsInputOutputFilesWithExistingData();
        
        Map<Integer, TVSeasonInput> editedTVSeason = fileManagerAccessor.getTVSeasonsFileManager().loadInputDataFrom(fromBinary);
        
        String filename = fromBinary == true ? DataStore.getBinaryInputTVSeasonsFilename() : DataStore.getTextInputTVSeasonsFilename();
                        
        if (editedTVSeason.size() > 1 || editedTVSeason.get(1) == null) 
        {
            throw new FileParsingException("Soubor " + filename + " musí obsahovat právě jednu sezónu vybranou pro editaci");
        }
        
        TVSeason convertedInputTVSeason = TVSeasonDataConverter.convertToDataFrom(editedTVSeason.get(1), tvShowForeignKey);

        boolean wasDataChanged = dbContext.getTVSeasonsTable().editBy(existingTVSeasonPrimaryKey, convertedInputTVSeason);
        
        if (wasDataChanged == true) 
        {
            updateTVSeasonsInputOutputFilesWithNewChanges();
        }
        
        return wasDataChanged; 
    }
    
    /**
     * Represents a method for editing chosen tv episode by its primary key and using tv episodes input file
     * @param existingTVEpisodePrimaryKey represents an existing tv episode identificator in database
     * @param tvSeasonForeignKey represents an existing tv season identificator in database 
     * (to link tv episode new data with this tv season).
     * @param fromBinary selects if parsing of new data for existing tv episode will be from text or binary input file
     * @return logical value indicating if existing tv episode data was changed or remained same
     * @throws java.io.IOException if reading from tv episodes input file fails or when updating tv episodes input/output files with new data fails
     * @throws java.io.FileNotFoundException if input file is not found
     * @throws utils.exceptions.FileEmptyException if input file is empty
     * @throws utils.exceptions.DatabaseException if tv episode database data are invalid, duplicity etc.
     * @throws utils.exceptions.FileParsingException when nothing was parsed from not-empty input file
     */
    public boolean editTVEpisodeBy(PrimaryKey existingTVEpisodePrimaryKey, PrimaryKey tvSeasonForeignKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, DatabaseException, FileParsingException 
    {
        updateTVEpisodesInputOutputFilesWithExistingData();
        
        Map<Integer, TVEpisodeInput> editedTVEpisode = fileManagerAccessor.getTVEpisodesFileManager().loadInputDataFrom(fromBinary);
        
        String filename = fromBinary == true ? DataStore.getBinaryInputTVEpisodesFilename() : DataStore.getTextInputTVEpisodesFilename();
                        
        if (editedTVEpisode.size() > 1 || editedTVEpisode.get(1) == null) 
        {
            throw new FileParsingException("Soubor " + filename + " musí obsahovat právě jednu epizodu vybranou pro editaci");
        }
        
        TVEpisode convertedInputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(editedTVEpisode.get(1), tvSeasonForeignKey);

        boolean wasDataChanged = dbContext.getTVEpisodesTable().editBy(existingTVEpisodePrimaryKey, convertedInputTVEpisode);
        
        if (wasDataChanged == true) 
        {
            updateTVEpisodesInputOutputFilesWithNewChanges();
        }

        return wasDataChanged; 
    }
    
    /**
     * Represents a method for saving current tv shows table state into input/output files
     * @throws java.io.IOException if saving tv shows table state into input/output files fails
     */
    private void updateTVShowsInputOutputFilesWithExistingData() throws IOException 
    {
        List<TVShow> currentTVShows = dbContext.getTVShowsTable().getAll();
        dbContext.getTVShowsTable().sortByPrimaryKey(currentTVShows);
        
        List<TVShowInputOutput> inputOutputTVShows = new ArrayList<>();
        TVShowInputOutput inputOutputTVShow;
        
        for (TVShow m : currentTVShows) 
        {
            inputOutputTVShow = TVShowDataConverter.convertToInputOutputDataFrom(m);
            inputOutputTVShows.add(inputOutputTVShow);
        }
        
        fileManagerAccessor.getTVShowsFileManager().saveInputOutputDataIntoFiles(inputOutputTVShows);
    }
    
    /**
     * Represents a method for saving updated tv shows table state into input/output files.
     * <p>
     * The correct usage of this method is to 
     * call {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean) 
     * transferBetweenInputOutputDataAndCopyFiles} method to
     * backup input/output files. Then call {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method to try to save input/output data.
     * <p>
     * If calling {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails, then transfer input/output data from copies back into
     * input/output files by {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean)
     * transferBetweenInputOutputDataAndCopyFiles} and load them back into database.
     * <p>
     * After all of it, call {@link IDataFileManager#tryDeleteInputOutputDataFilesCopies() 
     * tryDeleteInputOutputDataFilesCopies} method regardless if calling 
     * {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails or not
     * @throws java.io.IOException if saving tv shows table updated state into input/output files fails
     */
    private void updateTVShowsInputOutputFilesWithNewChanges() throws IOException 
    {
        List<TVShow> currentTVShows = dbContext.getTVShowsTable().getAll();
        dbContext.getTVShowsTable().sortByPrimaryKey(currentTVShows);
        
        List<TVShowInputOutput> inputOutputTVShows = new ArrayList<>();
        TVShowInputOutput inputOutputTVShow;

        for (TVShow m : currentTVShows) 
        {
            inputOutputTVShow = TVShowDataConverter.convertToInputOutputDataFrom(m);
            inputOutputTVShows.add(inputOutputTVShow);
        }
        
        fileManagerAccessor.getTVShowsFileManager().transferBetweenInputOutputDataAndCopyFiles(false);

        try 
        {
            fileManagerAccessor.getTVShowsFileManager().saveInputOutputDataIntoFiles(inputOutputTVShows);
        } 
        catch (IOException e)
        {
            fileManagerAccessor.getTVShowsFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            try 
            {
                inputOutputTVShows = fileManagerAccessor.getTVShowsFileManager().loadInputOutputDataFrom(true);
            }
            catch (IOException | FileParsingException f) 
            {
                fileManagerAccessor.getTVShowsFileManager().tryDeleteInputOutputDataFilesCopies();
                throw new IOException(f.getMessage());
            }
                                   
            TVShow convertedInputOutputTVShow;
            
            dbContext.getTVShowsTable().clearData();
            
            try 
            {
                for (TVShowInputOutput m : inputOutputTVShows) 
                {
                    convertedInputOutputTVShow = TVShowDataConverter.convertToDataFrom(m);
                    dbContext.getTVShowsTable().loadFrom(convertedInputOutputTVShow);
                }        
            }
            catch (DataConversionException | DatabaseException g) 
            {
                for (TVShow m : currentTVShows) 
                {
                    try 
                    {
                        dbContext.getTVShowsTable().loadFrom(m);
                    }
                    catch (DatabaseException h) 
                    {
                    }
                } 
            }    
        } 
        finally 
        {
            fileManagerAccessor.getTVShowsFileManager().tryDeleteInputOutputDataFilesCopies();
        }
    }
    
    /**
     * Represents a method for saving current tv seasons table state into input/output files
     * @throws java.io.IOException if saving tv seasons table state into input/output files fails
     */
    private void updateTVSeasonsInputOutputFilesWithExistingData() throws IOException 
    {
        List<TVSeason> currentTVSeasons = dbContext.getTVSeasonsTable().getAll();
        dbContext.getTVSeasonsTable().sortByPrimaryKey(currentTVSeasons);
        
        List<TVSeasonInputOutput> inputOutputTVSeasons = new ArrayList<>();
        TVSeasonInputOutput inputOutputTVSeason;
        
        for (TVSeason m : currentTVSeasons) 
        {
            inputOutputTVSeason = TVSeasonDataConverter.convertToInputOutputDataFrom(m);
            inputOutputTVSeasons.add(inputOutputTVSeason);
        }
        
        fileManagerAccessor.getTVSeasonsFileManager().saveInputOutputDataIntoFiles(inputOutputTVSeasons);
    }
    
    /**
     * Represents a method for saving updated tv seasons table state into input/output files.
     * <p>
     * The correct usage of this method is to 
     * call {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean) 
     * transferBetweenInputOutputDataAndCopyFiles} method to
     * backup input/output files. Then call {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method to try to save input/output data.
     * <p>
     * If calling {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails, then transfer input/output data from copies back into
     * input/output files by {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean)
     * transferBetweenInputOutputDataAndCopyFiles} and load them back into database.
     * <p>
     * After all of it, call {@link IDataFileManager#tryDeleteInputOutputDataFilesCopies() 
     * tryDeleteInputOutputDataFilesCopies} method regardless if calling 
     * {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails or not
     * @throws java.io.IOException if saving tv seasons table updated state into input/output files fails
     */
    private void updateTVSeasonsInputOutputFilesWithNewChanges() throws IOException 
    {
        List<TVSeason> currentTVSeasons = dbContext.getTVSeasonsTable().getAll();
        dbContext.getTVSeasonsTable().sortByPrimaryKey(currentTVSeasons);
        
        List<TVSeasonInputOutput> inputOutputTVSeasons = new ArrayList<>();
        TVSeasonInputOutput inputOutputTVSeason;

        for (TVSeason m : currentTVSeasons) 
        {
            inputOutputTVSeason = TVSeasonDataConverter.convertToInputOutputDataFrom(m);
            inputOutputTVSeasons.add(inputOutputTVSeason);
        }
        
        fileManagerAccessor.getTVSeasonsFileManager().transferBetweenInputOutputDataAndCopyFiles(false);

        try 
        {
            fileManagerAccessor.getTVSeasonsFileManager().saveInputOutputDataIntoFiles(inputOutputTVSeasons);
        } 
        catch (IOException e)
        {
            fileManagerAccessor.getTVSeasonsFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            try 
            {
                inputOutputTVSeasons = fileManagerAccessor.getTVSeasonsFileManager().loadInputOutputDataFrom(true);
            }
            catch (IOException | FileParsingException f) 
            {
                fileManagerAccessor.getTVSeasonsFileManager().tryDeleteInputOutputDataFilesCopies();
                throw new IOException(f.getMessage());
            }
                                   
            TVSeason convertedInputOutputTVSeason;
            
            dbContext.getTVSeasonsTable().clearData();
            
            try 
            {
                for (TVSeasonInputOutput m : inputOutputTVSeasons) 
                {
                    convertedInputOutputTVSeason = TVSeasonDataConverter.convertToDataFrom(m);
                    dbContext.getTVSeasonsTable().loadFrom(convertedInputOutputTVSeason);
                }        
            }
            catch (DatabaseException g) 
            {
                for (TVSeason m : currentTVSeasons) 
                {
                    try 
                    {
                        dbContext.getTVSeasonsTable().loadFrom(m);
                    }
                    catch (DatabaseException h) 
                    {
                    }
                } 
            }    
        } 
        finally 
        {
            fileManagerAccessor.getTVSeasonsFileManager().tryDeleteInputOutputDataFilesCopies();
        }
    }
    
    /**
     * Represents a method for saving current tv episodes table state into input/output files
     * @throws java.io.IOException if saving tv episodes table state into input/output files fails
     */
    private void updateTVEpisodesInputOutputFilesWithExistingData() throws IOException 
    {
        List<TVEpisode> currentTVEpisodes = dbContext.getTVEpisodesTable().getAll();
        dbContext.getTVEpisodesTable().sortByPrimaryKey(currentTVEpisodes);
        
        List<TVEpisodeInputOutput> inputOutputTVEpisodes = new ArrayList<>();
        TVEpisodeInputOutput inputOutputTVEpisode;
        
        for (TVEpisode m : currentTVEpisodes) 
        {
            inputOutputTVEpisode = TVEpisodeDataConverter.convertToInputOutputDataFrom(m);
            inputOutputTVEpisodes.add(inputOutputTVEpisode);
        }
        
        fileManagerAccessor.getTVEpisodesFileManager().saveInputOutputDataIntoFiles(inputOutputTVEpisodes);
    }
    
    /**
     * Represents a method for saving updated tv episodes table state into input/output files.
     * <p>
     * The correct usage of this method is to 
     * call {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean) 
     * transferBetweenInputOutputDataAndCopyFiles} method to
     * backup input/output files. Then call {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method to try to save input/output data.
     * <p>
     * If calling {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails, then transfer input/output data from copies back into
     * input/output files by {@link IDataFileManager#transferBetweenInputOutputDataAndCopyFiles(boolean)
     * transferBetweenInputOutputDataAndCopyFiles} and load them back into database.
     * <p>
     * After all of it, call {@link IDataFileManager#tryDeleteInputOutputDataFilesCopies() 
     * tryDeleteInputOutputDataFilesCopies} method regardless if calling 
     * {@link IDataFileManager#saveInputOutputDataIntoFiles(java.util.List)
     * saveInputOutputDataIntoFiles} method fails or not
     * @throws java.io.IOException if saving tv episodes table updated state into input/output files fails
     */
    private void updateTVEpisodesInputOutputFilesWithNewChanges() throws IOException 
    {
        List<TVEpisode> currentTVEpisodes = dbContext.getTVEpisodesTable().getAll();
        dbContext.getTVEpisodesTable().sortByPrimaryKey(currentTVEpisodes);
        
        List<TVEpisodeInputOutput> inputOutputTVEpisodes = new ArrayList<>();
        TVEpisodeInputOutput inputOutputTVEpisode;

        for (TVEpisode m : currentTVEpisodes) 
        {
            inputOutputTVEpisode = TVEpisodeDataConverter.convertToInputOutputDataFrom(m);
            inputOutputTVEpisodes.add(inputOutputTVEpisode);
        }
        
        fileManagerAccessor.getTVEpisodesFileManager().transferBetweenInputOutputDataAndCopyFiles(false);

        try 
        {
            fileManagerAccessor.getTVEpisodesFileManager().saveInputOutputDataIntoFiles(inputOutputTVEpisodes);
        } 
        catch (IOException e)
        {
            fileManagerAccessor.getTVEpisodesFileManager().transferBetweenInputOutputDataAndCopyFiles(true);
            
            try 
            {
                inputOutputTVEpisodes = fileManagerAccessor.getTVEpisodesFileManager().loadInputOutputDataFrom(true);
            }
            catch (IOException | FileParsingException f) 
            {
                fileManagerAccessor.getTVEpisodesFileManager().tryDeleteInputOutputDataFilesCopies();
                throw new IOException(f.getMessage());
            }
                                   
            TVEpisode convertedInputOutputTVEpisode;
            
            dbContext.getTVEpisodesTable().clearData();
            
            try 
            {
                for (TVEpisodeInputOutput m : inputOutputTVEpisodes) 
                {
                    convertedInputOutputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(m);
                    dbContext.getTVEpisodesTable().loadFrom(convertedInputOutputTVEpisode);
                }      
            }
            catch (DatabaseException g) 
            {
                for (TVEpisode m : currentTVEpisodes) 
                {
                    try 
                    {
                        dbContext.getTVEpisodesTable().loadFrom(m);
                    }
                    catch (DatabaseException h) 
                    {
                    }
                } 
            }      
        } 
        finally 
        {
            fileManagerAccessor.getTVEpisodesFileManager().tryDeleteInputOutputDataFilesCopies();
        }
    }
    
    /**
     * Represents a method for getting current date (present date as LocalDate).
     * Timezone is determined from operating system running application
     * @return instance of LocalDate, representing present date
     */
    public static LocalDate getCurrentDate() 
    {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
