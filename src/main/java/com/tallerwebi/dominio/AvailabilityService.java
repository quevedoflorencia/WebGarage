package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;

import java.util.List;

public interface AvailabilityService {

    List getAvailableHours(String days);

}
