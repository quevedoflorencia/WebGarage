package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.util.List;
import javax.persistence.Table;


@Entity
@Table(name = "tipo_vehiculo")
public class TipoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;

    public TipoVehiculo() {}

    public TipoVehiculo(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<GarageTipoVehiculo> getGarageTipoVehiculos() { return garageTipoVehiculos; }
    public void setGarageTipoVehiculos(List<GarageTipoVehiculo> garageTipoVehiculos) { this.garageTipoVehiculos = garageTipoVehiculos; }
}