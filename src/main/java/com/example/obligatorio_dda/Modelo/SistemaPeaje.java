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

        // calcular monto a pagar aplicando la bonificación si corresponde
        double montoBase = tarifa.getMonto();
        Bonificacion bon = null;
        if (propietario != null) {
            for (Asignacion a : propietario.getAsignaciones()) {
                if (a != null && a.getPuesto() != null && a.getPuesto().getPeajeString().equals(puesto.getPeajeString())) {
                    bon = a.getBonificacion();
                    break;
                }
            }
        }
        double montoAPagar = (bon != null) ? bon.calcularDescuento(montoBase) : montoBase;

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

        // crear transito con la tarifa encontrada y la fecha/hora indicada
        Transito transito = (fecha != null)
                ? new Transito(puesto, vehiculo, propietario, tarifa, fecha)
                : new Transito(puesto, vehiculo, propietario, tarifa);
        // registrar los valores efectivos aplicados al tránsito (no deben cambiar si luego se asigna una bonificación)
        if (bon != null) {
            double montoPagado = montoAPagar;
            double descuento = montoBase - montoPagado;
            transito.setBonificacionNombre(bon.getNombre());
            transito.setMontoPagado(montoPagado);
            transito.setDescuentoAplicado(descuento);
        } else {
            transito.setBonificacionNombre(null);
            transito.setMontoPagado(montoAPagar);
            transito.setDescuentoAplicado(0.0);
        }

        // registrar en colecciones
        this.transitos.add(transito);
        puesto.getTransitos().add(transito);
        vehiculo.agregarTransito(transito);
        if (propietario != null) {
            propietario.agregarTransito(transito);
            // actualizar saldo del propietario
            try {
                propietario.descontarSaldo(montoAPagar);
            } catch (Exception ex) {
                // no bloquear el registro por fallo en actualizacion de saldo
            }
            // Crear una notificación y agregarla al propietario
            try {
                int numeroPuesto = -1;
                for (int i = 0; i < this.puestos.size(); i++) {
                    if (this.puestos.get(i) == puesto) {
                        numeroPuesto = i + 1; // numeración 1-based
                        break;
                    }
                }
                String nroTexto = (numeroPuesto > 0) ? String.valueOf(numeroPuesto) : "";
                String nombrePuesto = puesto != null ? puesto.getNombre() : "";
                String placa = vehiculo != null ? vehiculo.getMatricula() : "";
                String mensaje = "Pasaste por el puesto \"" + nombrePuesto + "\" " + (nroTexto.isEmpty() ? "" : ("#" + nroTexto + " ")) + "con el vehículo " + placa;
                Notificacion not = new Notificacion(mensaje, propietario);
                propietario.getNotificaciones().add(not);
            } catch (Exception ex) {
                // no impedir el flujo si falla la notificación
            }
        }
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
