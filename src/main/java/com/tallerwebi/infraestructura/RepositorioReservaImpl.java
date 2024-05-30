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
    public Reserva agregarNuevaReserva(Reserva reserva) {
        sessionFactory.getCurrentSession().save(reserva);
        return reserva;
    }

    @Override
    public Reserva obtenerReservaPorId(Long reservaId) {
        String hql = "FROM Reserva R WHERE R.id = :reservaId";
        Query  query = this.sessionFactory.getCurrentSession().createQuery(hql, Reserva.class);
        query.setParameter("reservaId", reservaId );

        return (Reserva) query.getSingleResult();
    }

    @Override
    public Reserva obtenerReservasByReservaId(Long reservaId) {
        String hql = "FROM Reserva R WHERE R.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id",reservaId );

        return (Reserva) query.getSingleResult();
    }

    @Override
    public void modificarReserva(Reserva reserva) {
        sessionFactory.getCurrentSession().update(reserva);
    }




    @Override
    public List<Reserva> obtenerReservasByUserId(Long id) {

        String hql = "FROM Reserva R WHERE R.usuario.id = :userId order by R.dia";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", id);

        return query.getResultList();

    }
}
