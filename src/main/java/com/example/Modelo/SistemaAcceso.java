package com.example.Modelo;

import java.util.ArrayList;

public class SistemaAcceso {
    private ArrayList<Propietario> propietarios;
    private ArrayList<Administrador> administradores;

    public SistemaAcceso(ArrayList<Propietario> propietarios, ArrayList<Administrador> administradores) {
        this.propietarios = propietarios;
        this.administradores = administradores;
    }

    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }

    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }
}