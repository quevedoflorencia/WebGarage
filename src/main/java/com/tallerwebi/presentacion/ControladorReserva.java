package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioTipoVehiculo;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoEncontrado;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.ReservaDTO;
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
@RequestMapping("/reservas")
public class ControladorReserva {

    private ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioUsuario servicioUsuario;
    private ServicioGarage servicioGarage;
    private ServicioReserva servicioReserva;


    @Autowired
    public ControladorReserva(ServicioUsuario servicioUsuario, ServicioGarage servicioGarage, ServicioReserva servicioReserva, ServicioTipoVehiculo servicioTipoVehiculo) {
        this.servicioUsuario = servicioUsuario;
        this.servicioGarage = servicioGarage;
        this.servicioReserva = servicioReserva;
        this.servicioTipoVehiculo = servicioTipoVehiculo;
    }

    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    public ModelAndView listarReservas(HttpServletRequest request) {

        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();

        Long userId = (Long) session.getAttribute("ID");

        if(userId == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.get(userId);
        List<Reserva> reservas = servicioReserva.obtenerReservasByUserId(userId);

        List<Garage> garages = servicioGarage.traerTodos();
        Garage garage = garages.get(0);

        model.put("username", usuario.getNombre());
        model.put("garage", garage);
        model.put("reservas", reservas);

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
        List<GarageTipoVehiculo> garageTipoVehiculos = garage.getGarageTipoVehiculos();

        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(garage.getId());
        reservaDTO.setUserId(userId);

        model.put("garageId", garageId);

        model.put("garage", garage);
        model.put("totalHours", listaTotalDeHoras);
        model.put("reserva", reservaDTO);
        model.put("tiposVehiculos", tiposVehiculos);
        model.put("garageTipoVehiculos", garageTipoVehiculos);

        return new ModelAndView("pre-reservation", model);
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public ModelAndView confirmReservation(@ModelAttribute("reservation") ReservaDTO reservaDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Garage garage = servicioGarage.buscarPorId(reservaDTO.garageId);

        model.put("garage", garage);
        model.put("reserva", reservaDTO);

        return new ModelAndView("confirm-reservation", model);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("reservation") ReservaDTO reservaDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        try {
            servicioReserva.agregarReserva(reservaDTO);
        } catch (ExcepcionGarageNoEncontrado e) {
            List<Garage> garages = servicioGarage.traerTodos();
            Garage garage = garages.get(0);
            model.put("garage", garage);
            model.put("reserva", reservaDTO);
            model.put("error", "Error al intentar guardar la reserva. Por favor, intente nuevamente");
            return new ModelAndView("confirm-reservation", model);
        } catch (ExcepcionUsuarioNoEncontrado e) {
            return new ModelAndView("redirect:../login");
        }

        return new ModelAndView("redirect:/reservas/listar");
    }
}
