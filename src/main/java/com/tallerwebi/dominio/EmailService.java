package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("emailService")
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
    public void sendSimpleMessage(Reserva reserva) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(reserva.getUsuario().getEmail());
            helper.setSubject("Reserva N°: " + reserva.getId());

            String text = "<html><body>"
                    + "<img src='cid:logoImage' style=' width: 5%;'><br><br>"
                    + "Hola " + reserva.getUsuario().getNombre() + ",<br><br>"
                    + "Aquí están los detalles de tu reserva N° " + reserva.getId() + "<br><br>"
                    + "Fecha: " + reserva.getDia() + "<br>"
                    + "Horario de inicio: " + reserva.getHorarioInicio() + "<br>"
                    + "Horario de finalización: " + reserva.getHorarioFin() + "<br>"
                    + "Garege: " + reserva.getGarage().getNombre() + "<br>"
                    + "Mail del Usuario: " + reserva.getUsuario().getEmail() + "<br>"
                    + "Monto abonado: $" + reserva.getPrecio() + "<br>"
                    + "Gracias por reservar con WebGarage."
                    + "</body></html>";

            helper.setText(text, true);

            File archivo = new File("src/main/webapp/resources/core/img/navbar/logo.png");
            helper.addInline("logoImage", archivo);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
/*
    public void sendSimpleMessage(Reserva reserva) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reserva.getUsuario().getEmail());
        message.setSubject("Reserva N°: " + reserva.getId());
        String text = "Hola " + reserva.getUsuario().getNombre() + ",\n\n"
                + "Aquí están los detalles de tu reserva N° " + reserva.getId() + "\n\n"
                + "Fecha: " + reserva.getDia() + "\n"
                + "Horario de inicio: " + reserva.getHorarioInicio() + "\n"
                + "Horario de finalización: " + reserva.getHorarioFin() + "\n"
                + "Garege: " + reserva.getGarage().getNombre() + "\n"
                + "Mail del Usuario: " + reserva.getUsuario().getEmail() + "\n"
                + "Monto abonado: $" + reserva.getPrecio() + "\n"
                +"<img src= img/navbar/logo.png alt='Imagen de confirmación'/>"
                + "\n\nGracias por reservar con nosotros.";
        File file = new File("src/main/webapp/resourses/core/img/navbar/logo.png");

        message.setText(text);
        emailSender.send(message);
    }*/
}


