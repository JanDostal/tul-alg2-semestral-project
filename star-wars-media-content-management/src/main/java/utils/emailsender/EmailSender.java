
package utils.emailsender;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Admin
 */
public class EmailSender 
{
    private static EmailSender emailSender;
    
    private final int smtpPort = 465;
    
    private final String hostName = "smtp.googlemail.com";
    
    private final String randomGeneratedAppToken = "qnaadtxcznjyvzln";
    
    private final String appId = "honzaswtor";
    
    private EmailSender() 
    {
    }
    
    public static EmailSender getInstance() 
    {
        if (emailSender == null) 
        {
            emailSender = new EmailSender();
        }
        
        return emailSender;
    }
    
    public void sendEmail(String recipientEmailAddress, String subject, StringBuilder message) 
            throws EmailException, NullPointerException
    {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(hostName);
        email.setCharset(org.apache.commons.mail.EmailConstants.UTF_8);
        email.setSmtpPort(smtpPort);
        email.setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken));
        email.setSSLOnConnect(true);
        email.setFrom(recipientEmailAddress);
        email.setSubject(subject);
        email.addTo(recipientEmailAddress);
        email.setHtmlMsg(message.toString());
        email.send();  
    }
}
