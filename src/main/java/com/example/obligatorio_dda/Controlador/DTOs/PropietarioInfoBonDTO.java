package com.example.obligatorio_dda.Controlador.DTOs;

import java.util.ArrayList;
import java.util.List;

public class PropietarioInfoBonDTO {
    private String nombreCompleto;
    private String estado;
    private List<BonificacionAsignadaDTO> asignadas = new ArrayList<>();

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

    public List<BonificacionAsignadaDTO> getAsignadas() {
        return asignadas;
    }

    public void setAsignadas(List<BonificacionAsignadaDTO> asignadas) {
        this.asignadas = asignadas;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
