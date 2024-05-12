package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.Reservacion;
import com.tallerwebi.dominio.RepositorioReservacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("reservationRespository")
public class RepositorioReservacionImpl implements RepositorioReservacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioReservacionImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List reservasPorCliente(String cliente) {
        return null;
    }

    @Override
    public List reservasPorFecha(String date) {
        final Session session = sessionFactory.getCurrentSession();
        return sessionFactory.getCurrentSession().createCriteria(Reservacion.class).add(Restrictions.eq("day",date)).list();
    }

    @Override
    public void agregarNuevaReserva(Reservacion reservacion) {
        sessionFactory.getCurrentSession().save(reservacion);
    }

    @Override
    public List todasLasReservas() {
        return null;
    }


    @Override
    public List<Reservacion> obtenerReservasByUserId(Long id) {

        String hql = "FROM Reservacion R WHERE R.usuario.id = :userId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", id);

        return query.getResultList();

    }
}
