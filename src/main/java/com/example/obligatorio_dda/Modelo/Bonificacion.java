package com.example.obligatorio_dda.Modelo;
import java.util.ArrayList;

public abstract class Bonificacion {
    private String nombre;
    private ArrayList<Asignacion> asignaciones;
    private ArrayList<Transito> transitos;

    public Bonificacion(String nombre) {
        this.nombre = nombre;
        this.asignaciones = new ArrayList<>();
        this.transitos = new ArrayList<>();
    }

    public abstract double calcularDescuento(double monto);

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }
}
