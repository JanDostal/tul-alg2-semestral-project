/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests.mainmethods;

import app.logic.controllers.MoviesController;
import app.logic.controllers.TVEpisodesController;
import app.logic.datacontext.DataContextAccessor;
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
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import utils.emailsender.EmailSender;
import utils.interfaces.IDataTable;

/**
 *
 * @author Admin
 */
public class EmailSenderTest 
{
    public static void main(String[] args) throws EmailException 
    {   
//        String randomGeneratedAppToken = "qnaadtxcznjyvzln";
//        String appId = "honzaswtor";
//        String enteredEmailAddress = "honzaswtor@gmail.com";
//        
//        try 
//        {
//            HtmlEmail email = new HtmlEmail();
//            email.setHostName("smtp.googlemail.com");
//            email.setSmtpPort(465);
//            email.setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken));
//            email.setSSLOnConnect(true);
//            email.setFrom(enteredEmailAddress);
//            email.setSubject("Star Wars Media Content Management - Test Email");
//            email.addTo(enteredEmailAddress);
//            email.setHtmlMsg("sad");
//            email.send();  
//       }
//       catch (EmailException ex) 
//       {
//            System.out.println("Chyba sítě nebo evidentně neplatná zadaná emailová adresa nebo prazdne telo emailu");
//       }
//       catch (NullPointerException ex) 
//       {
//            System.out.println("Zadana emailova adresa ma hodnotu null");
//       }
        try 
        {
            DataContextAccessor dbContext = DataContextAccessor.getInstance();
            EmailSender emailSender = EmailSender.getInstance();
            IDataTable<Movie> moviesTable = dbContext.getMoviesTable();
            FileManagerAccessor fileManager = FileManagerAccessor.getInstance();

            MoviesController controller = MoviesController.getInstance(dbContext, emailSender, fileManager);
            TVEpisodesController controller2 = TVEpisodesController.getInstance(dbContext, emailSender, fileManager);

            Movie movieA = new Movie(new PrimaryKey(3), Duration.ofMinutes(200), "movieA",
                    60, true, "https://www.novinky.cz/?amp=1", "A",
                    LocalDate.parse("2023-05-18", DateTimeFormatter.ISO_LOCAL_DATE), Era.THE_NEW_REPUBLIC);

            Movie movieB = new Movie(new PrimaryKey(4), Duration.ofMinutes(200), "Poslední z Jediů",
                    40, false, "https://www.seznamzpravy.cz/?amp=1", "B",
                    LocalDate.parse("2023-05-15", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);

            Movie movieC = new Movie(new PrimaryKey(5), Duration.ofMinutes(200), "Klony Útočí",
                    40, false, "https://cs.wikipedia.org/wiki/Praha_čárka", "C",
                    LocalDate.parse("2023-05-13", DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);

            TVShow show = new TVShow(new PrimaryKey(1), "Clone Wars", LocalDate.parse("2023-05-20",
                    DateTimeFormatter.ISO_LOCAL_DATE), Era.FALL_OF_THE_JEDI);

            TVSeason season1 = new TVSeason(new PrimaryKey(1), 2, show.getPrimaryKey());
            TVSeason season2 = new TVSeason(new PrimaryKey(2), 1, show.getPrimaryKey());

            TVEpisode episodeB = new TVEpisode(new PrimaryKey(3), Duration.ofMinutes(50), "episodeB",
                    60, false, "https://www.example02.com", "Velmi špatná epizoda", 2, season1.getPrimaryKey());

            TVEpisode episodeC = new TVEpisode(new PrimaryKey(4), Duration.ofMinutes(50), "episodeA",
                    60, false, "https://www.example03.com", "Velmi špatná epizoda navždy", 1, season1.getPrimaryKey());

            TVEpisode episodeD = new TVEpisode(new PrimaryKey(5), Duration.ofMinutes(50), "episodeE",
                    60, false, "https://www.example04.com", "Velmi špatná epizoda a", 2, season2.getPrimaryKey());

            TVEpisode episodeE = new TVEpisode(new PrimaryKey(6), Duration.ofMinutes(50), "episodeD",
                    60, false, "https://www.example05.com", "Velmi špatná epizoda navždy asd", 1, season2.getPrimaryKey());

            dbContext.getTVShowsTable().loadFrom(show);
            dbContext.getTVSeasonsTable().loadFrom(season1);
            dbContext.getTVSeasonsTable().loadFrom(season2);
            dbContext.getTVEpisodesTable().loadFrom(episodeB);
            dbContext.getTVEpisodesTable().loadFrom(episodeC);
            dbContext.getTVEpisodesTable().loadFrom(episodeD);
            dbContext.getTVEpisodesTable().loadFrom(episodeE);
            moviesTable.loadFrom(movieA);
            moviesTable.loadFrom(movieB);
            moviesTable.loadFrom(movieC);

            //sendUnwatchedOldestMoviesWithHyperlinks method from MoviesController
            System.out.println();
            System.out.println("sendUnwatchedOldestMoviesWithHyperlinks "
                    + "method from MoviesController:");
            System.out.println();

            controller.sendUnwatchedOldestMoviesWithHyperlinks("honzaswtor@gmail.com");

            //sendUnwatchedMoviesWithHyperlinksInChronologicalEras method from MoviesController
            System.out.println();
            System.out.println("sendUnwatchedMoviesWithHyperlinksInChronologicalEras "
                    + "method from MoviesController:");
            System.out.println();

            controller.sendUnwatchedMoviesWithHyperlinksInChronologicalEras("honzaswtor@gmail.com");

            //sendUnwatchedEpisodesWithHyperlinksInTVShow method from MoviesController
            System.out.println();
            System.out.println("sendUnwatchedEpisodesWithHyperlinksInTVShow "
                    + "method from MoviesController:");
            System.out.println();

            controller2.sendUnwatchedEpisodesWithHyperlinksInTVShow("honzaswtor@gmail.com", show.getPrimaryKey());
        } 
        catch (EmailException ex) 
        {
            System.out.println("Chyba sítě nebo evidentně neplatná zadaná emailová adresa nebo prazdne telo emailu");
        } 
        catch (NullPointerException ex) 
        {
            System.out.println("Zadana emailova adresa ma hodnotu null");
        }
    }
}
