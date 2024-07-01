package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoExiste;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.GarageTipoVehiculoDTO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ControladorReserva {

    private ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioUsuario servicioUsuario;
    private ServicioGarage servicioGarage;
    private ServicioReserva servicioReserva;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;
    private ServicioEmail servicioEmail;

    @Autowired
    public ControladorReserva(
            ServicioUsuario servicioUsuario,
            ServicioGarage servicioGarage,
            ServicioReserva servicioReserva,
            ServicioTipoVehiculo servicioTipoVehiculo,
            ServicioGarageTipoVehiculo servicioGarageTipoVehiculo,
            ServicioEmail servicioEmail
    ) {
        this.servicioUsuario = servicioUsuario;
        this.servicioGarage = servicioGarage;
        this.servicioReserva = servicioReserva;
        this.servicioTipoVehiculo = servicioTipoVehiculo;
        this.servicioGarageTipoVehiculo = servicioGarageTipoVehiculo;
        this.servicioEmail = servicioEmail;
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
        servicioReserva.validarVencimientoReservas(reservas);
        List<Reserva> reservasVencidas = new ArrayList<>();
        List<Reserva> reservasActivas = new ArrayList<>();

        ordenarReservasEntreActivasYVencidas(reservas, reservasVencidas, reservasActivas);

        model.put("username", usuario.getNombre());
        model.put("reservasActivas", reservasActivas);
        model.put("reservasVencidas", reservasVencidas);
        model.put("estadosReserva", EstadoReserva.class);

        return new ModelAndView("my-reservation", model);
    }

    private void ordenarReservasEntreActivasYVencidas(List<Reserva> reservas, List<Reserva> reservasVencidas, List<Reserva> reservasActivas) {
        for(Reserva reserva : reservas) {
            if(estaVencida(reserva)) {
                reservasVencidas.add(reserva);
            } else if (estaActiva(reserva)) {
                reservasActivas.add(reserva);
            }
        }
    }

    private boolean estaActiva(Reserva reserva) {
        return !reserva.getEstado().getId().equals(EstadoReserva.VENCIDA) || !reserva.getEstado().getId().equals(EstadoReserva.CANCELADA);
    }

    private boolean estaVencida(Reserva reserva) {
        return reserva.getEstado().getId().equals(EstadoReserva.VENCIDA);
    }

    @RequestMapping(path = "/cancelar/{id}", method = RequestMethod.GET)
    public ModelAndView cancelar(@PathVariable("id") Long reservaId) {

        servicioReserva.cancelar(reservaId);

        servicioEmail.enviarMailReservaCancelada(servicioReserva.buscarPorId(reservaId));

        return new ModelAndView("redirect:/reservas/listar");
    }

    @RequestMapping("/start/{id}")
    public ModelAndView preReserva(HttpServletRequest request, @PathVariable("id") Integer garageId) {
        ModelMap model = new ModelMap();

        List<String> listaTotalDeHoras = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00");

        Garage garage = servicioGarage.buscarPorId(garageId);

        if (garage == null) {
            return new ModelAndView("redirect:../home");
        }

        Long userId = (Long) request.getSession().getAttribute("ID");

        if(userId == null) {
            return new ModelAndView("redirect:/login?from=garage/"+garageId);
        }

        List <GarageTipoVehiculoDTO> garageTipoVehiculoDTOList = generarDTOTipoVehiculo(garage);

        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(garage.getId());
        reservaDTO.setUserId(userId);

        model.put("garageId", garageId);

        model.put("garage", garage);
        model.put("totalHours", listaTotalDeHoras);
        model.put("reserva", reservaDTO);
        model.put("garageTipoVehiculoDto", garageTipoVehiculoDTOList);

        return new ModelAndView("pre-reservation", model);
    }

    private List<GarageTipoVehiculoDTO> generarDTOTipoVehiculo(Garage garage) {
        List<TipoVehiculo> tiposVehiculos = servicioTipoVehiculo.traerTodos();
        List<GarageTipoVehiculo> garageTipoVehiculos = garage.getGarageTipoVehiculos();

        return combinamosInfoDeTipoVehiculoYGarageTipoVehiculo(tiposVehiculos, garageTipoVehiculos);
    }

    private List<GarageTipoVehiculoDTO> combinamosInfoDeTipoVehiculoYGarageTipoVehiculo(List<TipoVehiculo> tiposVehiculos, List<GarageTipoVehiculo> garageTipoVehiculos) {
        List garageTipoVehiculoDtoList = new ArrayList();

        for (TipoVehiculo tipoVehiculo : tiposVehiculos) {
            boolean habilitado = false;
            GarageTipoVehiculoDTO tipoVehiculoDTO = new GarageTipoVehiculoDTO();
            tipoVehiculoDTO.setIdTipoVehiculo(tipoVehiculo.getId());
            tipoVehiculoDTO.setDescripcion(tipoVehiculo.getDescripcion());
            tipoVehiculoDTO.setIcono(tipoVehiculo.getIcono());
            for (GarageTipoVehiculo garageTipoVehiculo : garageTipoVehiculos) {
                if(tipoVehiculo.equals(garageTipoVehiculo.getTipoVehiculo())){
                    habilitado = true;
                    tipoVehiculoDTO.setPrecio(garageTipoVehiculo.getPrecioHora());
                    tipoVehiculoDTO.setIdGarageTipoVehiculo(garageTipoVehiculo.getId());
                }
            }
            tipoVehiculoDTO.setHabilitado(habilitado);
            garageTipoVehiculoDtoList.add(tipoVehiculoDTO);
        }
        return garageTipoVehiculoDtoList;
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public ModelAndView confirmar(@ModelAttribute("reserva") ReservaDTO reservaDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Garage garage = servicioGarage.buscarPorId(reservaDTO.garageId);

        GarageTipoVehiculo garageTipoVehiculo = servicioGarageTipoVehiculo.obtenerPorId(reservaDTO.garageTipoVehiculoId);
        TipoVehiculo tipoVehiculo = garageTipoVehiculo.getTipoVehiculo();


        double precioCalculado = servicioReserva.calcularPrecio(reservaDTO.horarioInicio, reservaDTO.horarioFin, garageTipoVehiculo);
        reservaDTO.setPrecio(precioCalculado);

        model.put("garage", garage);
        model.put("reserva", reservaDTO);
        model.put("tipoVehiculo", tipoVehiculo);

        return new ModelAndView("confirm-reservation", model);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public ModelAndView guardar(@ModelAttribute("reserva") ReservaDTO reservaDTO) {
        ModelMap model = new ModelMap();

        try {

            Reserva nuevaReserva = servicioReserva.agregarReserva(reservaDTO);
            Long reservaId = nuevaReserva.getId();
            return new ModelAndView("redirect:/pago/formulario-pago/"+reservaId);

        } catch (ExcepcionGarageNoExiste e) {

            model.put("reserva", reservaDTO);
            model.put("error", "Error al intentar guardar la reserva. Por favor, intente nuevamente");

            return new ModelAndView("confirm-reservation", model);

        } catch (ExcepcionUsuarioNoEncontrado e) {
            return new ModelAndView("redirect:../login");
        }

    }
}
