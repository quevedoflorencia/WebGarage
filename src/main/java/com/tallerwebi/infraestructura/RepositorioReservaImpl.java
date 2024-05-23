package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.RepositorioReserva;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("repositorioReserva")
public class RepositorioReservaImpl implements RepositorioReserva {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioReservaImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List reservasPorCliente(String cliente) {
        return null;
    }

    @Override
    public List reservasPorFecha(String date) {
        final Session session = sessionFactory.getCurrentSession();
        return sessionFactory.getCurrentSession().createCriteria(Reserva.class).add(Restrictions.eq("dia",date)).list();
    }

    @Override
    public void agregarNuevaReserva(Reserva reserva) {
        sessionFactory.getCurrentSession().save(reserva);
    }

    @Override
    public List todasLasReservas() {
        return null;
    }


    @Override
    public List<Reserva> obtenerReservasByUserId(Long id) {

        String hql = "FROM Reserva R WHERE R.usuario.id = :userId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", id);

        return query.getResultList();

    }
}
