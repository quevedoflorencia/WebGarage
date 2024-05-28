package com.tallerwebi.presentacion;

public class DatosPagoDTO {
    private String titular;
    private String numeroTarjeta;
    private String vencimiento;
    private String cvv;
    private Long idReserva;


    public DatosPagoDTO() {
    }

    public DatosPagoDTO(String titular, String numeroTarjeta, String vencimiento, String cvv, Long idReserva) {
        this.titular = titular;
        this.numeroTarjeta = numeroTarjeta;
        this.vencimiento = vencimiento;
        this.cvv = cvv;
        this.idReserva = idReserva;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }
}
