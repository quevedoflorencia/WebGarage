package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.EstadoReserva;
import com.tallerwebi.dominio.model.ReservaNotificacion;
import com.tallerwebi.dominio.model.Reserva;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServicioScheduledTasks {

    private ServicioReserva servicioReserva;
    private ServicioEmail servicioEmail;
    private ServicioNotificacion servicioNotificacion;

    private final int tiempoImpagas=1;

    private final String mensajeImpagas = "Le recordamos que tiene una reserva que esta sin abonarse y puede quedar cancelada de no pagarse en los proximos "+ tiempoImpagas + "minutos. Recomendamos visitar sus reservas para resolverlo. Gracias";

    private final String asuntoImpagas = "Reserva impaga";

    public ServicioScheduledTasks(ServicioReserva servicioReserva, ServicioEmail servicioEmail, ServicioNotificacion servicioNotificacion) {
        this.servicioReserva = servicioReserva;
        this.servicioEmail = servicioEmail;
        this.servicioNotificacion = servicioNotificacion;
    }

    @Scheduled(cron= "0 * * * * ?")
    public void scheduleVerificarReservasImpagas() {
        List<Reserva> reservas = validarReservasImpagas();
        if(reservas!=null){
            for(Reserva res : reservas){
                System.out.println("Enviando mail de mensaje impagas ");
                if(!notificado(res)){
                    enviarMail(res,mensajeImpagas,asuntoImpagas );
                    notificar(res);
                }

            }
        }
        System.out.println("Se verificaron Reservas Impagas");
    }

    private boolean notificado(Reserva res) {
        ReservaNotificacion reservaNotificacion = servicioNotificacion.traerNotificacionPorReservaId(res.getId());
        return reservaNotificacion != null;
    }

    private void notificar(Reserva res) {
        ReservaNotificacion reservaNotificacion = new ReservaNotificacion(res);
        servicioNotificacion.guardar(reservaNotificacion);
    }


    private List<Reserva> validarReservasImpagas() {
        List<Reserva> reservas =servicioReserva.traerPorEstado(EstadoReserva.CONFIRMADA);
        List<Reserva> reservasValidadas = new ArrayList<>();
        for (Reserva res: reservas){
            if(res.getFechaReserva()!=null && validarMinutos(res.getFechaReserva(),tiempoImpagas)){
                reservasValidadas.add(res);
            }
        }
        return reservasValidadas;
    }

    private void enviarMail(Reserva reserva,String mensaje,String asunto) {
        servicioEmail.enviarMailMensaje(reserva,mensaje,asunto);
    }

    private Boolean validarMinutos(LocalDateTime horarioReserva, int intervalo) {
        return ChronoUnit.MINUTES.between(horarioReserva, LocalDateTime.now())>intervalo;
    }
}