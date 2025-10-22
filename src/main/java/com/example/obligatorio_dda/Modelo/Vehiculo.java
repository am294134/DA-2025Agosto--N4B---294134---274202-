package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Vehiculo {
    private String matricula;
    private String color;
    private String modelo;
    private Categoria categoria;
    private ArrayList<Transito> transitos;
    
    public Vehiculo(String matricula, String color, String modelo, Categoria categoria) {
        this.matricula = matricula;
        this.color = color;
        this.modelo = modelo;
        this.categoria = categoria;
        this.transitos = new ArrayList<>();
    }

    public String getMatricula() {
        return matricula;
    }

    public String getColor() {
        return color;
    }

    public String getModelo() {
        return modelo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    
    
}