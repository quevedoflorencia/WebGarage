package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface ServicioGarage {

    List<Garage> traerTodos();
    
    Garage buscarPorId(Integer id);

    List<Garage> getGaragesPorCapacidad(Integer capacidadBuscada);
    List<Garage> obtenerGaragesPorTipoVehiculo(Integer tipoVehiculoId);
    List<Garage> getPaginacion(Integer page, Integer size, Boolean orderByCalificacion, String busqueda);
    Integer obtenerCantidadTotal(String busqueda);
    Integer calcularTotalPaginas(Integer totalGarages, Integer size);
    List<Integer> generarNumerosPagina(Integer totalPages);
    Integer validarPagina(Integer pagina);
    Integer validarTamanioPagina(Integer tamano);


    Double actualizarPromedio(Integer idGarage);

    void guardarPromedio(Garage garage);
}
