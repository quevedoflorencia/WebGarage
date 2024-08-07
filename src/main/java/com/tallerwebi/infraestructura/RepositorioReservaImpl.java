package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioReserva;
import com.tallerwebi.dominio.model.Reserva;
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
    public List reservasPorFecha(String date) {
        return sessionFactory.getCurrentSession().createCriteria(Reserva.class).add(Restrictions.eq("dia",date)).list();
    }

    @Override
    public Reserva guardar(Reserva reserva) {
        sessionFactory.getCurrentSession().save(reserva);
        return reserva;
    }

    @Override
    public Reserva obtenerPorId(Long reservaId) {
        String hql = "FROM Reserva R WHERE R.id = :reservaId";
        Query  query = this.sessionFactory.getCurrentSession().createQuery(hql, Reserva.class);
        query.setParameter("reservaId", reservaId );

        List<Reserva> result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public void actualizar(Reserva reserva) {
        sessionFactory.getCurrentSession().update(reserva);
    }

    @Override
    public List<Reserva> obtenerPorUserId(Long id) {

        String hql = "FROM Reserva R WHERE R.usuario.id = :userId order by R.dia";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", id);

        return query.getResultList();

    }

    @Override
    public List<Reserva> obtenerImpagas(int estadoId) {
        String hql = "FROM Reserva R WHERE R.estado.id = :estadoId ";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("estadoId", estadoId);
        return query.getResultList();
    }

    @Override
    public List reservasPorFechaYTipoDeAuto(String selectedDate, int garageTipoVehiculoId) {
        String hql = "FROM Reserva R WHERE R.garageTipoVehiculo.id = :garageTipoVehiculo and " +
                "R.dia = :dia";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("garageTipoVehiculo", garageTipoVehiculoId);
        query.setParameter("dia", selectedDate);

        return query.getResultList();
    }
}
