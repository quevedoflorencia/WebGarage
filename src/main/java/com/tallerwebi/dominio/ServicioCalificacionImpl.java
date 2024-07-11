package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Calificacion;
import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service ("servicioCalificacion")
@Transactional
public class ServicioCalificacionImpl implements ServicioCalificacion{

    private RepositorioCalificacion repositorioCalificacion;

    @Autowired
    public ServicioCalificacionImpl (RepositorioCalificacion repositorioCalificacion){
        this.repositorioCalificacion=repositorioCalificacion; }

    @Override
    public void guardarCalificacion(Integer puntaje, String comentario, Garage garage) {
        Calificacion calificacion = new Calificacion(puntaje, comentario, garage, LocalDateTime.now());
        repositorioCalificacion.guardarCalificacion(calificacion);
    }

    @Override
    public List<Calificacion> obtenerPorGarage(Integer idGarage, String orden) {
        return repositorioCalificacion.obtenerPorGarageYOrden(idGarage, orden);
    }
}
