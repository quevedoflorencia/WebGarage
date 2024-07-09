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

    //Se define el tiempo desde que se confirmo la reserva.
    private final int tiempoImpagas=1;
    //Se define el tiempo desde que se notifico que esta impaga y se cancela.
    private final int tiempoNotificada=2;

    private final String mensajeImpagas = "Le recordamos que tiene una reserva que esta sin abonarse y puede quedar cancelada de no pagarse en los proximos "+ tiempoImpagas + "minutos. Recomendamos visitar sus reservas para resolverlo. Gracias";

    private final String asuntoImpagas = "Reserva impaga";

    public ServicioScheduledTasks(ServicioReserva servicioReserva, ServicioEmail servicioEmail, ServicioNotificacion servicioNotificacion) {
        this.servicioReserva = servicioReserva;
        this.servicioEmail = servicioEmail;
        this.servicioNotificacion = servicioNotificacion;
    }

    @Scheduled(cron= "0 * * * * ?")
    public void scheduleVerificarReservasImpagas() {
        List<Reserva> reservas = validarReservasImpagasYNotificadas(tiempoImpagas);
        if(reservas!=null){
            for(Reserva res : reservas){
                if(!notificado(res)){
                    System.out.println("Enviando mail de mensaje impagas ");
                    enviarMail(res,mensajeImpagas,asuntoImpagas );
                    notificar(res);
                }

            }
        }
        System.out.println("Se verificaron Reservas Impagas");
    }

    @Scheduled(cron= "0 * * * * ?")
    public void scheduleCancelarReservasImpagas() {
        List<Reserva> reservas = validarReservasImpagasYNotificadas(tiempoNotificada);
        if(reservas!=null){
            for(Reserva res : reservas){
                if(notificado(res)){
                    System.out.println("Cancelando facturas Impagas");
                    cancelandoReservas(res);
                }

            }
        }
    }

    private boolean notificado(Reserva res) {
        ReservaNotificacion reservaNotificacion = servicioNotificacion.traerNotificacionPorReservaId(res.getId());
        return reservaNotificacion != null;
    }

    private void notificar(Reserva res) {
        ReservaNotificacion reservaNotificacion = new ReservaNotificacion(res);
        servicioNotificacion.guardar(reservaNotificacion);
    }


    private List<Reserva> validarReservasImpagasYNotificadas(int tiempo) {
        List<Reserva> reservas =servicioReserva.traerPorEstado(EstadoReserva.CONFIRMADA);
        List<Reserva> reservasValidadas = new ArrayList<>();
        for (Reserva res: reservas){
            if(res.getFechaReserva()!=null && validarMinutos(res.getFechaReserva(),tiempo)){
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

    private void cancelandoReservas(Reserva res) {
        servicioReserva.cancelar(res.getId());
    }
}