package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class SistemaTransito {
    private ArrayList<Transito> transitos;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Propietario> propietarios;

    protected SistemaTransito(ArrayList<Transito> transitos, ArrayList<Vehiculo> vehiculos,
            ArrayList<Propietario> propietarios) {
        this.transitos = transitos;
        this.vehiculos = vehiculos;
        this.propietarios = propietarios;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }

    public void agregarVehiculo(String matricula, String color, String modelo, Categoria categoria,
            Propietario propietario) {
        // Crea veh
        Vehiculo vehiculo = new Vehiculo(matricula, color, modelo, categoria, propietario);

        // Agrega el vehículo a la lista general
        vehiculos.add(vehiculo);
        // Agrega el vehiculo a la lista del propietario (relación bidireccional)
        propietario.getVehiculos().add(vehiculo);
    }

    public void agregarTransito(String matricula, Transito transito)   {
        Vehiculo v = buscarVehiculoPorMatricula(matricula);
        v.agregarTransito(transito);
    }
}
