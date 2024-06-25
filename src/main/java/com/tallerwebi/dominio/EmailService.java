package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

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
                + "\n\nGracias por reservar con nosotros.";
        message.setText(text);
        emailSender.send(message);
    }
}


