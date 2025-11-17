package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

import com.example.obligatorio_dda.Observador.Observable;

public class Fachada extends Observable {
    // singleton eager (como en el ejemplo del profesor)
    private static Fachada instancia = new Fachada();
    private SistemaAcceso sistemaAcceso;
    private SistemaPeaje sistemaPeaje;

    // eventos para avisar cambios relevantes
    public enum Eventos {
        propietarioAgregado,
        vehiculoAgregado
    }

    private Fachada() {
        this.sistemaAcceso = new SistemaAcceso();
        this.sistemaPeaje = new SistemaPeaje(new ArrayList<Puesto>(), new ArrayList<Tarifa>(),
                new ArrayList<Categoria>(), new ArrayList<Bonificacion>(), new ArrayList<Vehiculo>(),
                new ArrayList<Propietario>());
    }

    public static Fachada getInstancia() {
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
        // delegamos la creación al sistema de acceso y notificar al observador
        Propietario p = sistemaAcceso.agregarPropietario(nombre, apellido, cedula, contrasenia, saldoActual,
                saldoMinimo, estado);
        // registramos prop en sistema peaje
        sistemaPeaje.agregarPropietario(p);
        // avisamos a los observadores que se agrega prop
        avisar(new Object[] { Eventos.propietarioAgregado, p });
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
        sistemaPeaje.agregarVehiculo(matricula, color, modelo, nombreCategoria, cedulaPropietario);
        // vehiculo creado para notificar observadores
        Vehiculo v = sistemaPeaje.buscarVehiculoPorMatricula(matricula);
        avisar(new Object[] { Eventos.vehiculoAgregado, v });
    }

    public void agregarTransito(String puestoId, String matricula, String fechaHora) throws PeajeException {
        sistemaPeaje.agregarTransito(puestoId, matricula, fechaHora);
    }

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

    /**
     * Helper: buscar puesto por id/nombre delegando al SistemaPeaje.
     */
    public Puesto buscarPuestoPorId(String puestoId) throws PeajeException {
        // backward-compat: delegate to sistemaPeaje's search by name method
        return sistemaPeaje.buscarPuestoPorNombrePuesto(puestoId);
    }

    public Puesto buscarPuestoPorNombre(String nombre) throws PeajeException {
        return sistemaPeaje.buscarPuestoPorNombrePuesto(nombre);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws PeajeException {
        return sistemaPeaje.buscarVehiculoPorMatricula(matricula);
    }

    public Propietario buscarPropietarioPorCedula(String cedula) throws PeajeException {
        return sistemaAcceso.buscarPropietarioPorCedula(cedula);
    }

    public void cambiarEstado(String cedula, String estadoNombre) throws PeajeException {
        sistemaAcceso.cambiarEstado(cedula, estadoNombre);
    }
    
    public void agregarEstado(String nombreEstado) {
        sistemaAcceso.agregarEstado(new Estado(nombreEstado));
    }
}
