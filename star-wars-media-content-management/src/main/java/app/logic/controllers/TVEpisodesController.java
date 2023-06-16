
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
import app.models.output.TVEpisodeOutput;
import app.models.output.TVSeasonOutput;
import app.models.output.TVShowOutput;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
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

/**
 *
 * @author Admin
 */
public class TVEpisodesController 
{
    private static TVEpisodesController tvEpisodesController;
    
    private final DataContextAccessor dbContext;
    
    private final EmailSender emailSender;
    
    private final FileManagerAccessor fileManagerAccessor;
    
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
    
    private final Comparator<TVShow> BY_DATE_NEWEST_TVSHOW = (TVShow m1, TVShow m2) -> 
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
    
    private TVEpisodesController(DataContextAccessor dbContext, EmailSender emailSender, 
            FileManagerAccessor fileManagerAccessor) 
    {
        this.dbContext = dbContext;
        this.emailSender = emailSender;
        this.fileManagerAccessor = fileManagerAccessor;
    }
    
    public static TVEpisodesController getInstance(DataContextAccessor dbContext, EmailSender emailSender, FileManagerAccessor
            fileManagerAccessor) 
    {
        if (tvEpisodesController == null) 
        {
            tvEpisodesController = new TVEpisodesController(dbContext, emailSender, fileManagerAccessor);
        }
        
        return tvEpisodesController;
    }
    
    //email method
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
                        durationText = e.getRuntime() == null ? "<span style=\"color:red\">Není známa</span>" : 
                                String.format("%02d:%02d:%02d", e.getRuntime().toHoursPart(), e.getRuntime().toMinutesPart(),
                                e.getRuntime().toSecondsPart());
                        
                        nameText = e.getName() == null ? "<span style=\"color:red\">Není znám</span>" : e.getName();
                        
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
    
    //statistic method
    public Map<Integer, Duration> getTotalRuntimeOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) 
    {
        Duration totalDuration = Duration.ZERO;
        int durationsCount = 0;
        
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
                if (e.getRuntime() != null) 
                {
                    durationsCount++;
                    totalDuration = totalDuration.plus(e.getRuntime());
                }
            }
        }
        
        Map<Integer, Duration> result = new LinkedHashMap<>();
        result.put(durationsCount, totalDuration);
        
        return result;
    }
    
    //statistic method
    public Map<Integer, Duration> getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) 
    {
        Duration totalDuration = Duration.ZERO;
        int durationsCount = 0;
        
        List<TVEpisode> seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(s -> s.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey) &&
                        s.getWasWatched() == onlyWatched);
        
        for (TVEpisode e : seasonEpisodes) 
        {
            if (e.getRuntime() != null) 
            {
                durationsCount++;
                totalDuration = totalDuration.plus(e.getRuntime());
            }
        }
                
        Map<Integer, Duration> result = new LinkedHashMap<>();
        result.put(durationsCount, totalDuration);
              
        return result;
    }
    
    //statistic method
    public Map<Integer, Duration> getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) 
    {        
        long averageSeconds;
        
        Map<Integer, Duration> totalRuntimeOfAllReleasedEpisodesInTVShowSeason = 
                getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(tvShowSeasonPrimaryKey, onlyWatched);
        
        int durationsCount = totalRuntimeOfAllReleasedEpisodesInTVShowSeason.keySet().iterator().next();
        Duration totalDuration = totalRuntimeOfAllReleasedEpisodesInTVShowSeason.get(durationsCount);
        
        if (durationsCount == 0) 
        {
            averageSeconds = 0;
        }
        else 
        {
            averageSeconds = totalDuration.toSeconds() / durationsCount;
        }
        
        Duration averageDuration = Duration.ofSeconds(averageSeconds);
        
        Map<Integer, Duration> result = new LinkedHashMap<>();
        result.put(durationsCount, averageDuration);
       
        return result;
    }
    
    //statistic method
    public Map<Integer, Duration> getAverageRuntimeOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) 
    {        
        long averageSeconds;
        
        Map<Integer, Duration> totalRuntimeOfAllReleasedEpisodesInTVShow = 
                getTotalRuntimeOfAllReleasedEpisodesInTVShow(tvShowPrimaryKey, onlyWatched);
        
        int durationsCount = totalRuntimeOfAllReleasedEpisodesInTVShow.keySet().iterator().next();
        Duration totalDuration = totalRuntimeOfAllReleasedEpisodesInTVShow.get(durationsCount);
        
        if (durationsCount == 0) 
        {
            averageSeconds = 0;
        }
        else 
        {
            averageSeconds = totalDuration.toSeconds() / durationsCount;
        }
        
        Duration averageDuration = Duration.ofSeconds(averageSeconds);
        
        Map<Integer, Duration> result = new LinkedHashMap<>();
        result.put(durationsCount, averageDuration);
              
        return result;
    }
    
    //statistic method
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
    
    //statistic method
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
    
    public List<TVShow> getNewestReleasedTVShows()
    {
        LocalDate currentDate = getCurrentDate();
        
        List<TVShow> filteredTVShows = dbContext.getTVShowsTable().filterBy(m -> 
                m.getReleaseDate() != null && 
                m.getReleaseDate().compareTo(currentDate) <= 0);
        
        dbContext.getTVShowsTable().sortBy(BY_DATE_NEWEST_TVSHOW, filteredTVShows);
                
        return filteredTVShows;
    }
    
    public List<TVShow> getLongestReleasedTVShowsByEra(Era era) 
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
                    if (episode.getRuntime() != null) 
                    {
                        showTotalDuration = showTotalDuration.plus(episode.getRuntime());
                    }
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
    
    public List<TVEpisode> getReleasedTVShowSeasonLongestEpisodes(PrimaryKey tvShowSeasonPrimaryKey) 
    {
        List<TVEpisode> seasonEpisodes;
        
        seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(e -> e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey));
        
        dbContext.getTVEpisodesTable().sortBy(BY_LONGEST_DURATION_EPISODE, seasonEpisodes);

        return seasonEpisodes;
    }
    
    public List<TVShow> getAnnouncedTVShowsByEra(Era era) 
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
        List<TVShow> filteredTVShows = getAnnouncedTVShowsByEra(era);
                
        return filteredTVShows.size();
    }
    
    public List<TVEpisode> getFavoriteTVEpisodesFromEntireTVShow(PrimaryKey tvShowPrimaryKey) 
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
    
    public int getTVShowSeasonEpisodesCount(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) 
    {
        List<TVEpisode> filteredEpisodes = dbContext.getTVEpisodesTable().filterBy(e -> 
                e.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey) && 
                        e.getWasWatched() == onlyWatched);
        
        return filteredEpisodes.size();
    }
    
    public List<TVSeason> getTVShowSeasonsByOrder(PrimaryKey tvShowPrimaryKey) 
    {
        List<TVSeason> showSeasons;
        
        showSeasons = dbContext.
                getTVSeasonsTable().filterBy(e -> e.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        dbContext.getTVSeasonsTable().sortBy(BY_ORDER_ASCENDING_SEASON, showSeasons);
        
        return showSeasons;
    }
    
    public int getTVShowSeasonsCount(PrimaryKey tvShowPrimaryKey) 
    {
        List<TVSeason> filteredSeasons = getTVShowSeasonsByOrder(tvShowPrimaryKey);
        
        return filteredSeasons.size();
    }
    
    public boolean rateTVEpisode(TVEpisode existingEpisode, int percentageRating) throws DatabaseException, IOException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        
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
            updateTVEpisodesOutputFilesWithNewChanges();
        }
                
        return wasDataChanged;
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
    
    public StringBuilder getTVShowsChosenFileContent(String fileName) throws IOException, FileNotFoundException, FileEmptyException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryOutputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getBinaryOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextOutputTVShowsFilename())) 
        {
            content = fileManagerAccessor.getTVShowsFileManager().getTextOutputFileContent();
        }
        
        return content;
    }
    
    public StringBuilder getTVSeasonsChosenFileContent(String fileName) throws IOException, FileNotFoundException, FileEmptyException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryOutputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getBinaryOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextOutputTVSeasonsFilename())) 
        {
            content = fileManagerAccessor.getTVSeasonsFileManager().getTextOutputFileContent();
        }
        
        return content;
    }
    
    public StringBuilder getTVEpisodesChosenFileContent(String fileName) throws IOException, FileNotFoundException, FileEmptyException 
    {
        StringBuilder content = new StringBuilder();
        
        if (fileName.equals(DataStore.getBinaryInputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getBinaryInputFileContent();
        }
        else if (fileName.equals(DataStore.getBinaryOutputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getBinaryOutputFileContent();
        }
        else if (fileName.equals(DataStore.getTextInputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getTextInputFileContent();
        }
        else if (fileName.equals(DataStore.getTextOutputTVEpisodesFilename())) 
        {
            content = fileManagerAccessor.getTVEpisodesFileManager().getTextOutputFileContent();
        }
        
        return content;
    }
    
    public void loadAllOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException, 
            DataConversionException, DatabaseException, Exception 
    {
        try 
        {
            List<TVShowOutput> outputTVShows = fileManagerAccessor.getTVShowsFileManager().
                    loadOutputDataFrom(fromBinary);
            List<TVSeasonOutput> outputTVSeasons = fileManagerAccessor.getTVSeasonsFileManager().
                    loadOutputDataFrom(fromBinary);
            List<TVEpisodeOutput> outputTVEpisodes = fileManagerAccessor.getTVEpisodesFileManager().
                    loadOutputDataFrom(fromBinary);
        
            TVShow convertedOutputTVShow;
            TVSeason convertedOutputTVSeason;
            TVEpisode convertedOutputTVEpisode;
        
            for (TVShowOutput m : outputTVShows) 
            {
                convertedOutputTVShow = TVShowDataConverter.convertToDataFrom(m);
                dbContext.getTVShowsTable().loadFrom(convertedOutputTVShow);
            }

            for (TVSeasonOutput m : outputTVSeasons) 
            {
                convertedOutputTVSeason = TVSeasonDataConverter.convertToDataFrom(m);
                dbContext.getTVSeasonsTable().loadFrom(convertedOutputTVSeason);
            }

            for (TVEpisodeOutput m : outputTVEpisodes) 
            {
                convertedOutputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(m);
                dbContext.getTVEpisodesTable().loadFrom(convertedOutputTVEpisode);
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
    
    public StringBuilder addTVShowsFrom(boolean fromBinary) throws IOException, FileNotFoundException, FileEmptyException, FileParsingException 
    {
        updateTVShowsOutputFilesWithExistingData();
        
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

        updateTVShowsOutputFilesWithNewChanges();

        return message;
    }
    
    public StringBuilder addTVSeasonsFrom(PrimaryKey chosenTVShowPrimaryKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
    {
        updateTVSeasonsOutputFilesWithExistingData();
        
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

        updateTVSeasonsOutputFilesWithNewChanges();

        return message;
    }
    
    public StringBuilder addTVEpisodesFrom(PrimaryKey chosenTVSeasonPrimaryKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        
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

        updateTVEpisodesOutputFilesWithNewChanges();

        return message;
    }
    
    public void deleteTVShowBy(PrimaryKey tvShowPrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        updateTVSeasonsOutputFilesWithExistingData();
        updateTVShowsOutputFilesWithExistingData();
               
        dbContext.getTVShowsTable().deleteBy(tvShowPrimaryKey);
        
        updateTVEpisodesOutputFilesWithNewChanges();
        updateTVSeasonsOutputFilesWithNewChanges();
        updateTVShowsOutputFilesWithNewChanges();
    }
    
    public void deleteTVSeasonBy(PrimaryKey tvSeasonPrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        updateTVSeasonsOutputFilesWithExistingData();

        dbContext.getTVSeasonsTable().deleteBy(tvSeasonPrimaryKey);
        
        updateTVEpisodesOutputFilesWithNewChanges();
        updateTVSeasonsOutputFilesWithNewChanges();
    }
    
    public void deleteTVEpisodeBy(PrimaryKey tvEpisodePrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesOutputFilesWithExistingData();

        dbContext.getTVEpisodesTable().deleteBy(tvEpisodePrimaryKey);

        updateTVEpisodesOutputFilesWithNewChanges();
    }
    
    public void deleteTVShows(List<TVShow> chosenTVShows) throws IOException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        updateTVSeasonsOutputFilesWithExistingData();
        updateTVShowsOutputFilesWithExistingData();
                        
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
        
        updateTVEpisodesOutputFilesWithNewChanges();
        updateTVSeasonsOutputFilesWithNewChanges();
        updateTVShowsOutputFilesWithNewChanges();
    }
    
    public void deleteTVSeasons(List<TVSeason> chosenTVSeasons) throws IOException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        updateTVSeasonsOutputFilesWithExistingData();
                        
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
        
        updateTVEpisodesOutputFilesWithNewChanges();
        updateTVSeasonsOutputFilesWithNewChanges();
    }
    
    public void deleteTVEpisodes(List<TVEpisode> chosenTVEpisodes) throws IOException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        
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

        updateTVEpisodesOutputFilesWithNewChanges();
    }
    
    public boolean editTVShowBy(PrimaryKey existingTVShowPrimaryKey, boolean fromBinary) throws IOException, 
            FileNotFoundException, FileEmptyException, DataConversionException, DatabaseException, FileParsingException 
    {
        updateTVShowsOutputFilesWithExistingData();
        
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
            updateTVShowsOutputFilesWithNewChanges();
        }
        
        return wasDataChanged; 
    }
    
    public boolean editTVSeasonBy(PrimaryKey existingTVSeasonPrimaryKey, PrimaryKey tvSeasonForeignKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, DatabaseException, FileParsingException 
    {
        updateTVSeasonsOutputFilesWithExistingData();
        
        Map<Integer, TVSeasonInput> editedTVSeason = fileManagerAccessor.getTVSeasonsFileManager().loadInputDataFrom(fromBinary);
        
        String filename = fromBinary == true ? DataStore.getBinaryInputTVSeasonsFilename() : DataStore.getTextInputTVSeasonsFilename();
                        
        if (editedTVSeason.size() > 1 || editedTVSeason.get(1) == null) 
        {
            throw new FileParsingException("Soubor " + filename + " musí obsahovat právě jednu sezónu vybranou pro editaci");
        }
        
        TVSeason convertedInputTVSeason = TVSeasonDataConverter.convertToDataFrom(editedTVSeason.get(1), tvSeasonForeignKey);

        boolean wasDataChanged = dbContext.getTVSeasonsTable().editBy(existingTVSeasonPrimaryKey, convertedInputTVSeason);
        
        if (wasDataChanged == true) 
        {
            updateTVSeasonsOutputFilesWithNewChanges();
        }
        
        return wasDataChanged; 
    }
    
    public boolean editTVEpisodeBy(PrimaryKey existingTVEpisodePrimaryKey, PrimaryKey tvEpisodeForeignKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException, DatabaseException, FileParsingException 
    {
        updateTVEpisodesOutputFilesWithExistingData();
        
        Map<Integer, TVEpisodeInput> editedTVEpisode = fileManagerAccessor.getTVEpisodesFileManager().loadInputDataFrom(fromBinary);
        
        String filename = fromBinary == true ? DataStore.getBinaryInputTVEpisodesFilename() : DataStore.getTextInputTVEpisodesFilename();
                        
        if (editedTVEpisode.size() > 1 || editedTVEpisode.get(1) == null) 
        {
            throw new FileParsingException("Soubor " + filename + " musí obsahovat právě jednu epizodu vybranou pro editaci");
        }
        
        TVEpisode convertedInputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(editedTVEpisode.get(1), tvEpisodeForeignKey);

        boolean wasDataChanged = dbContext.getTVEpisodesTable().editBy(existingTVEpisodePrimaryKey, convertedInputTVEpisode);
        
        if (wasDataChanged == true) 
        {
            updateTVEpisodesOutputFilesWithNewChanges();
        }

        return wasDataChanged; 
    }
        
    private void updateTVShowsOutputFilesWithExistingData() throws IOException 
    {
        List<TVShow> currentTVShows = dbContext.getTVShowsTable().getAll();
        dbContext.getTVShowsTable().sortByPrimaryKey(currentTVShows);
        
        List<TVShowOutput> outputTVShows = new ArrayList<>();
        TVShowOutput outputTVShow;
        
        for (TVShow m : currentTVShows) 
        {
            outputTVShow = TVShowDataConverter.convertToOutputDataFrom(m);
            outputTVShows.add(outputTVShow);
        }
        
        fileManagerAccessor.getTVShowsFileManager().saveOutputDataIntoFiles(outputTVShows);
    }
    
    private void updateTVShowsOutputFilesWithNewChanges() throws IOException 
    {
        List<TVShow> currentTVShows = dbContext.getTVShowsTable().getAll();
        dbContext.getTVShowsTable().sortByPrimaryKey(currentTVShows);
        
        List<TVShowOutput> outputTVShows = new ArrayList<>();
        TVShowOutput outputTVShow;

        for (TVShow m : currentTVShows) 
        {
            outputTVShow = TVShowDataConverter.convertToOutputDataFrom(m);
            outputTVShows.add(outputTVShow);
        }
        
        fileManagerAccessor.getTVShowsFileManager().transferBetweenOutputDataAndCopyFiles(false);

        try 
        {
            fileManagerAccessor.getTVShowsFileManager().saveOutputDataIntoFiles(outputTVShows);
        } 
        catch (IOException e)
        {
            fileManagerAccessor.getTVShowsFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            try 
            {
                outputTVShows = fileManagerAccessor.getTVShowsFileManager().loadOutputDataFrom(true);
            }
            catch (IOException | FileParsingException f) 
            {
                fileManagerAccessor.getTVShowsFileManager().tryDeleteDataOutputFilesCopies();
                throw new IOException(f.getMessage());
            }
                                   
            TVShow convertedOutputTVShow;
            
            dbContext.getTVShowsTable().clearData();
            
            try 
            {
                for (TVShowOutput m : outputTVShows) 
                {
                    convertedOutputTVShow = TVShowDataConverter.convertToDataFrom(m);
                    dbContext.getTVShowsTable().loadFrom(convertedOutputTVShow);
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
            fileManagerAccessor.getTVShowsFileManager().tryDeleteDataOutputFilesCopies();
        }
    }
    
    private void updateTVSeasonsOutputFilesWithExistingData() throws IOException 
    {
        List<TVSeason> currentTVSeasons = dbContext.getTVSeasonsTable().getAll();
        dbContext.getTVSeasonsTable().sortByPrimaryKey(currentTVSeasons);
        
        List<TVSeasonOutput> outputTVSeasons = new ArrayList<>();
        TVSeasonOutput outputTVSeason;
        
        for (TVSeason m : currentTVSeasons) 
        {
            outputTVSeason = TVSeasonDataConverter.convertToOutputDataFrom(m);
            outputTVSeasons.add(outputTVSeason);
        }
        
        fileManagerAccessor.getTVSeasonsFileManager().saveOutputDataIntoFiles(outputTVSeasons);
    }
    
    private void updateTVSeasonsOutputFilesWithNewChanges() throws IOException 
    {
        List<TVSeason> currentTVSeasons = dbContext.getTVSeasonsTable().getAll();
        dbContext.getTVSeasonsTable().sortByPrimaryKey(currentTVSeasons);
        
        List<TVSeasonOutput> outputTVSeasons = new ArrayList<>();
        TVSeasonOutput outputTVSeason;

        for (TVSeason m : currentTVSeasons) 
        {
            outputTVSeason = TVSeasonDataConverter.convertToOutputDataFrom(m);
            outputTVSeasons.add(outputTVSeason);
        }
        
        fileManagerAccessor.getTVSeasonsFileManager().transferBetweenOutputDataAndCopyFiles(false);

        try 
        {
            fileManagerAccessor.getTVSeasonsFileManager().saveOutputDataIntoFiles(outputTVSeasons);
        } 
        catch (IOException e)
        {
            fileManagerAccessor.getTVSeasonsFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            try 
            {
                outputTVSeasons = fileManagerAccessor.getTVSeasonsFileManager().loadOutputDataFrom(true);
            }
            catch (IOException | FileParsingException f) 
            {
                fileManagerAccessor.getTVSeasonsFileManager().tryDeleteDataOutputFilesCopies();
                throw new IOException(f.getMessage());
            }
                                   
            TVSeason convertedOutputTVSeason;
            
            dbContext.getTVSeasonsTable().clearData();
            
            try 
            {
                for (TVSeasonOutput m : outputTVSeasons) 
                {
                    convertedOutputTVSeason = TVSeasonDataConverter.convertToDataFrom(m);
                    dbContext.getTVSeasonsTable().loadFrom(convertedOutputTVSeason);
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
            fileManagerAccessor.getTVSeasonsFileManager().tryDeleteDataOutputFilesCopies();
        }
    }
    
    private void updateTVEpisodesOutputFilesWithExistingData() throws IOException 
    {
        List<TVEpisode> currentTVEpisodes = dbContext.getTVEpisodesTable().getAll();
        dbContext.getTVEpisodesTable().sortByPrimaryKey(currentTVEpisodes);
        
        List<TVEpisodeOutput> outputTVEpisodes = new ArrayList<>();
        TVEpisodeOutput outputTVEpisode;
        
        for (TVEpisode m : currentTVEpisodes) 
        {
            outputTVEpisode = TVEpisodeDataConverter.convertToOutputDataFrom(m);
            outputTVEpisodes.add(outputTVEpisode);
        }
        
        fileManagerAccessor.getTVEpisodesFileManager().saveOutputDataIntoFiles(outputTVEpisodes);
    }
    
    private void updateTVEpisodesOutputFilesWithNewChanges() throws IOException 
    {
        List<TVEpisode> currentTVEpisodes = dbContext.getTVEpisodesTable().getAll();
        dbContext.getTVEpisodesTable().sortByPrimaryKey(currentTVEpisodes);
        
        List<TVEpisodeOutput> outputTVEpisodes = new ArrayList<>();
        TVEpisodeOutput outputTVEpisode;

        for (TVEpisode m : currentTVEpisodes) 
        {
            outputTVEpisode = TVEpisodeDataConverter.convertToOutputDataFrom(m);
            outputTVEpisodes.add(outputTVEpisode);
        }
        
        fileManagerAccessor.getTVEpisodesFileManager().transferBetweenOutputDataAndCopyFiles(false);

        try 
        {
            fileManagerAccessor.getTVEpisodesFileManager().saveOutputDataIntoFiles(outputTVEpisodes);
        } 
        catch (IOException e)
        {
            fileManagerAccessor.getTVEpisodesFileManager().transferBetweenOutputDataAndCopyFiles(true);
            
            try 
            {
                outputTVEpisodes = fileManagerAccessor.getTVEpisodesFileManager().loadOutputDataFrom(true);
            }
            catch (IOException | FileParsingException f) 
            {
                fileManagerAccessor.getTVEpisodesFileManager().tryDeleteDataOutputFilesCopies();
                throw new IOException(f.getMessage());
            }
                                   
            TVEpisode convertedOutputTVEpisode;
            
            dbContext.getTVEpisodesTable().clearData();
            
            try 
            {
                for (TVEpisodeOutput m : outputTVEpisodes) 
                {
                    convertedOutputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(m);
                    dbContext.getTVEpisodesTable().loadFrom(convertedOutputTVEpisode);
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
            fileManagerAccessor.getTVEpisodesFileManager().tryDeleteDataOutputFilesCopies();
        }
    }
    
    private static LocalDate getCurrentDate() 
    {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
