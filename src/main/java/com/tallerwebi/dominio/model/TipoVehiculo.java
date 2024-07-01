package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "tipo_vehiculo")
public class TipoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;
    private String icono;

    @OneToMany(mappedBy = "tipoVehiculo")
    private List<GarageTipoVehiculo> garageTipoVehiculos;

    public TipoVehiculo() {}

    public TipoVehiculo(Integer id, String descripcion, String icono) {
        this.id = id;
        this.descripcion = descripcion;
        this.icono = icono;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }

    public List<GarageTipoVehiculo> getGarageTipoVehiculos() { return garageTipoVehiculos; }
    public void setGarageTipoVehiculos(List<GarageTipoVehiculo> garageTipoVehiculos) { this.garageTipoVehiculos = garageTipoVehiculos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoVehiculo that = (TipoVehiculo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}