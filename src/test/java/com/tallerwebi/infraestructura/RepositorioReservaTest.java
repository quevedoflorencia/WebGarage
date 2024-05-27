package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioReserva;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.model.Usuario;
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

        Reserva reserva = new Reserva(null, usuario, garage, "2024-05-26", "10:00", "12:00");

        this.repositorioReserva.agregarNuevaReserva(reserva);

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

        Reserva reserva1 = new Reserva(null, usuario, garage, "2024-05-26", "10:00", "12:00");
        Reserva reserva2 = new Reserva(null, usuario, garage, "2024-05-26", "10:00", "12:00");

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

        Reserva reserva1 = new Reserva(null, usuario, garage, "2024-05-26", "10:00", "12:00");
        Reserva reserva2 = new Reserva(null, usuario, garage, "2024-05-27", "10:00", "12:00");

        this.sessionFactory.getCurrentSession().save(reserva1);
        this.sessionFactory.getCurrentSession().save(reserva2);

        List<Reserva> result = this.repositorioReserva.obtenerReservasByUserId(usuario.getId());

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getUsuario().getId(), equalTo(usuario.getId()));
        assertThat(result.get(1).getUsuario().getId(), equalTo(usuario.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerLasReservasDeUnUsuarioConSuIDyDevuelvaUnaListaVacia() {
        List<Reserva> result = this.repositorioReserva.obtenerReservasByUserId(1L);

        assertThat(result, is(empty()));
    }

    private Garage dadoUnGarage() {
        Garage garage = new Garage();

        this.sessionFactory.getCurrentSession().save(garage);

        return garage;
    }

    private Usuario dadoUnUsuario() {
        Usuario usuario = new Usuario();

        this.sessionFactory.getCurrentSession().save(usuario);

        return usuario;
    }
}
