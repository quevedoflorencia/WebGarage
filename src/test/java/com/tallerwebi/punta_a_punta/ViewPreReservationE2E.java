package com.tallerwebi.punta_a_punta;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.tallerwebi.punta_a_punta.vistas.ViewPreReservation;
import org.junit.jupiter.api.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ViewPreReservationE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    ViewPreReservation viewPreReservation;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));

    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        viewPreReservation = new ViewPreReservation(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    //noDeberiaMostrarElBotonDeContinuarSiLosCamposNoEstanCompletados
    void ShouldNotShowTheContinueButtonIfTheFieldsAreNotCompleted() {
        // Obtener el valor de los atributos "value" de los campos
        String valueTimepickerUntil = viewPreReservation.getAttribute("#timepicker_until", "value");
        String valueDatepickerFrom = viewPreReservation.getAttribute("#datepicker_from", "value");
        String valueTimepickerFrom = viewPreReservation.getAttribute("#timepicker_from", "value");

        assertThat(valueTimepickerUntil, isEmptyOrNullString());
        assertThat(valueDatepickerFrom, isEmptyOrNullString());
        assertThat(valueTimepickerFrom, isEmptyOrNullString());

        // Verificar que el botón de continuar no está visible inicialmente al cargar la página
        Boolean botonContinuarInvisible = viewPreReservation.isInvisible("#container-go-reservation");
        assertThat(botonContinuarInvisible, is(true));
    }
}
