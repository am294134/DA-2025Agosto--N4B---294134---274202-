package com.example.obligatorio_dda.Modelo;
public class Frecuente extends Bonificacion {
    public Frecuente(String nombre) {
        super("Frecuente");
    }

    @Override
    public double calcularDescuento(double monto) {
        // 50% descuento
        return monto * 0.5;
    }

}
