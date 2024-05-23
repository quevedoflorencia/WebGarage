package com.tallerwebi.dominio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.*;


@Entity
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Integer capacidad;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private String latitud;
    private String longitud;

    @OneToMany(mappedBy="garage")
    private List<GarageTipoVehiculo> garageTipoVehiculos;

    public Garage() {}

    public Garage(Integer id, String nombre, Integer capacidad, LocalTime horarioApertura, LocalTime horarioCierre, String latitud, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacity) {
        this.capacidad = capacity;
    }

    public LocalTime getHorarioApertura() {
        return horarioApertura;
    }

    public void setHorarioApertura(LocalTime openingTime) {
        this.horarioApertura = openingTime;
    }

    public LocalTime getHorarioCierre() {
        return horarioCierre;
    }

    public void setHorarioCierre(LocalTime closingTime) {
        this.horarioCierre = closingTime;
    }

    public List<GarageTipoVehiculo> getGarageTipoVehiculos() { return garageTipoVehiculos; }

    public void setGarageTipoVehiculos(List<GarageTipoVehiculo> garageTipoVehiculos) { this.garageTipoVehiculos = garageTipoVehiculos; }

    public String getLatitud() {
        return latitud;
    }

    public void setLatutud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String latitud) {
        this.longitud = longitud;
    }

}
