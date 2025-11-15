package com.example.obligatorio_dda.Modelo;

import java.time.LocalDateTime;

public class Transito {
    private Puesto puesto;
    private Tarifa tarifa;
    private Vehiculo vehiculo;
    private Propietario propietario;
    private LocalDateTime fechaHora;
    // campos para registrar lo que se aplicó al momento del tránsito
    private String bonificacionNombre;
    private double montoPagado;
    private double descuentoAplicado;

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

    public String getBonificacionNombre() {
        return bonificacionNombre;
    }

    public void setBonificacionNombre(String bonificacionNombre) {
        this.bonificacionNombre = bonificacionNombre;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    // Expert convenience methods: expose data belonging to this transito
    public double getMontoBase() {
        return tarifa != null ? tarifa.getMonto() : 0.0;
    }

    public String getPuestoNombre() {
        return puesto != null ? puesto.getNombre() : "";
    }

    public String getMatricula() {
        return vehiculo != null ? vehiculo.getMatricula() : "";
    }

    public String getBonificacionNombreOrEmpty() {
        return bonificacionNombre != null ? bonificacionNombre : "";
    }

    public String getFechaHoraFormatted(java.time.format.DateTimeFormatter fmt) {
        if (fechaHora == null) return "";
        try {
            return fechaHora.format(fmt);
        } catch (Exception ex) {
            return fechaHora.toString();
        }
    }
}
