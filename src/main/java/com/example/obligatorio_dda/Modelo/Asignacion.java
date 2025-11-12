package com.example.obligatorio_dda.Modelo;

import java.sql.Date;

public class Asignacion {
    private Date fechaAsignacion;
    private Bonificacion bonificacion;
    private Propietario propietario;
    private Puesto puesto;

    public Asignacion(Date fechaAsignacion, Bonificacion bonificacion, Propietario propietario, Puesto puesto) {
        this.fechaAsignacion = fechaAsignacion;
        this.bonificacion = bonificacion;
        this.propietario = propietario;
        this.puesto = puesto;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public Bonificacion getBonificacion() {
        return bonificacion;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public Puesto getPuesto() {
        return puesto;
    }
}
