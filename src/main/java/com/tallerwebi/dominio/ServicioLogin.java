package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExistUserException;
import com.tallerwebi.dominio.model.Usuario;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws ExistUserException;

}
