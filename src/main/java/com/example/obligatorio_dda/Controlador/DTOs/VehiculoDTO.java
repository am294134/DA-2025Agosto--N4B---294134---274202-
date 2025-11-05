package com.example.obligatorio_dda.Controlador.DTOs;
public class VehiculoDTO {
    private String matricula;
    private String modelo;
    private String color;
    private String categoria;

    public VehiculoDTO(String matricula, String modelo, String color, String categoria) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public String getCategoria() {
        return categoria;
    }
}
