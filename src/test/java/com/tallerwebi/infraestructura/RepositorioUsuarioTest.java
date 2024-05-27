package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    public void init() { this.repositorioUsuario = new RepositorioUsuarioImpl(this.sessionFactory); }


    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnUsuarioPorIdentificadorYQueDevuelvaUno() {
        Usuario usuario = dadoUnUsuario("test@example.com", "password123");

        Usuario result = this.repositorioUsuario.obtenerUsuario(usuario.getId());

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), equalTo(usuario.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnUsuarioPorIdentificadorYQueNoExista() {
        Usuario result = this.repositorioUsuario.obtenerUsuario(999L);

        assertThat(result, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnUsuarioPorEmailYPasswordYQueDevuelvaElUsuarioCorrecto() {
        Usuario usuario = dadoUnUsuario("test@example.com", "password123");

        Usuario result = this.repositorioUsuario.buscarUsuario("test@example.com", "password123");

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo("test@example.com"));
        assertThat(result.getPassword(), equalTo("password123"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnUsuarioPorEmailYPasswordPeroNoEncuentreUsuario() {
        Usuario result = this.repositorioUsuario.buscarUsuario("nonexistent@example.com", "wrongpassword");

        assertThat(result, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaCrearUnUsuarioNuevo() {
        Usuario usuario = new Usuario();
        usuario.setEmail("newuser@example.com");
        usuario.setPassword("newpassword123");

        this.repositorioUsuario.guardar(usuario);

        Usuario result = this.repositorioUsuario.buscar("newuser@example.com");

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo("newuser@example.com"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnUsuarioPorEmailYEncuentreUno() {
        Usuario usuario = dadoUnUsuario("test@example.com", "password123");

        Usuario result = this.repositorioUsuario.buscar("test@example.com");

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo("test@example.com"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnUsuarioPorEmailPeroNoEncuentreNinguno() {
        Usuario result = this.repositorioUsuario.buscar("nonexistent@example.com");

        assertThat(result, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarLosDatosDeUnUsuarioCorrectamente() {
        Usuario usuario = dadoUnUsuario("test@example.com", "password123");

        usuario.setEmail("updated@example.com");
        usuario.setPassword("updatedpassword123");

        this.repositorioUsuario.modificar(usuario);

        Usuario result = this.repositorioUsuario.obtenerUsuario(usuario.getId());

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo("updated@example.com"));
        assertThat(result.getPassword(), equalTo("updatedpassword123"));
    }

    private Usuario dadoUnUsuario(String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        this.sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }
}
