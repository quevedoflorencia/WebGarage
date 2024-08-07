package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioExiste;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.presentacion.dto.DatosLoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLoginDTO datosLoginDTOMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;


	@BeforeEach
	public void init(){
		datosLoginDTOMock = new DatosLoginDTO("dami@unlam.com", "123");
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock);
	}

	@Test
	public void irALoginDeberiaLlevarmeALaVistaLoginConTipoDeModeloCorrecto() {
		ModelAndView modelAndView = controladorLogin.irALogin(null);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));

		ModelMap modelMap = modelAndView.getModelMap();
		DatosLoginDTO datosLoginDTO = (DatosLoginDTO) modelMap.get("loginData");
		assertThat(datosLoginDTO, notNullValue());
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginDTOMock,null, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario o clave que ingresaste son incorrectos, intentá nuevamente."));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}
	
	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
		// preparacion
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);
		
		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginDTOMock,null,requestMock);
		
		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/garages/listado"));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}

	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws ExcepcionUsuarioExiste {

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(servicioLoginMock, times(1)).registrar(usuarioMock);
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws ExcepcionUsuarioExiste {
		// preparacion
		doThrow(ExcepcionUsuarioExiste.class).when(servicioLoginMock).registrar(usuarioMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El email que ingresaste ya está registrado"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws ExcepcionUsuarioExiste {
		// preparacion
		doThrow(RuntimeException.class).when(servicioLoginMock).registrar(usuarioMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Lo sentimos, hubo un error al registrar"));
	}

	@Test
	public void cerrarSesionDeUnUsuarioLogueadoDeberiaEliminarDatosDeSesionYRedirigirAHome() {
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("ID")).thenReturn(1L);

		ModelAndView modelAndView = controladorLogin.cerrarSesion(requestMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
	}

	@Test
	public void cerrarSesionDeUnUsuarioCuandoNoEstaLogueadoDeberiaRedirigirAHomeIgualmente() {
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("ID")).thenReturn(null);

		ModelAndView modelAndView = controladorLogin.cerrarSesion(requestMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
	}

	@Test
	public void cuandoSeQuiereIniciarElFormularioDeRegistroDeNuevoUsuarioDebeLlevarALaVistaNuevoUsuario() {
		ModelAndView modelAndView = controladorLogin.nuevoUsuario();

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));

		ModelMap modelMap = modelAndView.getModelMap();
		Usuario usuario = (Usuario) modelMap.get("usuario");
		assertThat(usuario, notNullValue());
	}


	@Test
	public void loginExitosoDesdeGarageDeberiaRedirigirAStartGarageCuandoVieneDeReservarGarage() {
		// Preparación
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);
		String garageId = "2";
		String from = "garage/" + garageId;

		// Ejecución
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginDTOMock, from, requestMock);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/reservas/start/" + garageId));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}
}
