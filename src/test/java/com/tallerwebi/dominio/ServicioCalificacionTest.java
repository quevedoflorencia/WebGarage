package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Calificacion;
import com.tallerwebi.dominio.model.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

public class ServicioCalificacionTest {

    private ServicioCalificacion servicioCalificacion;

    private RepositorioCalificacion repositorioCalificacion;

    @BeforeEach
    public void init() {
        this.repositorioCalificacion = mock(RepositorioCalificacion.class);
        this.servicioCalificacion = new ServicioCalificacionImpl(this.repositorioCalificacion);
    }

    @Test
    public void queSePuedaGuardarCalificacion() {
        Garage garage = dadoUnGarage();
        Integer puntaje = 5;
        String comentario = "Excelente servicio";

        Calificacion calificacion = new Calificacion(puntaje, comentario, garage, LocalDateTime.now());

        servicioCalificacion.guardarCalificacion(puntaje, comentario, garage);

        verify(repositorioCalificacion, times(1)).guardarCalificacion(any(Calificacion.class));
    }

    @Test
    public void queSePuedaGuardarMultiplesCalificaciones() {
        Garage garage = dadoUnGarage();

        servicioCalificacion.guardarCalificacion(5, "Excelente servicio", garage);
        servicioCalificacion.guardarCalificacion(3, "Buen servicio", garage);
        servicioCalificacion.guardarCalificacion(1, "Malo", garage);

        verify(repositorioCalificacion, times(3)).guardarCalificacion(any(Calificacion.class));
    }

    private Garage dadoUnGarage() {
        Garage garage = new Garage();
        garage.setNombre("Garage Test");
        garage.setHorarioApertura(LocalTime.of(8, 0));
        garage.setHorarioCierre(LocalTime.of(20, 0));
        garage.setLatitud("10.0000");
        garage.setLongitud("20.0000");
        garage.setRutaFoto("ruta/foto.jpg");
        garage.setPromedio(0.0);

        return garage;
    }
}
