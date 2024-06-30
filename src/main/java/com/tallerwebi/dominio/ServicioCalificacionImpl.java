package com.tallerwebi.dominio;


import com.tallerwebi.dominio.model.Calificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service ("servicioCalificacion")
@Transactional
public class ServicioCalificacionImpl implements ServicioCalificacion{


    private RepositorioCalificacion repositorioCalificacion;

    @Autowired
    public ServicioCalificacionImpl (RepositorioCalificacion repositorioCalificacion){this.repositorioCalificacion=repositorioCalificacion; }

    @Override
    public void guardarCalificacion(Integer calificacion, String comentario) {
        Calificacion calificacion =new Calificacion(calificacion, comentario);

    }
}
