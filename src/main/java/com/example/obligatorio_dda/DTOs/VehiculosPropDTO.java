package com.example.obligatorio_dda.DTOs;
import java.util.List;
import com.example.obligatorio_dda.Modelo.Estado;

//usamos esta clase para guardar sincronizadamente los datos del usuario junto a su lista de vehiculos
public class VehiculosPropDTO {
    private String nombreCompleto;
    private List<VehiculoDTO> vehiculos;
    private Estado estado;
    private double saldoActual;

    public VehiculosPropDTO() {}

    public VehiculosPropDTO(String nombreCompleto, List<VehiculoDTO> vehiculos) {
        this.nombreCompleto = nombreCompleto;
        this.vehiculos = vehiculos;
    }

    public VehiculosPropDTO(String nombreCompleto, List<VehiculoDTO> vehiculos, Estado estado, double saldoActual) {
        this.nombreCompleto = nombreCompleto;
        this.vehiculos = vehiculos;
        this.estado = estado;
        this.saldoActual = saldoActual;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public List<VehiculoDTO> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<VehiculoDTO> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }
}
