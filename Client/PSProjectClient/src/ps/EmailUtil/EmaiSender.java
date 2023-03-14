/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.EmailUtil;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Zbook G3
 */
public class EmaiSender {
    
    public static void sendMail(String recepient, String description){
        System.out.println("Priprema poruke");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = getEmail();
        String myAccountPassword = getPassword();
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        myAccountEmail, myAccountPassword);
            }
        });
        
        session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Message mesasge = prepareMessage(
                session, myAccountEmail, recepient, description);
              
        try {
            Transport.send(mesasge);
        } catch (Exception ex) {
            Logger.getLogger(EmaiSender.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        System.out.println("Poslata poruka");
    }

    private static Message prepareMessage(
            Session session, String email,
            String recepient, String description) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(
                    Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Ophthalmology office - LukeEye");
            message.setText(description);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(EmaiSender.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static String getEmail(){
        return "lukaeyeofficebg@gmail.com";
    }
    
    private static String getPassword(){
        return "luka12345!!!";
    }
    
}
