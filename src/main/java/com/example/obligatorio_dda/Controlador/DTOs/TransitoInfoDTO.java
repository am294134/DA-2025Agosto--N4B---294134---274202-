package com.example.obligatorio_dda.Controlador.DTOs;

public class TransitoInfoDTO {
    private String propietarioNombre;
    private String categoria;
    private String bonificacion;

    public TransitoInfoDTO() {
    }

    public TransitoInfoDTO(String propietarioNombre, String categoria, String bonificacion) {
        this.propietarioNombre = propietarioNombre;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
    }

    public String getPropietarioNombre() {
        return propietarioNombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getBonificacion() {
        return bonificacion;
    }

    public void setPropietarioNombre(String propietarioNombre) {
        this.propietarioNombre = propietarioNombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setBonificacion(String bonificacion) {
        this.bonificacion = bonificacion;
    }
}
