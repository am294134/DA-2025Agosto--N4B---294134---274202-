package com.example.obligatorio_dda.Controlador.DTOs;

public class BonificacionAsignadaDTO {
    private String nombreBonificacion;
    private String puesto;
    private String fechaAsignada;

    public BonificacionAsignadaDTO(String nombreBonificacion, String puesto, String fechaAsignada) {
        this.nombreBonificacion = nombreBonificacion;
        this.puesto = puesto;
        this.fechaAsignada = fechaAsignada;
    }

    public String getNombreBonificacion() {
        return nombreBonificacion;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getFechaAsignada() {
        return fechaAsignada;
    }
}
