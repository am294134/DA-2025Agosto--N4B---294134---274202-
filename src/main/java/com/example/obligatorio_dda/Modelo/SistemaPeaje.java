package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        Puesto puesto = null;
        // intentar encontrar por peajeString (id usado en selects)
        try {
            puesto = buscarPuestoPorId(nombrePuesto);
        } catch (PeajeException e) {
            // si no existe, intentar buscar por nombre simple (compatibilidad con Datos.cargar)
            for (Puesto p : this.puestos) {
                if (p.getNombre() != null && p.getNombre().equals(nombrePuesto)) {
                    puesto = p;
                    break;
                }
            }
            if (puesto == null) {
                throw new PeajeException("No existe el puesto: " + nombrePuesto);
            }
        }
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

    
    public void agregarVehiculo(String matricula, String color, String modelo, String nombreCategoria,
    String cedulaPropietario) throws PeajeException {
        Categoria categoria = null;
        for (Categoria c : this.categorias) {
            if (c.getNombre().equals(nombreCategoria)) {
                categoria = c;
            }
        }
        if (categoria == null) {
            throw new PeajeException("No existe la categoría: " + nombreCategoria);
        }

        Propietario propietario = null;
        for (Propietario p : this.propietarios) {
            if (p.getCedula().equals(cedulaPropietario)) {
                propietario = p;
            }
        }
        if (propietario == null) {
            throw new PeajeException("No existe el propietario con cédula: " + cedulaPropietario);
        }

        if (categoria == null) {
            throw new PeajeException("No existe la categoría: " + nombreCategoria);
        }

        Vehiculo vehiculo = new Vehiculo(matricula, color, modelo, categoria, propietario);
        vehiculos.add(vehiculo);
        propietario.agregarVehiculo(vehiculo);
    }

    
    public Puesto buscarPuestoPorId(String puestoId) throws PeajeException {
        for (Puesto p : puestos) {
            if (p.getPeajeString().equals(puestoId)) {
                return p;
            }
        }
        throw new PeajeException("No existe el puesto con id: " + puestoId);
    }
    
    public void agregarTransito(String puestoId, String matricula, String fechaHora) throws PeajeException {
        // parsear cadena y delegar a la versión que acepta LocalDateTime
        LocalDateTime fecha = null;
        if (fechaHora == null || fechaHora.trim().isEmpty()) {
            fecha = LocalDateTime.now();
        } else {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                fecha = LocalDateTime.parse(fechaHora, fmt);
            } catch (Exception ex) {
                throw new PeajeException("Formato de fecha inválido: " + fechaHora);
            }
        }
        agregarTransito(puestoId, matricula, fecha);
    }

   
    public void agregarTransito(String puestoId, String matricula, LocalDateTime fechaHora) throws PeajeException {
        // Normalizar y validar matrícula
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new PeajeException("Matrícula inválida");
        }
        Vehiculo vehiculo = buscarVehiculoPorMatricula(matricula);

        if (vehiculo == null) {
            throw new PeajeException("No existe el vehículo con matrícula: " + matricula);
        }

        if (puestoId == null || puestoId.trim().isEmpty()) {
            throw new PeajeException("Puesto inválido");
        }

        Puesto puesto = buscarPuestoPorId(puestoId);

        // buscar objeto Tarifa para la categoría del vehículo
        Tarifa tarifa = null;
        for (Tarifa t : puesto.getTarifas()) {
            if (t != null && t.getCategoria() != null && vehiculo.getCategoria() != null
                    && t.getCategoria().getNombre().equals(vehiculo.getCategoria().getNombre())) {
                tarifa = t;
                break;
            }
        }
        if (tarifa == null) {
            throw new PeajeException("No hay tarifa definida para la categoría del vehículo en este puesto");
        }

        Propietario propietario = vehiculo.getPropietario();
         if (propietario.getEstado() != null && "Deshabilitado".equalsIgnoreCase(propietario.getEstado().getNombre())) {
            throw new PeajeException("Usuario deshabilitado, no puede agregar transito");
        }
        if (propietario.getEstado() != null && "Suspendido".equalsIgnoreCase(propietario.getEstado().getNombre())) {
            throw new PeajeException("Usuario suspendido, no puede agregar transito");
        }

        // calculamos consultando al prop
        Bonificacion bon = (propietario != null) ? propietario.getBonificacionEnPuesto(puesto) : null;

        // crea el transito con la tarifa encontrada y la fecha/hora indicada; Transito crea y guarda los valores aplicados
        Transito transito = Transito.crearConValoresAplicados(puesto, vehiculo, propietario, tarifa, bon, fechaHora);

        // registrar en colecciones
        this.transitos.add(transito);
        puesto.getTransitos().add(transito);
        vehiculo.agregarTransito(transito);

            double montoAPagar = transito.getMontoPagado();
            propietario.registrarTransitoYAplicarPago(transito, montoAPagar);
    }
    
    public ArrayList<Transito> getTransitos() {
        return transitos;
    }
    
    public void agregarPuesto(String nombre, String ubicacion) {
        Puesto puesto = new Puesto(nombre, ubicacion);
        puestos.add(puesto);
    }
    
    public void agregarCategoria(String nombre) {
        Categoria categoria = new Categoria(nombre);
        categorias.add(categoria);
    }
    
    public void agregarPropietario(String nombre, String apellido, String cedula, String contrasenia,
    double saldoActual, double saldoMinimo, Estado estado) {
        Propietario propietario = new Propietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo, estado);
        if (propietario != null) {
            this.propietarios.add(propietario);
        }
    }

    //#region métodos auxiliares de búsqueda
    
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
