package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioLoginTest {

    private RepositorioUsuario repositorioUsuario;
    private ServicioLogin servicioLogin;

    @BeforeEach
    public void init(){
        this.repositorioUsuario = mock(RepositorioUsuario.class);
        this.servicioLogin = new ServicioLoginImpl(repositorioUsuario);
    }

}
