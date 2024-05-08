package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReservationController {

    private GarageService garageService;

    @Autowired
    public ReservationController(GarageService garageService) { this.garageService = garageService; }

    @RequestMapping(path = "/reservation/confirm", method = RequestMethod.POST)
    public ModelAndView confirmReservation(@ModelAttribute("reservation") ReservationDTO reservationDTO) {
        ModelMap model = new ModelMap();

        Garage garage = garageService.findById(reservationDTO.garageId);

        model.put("garage", garage);
        model.put("reservation", reservationDTO);

        return new ModelAndView("confirm-reservation", model);
    }
}
