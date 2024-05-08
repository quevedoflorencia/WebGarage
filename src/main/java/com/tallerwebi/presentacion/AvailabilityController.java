package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class AvailabilityController {

    private GarageService garageService;

    @Autowired
    public AvailabilityController(GarageService garageService) { this.garageService = garageService; }


}
