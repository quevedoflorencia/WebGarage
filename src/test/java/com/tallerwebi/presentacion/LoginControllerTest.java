package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.LoginService;
import com.tallerwebi.dominio.model.User;
import com.tallerwebi.dominio.excepcion.ExistUserException;
import com.tallerwebi.presentacion.dto.LoginDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

	private LoginController loginController;
	private User userMock;
	private LoginDataDTO loginDataDTOMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private LoginService loginServiceMock;


	@BeforeEach
	public void init(){
		loginDataDTOMock = new LoginDataDTO("dami@unlam.com", "123");
		userMock = mock(User.class);
		when(userMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		loginServiceMock = mock(LoginService.class);
		loginController = new LoginController(loginServiceMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(loginServiceMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = loginController.validarLogin(loginDataDTOMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}
	
	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
		// preparacion
		User userEncontradoMock = mock(User.class);
		when(userEncontradoMock.getRol()).thenReturn("ADMIN");

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(loginServiceMock.consultarUsuario(anyString(), anyString())).thenReturn(userEncontradoMock);
		
		// ejecucion
		ModelAndView modelAndView = loginController.validarLogin(loginDataDTOMock, requestMock);
		
		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
		verify(sessionMock, times(1)).setAttribute("ROL", userEncontradoMock.getRol());
	}

	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws ExistUserException {

		// ejecucion
		ModelAndView modelAndView = loginController.registrarme(userMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(loginServiceMock, times(1)).registrar(userMock);
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws ExistUserException {
		// preparacion
		doThrow(ExistUserException.class).when(loginServiceMock).registrar(userMock);

		// ejecucion
		ModelAndView modelAndView = loginController.registrarme(userMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws ExistUserException {
		// preparacion
		doThrow(RuntimeException.class).when(loginServiceMock).registrar(userMock);

		// ejecucion
		ModelAndView modelAndView = loginController.registrarme(userMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}
}
