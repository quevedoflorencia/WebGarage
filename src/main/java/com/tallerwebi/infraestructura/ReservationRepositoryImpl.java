package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.dominio.ReservationRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reservationRespository")
public class ReservationRepositoryImpl implements ReservationRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public ReservationRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List reservationByClient(String client) {
        return null;
    }

    @Override
    public List reservationByDate(String date) {
        final Session session = sessionFactory.getCurrentSession();
        return sessionFactory.getCurrentSession().createCriteria(Reservation.class).add(Restrictions.eq("day",date)).list();
    }

    @Override
    public List allReservations() {
        return null;
    }
}
