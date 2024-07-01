package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioUsuarioTest {

    private RepositorioUsuario repositorioUsuario;
    private ServicioUsuarioImpl servicioUsuario;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario);
    }

    @Test
    public void cuandoSeObtieneUsuarioPorIdDebeDevolverElUsuarioCorrespondiente() {
        Long id = 1L;
        Usuario usuarioMock = new Usuario(id, "Test", "test@test.com", "password", "USER", true);

        when(repositorioUsuario.obtenerUsuario(id)).thenReturn(usuarioMock);

        Usuario usuario = servicioUsuario.get(id);

        assertThat(usuario, is(notNullValue()));
        assertThat(usuario.getId(), equalTo(id));
        assertThat(usuario.getNombre(), equalTo("Test"));
        assertThat(usuario.getEmail(), equalTo("test@test.com"));
    }

    @Test
    public void cuandoSeObtieneUsuarioPorUnIdIncorrectoDebeDevolverNull() {
        Long idIncorrecto = 999L;

        when(repositorioUsuario.obtenerUsuario(idIncorrecto)).thenReturn(null);

        Usuario usuario = servicioUsuario.get(idIncorrecto);

        assertThat(usuario, nullValue());
    }
}
