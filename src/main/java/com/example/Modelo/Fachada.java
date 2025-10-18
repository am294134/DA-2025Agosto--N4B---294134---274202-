package com.example.Modelo;

import java.util.ArrayList;

public class Fachada {

    private static Fachada instancia;
    private SistemaAcceso sistemaAcceso;
    private SistemaPeaje sistemaPeaje;
    private SistemaTransito sistemaTransito;

    private Fachada() {
        inicializarSistemas();
    }

    private void inicializarSistemas() {
        Datos.cargar();
        ArrayList<Propietario> propietarios = new ArrayList<>(Datos.getPropietarios());
        ArrayList<Administrador> administradores = new ArrayList<>(Datos.getAdministradores());

        // Puestos y categorias directamente desde Datos
        ArrayList<Puesto> puestos = new ArrayList<>(Datos.getPuestos());
        ArrayList<Categoria> categorias = new ArrayList<>(Datos.getCategorias());

        // Tarifas: se obtienen agregando las tarifas de cada puesto
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        for (Puesto p : puestos) {
            if (p.getTarifas() != null) {
                tarifas.addAll(p.getTarifas());
            }
        }

        // Vehículos: como Datos no expone un getter global para vehiculos, los recopilamos de los propietarios
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        for (Propietario prop : propietarios) {
            if (prop.getVehiculos() != null) {
                vehiculos.addAll(prop.getVehiculos());
            }
        }

        // Transitos: no hay precarga explícita, inicializar vacío
        ArrayList<Transito> transitos = new ArrayList<>();

        // Crear instancias de los sistemas
        this.sistemaAcceso = new SistemaAcceso(propietarios, administradores);
        this.sistemaPeaje = new SistemaPeaje(puestos, tarifas, categorias);
        this.sistemaTransito = new SistemaTransito(transitos, vehiculos, puestos, tarifas, categorias, propietarios);
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    public void loginPropietario(String username, String password) throws PeajeException {
            sistemaAcceso.loginPropietario(username, password);
    }

    public void loginAdministrador(String username, String password) throws PeajeException {
            sistemaAcceso.loginAdministrador(username, password);
    }

    // Delegaciones para acceder a datos de los sistemas
    public ArrayList<Puesto> getPuestos() {
        return sistemaPeaje.getPuestos();
    }

    public ArrayList<Categoria> getCategorias() {
        return sistemaPeaje.getCategorias();
    }

    public ArrayList<Tarifa> getTarifas() {
        return sistemaPeaje.getTarifas();
    }

    public ArrayList<Propietario> getPropietarios() {
        return sistemaAcceso.getPropietarios();
    }

    public ArrayList<Administrador> getAdministradores() {
        return sistemaAcceso.getAdministradores();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return sistemaTransito.getVehiculos();
    }

    public ArrayList<Transito> getTransitos() {
        return sistemaTransito.getTransitos();
    }

    /**
     * Vuelve a cargar los datos desde Datos y reconstruye las instancias de los sistemas.
     */
    public void recargarDatos() {
        inicializarSistemas();
    }
}
