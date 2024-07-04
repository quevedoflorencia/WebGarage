package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioReserva;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioReservaTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioReserva repositorioReserva;

    @BeforeEach
    public void init() { this.repositorioReserva = new RepositorioReservaImpl(this.sessionFactory); }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaCrearUnaNuevaReserva() {
        Usuario usuario = dadoUnUsuario();
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        EstadoReserva estadoReserva = dadoUnEstadoReserva();
        LocalDateTime fechaCreacion = LocalDateTime.now();

        Reserva reserva = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva, fechaCreacion);

        this.repositorioReserva.guardar(reserva);

        Reserva result = (Reserva) this.sessionFactory.getCurrentSession()
                .createQuery("FROM Reserva WHERE dia = :dia")
                .setParameter("dia", "2024-05-26")
                .getSingleResult();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDia(), equalTo("2024-05-26"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerLasReservasSegunUnaFechaDadaYDevuelvaUnaListaDeReservas() {
        Usuario usuario = dadoUnUsuario();
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        EstadoReserva estadoReserva = dadoUnEstadoReserva();

        Reserva reserva1 = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva, LocalDateTime.now());
        Reserva reserva2 = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva,LocalDateTime.now());

        this.sessionFactory.getCurrentSession().save(reserva1);
        this.sessionFactory.getCurrentSession().save(reserva2);

        List<Reserva> result = this.repositorioReserva.reservasPorFecha("2024-05-26");

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(2));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerLasReservasSegunUnaFechaDadaPeroDevuelvaUnaListaVacia() {
        List<Reserva> result = this.repositorioReserva.reservasPorFecha("2024-05-26");

        assertThat(result, is(empty()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerLasReservasDeUnUsuarioConSuIDyDevuelvaUnaListaDeSusReservas() {
        Usuario usuario = dadoUnUsuario();
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        EstadoReserva estadoReserva = dadoUnEstadoReserva();

        Reserva reserva1 = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva,LocalDateTime.now());
        Reserva reserva2 = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-27", "10:00", "12:00", 1000.00, estadoReserva,LocalDateTime.now());

        this.sessionFactory.getCurrentSession().save(reserva1);
        this.sessionFactory.getCurrentSession().save(reserva2);

        List<Reserva> result = this.repositorioReserva.obtenerPorUserId(usuario.getId());

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getUsuario().getId(), equalTo(usuario.getId()));
        assertThat(result.get(1).getUsuario().getId(), equalTo(usuario.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerLasReservasDeUnUsuarioConSuIDyDevuelvaUnaListaVacia() {
        List<Reserva> result = this.repositorioReserva.obtenerPorUserId(1L);

        assertThat(result, is(empty()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaActualizarUnaReserva() {
        Usuario usuario = dadoUnUsuario();
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        EstadoReserva estadoReserva = dadoUnEstadoReserva();
        String nuevoHorarioFin = "13:00";

        Reserva reserva = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva, LocalDateTime.now());
        this.sessionFactory.getCurrentSession().save(reserva);

        reserva.setHorarioFin(nuevoHorarioFin);
        this.repositorioReserva.actualizar(reserva);

        Reserva updatedReserva = this.repositorioReserva.obtenerPorId(reserva.getId());
        assertThat(updatedReserva.getHorarioFin(), equalTo(nuevoHorarioFin));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEliminarUnaReserva() {
        Usuario usuario = dadoUnUsuario();
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        EstadoReserva estadoReserva = dadoUnEstadoReserva();

        Reserva reserva = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva, LocalDateTime.now());
        this.sessionFactory.getCurrentSession().save(reserva);

        Reserva retrievedReserva = this.repositorioReserva.obtenerPorId(reserva.getId());
        assertThat(retrievedReserva, is(notNullValue()));

        this.sessionFactory.getCurrentSession().delete(reserva);

        Reserva deletedReserva = this.repositorioReserva.obtenerPorId(reserva.getId());
        assertThat(deletedReserva, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnaReservaPorSuID() {
        Usuario usuario = dadoUnUsuario();
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        EstadoReserva estadoReserva = dadoUnEstadoReserva();

        Reserva reserva = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva,LocalDateTime.now());
        this.sessionFactory.getCurrentSession().save(reserva);

        Reserva retrievedReserva = this.repositorioReserva.obtenerPorId(reserva.getId());
        assertThat(retrievedReserva, is(notNullValue()));
        assertThat(retrievedReserva.getId(), equalTo(reserva.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerReservasPorUnRangoDeFechas() {
        Usuario usuario = dadoUnUsuario();
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        EstadoReserva estadoReserva = dadoUnEstadoReserva();

        Reserva reserva1 = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva,LocalDateTime.now());
        Reserva reserva2 = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-27", "10:00", "12:00", 1000.00, estadoReserva,LocalDateTime.now());

        this.sessionFactory.getCurrentSession().save(reserva1);
        this.sessionFactory.getCurrentSession().save(reserva2);

        List<Reserva> result = this.repositorioReserva.reservasPorFecha("2024-05-26");
        assertThat(result, hasSize(1));
        assertThat(result.get(0).getDia(), equalTo("2024-05-26"));
    }

    private Garage dadoUnGarage() {
        Garage garage = new Garage();

        this.sessionFactory.getCurrentSession().save(garage);

        return garage;
    }

    private GarageTipoVehiculo dadoUnGarageTipoVehiculo() {
        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo();

        this.sessionFactory.getCurrentSession().save(garageTipoVehiculo);

        return garageTipoVehiculo;
    }

    private Usuario dadoUnUsuario() {
        Usuario usuario = new Usuario();

        this.sessionFactory.getCurrentSession().save(usuario);

        return usuario;
    }

    private EstadoReserva dadoUnEstadoReserva() {
        EstadoReserva estadoReserva = new EstadoReserva();

        this.sessionFactory.getCurrentSession().save(estadoReserva);

        return estadoReserva;
    }
}
