package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("servicioEmail")
public class ServicioEmailImpl implements ServicioEmail {

    private static final String LOGO_IMG_PATH = "src/main/webapp/resources/core/img/navbar/logo.png";
    private final JavaMailSender emailSender;

    @Autowired
    public ServicioEmailImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void enviarMailReservaExitosa(Reserva reserva) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(reserva.getUsuario().getEmail());

            helper.setSubject("Reserva N°: " + reserva.getId());

            String body = "<html><body>"
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

            helper.setText(body, true);

            File logo = new File(LOGO_IMG_PATH);
            helper.addInline("logoImage", logo);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enviarMailReservaCancelada(Reserva reserva) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(reserva.getUsuario().getEmail());

            helper.setSubject("Cancelaste tu Reserva N°: " + reserva.getId());

            String body = "<html><body>"
                    + "<img src='cid:logoImage' style=' width: 5%;'><br><br>"
                    + "Hola " + reserva.getUsuario().getNombre() + ", cancelaste tu reserva N° " + reserva.getId() + ".<br><br>"
                    + "Detalles de tu reserva cancelada: " +"<br>"
                    + "Fecha: " + reserva.getDia() + "<br>"
                    + "Horario de inicio: " + reserva.getHorarioInicio() + "<br>"
                    + "Horario de finalización: " + reserva.getHorarioFin() + "<br>"
                    + "Garege: " + reserva.getGarage().getNombre() + "<br>"
                    + "Mail del Usuario: " + reserva.getUsuario().getEmail() + "<br>"
                    + "Monto abonado: $" + reserva.getPrecio() + "<br>"
                    + "Te esperamos para una nueva reserva."
                    + "</body></html>";

            helper.setText(body, true);

            File logo = new File(LOGO_IMG_PATH);
            helper.addInline("logoImage", logo);

            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
