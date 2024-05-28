package com.tallerwebi.presentacion.dto;

import java.time.LocalDate;

public class DatosPagoDTO {

    private String titularTarjeta;
    private Long numeroTarjeta;
    private LocalDate fechaVencimiento;
    private Integer cvv;
    private Long idReserva;

    public DatosPagoDTO() {
    }

    public DatosPagoDTO(String titularTarjeta, Long numeroTarjeta, LocalDate fechaVencimiento, Integer cvv, Long idReserva) {
        this.titularTarjeta = titularTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
        this.idReserva= idReserva;
    }



    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public Long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(Long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Long getIdReserva() {return idReserva; }
}
