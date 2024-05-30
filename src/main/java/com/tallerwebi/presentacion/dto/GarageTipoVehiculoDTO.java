package com.tallerwebi.presentacion.dto;

public class GarageTipoVehiculoDTO {
    public Integer idTipoVehiculo;
    public Integer idGarageTipoVehiculo;
    public String descripcion ;
    public String icono;
    public Boolean habilitado;
    public Double Precio;

    public Integer getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(Integer idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public Integer getIdGarageTipoVehiculo() {
        return idGarageTipoVehiculo;
    }

    public void setIdGarageTipoVehiculo(Integer idGarageTipoVehiculo) {
        this.idGarageTipoVehiculo = idGarageTipoVehiculo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcono() { return icono; }

    public void setIcono(String icono) { this.icono = icono; }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }
}
