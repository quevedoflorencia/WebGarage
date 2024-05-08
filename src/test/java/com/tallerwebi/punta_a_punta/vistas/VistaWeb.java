package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class VistaWeb {
    protected Page page;

    public VistaWeb(Page page) {
        this.page = page;
    }

    public String obtenerURLActual(){
        return page.url();
    }

    protected String obtenerTextoDelElemento(String selectorCSS){
        return this.obtenerElemento(selectorCSS).textContent();
    }

    protected void darClickEnElElemento(String selectorCSS){
        this.obtenerElemento(selectorCSS).click();
    }

    protected void escribirEnElElemento(String selectorCSS, String texto){
        this.obtenerElemento(selectorCSS).type(texto);
    }

    private Locator obtenerElemento(String selectorCSS){
        return page.locator(selectorCSS);
    }

    //**NUEVOS METODOS
    public String getAttribute(String selectorCSS, String attributeName) {
        return this.obtenerElemento(selectorCSS).getAttribute(attributeName);
    }

    public void addValue(String selectorCSS, String attributeValue) {
        this.obtenerElemento(selectorCSS).fill(attributeValue);
    }

    public ElementHandle getElement(String selectorCSS) {
        return page.locator(selectorCSS).first().elementHandle();
    }


    public Boolean isInvisible(String selectorCSS) {
        return !this.obtenerElemento(selectorCSS).isVisible();
    }


}
