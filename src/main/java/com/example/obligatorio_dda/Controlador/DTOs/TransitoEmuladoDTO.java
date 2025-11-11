package com.example.obligatorio_dda.Controlador.DTOs;

import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Tarifa;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Propietario;

public class TransitoEmuladoDTO {

    private Puesto puesto;
    private Tarifa tarifa;
    private Vehiculo vehiculo;
    private Propietario propietario;

    public TransitoEmuladoDTO(Puesto puesto, Vehiculo vehiculo, Propietario propietario, Tarifa tarifa) {
        this.puesto = puesto;
        this.tarifa = tarifa;
        this.vehiculo = vehiculo;
        this.propietario = propietario;
    }

    public Puesto getPuesto() {
        return puesto;
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
}
