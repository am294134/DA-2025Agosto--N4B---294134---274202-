package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class SistemaTransito {
    private ArrayList<Transito> transitos;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Puesto> puestos;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<Categoria> categorias;
    private ArrayList<Propietario> propietarios;

    protected SistemaTransito(ArrayList<Transito> transitos, ArrayList<Vehiculo> vehiculos,
            ArrayList<Puesto> puestos, ArrayList<Tarifa> tarifas,
            ArrayList<Categoria> categorias, ArrayList<Propietario> propietarios) {
        this.transitos = transitos;
        this.vehiculos = vehiculos;
        this.puestos = puestos;
        this.tarifas = tarifas;
        this.categorias = categorias;
        this.propietarios = propietarios;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }

    public void agregarPuesto(Puesto puesto) {
        puestos.add(puesto);
    }

    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public void agregarVehiculo(String matricula, String color, String modelo, String nombreCategoria,
            String cedulaPropietario) throws PeajeException {
        // busca y valida que exista en sistema
        Propietario propietario = buscarPropietario(cedulaPropietario);
        if (propietario == null) {
            throw new PeajeException("No se encontró el propietario con cédula: " + cedulaPropietario);
        }

        // Busca cat
        Categoria categoria = buscarCategoria(nombreCategoria);

        // Crea veh
        Vehiculo vehiculo = new Vehiculo(matricula, color, modelo, categoria, propietario);

        // Agrega el vehículo a la lista general
        vehiculos.add(vehiculo);
        // Agrega el vehiculo a la lista del propietario (relación bidireccional)
        propietario.getVehiculos().add(vehiculo);
    }

    public void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) throws PeajeException {
        Puesto puesto = buscarPuesto(nombrePuesto);
        Categoria categoria = buscarCategoria(nombreCategoria);

        // creamos y agregamos tarifa
        Tarifa tarifa = new Tarifa(monto, categoria);
        tarifas.add(tarifa);
        puesto.getTarifas().add(tarifa);
    }

    // métodos pa no repetir código
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

    private Propietario buscarPropietario(String cedula) throws PeajeException {
        for (Propietario p : propietarios) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        throw new PeajeException("No existe el propietario con cédula: " + cedula);
    }
}
