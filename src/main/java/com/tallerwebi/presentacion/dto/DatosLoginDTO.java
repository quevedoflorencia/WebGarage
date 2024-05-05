package com.tallerwebi.presentacion.dto;

public class DatosLoginDTO {
    private String email;
    private String password;

    public DatosLoginDTO() {
    }

    public DatosLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

