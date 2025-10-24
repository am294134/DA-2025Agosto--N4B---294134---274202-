package com.example.obligatorio_dda.Controlador;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.obligatorio_dda.DTOs.VehiculoDTO;
import com.example.obligatorio_dda.DTOs.VehiculosPropDTO;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Vehiculo;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class ControladorVehiculos {

    @GetMapping("/listarVehiculos")
    public VehiculosPropDTO listarVehiculos(HttpSession sesion) throws Exception {
        // obtenemos session
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
        
        String nombreCompleto = propietario.getNombre() + " " + propietario.getApellido();
        return new VehiculosPropDTO(nombreCompleto, vehiculosDTO);
    }
}