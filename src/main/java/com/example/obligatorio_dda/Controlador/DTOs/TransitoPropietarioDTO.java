package com.example.obligatorio_dda.Controlador.DTOs;

public class TransitoPropietarioDTO {
    private String puesto;
    private String matricula;
    private Double montoBase;
    private String bonificacion;
    private Double descuento; // monto de descuento (positivo, se puede mostrar con signo - en UI)
    private Double montoPagado;
    private String fechaHora; // formateada

    public TransitoPropietarioDTO() {}

    public TransitoPropietarioDTO(String puesto, String matricula, Double montoBase, String bonificacion, Double descuento, Double montoPagado, String fechaHora) {
        this.puesto = puesto;
        this.matricula = matricula;
        this.montoBase = montoBase;
        this.bonificacion = bonificacion;
        this.descuento = descuento;
        this.montoPagado = montoPagado;
        this.fechaHora = fechaHora;
    }



    public String getPuesto() { return puesto; }
    public String getMatricula() { return matricula; }
    public Double getMontoBase() { return montoBase; }
    public String getBonificacion() { return bonificacion; }
    public Double getDescuento() { return descuento; }
    public Double getMontoPagado() { return montoPagado; }
    public String getFechaHora() { return fechaHora; }

    public void setPuesto(String puesto) { this.puesto = puesto; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setMontoBase(Double montoBase) { this.montoBase = montoBase; }
    public void setBonificacion(String bonificacion) { this.bonificacion = bonificacion; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }
    public void setMontoPagado(Double montoPagado) { this.montoPagado = montoPagado; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
}
