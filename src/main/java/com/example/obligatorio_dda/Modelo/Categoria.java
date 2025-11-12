package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Categoria {
    private String nombre;
    private ArrayList<Tarifa> tarifas;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public double obtenerTarifa() { 
        if (tarifas == null || tarifas.isEmpty()) {
            return throw PeajeException("No hay tarifas definidas para esta categoria");
        }
        // Suponiendo que queremos la tarifa más reciente o alguna lógica específica
        return tarifas.get(tarifas.size() - 1).getMonto();
    }
    

}
