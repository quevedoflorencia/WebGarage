package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.RepositorioEstadoReserva;
import com.tallerwebi.dominio.model.EstadoReserva;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioEstadoReserva")
public class RepositorioEstadoReservaImpl implements RepositorioEstadoReserva {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEstadoReservaImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public List<EstadoReserva> listarEstadoReservas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(EstadoReserva.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public EstadoReserva getEstadoReservaByDescripcion(String descripcion) {
        return (EstadoReserva) sessionFactory.getCurrentSession()
                .createCriteria(EstadoReserva.class)
                .add(Restrictions.eq("descripcion", descripcion))
                .uniqueResult();
    }
}
