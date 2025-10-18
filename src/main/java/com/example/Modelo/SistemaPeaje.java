package com.example.Modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaPeaje {
    private ArrayList<Puesto> puestos;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<Categoria> categorias;

    public SistemaPeaje(ArrayList<Puesto> puestos, ArrayList<Tarifa> tarifas, ArrayList<Categoria> categorias) {
        this.puestos = puestos;
        this.tarifas = tarifas;
        this.categorias = categorias;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }
}
