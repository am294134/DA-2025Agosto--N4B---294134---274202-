package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Transito {
    private Puesto puesto;
    private ArrayList<Tarifa> tarifas;
    private Vehiculo vehiculo;
    private Propietario propietario;

    public Transito(Puesto puesto, Vehiculo vehiculo, Propietario propietario) {
        this.puesto = puesto;
        this.vehiculo = vehiculo;
        this.propietario = propietario;
        this.tarifas = new ArrayList<>();
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    


}
