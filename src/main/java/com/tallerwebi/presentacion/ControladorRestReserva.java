package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
            Integer garageTipoVehiculoId = (Integer) requestBody.get("garageTipoVehiculoId");
            Integer garageId = (Integer) requestBody.get("garageId");


            List<String> horasOcupadas = new ArrayList<>();
            if(selectedDate!=null){
                traerHorasOcupadas(horasOcupadas, selectedDate, garageTipoVehiculoId, garageId);
            }

            if (horasOcupadas != null && !horasOcupadas.isEmpty()) {
                return ResponseEntity.ok(horasOcupadas);
            } else {
                return ResponseEntity.ok(Collections.emptyList());
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void traerHorasOcupadas(List<String> horasOcupadas, String selectedDate, Integer garageTipoVehiculoId, Integer garageId) {


        horasOcupadas.addAll(servicioDisponibilidad.traerHorasOcupadasPorDiaYTipoVehiculo(selectedDate, garageTipoVehiculoId));
        horasOcupadas.addAll(servicioDisponibilidad.traerHorasCierre(garageId));

    }

}

