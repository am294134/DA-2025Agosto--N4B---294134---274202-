package com.example.obligatorio_dda.Controlador.DTOs;

public class TarifaDTO {
    private double monto;
    private String categoria;

    public TarifaDTO(double monto, String nombreCategoria) {
        this.monto = monto;
        this.categoria = nombreCategoria;
    }

    public double getMonto() {
        return monto;
    }

    public String getCategoria() {
        return categoria;
    }
}
