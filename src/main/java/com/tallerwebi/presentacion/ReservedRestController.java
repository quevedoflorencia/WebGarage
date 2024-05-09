package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ReservedRestController {

    private ReservationService availabilityService;

    @Autowired
    public ReservedRestController(ReservationService availabilityService){
        this.availabilityService = availabilityService;
    }

    @PostMapping(path = "/getAvailableHours")
    public ResponseEntity<List<String>> getAvailableHours(@RequestBody String date) {
        try {
            List<String> hours = availabilityService.getReservedHours(date);

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

