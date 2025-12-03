package com.example.obligatorio_dda.Modelo;

public abstract class Estado {
    private String nombre;
    //private Propietario propietario;

    public Estado(String nombre /*Propietario propietario*/) {
        this.nombre = nombre;
        /*this.propietario = propietario;*/
    }
/* 
    public abstract void Habilitado();
    public abstract void Deshabilitado();
    public abstract void Suspendido();
    public abstract void Penalizado();
    

    public abstract void ingresarAlSistema();
    public abstract void agregarTransito();
    public abstract void asignarBonificaciones();
    
   */ 

    public String getNombre() {
        return nombre;
    }
}