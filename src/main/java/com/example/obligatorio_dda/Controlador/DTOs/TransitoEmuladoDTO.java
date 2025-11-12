package com.example.obligatorio_dda.Controlador.DTOs;

import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Propietario;

public class TransitoEmuladoDTO {
    private Propietario propietario;
    private String categoria;
    private String bonificacion;
    private double costoDelTransito;
    private double saldoLuegoDelTransito;
   
    public transitoEmuladoDTO(Puesto puesto, Vehiculo vehiculo, Propietario propietario) {
        this.propietario = propietario;
        this.categoria = vehiculo.getCategoria().getNombre();
        this.bonificacion = propietario.getBonificacion().getTipoBonificacion();
        this.costoDelTransito = vehiculo.calcularTarifa();
        this.saldoLuegoDelTransito = propietario.getSaldoDisponible() - costoDelTransito;
    }

}
