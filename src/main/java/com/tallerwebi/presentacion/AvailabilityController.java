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

    @RequestMapping("/pre-reservation")
    public ModelAndView goToPreReservation(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        List<String> totalHoursList = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00");

        List<Garage> garages = garageService.getAll();
        Garage garage = garages.get(0);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setGarageId(garage.getId());
//        reservationDTO.setUserId((Long) request.getSession().getAttribute("ID"));

        model.put("totalHours", totalHoursList);
        model.put("reservation", reservationDTO);

        return new ModelAndView("pre-reservation", model);
    }

}
