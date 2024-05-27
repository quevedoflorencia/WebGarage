package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.presentacion.dto.DatosLoginDTO;
import com.tallerwebi.presentacion.dto.DatosPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping("/pago")
public class ControladorPago {

    private ServicioPago servicioPago;
    private ServicioReserva servicioReserva;

    @Autowired
    public ControladorPago(ServicioPago servicioPago, ServicioReserva servicioReserva) {
        this.servicioPago= servicioPago;
        this.servicioReserva= servicioReserva;
    }

    @RequestMapping("/formulario-pago")
    public ModelAndView irPago() {

        ModelMap modelo = new ModelMap();
        modelo.put("pagoData", new DatosPagoDTO());
        return new ModelAndView("formulario-pago", modelo);
    }
    @RequestMapping(path = "/validar/{id}", method = RequestMethod.POST)
    public ModelAndView validarPago(@ModelAttribute("pagoData") DatosPagoDTO datosPagoDTO, @PathVariable("id") Long reservaId, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Reserva reserva = servicioReserva.buscarPorId(reservaId);
       // String titularTarjeta= datosPagoDTO.getTitularTarjeta();
        Long numeroTarjeta = datosPagoDTO.getNumeroTarjeta();
      //  LocalDate fechaVencimiento = datosPagoDTO.getFechaVencimiento();


        Boolean resultadoValidacion = servicioPago.validarNumeroTarjeta(numeroTarjeta);

        if(resultadoValidacion ==true){
            servicioPago.registrarPago (datosPagoDTO, reserva.getId());
            model.put("exitoso", "Pago exitoso!");
        }else {
            model.put("error", "Número de Tarjeta Inválido");
        }

    /*
        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLoginDTO.getEmail(), datosLoginDTO.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ID", usuarioBuscado.getId());
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }

     */
        return new ModelAndView("mostrar-mensaje", model);
    }



}






