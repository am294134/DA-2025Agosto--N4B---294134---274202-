package com.example.obligatorio_dda.Modelo;

import java.time.LocalDateTime;

public class Transito {
    private Puesto puesto;
    private Tarifa tarifa;
    private Vehiculo vehiculo;
    private Propietario propietario;
    private LocalDateTime fechaHora;

    public Transito(Puesto puesto, Vehiculo vehiculo, Propietario propietario, Tarifa tarifa) {
        this.puesto = puesto;
        this.vehiculo = vehiculo;
        this.propietario = propietario;
        this.tarifa = tarifa;
        this.fechaHora = LocalDateTime.now();
    }

    // constructor que permite especificar la fecha/hora del tránsito
    public Transito(Puesto puesto, Vehiculo vehiculo, Propietario propietario, Tarifa tarifa, LocalDateTime fechaHora) {
        this.puesto = puesto;
        this.vehiculo = vehiculo;
        this.propietario = propietario;
        this.tarifa = tarifa;
        this.fechaHora = fechaHora != null ? fechaHora : LocalDateTime.now();
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
