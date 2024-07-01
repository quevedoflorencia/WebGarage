package com.tallerwebi.dominio;


import com.tallerwebi.dominio.model.Calificacion;
import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
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
        Calificacion calificacion = new Calificacion(puntaje, comentario, garage);
        repositorioCalificacion.guardarCalificacion(calificacion);
    }


    @Override
    public List getCalificacionesSegunGarage(Integer idGarage) {
        return repositorioCalificacion.getCalificacionesSegunGarage(idGarage);
    }

    @Override
    public Double calcularPromedio(Integer idgarage) {
       List <Calificacion> calificaciones = getCalificacionesSegunGarage(idgarage);
        Integer contador =0;
        contador= calificaciones.size();
        Double suma = 0.0;
        Double promedio=0.0;

       for (Calificacion calificacion : calificaciones){
            suma+=calificacion.getPuntaje();
        }
        promedio=suma/contador;

        return promedio;
    }


}
