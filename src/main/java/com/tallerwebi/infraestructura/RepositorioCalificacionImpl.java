package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioCalificacion;
import com.tallerwebi.dominio.model.Calificacion;
import com.tallerwebi.dominio.model.Garage;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("calificacionRepositorio")
public class RepositorioCalificacionImpl implements RepositorioCalificacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCalificacionImpl (SessionFactory sessionFactory){this.sessionFactory=sessionFactory;}

    @Override
    public  void guardarCalificacion (Calificacion calificacion){sessionFactory.getCurrentSession().save(calificacion);}

    @Override
    public List<Calificacion> getCalificacionesSegunGarage(Integer idGarage) {
        return  sessionFactory.getCurrentSession().createCriteria(Garage.class).add(Restrictions.eq("idGarage", idGarage)).list();
    }


}
