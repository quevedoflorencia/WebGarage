package com.tallerwebi.punta_a_punta.vistas;
import com.microsoft.playwright.Page;

public class ViewPreReservation  extends VistaWeb {
    public ViewPreReservation(Page page) {
        super(page);
        page.navigate("localhost:8080/spring/pre-reservation");
    }

}
