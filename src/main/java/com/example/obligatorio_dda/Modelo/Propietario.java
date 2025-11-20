package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Propietario extends Usuario {
    private double saldoActual;
    private double saldoMinimo;
    private Estado estado;
    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();
    private ArrayList<Asignacion> asignaciones = new ArrayList<>();
    private ArrayList<Transito> transitos = new ArrayList<>();

    public Propietario(String nombre, String apellido, String cedula, String contrasenia,
            double saldoActual, double saldoMinimo, Estado estado) {
        super(nombre, apellido, cedula, contrasenia);
        this.saldoActual = saldoActual;
        this.saldoMinimo = saldoMinimo;
        this.estado = estado;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getSaldoMinimo() {
        return saldoMinimo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    // Copia ordenada de las notificaciones (recientes primero)
    public List<Notificacion> listarNotificacionesOrdenadasDesc() {
        List<Notificacion> copia = new ArrayList<>(notificaciones);
        copia.sort((a, b) -> b.getFechaHora().compareTo(a.getFechaHora()));
        return copia;
    }

    // Obtener una página de notificaciones ordenadas
    public List<Notificacion> obtenerNotificacionesPagina(int page, int pageSize) {
        List<Notificacion> ordenadas = listarNotificacionesOrdenadasDesc();
        if (pageSize <= 0)
            return ordenadas;
        int totalItems = ordenadas.size();
        int fromIndex = Math.max(0, Math.min(totalItems, (page - 1) * pageSize));
        int toIndex = Math.min(totalItems, fromIndex + pageSize);
        return ordenadas.subList(fromIndex, toIndex);
    }

    public int getTotalNotificaciones() {
        return notificaciones.size();
    }

    public int getTotalPagesFor(int pageSize) {
        if (pageSize <= 0)
            return 1;
        return Math.max(1, (int) Math.ceil((double) notificaciones.size() / pageSize));
    }

    public int getTotalNotificacionesNoLeidas() {
        int c = 0;
        for (Notificacion n : notificaciones)
            if (!n.isLeida())
                c++;
        return c;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    // devuelve la bon asignada al propietario para el puesto, sino null
    public Bonificacion getBonificacionEnPuesto(Puesto puesto) {
        // Si el propietario está penalizado no aplica bonificaciones
        if (this.estado != null && "Penalizado".equalsIgnoreCase(this.estado.getNombre())) {
            return null;
        }

        if (puesto == null || asignaciones == null)
            return null;

        for (Asignacion a : asignaciones) {
            if (a == null || a.getPuesto() == null)
                continue;
            if (a.getPuesto().getPeajeString().equals(puesto.getPeajeString())) {
                return a.getBonificacion();
            }
        }

        return null;
    }


    // calcula el monto a pagar en un puesto y si hay bon aploca descuento
    public double calcularMontoAPagarParaPuesto(Puesto puesto, double montoBase) {
        Bonificacion b = getBonificacionEnPuesto(puesto);
        if (b != null)
            return b.calcularDescuento(montoBase);
        return montoBase;
    }

    // Expert: calcular saldo luego de un pago (no modifica el saldo)
    public double calcularSaldoLuegoDePago(double montoAPagar) {
        return this.saldoActual - montoAPagar;
    }

    // verificamos si ya existe una asignación para un puesto
    public boolean existeAsignacionEnPuesto(Puesto puesto) {
        if (asignaciones == null)
            return false;

        for (Asignacion asg : asignaciones) {
            Puesto puestoExistente = asg.getPuesto();

            if (puesto == null && puestoExistente == null)
                return true;

            if (puesto != null && puestoExistente != null) {
                String p1 = puesto.getPeajeString();
                String p2 = puestoExistente.getPeajeString();
                if (p1 != null && p1.equals(p2))
                    return true;
            }
        }
        return false;
    }

    public void descontarSaldo(double monto) {
        this.saldoActual = this.saldoActual - monto;
    }

    public void agregarTransito(Transito transito) {
        transitos.add(transito);
    }

    public void registrarTransitoYAplicarPago(Transito transito, double montoAPagar) {
        // registrar tránsito en la lista del propietario
        agregarTransito(transito);
        descontarSaldo(montoAPagar);
    }

    public java.util.List<Transito> getTransitos() {
        return transitos;
    }

    public void cambiarEstado(Estado nuevoEstado) {
        this.estado = estado;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }
}
