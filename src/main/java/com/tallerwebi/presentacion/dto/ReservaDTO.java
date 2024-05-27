package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.TipoVehiculo;

public class ReservaDTO {
    public Integer garageId;
    public Long userId;
    public Integer garageTipoVehiculoId;
    public TipoVehiculo tipoVehiculo;
    public String dia;
    public String horarioInicio;
    public String horarioFin;
    public Double precio;
    public Integer estado;


    public Integer getGarageId() {
        return garageId;
    }

    public void setGarageId(Integer garageId) {
        this.garageId = garageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDia() {
        return dia;
    }

    public Integer getGarageTipoVehiculoId() { return garageTipoVehiculoId; }

    public void setGarageTipoVehiculoId(Integer garageTipoVehiculoId) { this.garageTipoVehiculoId = garageTipoVehiculoId; }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getEstado() { return estado; }

    public void setEstado(Integer estado) { this.estado = estado; }


}
