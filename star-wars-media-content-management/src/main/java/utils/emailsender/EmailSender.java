package utils.emailsender;

import app.logic.datastore.DataStore;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Represents an email service for usage of sending encoded HTML data through e-mail.
 * EmailSender class uses SMTP protocol port number and google SMTP server.
 * EmailSender class uses UTF-8 to encode charsets in HTML.
 * EmailSender class uses autentification for google SMTP server
 * @author jan.dostal
 */
public class EmailSender 
{
    private static EmailSender emailSender;
    
    private final int smtpPort = 465;
    
    private final String hostName = "smtp.gmail.com";
    
    private final String randomGeneratedAppToken = "vbogvcpraybvimny";
    
    private final String appId = "datadispatcher.honzaswtor";
    
    /**
     * Creates singleton instance of EmailSender.
     */
    private EmailSender() 
    {
    }
    
    /**
     * Represents a factory method for creating singleton instance.
     * @return singleton instance of EmailSender
     */
    public static EmailSender getInstance() 
    {
        if (emailSender == null) 
        {
            emailSender = new EmailSender();
        }
        
        return emailSender;
    }
    
    /**
     * Method sends HTML email with defined subject and message body to recipient email address.
     * @param recipientEmailAddress represents recipient e-mail address entered from user
     * @param subject represents e-mail heading/subject
     * @param message represents e-mail HTML body message
     * @throws org.apache.commons.mail.EmailException if network error occures or recipient
     * email address is invalid.
     */
    public void sendEmail(String recipientEmailAddress, String subject, StringBuilder message) throws EmailException
    {
        try 
        {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(hostName);
            email.setCharset(org.apache.commons.mail.EmailConstants.UTF_8);
            email.setSmtpPort(smtpPort);
            email.setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken));
            email.setSSLOnConnect(true);
            email.setFrom(DataStore.getAppCreator());
            email.setSubject(subject);
            email.addTo(recipientEmailAddress);
            email.setHtmlMsg(message.toString());
            email.send();  
        }
        catch (EmailException ex) 
        {
            throw new EmailException("Chyba při odesílání přes internet nebo evidentně neplatná e-mailová adresa příjemce");
        }
    }
}
