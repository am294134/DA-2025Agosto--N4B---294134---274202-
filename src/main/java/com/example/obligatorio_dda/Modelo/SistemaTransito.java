package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class SistemaTransito {
    private ArrayList<Transito> transitos;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Puesto> puestos;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<Categoria> categorias;
    private ArrayList<Propietario> propietarios;

    public SistemaTransito(ArrayList<Transito> transitos, ArrayList<Vehiculo> vehiculos,
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
        //buscamoss ya validando que existan
        Categoria categoria = buscarCategoria(nombreCategoria);
        Propietario propietario = buscarPropietario(cedulaPropietario);
        
        // Creamos el veh
        Vehiculo vehiculo = new Vehiculo(matricula, modelo, color, categoria, propietario);
        
        // Agregamos el vehículo a la lista de v
        vehiculos.add(vehiculo);
        
        // agregamos el vehículo a la lista del prop, (bidireccional), para que agregar un vehículo si no es de un prop? 
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

 
    //métodos pa no repetir código
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

    private Propietario buscarPropietario(String cedulaPropietario) throws PeajeException {
        for (Propietario p : propietarios) {
            if (p.getCedula().equals(cedulaPropietario)) {
                return p;
            }
        }
        throw new PeajeException("No existe el propietario con cédula: " + cedulaPropietario);
    }
}
