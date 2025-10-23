package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Fachada {
    private static Fachada instancia = null;
    private SistemaAcceso sistemaAcceso;
    private SistemaPeaje sistemaPeaje;
    private SistemaTransito sistemaTransito;

    private Fachada() {
        this.sistemaAcceso = new SistemaAcceso(new ArrayList<>(), new ArrayList<>());
        this.sistemaPeaje = new SistemaPeaje(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.sistemaTransito = new SistemaTransito(
            new ArrayList<>(), // transitos
            new ArrayList<>(), // vehiculos
            new ArrayList<>(), // puestos
            new ArrayList<>(), // tarifas
            new ArrayList<>(), // categorias
            new ArrayList<>()  // propietarios
        );
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }
    
    //DELEGACIONES
    
    //#region logins
    public Propietario loginPropietario(String username, String password) throws PeajeException {
           return sistemaAcceso.loginPropietario(username, password);
    }
    
    public Administrador loginAdministrador(String username, String password) throws PeajeException {
           return sistemaAcceso.loginAdministrador(username, password);
    }
    //#endregion

    //#region agregados
    public void agregarAdministrador(String nombre, String apellido, String cedula, String contrasenia) {
        sistemaAcceso.agregarAdministrador(nombre, apellido, cedula, contrasenia);
    }

    public void agregarPropietario(String nombre, String apellido, String cedula, String contrasenia,
                      double saldoActual, double saldoMinimo, Estado estado) {
        sistemaAcceso.agregarPropietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo, estado);
    }

    public void agregarPuesto(String nombre, String ubicacion) {
        sistemaTransito.agregarPuesto(new Puesto(nombre, ubicacion));
    }

    public void agregarCategoria(String nombre) {
        sistemaTransito.agregarCategoria(new Categoria(nombre));
    }

    public void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) throws PeajeException {
        sistemaTransito.agregarTarifa(nombrePuesto, nombreCategoria, monto);
    }

    public void agregarVehiculo(String matricula, String color, String modelo, String categoria, String cedulaPropietario) throws PeajeException {
        sistemaTransito.agregarVehiculo(matricula, color, modelo, categoria, cedulaPropietario);
    }

    //#endregion
    
    //#region getters para listas
    public ArrayList<Puesto> getPuestos() {
        return sistemaPeaje.getPuestos();
    }

    public ArrayList<Categoria> getCategorias() {
        return sistemaPeaje.getCategorias();
    }

    public ArrayList<Tarifa> getTarifas() {
        return sistemaPeaje.getTarifas();
    }

    public ArrayList<Propietario> getPropietarios() {
        return sistemaAcceso.getPropietarios();
    }

    public ArrayList<Administrador> getAdministradores() {
        return sistemaAcceso.getAdministradores();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return sistemaTransito.getVehiculos();
    }

    public ArrayList<Transito> getTransitos() {
        return sistemaTransito.getTransitos();
    }
    //#endregion

}
