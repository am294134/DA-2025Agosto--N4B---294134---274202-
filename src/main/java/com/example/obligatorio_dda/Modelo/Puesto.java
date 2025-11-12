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

    public String getId() {
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

    public void agregarTransito(Transito transito) {
        transitos.add(transito);
    }   

    public double obtenerTarifaParaCategoria(Categoria categoria) throws PeajeException {
        for (Tarifa t : tarifas) {
            if (t.getCategoria().getNombre().equals(categoria.getNombre())) {
                return t.getMonto();
            }
        }
        throw new PeajeException("No hay tarifa definida para la categoría: " + categoria.getNombre() + " en el puesto: " + nombre);
    }
    

}
