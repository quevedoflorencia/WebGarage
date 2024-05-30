package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioExiste;
import com.tallerwebi.dominio.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioLoginTest {

    private static final String NOMBRE = "test";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "password";
    private static final String ROL = "ADMIN";

    private RepositorioUsuario repositorioUsuario;
    private ServicioLogin servicioLogin;

    @BeforeEach
    public void init(){
        this.repositorioUsuario = mock(RepositorioUsuario.class);
        this.servicioLogin = new ServicioLoginImpl(repositorioUsuario);
    }


    @Test
    public void cuandoSeConsultaUsuarioConEmailYPasswordCorrectosDebeDevolverElUsuarioCorrespondiente(){
        Usuario usuarioMock = new Usuario(1L, NOMBRE, EMAIL, PASSWORD, ROL, true);

        when(repositorioUsuario.buscarUsuario(EMAIL, PASSWORD)).thenReturn(usuarioMock);

        Usuario usuario = servicioLogin.consultarUsuario(EMAIL, PASSWORD);

        assertThat(usuario, is(notNullValue()));
        assertThat(usuario.getEmail(), equalTo(EMAIL));
        assertThat(usuario.getPassword(), equalTo(PASSWORD));
    }

    @Test
    public void cuandoSeConsultaUsuarioConEmailYPasswordIncorrectosDebeDevolverNull(){
        String email = "test@test.com";
        String password = "password";

        when(repositorioUsuario.buscarUsuario(email, password)).thenReturn(null);

        Usuario usuario = servicioLogin.consultarUsuario(email, password);

        assertThat(usuario, nullValue());
    }

    @Test
    public void cuandoSeRegistraUsuarioNuevoDebeGuardarseCorrectamente() throws ExcepcionUsuarioExiste {
        Usuario usuarioNuevo = new Usuario(1L, NOMBRE, EMAIL, PASSWORD, ROL, true);

        when(repositorioUsuario.buscarUsuario(usuarioNuevo.getEmail(), usuarioNuevo.getPassword())).thenReturn(null);

        servicioLogin.registrar(usuarioNuevo);

        verify(repositorioUsuario, times(1)).guardar(usuarioNuevo);
    }

    @Test
    public void cuandoSeRegistraUsuarioExistenteDebeLanzarExcepcion() {
        Usuario usuarioExistente =  new Usuario(1L, NOMBRE, EMAIL, PASSWORD, ROL, true);

        when(repositorioUsuario.buscarUsuario(usuarioExistente.getEmail(), usuarioExistente.getPassword())).thenReturn(usuarioExistente);

        assertThrows(ExcepcionUsuarioExiste.class, () -> servicioLogin.registrar(usuarioExistente));
        verify(repositorioUsuario, never()).guardar(usuarioExistente);
    }
}
