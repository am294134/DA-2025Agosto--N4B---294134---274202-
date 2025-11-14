package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Propietario extends Usuario {
    private double saldoActual;
    private double saldoMinimo;
    private Estado estado;
    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();
    private ArrayList<Asignacion> asignaciones = new ArrayList<>();
    private ArrayList<Transito> transitos = new ArrayList<>();
    
    public Propietario(String nombre, String apellido, String cedula, String contrasenia,
                      double saldoActual, double saldoMinimo, Estado estado) {
        super(nombre, apellido, cedula, contrasenia);
        this.saldoActual = saldoActual;
        this.saldoMinimo = saldoMinimo;
        this.estado = estado;
    }   
    
    public double getSaldoActual() {
        return saldoActual;
    }


    public double getSaldoMinimo() {
        return saldoMinimo;
    }


    public Estado getEstado() {
        return estado;
    }


    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }


    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }


    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void descontarSaldo(double monto) {
        this.saldoActual = this.saldoActual - monto;
    }

    public  void agregarTransito(Transito transito) {
        transitos.add(transito);
    }   
    



}
