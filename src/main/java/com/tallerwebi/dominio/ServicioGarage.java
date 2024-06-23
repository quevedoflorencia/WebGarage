package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface ServicioGarage {

    List<Garage> traerTodos();
    
    Garage buscarPorId(Integer id);

    List<Garage> getGaragesPorCapacidad(Integer capacidadBuscada);
    List<Garage> getPaginacion(Integer page, Integer size);
    Integer calcularTotalPaginas(Integer totalGarages, Integer size);
    List<Integer> generarNumerosPagina(Integer totalPages);
    default Integer validarPagina(Integer pagina) {
        return (pagina == null || pagina <= 0) ? 1 : pagina;
    }

    default Integer validarTamanioPagina(Integer tamano) {
        return (tamano == null || tamano <= 0) ? 3 : tamano;
    }
}
