package com.example.obligatorio_dda.Modelo;
import java.util.ArrayList;

public class Puesto {
    private String nombre;
    private String direccion;
    private ArrayList<Transito> transitos;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<Asignacion> asignaciones;

    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.transitos = new ArrayList<>();
        this.tarifas = new ArrayList<>();
        this.asignaciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPeajeString() {
        return nombre + "-" + direccion;
    }   

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    
}
