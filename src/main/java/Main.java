import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args){


        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session =  Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("webgarage2024@gmail.com", "kiehqtgqfltbtpvg");
            }
        });


        try {
            MimeMessage message =  new MimeMessage(session);
            message.setFrom(new InternetAddress("webgarage2024@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("virginiaprats1986@gmail.com"));
            message.setSubject("Prueba");
            message.setText("1 2 3 Probation");

            logger.info("Sending...");
            Transport.send(message);
            logger.info("Sent message successfully.");

        } catch (MessagingException e) {
            logger.error("Failed to send message", e);

        }

    }
}


