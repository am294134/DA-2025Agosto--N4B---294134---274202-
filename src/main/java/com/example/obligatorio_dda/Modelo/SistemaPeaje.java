package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class SistemaPeaje {
    private ArrayList<Puesto> puestos;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<Categoria> categorias;
    private ArrayList<Bonificacion> bonificaciones;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Propietario> propietarios;
    private ArrayList<Transito> transitos;

    protected SistemaPeaje(ArrayList<Puesto> puestos, ArrayList<Tarifa> tarifas, ArrayList<Categoria> categorias,
            ArrayList<Bonificacion> bonificaciones, ArrayList<Vehiculo> vehiculos,
            ArrayList<Propietario> propietarios) {
        this.puestos = puestos;
        this.tarifas = tarifas;
        this.categorias = categorias;
        this.bonificaciones = bonificaciones;
        this.vehiculos = new ArrayList<>();
        this.propietarios = new ArrayList<>();
        this.transitos = new ArrayList<>();
    }

    public SistemaPeaje() {
        this.puestos = new ArrayList<>();
        this.tarifas = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.propietarios = new ArrayList<>();
        this.transitos = new ArrayList<>();
    }

    public void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) throws PeajeException {
        Puesto puesto = buscarPuesto(nombrePuesto);
        Categoria categoria = buscarCategoria(nombreCategoria);

        // creamos y agregamos tarifa
        Tarifa tarifa = new Tarifa(monto, categoria);
        tarifas.add(tarifa);
        puesto.getTarifas().add(tarifa);
    }

    public void agrgarBonificacion(Bonificacion bonificacion) {
        bonificaciones.add(bonificacion);
    }

    public ArrayList<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    
    public void agregarVehiculo(String matricula, String color, String modelo, Categoria categoria,
    Propietario propietario) {
        // Crea veh
        Vehiculo vehiculo = new Vehiculo(matricula, color, modelo, categoria, propietario);
        
        // agrega el vehículo a la lista general
        vehiculos.add(vehiculo);
        // agrega el vehiculo a la lista del propietario (relación bidireccional)
        propietario.getVehiculos().add(vehiculo);
    }
    
    public void agregarVehiculo(String matricula, String color, String modelo, String nombreCategoria,
    String cedulaPropietario) throws PeajeException {
        Categoria categoria = null;
        for (Categoria c : this.categorias) {
            if (c.getNombre().equals(nombreCategoria)) {
                categoria = c;
                break;
            }
        }
        if (categoria == null) {
            throw new PeajeException("No existe la categoría: " + nombreCategoria);
        }

        Propietario propietario = null;
        for (Propietario p : this.propietarios) {
            if (p.getCedula().equals(cedulaPropietario)) {
                propietario = p;
                break;
            }
        }
        if (propietario == null) {
            throw new PeajeException("No existe el propietario con cédula: " + cedulaPropietario);
        }
        
        agregarVehiculo(matricula, color, modelo, categoria, propietario);
    }

    
    public Puesto buscarPuestoPorNombrePuesto(String puestoNombre) throws PeajeException {
        for (Puesto p : puestos) {
            if (p.getPeajeString().equals(puestoNombre)) {
                return p;
            }
        }
        throw new PeajeException("No existe el puesto con nombre: " + puestoNombre);
    }
    
    public void agregarTransito(String puestoId, String matricula, String fechaHora) throws PeajeException {
        // Vehiculo vehiculo = buscarVehiculoPorMatricula(matricula);
        // Puesto puesto = buscarPuestoPorId(puestoId);
        // Propietario propietario = vehiculo.getPropietario();  
        
        
        // double tarifa = puesto.obtenerTarifaParaCategoria(vehiculo.getCategoria());
        // Transito transito = new Transito(puesto, tarifa, vehiculo, propietario, fechaHora );
        // vehiculo.agregarTransito(transito);   
        // propietario.agregarTransito(transito);
    }
    
    public ArrayList<Transito> getTransitos() {
        return transitos;
    }
    
    public void agregarPuesto(Puesto puesto) {
        puestos.add(puesto);
    }
    
    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }
    
    public void agregarPropietario(Propietario propietario) {
        if (propietario != null) {
            this.propietarios.add(propietario);
        }
    }

    //#region métodos auxiliares de búsqueda
    private Puesto buscarPuesto(String nombrePuesto) throws PeajeException {
        for (Puesto p : puestos) {
            if (p.getNombre().equals(nombrePuesto)) {
                return p;
            }
        }
        throw new PeajeException("No existe el puesto: " + nombrePuesto);
    }
    
    private Categoria buscarCategoria(String nombreCategoria) throws PeajeException {
        for (Categoria c : categorias) {
            if (c.getNombre().equals(nombreCategoria)) {
                return c;
            }
        }
        throw new PeajeException("No existe la categoría: " + nombreCategoria);
    }
    
    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws PeajeException {
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula().equals(matricula)) {
                return v;
            }
        }
        throw new PeajeException("No existe el vehículo con matrícula: " + matricula);
    }
    //#endregion
    
    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }
    
    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }
    
    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }
    
    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }
    
}
