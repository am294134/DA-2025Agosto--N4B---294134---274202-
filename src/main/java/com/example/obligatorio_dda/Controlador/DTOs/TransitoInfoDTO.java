package com.example.obligatorio_dda.Controlador.DTOs;

public class TransitoInfoDTO {
    private String propietarioNombre;
    private String categoria;
    private String bonificacion;
    private Double monto; // costo del tránsito (ya con bonificación aplicada si corresponde)
    private Double saldoLuegoDelTransito;

    public TransitoInfoDTO() {
    }

    public TransitoInfoDTO(String propietarioNombre, String categoria, String bonificacion) {
        this.propietarioNombre = propietarioNombre;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.monto = null;
        this.saldoLuegoDelTransito = null;
    }

    public TransitoInfoDTO(String propietarioNombre, String categoria, String bonificacion, Double monto, Double saldoLuegoDelTransito) {
        this.propietarioNombre = propietarioNombre;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.monto = monto;
        this.saldoLuegoDelTransito = saldoLuegoDelTransito;
    }

    public String getPropietarioNombre() {
        return propietarioNombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getBonificacion() {
        return bonificacion;
    }

    public Double getMonto() {
        return monto;
    }

    public Double getSaldoLuegoDelTransito() {
        return saldoLuegoDelTransito;
    }

    public void setPropietarioNombre(String propietarioNombre) {
        this.propietarioNombre = propietarioNombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setBonificacion(String bonificacion) {
        this.bonificacion = bonificacion;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setSaldoLuegoDelTransito(Double saldoLuegoDelTransito) {
        this.saldoLuegoDelTransito = saldoLuegoDelTransito;
    }
}
