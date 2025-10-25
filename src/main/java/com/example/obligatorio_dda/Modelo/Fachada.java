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
        Propietario propietario = sistemaAcceso.agregarPropietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo, estado);
        // sincroniza lista con SistemaTransito
        sistemaTransito.getPropietarios().add(propietario);
    }

    public void agregarPuesto(String nombre, String ubicacion) {
        sistemaPeaje.agregarPuesto(new Puesto(nombre, ubicacion));
    }

    public void agregarCategoria(String nombre) {
        sistemaPeaje.agregarCategoria(new Categoria(nombre));
    }

    public void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) throws PeajeException {
        sistemaPeaje.agregarTarifa(nombrePuesto, nombreCategoria, monto);
    }

    public void agregarVehiculo(String matricula, String color, String modelo, String nombreCategoria, String cedulaPropietario) throws PeajeException {
        Categoria categoria = null;
        for (Categoria c : sistemaPeaje.getCategorias()) {
            if (c.getNombre().equals(nombreCategoria)) {
                categoria = c;
                break;
            }
        }
        if (categoria == null) {
            throw new PeajeException("No existe la categoría: " + nombreCategoria);
        }

        Propietario propietario = null;
        for (Propietario p : sistemaTransito.getPropietarios()) {
            if (p.getCedula().equals(cedulaPropietario)) {
                propietario = p;
                break;
            }
        }
        if (propietario == null) {
            throw new PeajeException("No existe el propietario con cédula: " + cedulaPropietario);
        }

        sistemaTransito.agregarVehiculo(matricula, color, modelo, categoria, propietario);
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
