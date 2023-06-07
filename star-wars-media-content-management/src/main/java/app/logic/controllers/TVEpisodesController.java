
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
        TVShow queriedTVShow = dbContext.getTVShowsTable().getBy(tvShowPrimaryKey);
        LocalDate currentDate = getCurrentDate();
        
        if (queriedTVShow == null) 
        {
            throw new DatabaseException("Seriál vybraný pro odeslání e-mailu nebyl nalezen");
        }
        else if (queriedTVShow.getReleaseDate() == null || 
                 queriedTVShow.getReleaseDate().compareTo(currentDate) > 0)
        
        {
            throw new DatabaseException("Seriál vybraný pro odeslání e-mailu ještě nebyl vydán");
        }
        
        List<TVEpisode> seasonEpisodes;
       
        List<TVSeason> showSeasons = dbContext.
                getTVSeasonsTable().filterBy(s -> s.getTVShowForeignKey().equals(tvShowPrimaryKey));
        
        dbContext.getTVSeasonsTable().sortBy(BY_ORDER_ASCENDING_SEASON, showSeasons);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.forLanguageTag("cs-CZ"));
        
        String subject = String.format("%s - Nezhlédnuté epizody seriálu %s, nacházejícího se v období %s"
                + " s datumem vydání %s", 
                DataStore.getAppName(), queriedTVShow.getName(), queriedTVShow.getEra().getDisplayName(), 
                queriedTVShow.getReleaseDate().format(formatter));
        
        StringBuilder message = new StringBuilder();
        String durationText;
        String nameText;
        String hyperlinkText;
        
        message.append("<html>");
        message.append("<h1>");
        message.append(String.format("Nezhlédnuté epizody seriálu %s", queriedTVShow.getName()));
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
                                String.format("%dh %dm %ds", e.getRuntime().toHoursPart(), e.getRuntime().toMinutesPart(),
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
    public Duration getTotalRuntimeOfAllEpisodesInTVShow(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) 
    {
        Duration duration = Duration.ZERO;
        
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
                    duration = duration.plus(e.getRuntime());
                }
            }
        }
        
        return duration;
    }
    
    //statistic method
    public Duration getTotalRuntimeOfAllEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) 
    {
        Duration duration = Duration.ZERO;
        
        List<TVEpisode> seasonEpisodes = dbContext.
                getTVEpisodesTable().filterBy(s -> s.getTVSeasonForeignKey().equals(tvShowSeasonPrimaryKey) &&
                        s.getWasWatched() == onlyWatched);
        
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
    
    public boolean rateTVEpisode(TVEpisode existingEpisode, int percentageRating) throws DatabaseException, IOException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        
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
    
    public StringBuilder getTVSeasonsChosenFileContent(String fileName) throws IOException, FileNotFoundException, FileNotFoundException 
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
    
    public TVShow getTVShowDetail(PrimaryKey chosenTVShowPrimaryKey) 
    {
        TVShow foundTVShow = dbContext.getTVShowsTable().getBy(chosenTVShowPrimaryKey);
        
        return foundTVShow;
    }
    
    public TVSeason getTVSeasonDetail(PrimaryKey chosenTVSeasonPrimaryKey) 
    {
        TVSeason foundTVSeason = dbContext.getTVSeasonsTable().getBy(chosenTVSeasonPrimaryKey);
        
        return foundTVSeason;
    }
    
    public TVEpisode getTVEpisodeDetail(PrimaryKey chosenTVEpisodePrimaryKey) 
    {
        TVEpisode foundTVEpisode = dbContext.getTVEpisodesTable().getBy(chosenTVEpisodePrimaryKey);
        
        return foundTVEpisode;
    }
    
    public StringBuilder addTVShowsFrom(boolean fromBinary) throws IOException, FileNotFoundException, FileEmptyException 
    {
        updateTVShowsOutputFilesWithExistingData();
        
        List<TVShowInput> inputTVShows = fileManagerAccessor.getTVShowsFileManager().loadInputDataFrom(fromBinary);
        
        StringBuilder message = new StringBuilder();
        
        if (inputTVShows.isEmpty()) 
        {
            message.append("Nic se nenahrálo ze souboru ").append(fromBinary == true ? 
                    DataStore.getBinaryInputTVShowsFilename() : DataStore.getTextInputTVShowsFilename());
            return message;
        }
        else 
        {
            StringBuilder moviesErrorMessages = new StringBuilder();
            TVShow convertedInputTVShow;
            int counter = 0;
            int errorCounter = 0;
            
            for (TVShowInput inputTVShow : inputTVShows) 
            {
                counter++;
                
                try 
                {
                    convertedInputTVShow = TVShowDataConverter.convertToDataFrom(inputTVShow);
                    dbContext.getTVShowsTable().addFrom(convertedInputTVShow);
                }
                catch (DatabaseException | DataConversionException e) 
                {
                    errorCounter++;
                    moviesErrorMessages.append(String.format("Chybový stav seriálu s pořadím %d: %s", 
                            counter, e.getMessage())).append("\n");
                }
            }
            
            int successfullyUploadedTVShowsCount = inputTVShows.size() - errorCounter;
            message.append(String.format("Celkově se podařilo nahrát %d seriálů do databáze a naopak se nepodařilo nahrát %d seriálů", 
                    successfullyUploadedTVShowsCount, errorCounter)).append("\n");
            message.append(moviesErrorMessages);
                  
            updateTVShowsOutputFilesWithNewChanges();
        }
        
        return message;
    }
    
    public StringBuilder addTVSeasonsFrom(PrimaryKey chosenTVShowPrimaryKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException 
    {
        updateTVSeasonsOutputFilesWithExistingData();
        
        List<TVSeasonInput> inputTVSeasons = fileManagerAccessor.getTVSeasonsFileManager().loadInputDataFrom(fromBinary);
        
        StringBuilder message = new StringBuilder();
        
        if (inputTVSeasons.isEmpty()) 
        {
            message.append("Nic se nenahrálo ze souboru ").append(fromBinary == true ? 
                    DataStore.getBinaryInputTVSeasonsFilename() : DataStore.getTextInputTVSeasonsFilename());
            return message;
        }
        else 
        {
            StringBuilder moviesErrorMessages = new StringBuilder();
            TVSeason convertedInputTVSeason;
            int counter = 0;
            int errorCounter = 0;
            
            for (TVSeasonInput inputTVSeason : inputTVSeasons) 
            {
                counter++;
                
                try 
                {
                    convertedInputTVSeason = TVSeasonDataConverter.convertToDataFrom(inputTVSeason, chosenTVShowPrimaryKey);
                    dbContext.getTVSeasonsTable().addFrom(convertedInputTVSeason);
                }
                catch (DatabaseException e) 
                {
                    errorCounter++;
                    moviesErrorMessages.append(String.format("Chybový stav sezóny vybraného seriálu s pořadím %d: %s", 
                            counter, e.getMessage())).append("\n");
                }
            }

            int successfullyUploadedTVSeasonsCount = inputTVSeasons.size() - errorCounter;
            message.append(String.format("Celkově se podařilo nahrát %d sezón vybraného seriálu "
                    + "do databáze a naopak se nepodařilo nahrát %d sezón vybraného seriálu", 
                    successfullyUploadedTVSeasonsCount, errorCounter)).append("\n");
            message.append(moviesErrorMessages);
                  
            updateTVSeasonsOutputFilesWithNewChanges();
        }
        
        return message;
    }
    
    public StringBuilder addTVEpisodesFrom(PrimaryKey chosenTVSeasonPrimaryKey, boolean fromBinary) 
            throws IOException, FileNotFoundException, FileEmptyException 
    {
        updateTVEpisodesOutputFilesWithExistingData();
        
        List<TVEpisodeInput> inputTVEpisodes = fileManagerAccessor.getTVEpisodesFileManager().loadInputDataFrom(fromBinary);
        
        StringBuilder message = new StringBuilder();
        
        if (inputTVEpisodes.isEmpty()) 
        {
            message.append("Nic se nenahrálo ze souboru ").append(fromBinary == true ? 
                    DataStore.getBinaryInputTVEpisodesFilename() : DataStore.getTextInputTVEpisodesFilename());
            return message;
        }
        else 
        {
            StringBuilder moviesErrorMessages = new StringBuilder();
            TVEpisode convertedInputTVEpisode;
            int counter = 0;
            int errorCounter = 0;
            
            for (TVEpisodeInput inputTVEpisode : inputTVEpisodes) 
            {
                counter++;
                
                try 
                {
                    convertedInputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(inputTVEpisode, chosenTVSeasonPrimaryKey);
                    dbContext.getTVEpisodesTable().addFrom(convertedInputTVEpisode);
                }
                catch (DatabaseException e) 
                {
                    errorCounter++;
                    moviesErrorMessages.append(String.format("Chybový stav epizody vybrané sezóny s pořadím %d: %s", 
                            counter, e.getMessage())).append("\n");
                }
            }

            int successfullyUploadedTVEpisodesCount = inputTVEpisodes.size() - errorCounter;
            message.append(String.format("Celkově se podařilo nahrát %d epizod vybrané sezóny do databáze"
                    + " a naopak se nepodařilo nahrát %d epizod vybrané sezóny", 
                    successfullyUploadedTVEpisodesCount, errorCounter)).append("\n");
            message.append(moviesErrorMessages);
                  
            updateTVEpisodesOutputFilesWithNewChanges();
        }
        
        return message;
    }
    
    public void deleteTVShowBy(PrimaryKey tvShowPrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        updateTVSeasonsOutputFilesWithExistingData();
        updateTVShowsOutputFilesWithExistingData();
       
        List<TVSeason> showSeasons = dbContext.getTVSeasonsTable().filterBy(e -> 
                    e.getTVShowForeignKey().equals(tvShowPrimaryKey));
        List<TVEpisode> seasonEpisodes;
            
        for (TVSeason o : showSeasons) 
        {
            seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e ->
                    e.getTVSeasonForeignKey().equals(o.getPrimaryKey()));
                
            for (TVEpisode s : seasonEpisodes) 
            {
                try 
                {
                    dbContext.getTVEpisodesTable().deleteBy(s.getPrimaryKey());
                }
                catch (DatabaseException e) 
                {
                }
            }
            
            try 
            {
                dbContext.getTVSeasonsTable().deleteBy(o.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }
        
        dbContext.getTVShowsTable().deleteBy(tvShowPrimaryKey);
        
        updateTVEpisodesOutputFilesWithNewChanges();
        updateTVSeasonsOutputFilesWithNewChanges();
        updateTVShowsOutputFilesWithNewChanges();
    }
    
    public void deleteTVSeasonBy(PrimaryKey tvSeasonPrimaryKey) throws IOException, DatabaseException
    {
        updateTVEpisodesOutputFilesWithExistingData();
        updateTVSeasonsOutputFilesWithExistingData();
                
        List<TVEpisode> seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e -> 
                e.getTVSeasonForeignKey().equals(tvSeasonPrimaryKey));

        for (TVEpisode o : seasonEpisodes) 
        {
            try 
            {
                dbContext.getTVEpisodesTable().deleteBy(o.getPrimaryKey());
            }
            catch (DatabaseException e) 
            {
            }
        }

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
                
        List<TVSeason> showSeasons;
        List<TVEpisode> seasonEpisodes;
        
        for (TVShow m : chosenTVShows) 
        {
            showSeasons = dbContext.getTVSeasonsTable().filterBy(e -> 
                    e.getTVShowForeignKey().equals(m.getPrimaryKey()));
            
            for (TVSeason o : showSeasons) 
            {
                seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e ->
                    e.getTVSeasonForeignKey().equals(o.getPrimaryKey()));
                
                for (TVEpisode s : seasonEpisodes) 
                {
                    try 
                    {
                        dbContext.getTVEpisodesTable().deleteBy(s.getPrimaryKey());
                    }
                    catch (DatabaseException e) 
                    {
                    }
                }
                
                try 
                {
                    dbContext.getTVSeasonsTable().deleteBy(o.getPrimaryKey());
                }
                catch (DatabaseException e) 
                {
                }
            }
            
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
                
        List<TVEpisode> seasonEpisodes;
        
        for (TVSeason m : chosenTVSeasons) 
        {
            seasonEpisodes = dbContext.getTVEpisodesTable().filterBy(e -> 
                    e.getTVSeasonForeignKey().equals(m.getPrimaryKey()));
            
            for (TVEpisode o : seasonEpisodes) 
            {
                try 
                {
                    dbContext.getTVEpisodesTable().deleteBy(o.getPrimaryKey());
                }
                catch (DatabaseException e) 
                {
                }
            }
            
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
            FileNotFoundException, FileEmptyException, FileParsingException, DataConversionException, DatabaseException 
    {
        updateTVShowsOutputFilesWithExistingData();
        
        List<TVShowInput> editedTVShow = fileManagerAccessor.getTVShowsFileManager().loadInputDataFrom(fromBinary);
                
        if (editedTVShow.isEmpty()) 
        {
            String filename = fromBinary == true ? DataStore.getBinaryInputTVShowsFilename() : DataStore.getTextInputTVShowsFilename();
            throw new FileParsingException("Data seriálu vybraného pro editaci se nepodařilo nahrát ze souboru " + filename);
        }
        
        TVShow convertedInputTVShow = TVShowDataConverter.convertToDataFrom(editedTVShow.get(0));

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
        
        List<TVSeasonInput> editedTVSeason = fileManagerAccessor.getTVSeasonsFileManager().loadInputDataFrom(fromBinary);
                
        if (editedTVSeason.isEmpty()) 
        {
            String filename = fromBinary == true ? DataStore.getBinaryInputTVSeasonsFilename() : DataStore.getTextInputTVSeasonsFilename();
            throw new FileParsingException("Data sezóny vybrané pro editaci se nepodařilo nahrát ze souboru " + filename);
        }
        
        TVSeason convertedInputTVSeason = TVSeasonDataConverter.convertToDataFrom(editedTVSeason.get(0), tvSeasonForeignKey);

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
        
        List<TVEpisodeInput> editedTVEpisode = fileManagerAccessor.getTVEpisodesFileManager().loadInputDataFrom(fromBinary);
                
        if (editedTVEpisode.isEmpty()) 
        {
            String filename = fromBinary == true ? 
                    DataStore.getBinaryInputTVEpisodesFilename() : DataStore.getTextInputTVEpisodesFilename();
            throw new FileParsingException("Data epizody vybrané pro editaci se nepodařilo nahrát ze souboru " + filename);
        }
        
        TVEpisode convertedInputTVEpisode = TVEpisodeDataConverter.convertToDataFrom(editedTVEpisode.get(0), tvEpisodeForeignKey);

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
