package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class ControladorRestReserva {

    private ServicioReserva servicioDisponibilidad;

    @Autowired
    public ControladorRestReserva(ServicioReserva servicioDisponibilidad){
        this.servicioDisponibilidad = servicioDisponibilidad;
    }

    @PostMapping(path = "/getAvailableHours")
    public ResponseEntity<List<String>> traerDisponibilidad(@RequestBody Map<String, Object> requestBody) {
        try {
            String selectedDate = (String) requestBody.get("selectedDate");
            String garageTipoVehiculoId = (String) requestBody.get("garageTipoVehiculoId");
            Integer garageId = (Integer) requestBody.get("garageId");
            List<String> horasOcupadas = servicioDisponibilidad.traerHorasOcupadas(selectedDate); //todo se debe validar por si el date es nulo o vacio y no ejecutar la funcionalidad

            if (horasOcupadas != null && !horasOcupadas.isEmpty()) {
                return ResponseEntity.ok(horasOcupadas);
            } else {
                return ResponseEntity.ok(Collections.emptyList());
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

