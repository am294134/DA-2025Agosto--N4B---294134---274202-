package com.example.obligatorio_dda.Modelo;

public class Deshabilitado extends Estado {


    public Deshabilitado(String string/*Propietario propietario */) {
        super("Deshabilitado"/*, propietario*/);
    }

    /* 
    @Override
    public  void Habilitado(){
    getPropietario().cambiarEstado(new Habilitado(getPropietario()));
    }

    public abstract void Deshabilitado(){throws PeajeExcpetion
     thor new PeajeException "EL estado ya es deshabilitado"
    };

    public abstract void Suspendido(){
    getPropietario().cambiarEstado(new Suspendido(getPropietario()));
    };

    public  void Penalizado(){
    getPropietario().cambiarEstado(new Penalizado(getPropietario()));
    };
    

    public  void ingresarAlSistema(){
    throw new AgendaException("El peaje esta deshabilitado. no puede ingresar al sistema");
    };

    public  void agregarTransito(){
    throw new AgendaException("El peaje esta deshabilitado. no puede agregar transito");};

    public  void asignarBonificaciones(){
    throw new AgendaException("El peaje esta deshabilitado. no puede agregar bonificaciones");};

    */
}
