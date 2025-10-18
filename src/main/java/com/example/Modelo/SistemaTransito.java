package com.example.Modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaTransito {
    private ArrayList<Transito> transitos;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Puesto> puestos;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<Categoria> categorias;
    private ArrayList<Propietario> propietarios;

    public SistemaTransito(ArrayList<Transito> transitos, ArrayList<Vehiculo> vehiculos,
                          ArrayList<Puesto> puestos, ArrayList<Tarifa> tarifas,
                          ArrayList<Categoria> categorias, ArrayList<Propietario> propietarios) {
        this.transitos = transitos;
        this.vehiculos = vehiculos;
        this.puestos = puestos;
        this.tarifas = tarifas;
        this.categorias = categorias;
        this.propietarios = propietarios;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
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

    public List<Propietario> getPropietarios() {
        return propietarios;
    }
}
