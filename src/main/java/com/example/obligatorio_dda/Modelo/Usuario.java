package com.example.obligatorio_dda.Modelo;

public abstract class Usuario {
    private String nombre;
    private String apellido;
    private String cedula;
    private String contrasenia;

    public Usuario(String nombre, String apellido, String cedula, String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}
