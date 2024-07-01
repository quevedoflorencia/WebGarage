package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service("servicioGarage")
@Transactional
public class ServicioGarageImpl implements ServicioGarage {

    private RepositorioGarage repositorioGarage;

    @Autowired
    public ServicioGarageImpl(RepositorioGarage repositorioGarage) { this.repositorioGarage = repositorioGarage; }

    @Override
    public List<Garage> traerTodos() {
        return repositorioGarage.findAll();
    }

    @Override
    public Garage buscarPorId(Integer id) {
        return repositorioGarage.findById(id);
    }

    @Override
    public List<Garage> getGaragesPorCapacidad(Integer capacidadBuscada) {
        return repositorioGarage.getGarageSegunCapacidad(capacidadBuscada);
    }

    @Override
    public List<Garage> getPaginacion(Integer page, Integer size) {
        // Validar la página y el tamaño de la página
        page = validarPagina(page);
        size = validarTamanioPagina(size);

        // Calcular el total de garages y total de páginas
        Integer totalGarages = traerTodos().size();
        Integer totalPages = calcularTotalPaginas(totalGarages, size);

        // Ajustar la página si excede el número total de páginas
        if (page > totalPages) {
            page = totalPages > 0 ? totalPages : 1;
        }

        // Obtengo la lista paginada de garages
        List<Garage> garagesPaginados = repositorioGarage.obtenerPaginacion(page, size);

        // Si la pagina solicitada no contiene elementos y no es la primera página, ajustar a una página válida
        if (garagesPaginados.isEmpty() && page > 1) {
            page = totalPages > 0 ? totalPages : 1;
            garagesPaginados = repositorioGarage.obtenerPaginacion(page, size);
        }

        return garagesPaginados;
    }

    @Override
    public Integer calcularTotalPaginas(Integer totalGarages, Integer size) {
        return (int) Math.ceil((double) totalGarages / size);
    }

    @Override
    public List<Integer> generarNumerosPagina(Integer totalPages) {
        return IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public Integer validarPagina(Integer pagina) {
        return (pagina == null || pagina <= 0) ? 1 : pagina;
    }

    @Override
    public Integer validarTamanioPagina(Integer tamano) {
        return (tamano == null || tamano <= 0) ? 3 : tamano;
    }

    @Override
    public List<Garage> obtenerGaragesPorTipoVehiculo(Integer tipoVehiculoId){
        return repositorioGarage.getGaragesPorTipoVehiculo(tipoVehiculoId);
    }
}
