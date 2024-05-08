package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.ReservedService;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReservationController {

    private GarageService garageService;
    private ReservedService reservedService;

    @Autowired
    public ReservationController(GarageService garageService, ReservedService reservedService) {
        this.garageService = garageService;
        this.reservedService= reservedService;
    }

    @RequestMapping(path = "/reservation/confirm", method = RequestMethod.POST)
    public ModelAndView confirmReservation(@ModelAttribute("reservation") ReservationDTO reservationDTO) {
        ModelMap model = new ModelMap();

        Garage garage = garageService.findById(reservationDTO.garageId);

        model.put("garage", garage);
        model.put("reservation", reservationDTO);

        return new ModelAndView("confirm-reservation", model);
    }

    @RequestMapping(path = "/reservations/list", method = RequestMethod.GET)
    public ModelAndView listReservation(HttpServletRequest request) {

        //ModelMap model = new ModelMap();
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();

        Long idUserToFind = (Long)session.getAttribute("ID");

        List<Reservation> reservations = reservedService.obtenerReservasByUserId(idUserToFind);

        List<Garage> garages = garageService.getAll();
        Garage garage = garages.get(0);

        model.put("reservations", reservations);
        model.put("garage", garage);

        return new ModelAndView("my-reservation", model);
    }
}
