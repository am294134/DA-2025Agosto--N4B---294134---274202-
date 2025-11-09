package com.example.obligatorio_dda.Modelo;
public class Trabajador extends Bonificacion {
    public Trabajador(String nombre) {
        super("Trabajador");
    }

    @Override
    public double calcularDescuento(double monto) {
        // 80% de descuento
        return monto * 0.2;
    }

}
