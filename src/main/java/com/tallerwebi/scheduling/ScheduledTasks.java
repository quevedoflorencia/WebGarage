package com.tallerwebi.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(fixedDelay = 300000) // 300000 ms = 5 minutos
    public void scheduleFixedDelayTask() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
        // Lógica de la tarea
    }

    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutos
    public void scheduleFixedRateTask() {
        System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
        // Lógica de la tarea
    }
}