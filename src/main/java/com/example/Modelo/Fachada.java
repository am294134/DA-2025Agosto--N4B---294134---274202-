package com.example.Modelo;

public class Fachada {
    //Cuando creemos las clases vamos a tener que hacerlo sin singleton
    //A los métodos de los SISTEMAS, ingresamos con Fachada.

    private static Fachada instancia;

    private Fachada() {
        // sistemas
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    /* 
    DELEGACIONES (Nunca lógica)
    public List<Cliente> getClientes() {
        return SistemaClientes.getInstancia().getClientes();
    }

    Generar métodos con source action

    */


}
