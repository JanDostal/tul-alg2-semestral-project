package tests.mainmethods;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
import app.logic.datastore.DataStore;
import app.logic.filemanager.FileManagerAccessor;
import app.models.data.Era;
import app.models.data.Movie;
import app.models.data.PrimaryKey;
import app.models.data.TVEpisode;
import app.models.data.TVSeason;
import app.models.data.TVShow;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import utils.emailsender.EmailSender;
import utils.exceptions.DatabaseException;
import utils.interfaces.IDataTable;

/**
 * Represents a custom unit test class for testing email service of email sender module.
 * @author jan.dostal
 */
public class EmailSenderTest 
{
    //testing main
    public static void main(String[] args) throws EmailException 
    {   
//        String randomGeneratedAppToken = "qnaadtxcznjyvzln";
//        String appId = "honzaswtor";
//        String recipientEmailAddress = "honzaswtor@gmail.com";
//        
//        try 
//        {
//            HtmlEmail email = new HtmlEmail();
//            email.setHostName("smtp.googlemail.com");
//            email.setSmtpPort(465);
//            email.setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken));
//            email.setSSLOnConnect(true);
//            email.setFrom(DataStore.getAppCreator());
//            email.setSubject(String.format("%s –⁠ Test Email", DataStore.getAppName()));
//            email.addTo(recipientEmailAddress);
//            email.setHtmlMsg("sad");
//            email.send();  
//       }
//       catch (EmailException ex) 
//       {
//            System.out.println("Network error or clearly invalidly entered email address of recipient or empty email body");
//       }
//       catch (NullPointerException ex) 
//       {
//            System.out.println("Entered email address of recipient has value null");
//       }
//        
//       try 
//       {
//           DataContextAccessor dbContext = DataContextAccessor.getInstance();
//           EmailSender emailSender = EmailSender.getInstance();
//           IDataTable<Movie> moviesTable = dbContext.getMoviesTable();
//           FileManagerAccessor fileManager = FileManagerAccessor.getInstance();
//
//           MoviesController controller = MoviesController.getInstance(dbContext, emailSender, fileManager);
//           TVEpisodesController controller2 = TVEpisodesController.getInstance(dbContext, emailSender, fileManager);
//
//           Movie movieA = new Movie(new PrimaryKey(3), Duration.ofMinutes(200), "movieA",
//                   60, true, "https://www.novinky.cz/?amp=1", "A",
//                   LocalDate.parse("2023-05-18", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);
//
//           Movie movieB = new Movie(new PrimaryKey(4), null, "Last Jedi",
//                   40, false, null, "B",
//                   LocalDate.parse("2023-05-15", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//
//           Movie movieC = new Movie(new PrimaryKey(5), Duration.ofMinutes(200), "Attack of the clones",
//                   40, false, "https://cs.wikipedia.org/wiki/Praha_čárka", "C",
//                   LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//
//           TVShow show = new TVShow(new PrimaryKey(1), "Clone Wars", LocalDate.parse("2023-05-20",
//                   DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);
//
//           TVSeason season1 = new TVSeason(new PrimaryKey(1), 2, show.getPrimaryKey());
//           TVSeason season2 = new TVSeason(new PrimaryKey(2), 1, show.getPrimaryKey());
//
//           TVEpisode episodeB = new TVEpisode(new PrimaryKey(3), Duration.ofMinutes(50), null,
//                   60, false, null, "very bad episode", 2, season1.getPrimaryKey());
//
//           TVEpisode episodeC = new TVEpisode(new PrimaryKey(4), Duration.ofMinutes(50), "episodeA",
//                   60, false, "https://www.example03.com", "very bad episode forever", 1, season1.getPrimaryKey());
//
//           TVEpisode episodeD = new TVEpisode(new PrimaryKey(5), Duration.ofMinutes(50), "episodeE",
//                   60, false, "https://www.example04.com", "very bad episode a", 2, season2.getPrimaryKey());
//
//           TVEpisode episodeE = new TVEpisode(new PrimaryKey(6), Duration.ofMinutes(50), "episodeD",
//                   60, false, "https://www.example05.com", "very bad episode forever ever after", 1, season2.getPrimaryKey());
//
//           dbContext.getTVShowsTable().loadFrom(show);
//           dbContext.getTVSeasonsTable().loadFrom(season1);
//           dbContext.getTVSeasonsTable().loadFrom(season2);
//           dbContext.getTVEpisodesTable().loadFrom(episodeB);
//           dbContext.getTVEpisodesTable().loadFrom(episodeC);
//           dbContext.getTVEpisodesTable().loadFrom(episodeD);
//           dbContext.getTVEpisodesTable().loadFrom(episodeE);
//           moviesTable.loadFrom(movieA);
//           moviesTable.loadFrom(movieB);
//           moviesTable.loadFrom(movieC);
//
//           //sendUnwatchedOldestMoviesWithHyperlinksByEmail method from MoviesController
//           System.out.println();
//           System.out.println("sendUnwatchedOldestMoviesWithHyperlinksByEmail "
//                   + "method from MoviesController:");
//           System.out.println();
//
//           controller.sendUnwatchedOldestMoviesWithHyperlinksByEmail(DataStore.getAppCreator());
//
//           //sendUnwatchedMoviesWithHyperlinksInChronologicalErasByEmail method from MoviesController
//           System.out.println();
//           System.out.println("sendUnwatchedMoviesWithHyperlinksInChronologicalErasByEmail "
//                   + "method from MoviesController:");
//           System.out.println();
//
//           controller.sendUnwatchedMoviesWithHyperlinksInChronologicalErasByEmail(DataStore.getAppCreator());
//
//           //sendUnwatchedEpisodesWithHyperlinksInTVShowByEmail method from MoviesController
//           System.out.println();
//           System.out.println("sendUnwatchedEpisodesWithHyperlinksInTVShowByEmail "
//                   + "method from MoviesController:");
//           System.out.println();
//
//           controller2.sendUnwatchedEpisodesWithHyperlinksInTVShowByEmail(DataStore.getAppCreator(), show.getPrimaryKey());
//       } 
//       catch (NullPointerException ex) 
//       {
//            System.out.println("Entered email address of recipient has value null");
//       } 
//       catch (DatabaseException | EmailException e) 
//       {
//            System.out.println(e.getMessage());
//       }
    }
}
