package com.example.Modelo;

import java.util.ArrayList;

public class SistemaAcceso {
    private ArrayList<Propietario> propietarios;
    private ArrayList<Administrador> administradores;

    public SistemaAcceso(ArrayList<Propietario> propietarios, ArrayList<Administrador> administradores) {
        this.propietarios = propietarios;
        this.administradores = administradores;
    }

    public void loginAdmin(String name, String pass) throws PeajeException{
       login(name, pass, administradores);
       throw new PeajeException("Login incorrecto");
    }

    private Administrador login(String name, String pass, ArrayList lista){
        Administrador userAdmin;
        for(Object o:lista){
            userAdmin = (Administrador) o;
            if(userAdmin.getNombre().equals(name) && userAdmin.getContrasenia().equals(pass)){
                return userAdmin;
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
}