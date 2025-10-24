package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Vehiculo;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class ControladorVehiculos {

    @GetMapping("/listarVehiculos")
    public List<VehiculoDTO> listarVehiculos(HttpSession sesion) throws Exception {
        // Obtener el propietario de la sesión
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            throw new Exception("No hay un propietario logueado");
        }

        // Convertir los vehículos a DTO para enviar solo la información necesaria
        List<VehiculoDTO> vehiculosDTO = new ArrayList<>();
        for (Vehiculo v : propietario.getVehiculos()) {
            vehiculosDTO.add(new VehiculoDTO(
                v.getMatricula(),
                v.getModelo(),
                v.getColor(),
                v.getCategoria().getNombre()
            ));
        }
        return vehiculosDTO;
    }

    // Clase DTO (Data Transfer Object) para enviar solo los datos necesarios al frontend
    private static class VehiculoDTO {
        private String matricula;
        private String modelo;
        private String color;
        private String categoria;

        public VehiculoDTO(String matricula, String modelo, String color, String categoria) {
            this.matricula = matricula;
            this.modelo = modelo;
            this.color = color;
            this.categoria = categoria;
        }

        public String getMatricula() { return matricula; }
        public String getModelo() { return modelo; }
        public String getColor() { return color; }
        public String getCategoria() { return categoria; }
    }
}