package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface ServicioGarage {

    List<Garage> traerTodos();
    
    Garage buscarPorId(Integer id);

    List<Garage> getGaragesPorCapacidad(Integer capacidadBuscada);
    List<Garage> obtenerGaragesPorTipoVehiculo(Integer tipoVehiculoId);
    List<Garage> getPaginacion(Integer page, Integer size);
    Integer calcularTotalPaginas(Integer totalGarages, Integer size);
    List<Integer> generarNumerosPagina(Integer totalPages);
    Integer validarPagina(Integer pagina);
    Integer validarTamanioPagina(Integer tamano);


}
