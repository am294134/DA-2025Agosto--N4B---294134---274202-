package com.example.obligatorio_dda.Modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        this.fechaHora = fechaHora;
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

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setPropietario(Propietario propietario) {
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

    // exponemos datos que pertenecen a este tránsito
    public double getMontoBase() {
        return tarifa.getMonto();
    }

    public String getPuestoNombre() {
        return puesto.getNombre();
    }

    public String getMatricula() {
        return vehiculo.getMatricula();
    }

    public String getBonificacionNombreOrEmpty() {
        return bonificacionNombre;
    }

    public String getFechaHoraFormatted(DateTimeFormatter fmt) {
        if (fechaHora == null)
            return "";
        try {
            return fechaHora.format(fmt);
        } catch (Exception ex) {
            return fechaHora.toString();
        }
    }

    // sería como la fabrica, crea un tránsito y calcula almacenando los valores ya
    // aplicados
    public static Transito crearConValoresAplicados(Puesto puesto, Vehiculo vehiculo, Propietario propietario,
            Tarifa tarifa, Bonificacion bon, LocalDateTime fecha) {
        Transito t = new Transito(puesto, vehiculo, propietario, tarifa, fecha);

        double montoBase = tarifa.getMonto();

        double montoPagado;
        if (bon != null) {
            montoPagado = bon.calcularDescuento(montoBase);
        } else {
            montoPagado = montoBase;
        }

        double descuento = montoBase - montoPagado;

        t.setBonificacionNombre(bon != null ? bon.getNombre() : null);
        t.setMontoPagado(montoPagado);
        t.setDescuentoAplicado(descuento);
        return t;
    }
}
