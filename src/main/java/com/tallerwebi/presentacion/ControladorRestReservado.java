package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ControladorRestReservado {

    private ServicioRepositorio availabilityService;

    @Autowired
    public ControladorRestReservado(ServicioRepositorio availabilityService){
        this.availabilityService = availabilityService;
    }

    @PostMapping(path = "/getAvailableHours")
    public ResponseEntity<List<String>> getAvailableHours(@RequestBody String date) {
        try {
            List<String> hours = availabilityService.traerHorasOcupadas(date); //todo se debe validar por si el date es nulo o vacio y no ejecutar la funcionalidad

            if (hours != null && !hours.isEmpty()) {
                return ResponseEntity.ok(hours);
            } else {
                return ResponseEntity.ok(Collections.emptyList()); // Devuelve una lista vacía si no hay horas disponibles
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

