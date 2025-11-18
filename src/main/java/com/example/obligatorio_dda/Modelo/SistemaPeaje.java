package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

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
        
        agregarVehiculo(matricula, color, modelo, categoria, propietario);

        vehiculos.add(vehiculo);

        propietario.agregarVehiculo(vehiculo);
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
        // Normalizar matrícula (si en el sistema las matriculas se guardaron con ciertos caracteres,
        // asumimos aquí que deben coincidir exactamente con la almacenada en la colección)
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new PeajeException("Matrícula inválida");
        }
        Vehiculo vehiculo = null;
        for (Vehiculo v : this.vehiculos) {
            if (v != null && v.getMatricula() != null && v.getMatricula().equals(matricula)) {
                vehiculo = v;
                break;
            }
        }
        if (vehiculo == null) {
            throw new PeajeException("No existe el vehículo con matrícula: " + matricula);
        }

        if (puestoId == null || puestoId.trim().isEmpty()) {
            throw new PeajeException("Puesto inválido");
        }
        Puesto puesto = buscarPuestoPorNombrePuesto(puestoId);

        // buscar la tarifa para la categoría del vehículo
        Tarifa tarifa = null;
        for (Tarifa t : puesto.getTarifas()) {
            if (t.getCategoria() != null && vehiculo.getCategoria() != null && t.getCategoria().getNombre().equals(vehiculo.getCategoria().getNombre())) {
                tarifa = t;
                break;
            }
        }
        if (tarifa == null) {
            throw new PeajeException("No hay tarifa definida para la categoría del vehículo en este puesto");
        }

        Propietario propietario = vehiculo.getPropietario();

        // calculamos consultando al prop
        Bonificacion bon = (propietario != null) ? propietario.getBonificacionEnPuesto(puesto) : null;

        // parsear la fecha/hora proporcionada (se espera formato yyyy-MM-dd'T'HH:mm)
        java.time.LocalDateTime fecha = null;
        if (fechaHora != null && !fechaHora.trim().isEmpty()) {
            try {
                java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                fecha = java.time.LocalDateTime.parse(fechaHora, fmt);
            } catch (Exception ex) {
                // si falla el parseo, dejamos la fecha null y el constructor del transito usará now
                fecha = null;
            }
        }

        // Si el propietario está suspendido, no permitimos registrar el tránsito
        if (propietario != null && propietario.getEstado() != null
                && "Suspendido".equals(propietario.getEstado().getNombre())) {
            throw new PeajeException("El propietario está suspendido,no puede realizar tránsitos");
        }

        // crea el transito con la tarifa encontrada y la fecha/hora indicada; Transito crea y guarda los valores aplicados
        Transito transito = Transito.crearConValoresAplicados(puesto, vehiculo, propietario, tarifa, bon, fecha);

        // registrar en colecciones
        this.transitos.add(transito);
        puesto.getTransitos().add(transito);
        vehiculo.agregarTransito(transito);

        if (propietario != null) {
            double montoAPagar = transito.getMontoPagado();
            
            // delegar al propietario la actualización de su estado (saldo, notificaciones, lista de tránsitos)
            propietario.registrarTransitoYAplicarPago(transito, montoAPagar);
        }
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
    
    public void agregarPropietario(Strin nombre, String apellido, String cedula, String contrasenia,
    double saldoActual, double saldoMinimo, Estado estado) {
        Propietario propietario = new Propietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo, estado);
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
