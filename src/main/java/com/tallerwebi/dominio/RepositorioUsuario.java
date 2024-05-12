package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Usuario;

public interface RepositorioUsuario {

    Usuario obtenerUsuario(Long id);
    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
}

