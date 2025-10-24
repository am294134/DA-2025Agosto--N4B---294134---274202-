package com.example.obligatorio_dda.DTOs;
import java.util.List;

//usamos esta clase para guardar sincronizadamente los datos del usuario junto a su lista de vehiculos
public class VehiculosPropDTO {
    private String nombreCompleto;
    private List<VehiculoDTO> vehiculos;

    public VehiculosPropDTO() {}

    public VehiculosPropDTO(String nombreCompleto, List<VehiculoDTO> vehiculos) {
        this.nombreCompleto = nombreCompleto;
        this.vehiculos = vehiculos;
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
}
