package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/garages")
public class ControladorGarage {

    private ServicioGarage servicioGarage;

    @Autowired
    public ControladorGarage(ServicioGarage servicioGarage) {
        this.servicioGarage = servicioGarage;
    }

    @RequestMapping(path = "/listado/", method = RequestMethod.GET)
    public ModelAndView inicio(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "3") Integer size) {
        ModelMap model = new ModelMap();
        page = servicioGarage.validarPagina(page);
        size = servicioGarage.validarTamanioPagina(size);

        List<Garage> garagesPaginados = servicioGarage.getPaginacion(page, size);
        Integer totalGarages = servicioGarage.traerTodos().size();
        Integer totalPages = servicioGarage.calcularTotalPaginas(totalGarages, size);
        List<Integer> pageNumbers = servicioGarage.generarNumerosPagina(totalPages);

        model.put("garages", garagesPaginados);
        model.put("pageNumbers", pageNumbers);
        model.put("currentPage", page);
        model.put("pageSize", size);

        return new ModelAndView("listar-garages", model);
    }
}
