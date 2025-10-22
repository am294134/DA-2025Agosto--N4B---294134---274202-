package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

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


    

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }
}
