package com.tallerwebi.dominio;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServicioScheduledTasks {
    @Scheduled(cron= "0 * * * * ?")
    public void scheduleVerificarReservasImpagas() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron= "0 * * * * ?")
    public void scheduleFinalizarReservasProximasAVencerse() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron= "0 * * * * ?")
    public void scheduleInicializarReservasProximas() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

}