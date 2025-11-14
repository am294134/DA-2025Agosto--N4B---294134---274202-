package com.example.obligatorio_dda.Controlador.DTOs;

public class PropietarioInfoBonDTO {
    private String nombreCompleto;
    private String estado;

    public PropietarioInfoBonDTO() {}

    public PropietarioInfoBonDTO(String nombreCompleto, String estado) {
        this.nombreCompleto = nombreCompleto;
        this.estado = estado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEstado() {
        return estado;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
