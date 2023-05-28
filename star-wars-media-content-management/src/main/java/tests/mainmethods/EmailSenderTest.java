/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests.mainmethods;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Admin
 */
public class EmailSenderTest 
{
    public static void main(String[] args) throws EmailException 
    {   
        String randomGeneratedAppToken = "qnaadtxcznjyvzln";
        String appId = "honzaswtor";
        String enteredEmailAddress = "honzaswtor@gmail.com";
        
        try 
        {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken));
            email.setSSLOnConnect(true);
            email.setFrom(enteredEmailAddress);
            email.setSubject("Star Wars Media Content Management - Test Email");
            email.addTo(enteredEmailAddress);
            email.setHtmlMsg("sad");
            email.send();  
       }
       catch (EmailException ex) 
       {
            System.out.println("Chyba sítě nebo neexistující zadaná emailová adresa nebo prazdne telo emailu");
       }
       catch (NullPointerException ex) 
       {
            System.out.println("Zadana emailova adresa je prazdna");
       } 
    }
}
