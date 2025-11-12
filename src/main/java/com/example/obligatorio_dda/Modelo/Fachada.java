package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class Fachada {
    private static Fachada instancia = null;
    private SistemaAcceso sistemaAcceso;
    private SistemaPeaje sistemaPeaje;

    private Fachada() {
        this.sistemaAcceso = new SistemaAcceso(new ArrayList<>(), new ArrayList<>());
        this.sistemaPeaje = new SistemaPeaje(new ArrayList<Puesto>(), new ArrayList<Tarifa>(),
                new ArrayList<Categoria>(), new ArrayList<Bonificacion>(), new ArrayList<Vehiculo>(),
                new ArrayList<Propietario>());
        ;
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    // DELEGACIONES

    // #region logins
    public Propietario loginPropietario(String username, String password) throws PeajeException {
        return sistemaAcceso.loginPropietario(username, password);
    }

    public Administrador loginAdministrador(String username, String password) throws PeajeException {
        return sistemaAcceso.loginAdministrador(username, password);
    }
    // #endregion

    // #region agregados
    public void agregarAdministrador(String nombre, String apellido, String cedula, String contrasenia) {
        sistemaAcceso.agregarAdministrador(nombre, apellido, cedula, contrasenia);
    }

    public void agregarPropietario(String nombre, String apellido, String cedula, String contrasenia,
            double saldoActual, double saldoMinimo, Estado estado) {
    // Delegar la creación del propietario al sistema de acceso
    sistemaAcceso.agregarPropietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo, estado);
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

    public void agrgarBonificacion(Bonificacion bonificacion) {
        sistemaPeaje.agrgarBonificacion(bonificacion);
    }


    public void agregarVehiculo(String matricula, String color, String modelo, String nombreCategoria,
            String cedulaPropietario) throws PeajeException {
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
        for (Propietario p : sistemaPeaje.getPropietarios()) {
            if (p.getCedula().equals(cedulaPropietario)) {
                propietario = p;
                break;
            }
        }
        if (propietario == null) {
            throw new PeajeException("No existe el propietario con cédula: " + cedulaPropietario);
        }

        sistemaPeaje.agregarVehiculo(matricula, color, modelo, categoria, propietario);
    }
    

    

     public void agregarTransito(String puestoId, String matricula, String fechaHora) throws PeajeException {
     sistemaPeaje.agregarTransito(puestoId, matricula, fechaHora);
    }

    // public void agregarTransito(String puestoId, String matricula, String
    // fechaHora) throws PeajeException {
    // sistemaTransito.agregarTransito(puestoId, matricula, fechaHora);
    // }

    // #endregion

    // #region getters para listas
    public ArrayList<Puesto> getPuestos() {
        return sistemaPeaje.getPuestos();
    }

    public ArrayList<Categoria> getCategorias() {
        return sistemaPeaje.getCategorias();
    }

    public ArrayList<Tarifa> getTarifas() {
        return sistemaPeaje.getTarifas();
    }

    public ArrayList<Bonificacion> getBonificaciones() {
        return sistemaPeaje.getBonificaciones();
    }

    public ArrayList<Propietario> getPropietarios() {
        return sistemaAcceso.getPropietarios();
    }

    public ArrayList<Administrador> getAdministradores() {
        return sistemaAcceso.getAdministradores();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return sistemaPeaje.getVehiculos();
    }

    public ArrayList<Transito> getTransitos() {
        return sistemaPeaje.getTransitos();
    }
    // #endregion

}
