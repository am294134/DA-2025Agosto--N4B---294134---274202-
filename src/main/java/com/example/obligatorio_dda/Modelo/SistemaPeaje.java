package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.example.obligatorio_dda.Controlador.DTOs.TarifaDTO;

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
        Puesto puesto = buscarPuestoPorId(nombrePuesto);
        Categoria categoria = buscarCategoria(nombreCategoria);

        Tarifa tarifa = new Tarifa(monto, categoria);
        tarifas.add(tarifa);
        puesto.getTarifas().add(tarifa);
    }

    public void agrgarBonificacion(Bonificacion bonificacion) {
        bonificaciones.add(bonificacion);
    }


    public void agregarVehiculo(String matricula, String color, String modelo, String nombreCategoria,
            String cedulaPropietario) throws PeajeException {
        
        Categoria categoria = buscarCategoria(nombreCategoria); 
        Propietario propietario = buscarPropietarioPorCedula(cedulaPropietario);
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

    public Transito agregarTransito(String puestoId, String matricula, String fechaHora) throws PeajeException {
       //CAMBIO DE TIPO DE FECHA 
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
        return agregarTransito(puestoId, matricula, fecha);
    }

    public Transito agregarTransito(String puestoId, String matricula, LocalDateTime fechaHora) throws PeajeException {
        // Normalizar y validar matrícula
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new PeajeException("Matrícula inválida");
        }
        Vehiculo vehiculo = buscarVehiculoPorMatricula(matricula);

        if (vehiculo == null) {
            throw new PeajeException("No existe el vehículo con matrícula: " + matricula);
        }
        Puesto puesto = buscarPuestoPorId(puestoId);
        Tarifa tarifa = buscarTarifaEnPuesto(puesto, vehiculo.getCategoria());
        Propietario propietario = vehiculo.getPropietario();

        if (propietario.getEstado() != null && "Deshabilitado".equalsIgnoreCase(propietario.getEstado().getNombre())) {
            throw new PeajeException("Usuario deshabilitado, no puede agregar transito");
        }
        if (propietario.getEstado() != null && "Suspendido".equalsIgnoreCase(propietario.getEstado().getNombre())) {
            throw new PeajeException("Usuario suspendido, no puede agregar transito");
        }

        Bonificacion bon = propietario.getBonificacionEnPuesto(puesto);

        // crea el transito con la tarifa encontrada y la fecha/hora indicada; Transito
        Transito transito = Transito.crearConValoresAplicados(puesto, vehiculo, propietario, tarifa, bon, fechaHora);

        this.transitos.add(transito);
        puesto.getTransitos().add(transito);
        vehiculo.agregarTransito(transito);

        double montoAPagar = transito.getMontoPagado();
        propietario.registrarTransitoYAplicarPago(transito, montoAPagar);

        return transito;
    }

    private Tarifa buscarTarifaEnPuesto(Puesto puesto, Categoria categoria) throws PeajeException {
        for (Tarifa t : puesto.getTarifas()) {
            if (t.getCategoria() != null && categoria != null && t.getCategoria().getNombre().equals(categoria.getNombre())) {
                return t;
            }
        }
        throw new PeajeException("No hay tarifa definida para la categoría: " + (categoria==null?"(nula)":categoria.getNombre()) + " en el puesto: " + (puesto==null?"(nulo)":puesto.getNombre()));
    }

    public ArrayList<Vehiculo> obtenerVehiculosPropietario(Propietario propietario) {
        ArrayList<Vehiculo> lista = new ArrayList<>();
        if (propietario == null) return lista;
        for (Vehiculo v : vehiculos) {
            if (v.getPropietario() != null && v.getPropietario().getCedula() != null
                    && v.getPropietario().getCedula().equals(propietario.getCedula())) {
                lista.add(v);
            }
        }
        return lista;
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
        Propietario propietario = new Propietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo,
                estado);
        if (propietario != null) {
            this.propietarios.add(propietario);
        }
    }

    // #region métodos auxiliares de búsqueda

    private Categoria buscarCategoria(String nombreCategoria) throws PeajeException {
        for (Categoria c : categorias) {
            if (c.getNombre().equals(nombreCategoria)) {
                return c;
            }
        }
        throw new PeajeException("No existe la categoría: " + nombreCategoria);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws PeajeException {
    if (matricula == null) {
        throw new PeajeException("Matrícula inválida");
    }

    // Normalizar entrada
    String buscada = matricula.replaceAll("[^A-Za-z0-9]", "").toUpperCase();

    for (Vehiculo v : vehiculos) {
        if (v.getMatricula() != null) {
            String actual = v.getMatricula().replaceAll("[^A-Za-z0-9]", "").toUpperCase();
            if (actual.equals(buscada)) {
                return v;
            }
        }
    }

    throw new PeajeException("No existe el vehículo con matrícula: " + matricula);
}


    public String buscarBonificacionNombreEnPuesto(Propietario prop, Puesto puesto) throws PeajeException {
        if (prop == null || puesto == null) {
            return null;
        }
        Bonificacion bonificacion = prop.getBonificacionEnPuesto(puesto);
        if (bonificacion != null) {
            return bonificacion.getNombre();
        } else {
            return null;
        }
    }

    public Propietario buscarPropietarioPorCedula(String cedula) throws PeajeException {
        for (Propietario propietario : propietarios) {
            if (propietario.getCedula().equals(cedula)) {
                return propietario;
            }
        }
        throw new PeajeException("No se encontró el propietario con cédula: " + cedula);
    }   
    // #endregion

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
    
    public ArrayList<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    public ArrayList<TarifaDTO> obtenerTarifasPorPuesto(String puestoId) throws PeajeException {
        Puesto puesto = buscarPuestoPorId(puestoId);
        ArrayList<TarifaDTO> lista = new ArrayList<>();
        for (Tarifa t : puesto.getTarifas()) {
            String nombreCategoria = t.getCategoria().getNombre();
            lista.add(new TarifaDTO(t.getMonto(), nombreCategoria));
        }
        return lista;
    }

}