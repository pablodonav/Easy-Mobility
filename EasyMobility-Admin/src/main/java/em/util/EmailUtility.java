/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EmailUtility.java
    Date: 23 abr. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * Clase utility para mandar mensajes por correo electrónico.
 */
public class EmailUtility {

    private final Config config = ConfigProvider.getConfig();
    
    public void sendEmail(final String senderEmail, String subject, String content) throws AddressException,
            MessagingException, UnsupportedEncodingException {
        
        String user = this.config.getValue("smtp.user", String.class);
        String appPassword = this.config.getValue("smtp.password", String.class);

        // se establecen las propiedades del servidor SMTP.
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.config.getValue("smtp.host", String.class));
        properties.put("mail.smtp.auth", this.config.getValue("smtp.auth", String.class));
        properties.put("mail.smtp.port", this.config.getValue("smtp.port", String.class));
        properties.put("mail.smtp.starttls.enable", this.config.getValue("smtp.starttls.enable", String.class));
        properties.put("mail.smtp.ssl.trust", this.config.getValue("smtp.ssl.trust", String.class));

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, appPassword);
            }
        });

        // Crea un nuevo mensaje de correo
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(senderEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

        } catch (MessagingException mex) {
            System.out.println("Error: unable to send message....");
            mex.printStackTrace();
        }
    }
}
