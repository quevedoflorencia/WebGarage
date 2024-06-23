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
        // validacion
        page = (page == null || page <= 0) ? 1 : page;
        size = (size == null || size <= 0) ? 3 : size;

        // Obtener la lista paginada de garages y calcular total de elementos y páginas
        List<Garage> garagesPaginados = servicioGarage.getPaginacion(page, size);
        Integer todosGarages = servicioGarage.traerTodos().size();
        Integer totalPages = (int) Math.ceil((double) todosGarages / size);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        model.put("garages", garagesPaginados);
        model.put("pageNumbers", pageNumbers);
        model.put("currentPage", page);
        model.put("pageSize", size);

        return new ModelAndView("listar-garages", model);
    }
}
