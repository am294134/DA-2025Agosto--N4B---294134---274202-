package com.example.obligatorio_dda.Modelo;
import java.util.ArrayList;

public class SistemaAcceso {
    private ArrayList<Propietario> propietarios;
    private ArrayList<Administrador> administradores;

    public SistemaAcceso(ArrayList<Propietario> propietarios, ArrayList<Administrador> administradores) {
        this.propietarios = propietarios;
        this.administradores = administradores;
    }

    public void agregarPropietario(String nombre, String apellido, String cedula, String contrasenia,
                      double saldoActual, double saldoMinimo, Estado estado) {
        this.propietarios.add(new Propietario(nombre, apellido, cedula, contrasenia, saldoActual, saldoMinimo, estado)  );
    }

    public void agregarAdministrador(String nombre, String apellido, String cedula, String contrasenia) {
        this.administradores.add(new Administrador(nombre, apellido, cedula, contrasenia)  );
    }
    
    public Propietario loginPropietario(String name, String pass) throws PeajeException{
        Propietario prop = (Propietario)this.login(name, pass, this.propietarios);
        if(prop == null){
            throw new PeajeException("Usuario o contraseña incorrectos");
        }else{
            return prop;
        }
    }

    public Administrador loginAdministrador(String name, String pass) throws PeajeException {
      Administrador admin = (Administrador)this.login(name, pass, this.administradores);
      if (admin == null) {
         throw new PeajeException("Usuario o contraseña incorrectos");
      } else {
         return admin;
      }
   }

      private Usuario login(String cedula, String pwd, ArrayList lista) {
        Usuario usuario;
        
        for(Object o:lista) {
            usuario = (Usuario)o;
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
}