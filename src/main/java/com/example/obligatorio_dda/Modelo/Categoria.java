package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Categoria {
    private String nombre;
    private ArrayList<Tarifa> tarifas;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.tarifas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }
    
}
