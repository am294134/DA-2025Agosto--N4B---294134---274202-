package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Transito {
    private Puesto puesto;
    private Tarifa tarifa;
    private Vehiculo vehiculo;
    private Propietario propietario;
    private DateTime fechaHora;

    public Transito(Puesto puesto, Vehiculo vehiculo, Propietario propietario, Tarifa tarifa) {
        this.puesto = puesto;
        this.vehiculo = vehiculo;
        this.propietario = propietario;
        this.tarifa = tarifa;
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

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa (Tarifa tarifa) {
        this.tarifa = tarifa;
    }
    
    public void setPuesto (Puesto puesto) {
        this.puesto = puesto;
    }
    public void setVehiculo (Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setPropietario (Propietario propietario) {
        this.propietario = propietario;
    }


}
