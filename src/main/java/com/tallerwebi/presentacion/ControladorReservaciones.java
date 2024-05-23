package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoEncontrado;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.ReservacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ControladorReservaciones {

    private ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioUsuario servicioUsuario;
    private ServicioGarage servicioGarage;
    private ServicioRepositorio servicioRepositorio;


    @Autowired
    public ControladorReservaciones(ServicioUsuario servicioUsuario, ServicioGarage servicioGarage, ServicioRepositorio servicioRepositorio, ServicioTipoVehiculo servicioTipoVehiculo) {
        this.servicioUsuario = servicioUsuario;
        this.servicioGarage = servicioGarage;
        this.servicioRepositorio = servicioRepositorio;
        this.servicioTipoVehiculo = servicioTipoVehiculo;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView listReservation(HttpServletRequest request) {

        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();

        Long userId = (Long) session.getAttribute("ID");

        if(userId == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.get(userId);
        List<Reservacion> reservaciones = servicioRepositorio.obtenerReservasByUserId(userId);

        List<Garage> garages = servicioGarage.traerTodos();
        Garage garage = garages.get(0);

        model.put("username", usuario.getNombre());
        model.put("garage", garage);
        model.put("reservations", reservaciones);

        return new ModelAndView("my-reservation", model);
    }

    @RequestMapping("/start/{id}")
    public ModelAndView goToPreReservation(HttpServletRequest request, @PathVariable("id") Integer garageId) {
        ModelMap model = new ModelMap();

        List<String> listaTotalDeHoras = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00");

        List<Garage> garages = servicioGarage.traerTodos();
        Garage garage = garages.get(garageId);

        if (garage == null) {
            //TODO: redireccionar a la nueva home!
            return new ModelAndView("redirect:../login");
        }

        Long userId = (Long) request.getSession().getAttribute("ID");

        if(userId == null) {
            return new ModelAndView("redirect:../login");
        }

        //traer todos los tipos de vehiculos para matchear en el listado segun el garage..
        List<TipoVehiculo> tiposVehiculos = servicioTipoVehiculo.traerTodos();
        //List<GarageTipoVehiculo> garageTipoVehiculos = garage.getGarageTipoVehiculos();

        ReservacionDTO reservacionDTO = new ReservacionDTO();
        reservacionDTO.setGarageId(garage.getId());
        reservacionDTO.setUserId(userId);

        model.put("garageId", garageId);

        model.put("garage", garage);
        model.put("totalHours", listaTotalDeHoras);
        model.put("reservation", reservacionDTO);
        model.put("tiposVehiculos", tiposVehiculos);
        //model.put("garageTipoVehiculos", garageTipoVehiculos);

        return new ModelAndView("pre-reservation", model);
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public ModelAndView confirmReservation(@ModelAttribute("reservation") ReservacionDTO reservacionDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Garage garage = servicioGarage.buscarPorId(reservacionDTO.garageId);

        model.put("garage", garage);
        model.put("reservation", reservacionDTO);

        return new ModelAndView("confirm-reservation", model);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("reservation") ReservacionDTO reservacionDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        try {
            servicioRepositorio.agregarReserva(reservacionDTO);
        } catch (ExcepcionGarageNoEncontrado e) {
            List<Garage> garages = servicioGarage.traerTodos();
            Garage garage = garages.get(0);
            model.put("garage", garage);
            model.put("reservation", reservacionDTO);
            model.put("error", "Error al intentar guardar la reserva. Por favor, intente nuevamente");
            return new ModelAndView("confirm-reservation", model);
        } catch (ExcepcionUsuarioNoEncontrado e) {
            return new ModelAndView("redirect:../login");
        }

        return new ModelAndView("redirect:/reservations/list");
    }
}
