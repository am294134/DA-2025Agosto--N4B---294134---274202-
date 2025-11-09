package com.example.obligatorio_dda.Modelo;
public class Exonerada extends Bonificacion {
    public Exonerada(String nombre) {
        super("Exonerado");
    }

    @Override
    public double calcularDescuento(double monto) {
        // no pagan
        return 0.0;
    }

}
