package com.example.obligatorio_dda.Controlador;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.obligatorio_dda.Controlador.DTOs.VehiculoDTO;
import com.example.obligatorio_dda.Controlador.DTOs.VehiculosPropDTO;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Vehiculo;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class ControladorVehiculos {

    @PostMapping("/listarVehiculos")
    public java.util.List<Respuesta> listarVehiculos(HttpSession sesion) throws Exception {
        // obtenemos session
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            throw new Exception("No hay un propietario logueado");
        }

        // Convertimos los vehículos a DTO para enviar solo la información necesaria
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
        // incluir estado y saldoActual en la respuesta para que la vista pueda mostrarlos en el menú
        VehiculosPropDTO dto = new VehiculosPropDTO(nombreCompleto, vehiculosDTO, propietario.getEstado(), propietario.getSaldoActual());
        return Respuesta.lista(new Respuesta("vehiculosProp", dto));
    }
}