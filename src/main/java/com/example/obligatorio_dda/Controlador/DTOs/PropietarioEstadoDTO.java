package com.example.obligatorio_dda.Controlador.DTOs;

public class PropietarioEstadoDTO {
    private String nombreCompleto;
    private String estadoActual;

    public PropietarioEstadoDTO(String nombreCompleto, String estadoActual) {
        this.nombreCompleto = nombreCompleto;
        this.estadoActual = estadoActual;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }
}