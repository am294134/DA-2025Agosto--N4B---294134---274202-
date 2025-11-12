package com.example.obligatorio_dda.Controlador.DTOs;

import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Tarifa;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Propietario;

public class TransitoEmuladoDTO {
    private Propietario propietario;
    private String categoria;
    private String bonificacion;
    private double costoDelTransito;
    private double saldoLuegoDelTransito;
   
    public void transitoEmuladoDTO(Puesto puesto, Vehiculo vehiculo, Propietario propietario) {
        this.propietario = propietario;
        this.categoria = vehiculo.getCategoria().getNombre();
        // this.bonificacion = propietario.getAsignaciones().getTipoBonificacion();  revisar
        // this.costoDelTransito = vehiculo.calcularTarifa(); revisar
        // this.saldoLuegoDelTransito = propietario.getSaldoDisponible() - costoDelTransito;  revisar
    }

}
