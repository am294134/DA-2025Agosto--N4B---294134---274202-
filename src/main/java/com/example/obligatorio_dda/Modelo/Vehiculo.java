package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Vehiculo {
    private String matricula;
    private String color;
    private String modelo;
    private Categoria categoria;
    private Propietario propietario;
    private ArrayList<Transito> transitos;
    
    public Vehiculo(String matricula, String color, String modelo, Categoria categoria, Propietario propietario) {
        this.matricula = matricula;
        this.color = color;
        this.modelo = modelo;
        this.categoria = categoria;
        this.propietario = propietario;
        this.transitos = new ArrayList<>();
        
    }

    public void agregarTransito(Transito transito) {
        transitos.add(transito);
    }   

    public String getMatricula() {
        return matricula;
    }

    public String getColor() {
        return color;
    }

    public String getModelo() {
        return modelo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    // Normaliza y compara matrículas de forma tolerante (quita no alfanuméricos y compara en mayúsculas)
    public boolean matchesMatriculaTolerant(String input) {
        if (input == null || this.matricula == null) return false;
        String a = this.matricula.replaceAll("[^A-Za-z0-9]", "").toUpperCase(java.util.Locale.ROOT);
        String b = input.replaceAll("[^A-Za-z0-9]", "").toUpperCase(java.util.Locale.ROOT);
        return a.equals(b);
    }
}