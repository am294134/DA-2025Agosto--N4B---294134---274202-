package com.example.Modelo;

import java.util.ArrayList;

public class Propietario {
    private String nombre;
    private String apellido;
    private String contrasenia;
    private String cedula;
    private double saldoActual;
    private double saldoMinimo;
    private Estado estado;
    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();
    private ArrayList<Asignacion> asignaciones = new ArrayList<>();
    
    
    public Propietario(String nombre, String apellido, String contrasenia, String cedula, double saldoActual,
            double saldoMinimo, Estado estado, ArrayList<Vehiculo> vehiculos, ArrayList<Notificacion> notificaciones,
            ArrayList<Asignacion> asignaciones) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.cedula = cedula;
        this.saldoActual = saldoActual;
        this.saldoMinimo = saldoMinimo;
        this.estado = estado;
        this.vehiculos = vehiculos;
        this.notificaciones = notificaciones;
        this.asignaciones = asignaciones;
    }


    public String getNombre() {
        return nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public String getContrasenia() {
        return contrasenia;
    }


    public String getCedula() {
        return cedula;
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

    



}
