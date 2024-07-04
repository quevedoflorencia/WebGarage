package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioExiste;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.presentacion.dto.DatosLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin(@RequestParam(required = false) String from) {
        ModelMap modelo = new ModelMap();
        modelo.put("loginData", new DatosLoginDTO());
        modelo.put("from", from);
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("loginData") DatosLoginDTO datosLoginDTO, @RequestParam(required = false) String from, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLoginDTO.getEmail(), datosLoginDTO.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ID", usuarioBuscado.getId());
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());

            if (from != null && from.contains("garage/")) {
                String[] parts = from.split("/");
                if (parts.length > 1) {
                    String garageId = parts[1];
                    return new ModelAndView("redirect:/reservas/start/" + garageId);
                }
            }else{
                return new ModelAndView("redirect:/garages/listado");
            }
        } else {
            model.put("error", "El usuario o clave que ingresaste son incorrectos, intentá nuevamente.");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/cerrar-sesion")
    public ModelAndView cerrarSesion(HttpServletRequest request) {

        if (request.getSession().getAttribute("ID") != null) {
            request.getSession().removeAttribute("ID");
            request.getSession().removeAttribute("ROL");
        }

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioLogin.registrar(usuario);
        } catch (ExcepcionUsuarioExiste e){
            model.put("error", "El email que ingresaste ya está registrado");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            model.put("error", "Lo sentimos, hubo un error al registrar");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }
}

