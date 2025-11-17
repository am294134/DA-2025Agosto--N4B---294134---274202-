package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

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

    // Expert: devolver una copia ordenada de las notificaciones (recientes primero)
    public java.util.List<Notificacion> listarNotificacionesOrdenadasDesc() {
        java.util.List<Notificacion> copia = new java.util.ArrayList<>(notificaciones);
        copia.sort((a, b) -> b.getFechaHora().compareTo(a.getFechaHora()));
        return copia;
    }

    // Expert: obtener una página de notificaciones ordenadas
    public java.util.List<Notificacion> obtenerNotificacionesPagina(int page, int pageSize) {
        java.util.List<Notificacion> ordenadas = listarNotificacionesOrdenadasDesc();
        if (pageSize <= 0) return ordenadas;
        int totalItems = ordenadas.size();
        int fromIndex = Math.max(0, Math.min(totalItems, (page - 1) * pageSize));
        int toIndex = Math.min(totalItems, fromIndex + pageSize);
        return ordenadas.subList(fromIndex, toIndex);
    }

    public int getTotalNotificaciones() {
        return notificaciones.size();
    }

    public int getTotalPagesFor(int pageSize) {
        if (pageSize <= 0) return 1;
        return Math.max(1, (int) Math.ceil((double) notificaciones.size() / pageSize));
    }

    public int getTotalNotificacionesNoLeidas() {
        int c = 0;
        for (Notificacion n : notificaciones) if (!n.isLeida()) c++;
        return c;
    }


    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    // Expert: encontrar la bonificación (si existe) para un puesto dado
    public Bonificacion getBonificacionForPuesto(Puesto puesto) {
        if (puesto == null || asignaciones == null) return null;
        for (Asignacion a : asignaciones) {
            if (a == null || a.getPuesto() == null) continue;
            if (a.getPuesto().getPeajeString().equals(puesto.getPeajeString())) {
                return a.getBonificacion();
            }
        }
        return null;
    }

    // Expert: calcular el monto a pagar en un puesto dado aplicando la bonificación si corresponde
    public double calcularMontoAPagarParaPuesto(Puesto puesto, double montoBase) {
        Bonificacion b = getBonificacionForPuesto(puesto);
        if (b != null) return b.calcularDescuento(montoBase);
        return montoBase;
    }

    // Expert: calcular saldo luego de un pago (no modifica el saldo)
    public double calcularSaldoLuegoDePago(double montoAPagar) {
        return this.saldoActual - montoAPagar;
    }

    // Expert: verificar si ya existe una asignación para un puesto (acepta puesto null)
    public boolean hasAsignacionForPuestoNullable(Puesto puesto) {
        if (asignaciones == null) return false;
        for (Asignacion existente : asignaciones) {
            Puesto puestoExistente = existente.getPuesto();
            if (puesto == null && puestoExistente == null) return true;
            if (puesto != null && puestoExistente != null) {
                String p1 = puesto.getPeajeString();
                String p2 = puestoExistente.getPeajeString();
                if (p1 != null && p1.equals(p2)) return true;
            }
        }
        return false;
    }

    public void descontarSaldo(double monto) {
        this.saldoActual = this.saldoActual - monto;
    }

    public  void agregarTransito(Transito transito) {
        transitos.add(transito);
    }   
    public java.util.List<Transito> getTransitos() {
        return transitos;
    }
    



}
