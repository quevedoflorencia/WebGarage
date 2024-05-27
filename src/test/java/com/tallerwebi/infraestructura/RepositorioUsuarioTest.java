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
        Usuario usuario = dadoUnUsuario();

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
        Usuario usuario = dadoUnUsuario();

        Usuario result = this.repositorioUsuario.buscarUsuario("prueba@webgarage.com", "12345");

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo(usuario.getEmail()));
        assertThat(result.getPassword(), equalTo(usuario.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnUsuarioPorEmailYPasswordPeroNoEncuentreUsuario() {
        Usuario result = this.repositorioUsuario.buscarUsuario("noexiste@webgarage.com", "contraseniaerronea");

        assertThat(result, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaCrearUnUsuarioNuevo() {
        Usuario usuario = new Usuario();
        usuario.setEmail("nuevousuario@webgarage.com");
        usuario.setPassword("nuevacontrasenia");

        this.repositorioUsuario.guardar(usuario);

        Usuario result = this.repositorioUsuario.buscar("nuevousuario@webgarage.com");

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo("nuevousuario@webgarage.com"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnUsuarioPorEmailYEncuentreUno() {
        Usuario usuario = dadoUnUsuario();

        Usuario result = this.repositorioUsuario.buscar("prueba@webgarage.com");

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo(usuario.getEmail()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnUsuarioPorEmailPeroNoEncuentreNinguno() {
        Usuario result = this.repositorioUsuario.buscar("noexiste@webgarage.com");

        assertThat(result, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarLosDatosDeUnUsuarioCorrectamente() {
        Usuario usuario = dadoUnUsuario();

        usuario.setEmail("usuario-actualizado@webgarage.com");
        usuario.setPassword("actualizado123");

        this.repositorioUsuario.modificar(usuario);

        Usuario result = this.repositorioUsuario.obtenerUsuario(usuario.getId());

        assertThat(result, is(notNullValue()));
        assertThat(result.getEmail(), equalTo("usuario-actualizado@webgarage.com"));
        assertThat(result.getPassword(), equalTo("actualizado123"));
    }

    private Usuario dadoUnUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("prueba@webgarage.com");
        usuario.setPassword("12345");
        this.sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }
}
