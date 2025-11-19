package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;
import java.time.LocalDateTime;
import com.example.obligatorio_dda.Observador.Observable;
import com.example.obligatorio_dda.Controlador.DTOs.TarifaDTO;

public class Fachada extends Observable {
    // singleton(como en el ejemplo del profesor)
    private static Fachada instancia = new Fachada();
    private SistemaAcceso sistemaAcceso;
    private SistemaPeaje sistemaPeaje;

    // eventos para avisar cambios relevantes
    public enum Eventos {
        propietarioAgregado,
        vehiculoAgregado, transitoRegistrado,
        estadoCambiado
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
        Propietario propietario = sistemaAcceso.agregarPropietario(nombre, apellido, cedula, contrasenia,
                saldoActual, saldoMinimo, estado);
        this.sistemaPeaje.getPropietarios().add(propietario);
        // notifica observadores que hay un nuevo propietario
        avisar(new Object[] { Eventos.propietarioAgregado, propietario });
    }

    public void agregarPuesto(String nombre, String ubicacion) {
        sistemaPeaje.agregarPuesto(nombre, ubicacion);
    }

    public void agregarCategoria(String nombre) {
        sistemaPeaje.agregarCategoria(nombre);
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
        // obtenemos transito creado y notificamso
        Transito t = sistemaPeaje.agregarTransito(puestoId, matricula, fechaHora);
        avisar(new Object[] { Eventos.transitoRegistrado, t });
    }

    public void agregarTransito(String puestoId, String matricula, LocalDateTime fechaHora) throws PeajeException {
        Transito t = sistemaPeaje.agregarTransito(puestoId, matricula, fechaHora);
        avisar(new Object[] { Eventos.transitoRegistrado, t });
    }

    public void agregarEstado(String nombreEstado) {
        sistemaAcceso.agregarEstado(nombreEstado);
    }

    public void cambiarEstado(String cedula, String estadoNombre) throws PeajeException {
        sistemaAcceso.cambiarEstado(cedula, estadoNombre);
        Propietario p = sistemaAcceso.buscarPropietarioPorCedula(cedula);
        avisar(new Object[] { Eventos.estadoCambiado, p });
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

    public Puesto buscarPuestoPorId(String puestoId) throws PeajeException {
        return sistemaPeaje.buscarPuestoPorId(puestoId);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws PeajeException {
        return sistemaPeaje.buscarVehiculoPorMatricula(matricula);
    }

    public Propietario buscarPropietarioPorCedula(String cedula) throws PeajeException {
        return sistemaAcceso.buscarPropietarioPorCedula(cedula);
    }

    public String buscarBonificacionNombreEnPuesto(Propietario prop, Puesto puesto) throws PeajeException {
        return sistemaPeaje.buscarBonificacionNombreEnPuesto(prop, puesto);
    }

    public ArrayList<TarifaDTO> obtenerTarifasPorPuesto(String puestoId) throws PeajeException {
        return sistemaPeaje.obtenerTarifasPorPuesto(puestoId);
    }

    public ArrayList<Vehiculo> obtenerVehiculosPropietario(Propietario propietario) {
        return sistemaPeaje.obtenerVehiculosPropietario(propietario);
    }   
}
