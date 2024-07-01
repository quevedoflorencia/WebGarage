package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioCalificacion;
import com.tallerwebi.dominio.model.Calificacion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("calificacionRepositorio")
public class RepositorioCalificacionImpl implements RepositorioCalificacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCalificacionImpl (SessionFactory sessionFactory){this.sessionFactory=sessionFactory;}

    @Override
    public  void guardarCalificacion (Calificacion calificacion){sessionFactory.getCurrentSession().save(calificacion);}


}
