package com.example.Modelo;

import java.util.ArrayList;

public class SistemaAcceso {
    private ArrayList<Propietario> propietarios;
    private ArrayList<Administrador> administradores;

    public SistemaAcceso(ArrayList<Propietario> propietarios, ArrayList<Administrador> administradores) {
        this.propietarios = propietarios;
        this.administradores = administradores;
    }

    public void loginPropietario(String name, String pass) throws PeajeException{
       login(name, pass, propietarios);
       throw new PeajeException("Usuario o contraseña incorrectos");
       
    }
     public void loginAdministrador(String name, String pass) throws PeajeException{
       login(name, pass, administradores);
       throw new PeajeException("Usuario o contraseña incorrectos");
    }
    
     private Usuario login(String name, String pass, ArrayList lista){
        Usuario usuario;
        
        for(Object o:lista){
            usuario = (Usuario)o;
            if(usuario.getNombre().equals(name) && usuario.getContrasenia().equals(pass)){
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
}