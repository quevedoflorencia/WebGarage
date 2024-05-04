package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Dates;

import java.util.ArrayList;

@Controller
public class AvailabilityController {

    @RequestMapping("/pre-reservation")
    public ModelAndView goToPreReservation() {

        ModelMap model = new ModelMap();

        model.put("datesAvailabilities","");
        return new ModelAndView("pre-reservation", model);
    }

}
