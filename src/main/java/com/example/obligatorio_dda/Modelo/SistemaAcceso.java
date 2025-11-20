package com.example.obligatorio_dda.Modelo;

import java.util.ArrayList;

public class SistemaAcceso {
    private ArrayList<Propietario> propietarios;
    private ArrayList<Administrador> administradores;
    private ArrayList<Estado> estados;

    protected SistemaAcceso(ArrayList<Propietario> propietarios, ArrayList<Administrador> administradores, ArrayList<Estado> estados) {
        this.propietarios = propietarios;
        this.administradores = administradores;
        this.estados = estados;
    }

    public SistemaAcceso() {
        this.propietarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.estados = new ArrayList<>();
    }

    public Propietario agregarPropietario(String nombre, String apellido, String cedula, String contrasenia,
            double saldoActual, double saldoMinimo, Estado estado) {
        Propietario propietario = new Propietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo,
                estado);
        this.propietarios.add(propietario);
        return propietario;
    }

    public void agregarAdministrador(String nombre, String apellido, String cedula, String contrasenia) {
        this.administradores.add(new Administrador(nombre, apellido, cedula, contrasenia));
    }

    public Propietario loginPropietario(String name, String pass) throws PeajeException {
        Propietario prop = (Propietario) this.login(name, pass, this.propietarios);
        if (prop == null) {
            throw new PeajeException("Usuario o contraseña incorrectos");
        }
        if (prop.getEstado() != null && "Deshabilitado".equalsIgnoreCase(prop.getEstado().getNombre())) {
            throw new PeajeException("Usuario deshabilitado, no puede ingresar al sistema");
        }
        return prop;
    }

    public Administrador loginAdministrador(String name, String pass) throws PeajeException {
        Administrador admin = (Administrador) this.login(name, pass, this.administradores);
        if (admin == null) {
            throw new PeajeException("Usuario o contraseña incorrectos");
        } else {
            return admin;
        }
    }

    private Usuario login(String cedula, String pwd, ArrayList lista) {
        Usuario usuario;

        for (Object o : lista) {
            usuario = (Usuario) o;
            boolean cedulaMatches = usuario.getCedula() != null && usuario.getCedula().equals(cedula);
            if ((cedulaMatches) && usuario.getContrasenia().equals(pwd)) {
                return usuario;
            }
        }
        return null;
    }

    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }

    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }

    public Propietario buscarPropietarioPorCedula(String cedula) throws PeajeException {
        if (cedula == null) {
            throw new PeajeException("No se encontró el propietario con cédula: null");
        }
        String buscada = cedula.trim().replaceAll("\\D", "");
        for (Propietario propietario : propietarios) {
            String cedProp = propietario.getCedula() == null ? "" : propietario.getCedula().trim().replaceAll("\\D", "");
            if (cedProp.equals(buscada)) {
                return propietario;
            }
        }
        throw new PeajeException("Acceso Denegado");
    }

    public void cambiarEstado(String cedula, String nombreEstado) throws PeajeException {
        Propietario propietario = buscarPropietarioPorCedula(cedula);
        Estado estado = buscarEstadoPorNombre(nombreEstado);
        propietario.cambiarEstado(estado);
        
    }

    public Estado buscarEstadoPorNombre(String nombreEstado) throws PeajeException {
        for (Estado estado : estados) {
            if (estado.getNombre().equalsIgnoreCase(nombreEstado)) {
                return estado;
            }
        }
        throw new PeajeException("No se encontró el estado con nombre: " + nombreEstado);
    }

    public void agregarEstado(String nombre) {
        Estado estado = new Estado(nombre);
        this.estados.add(estado);
    }
 } 