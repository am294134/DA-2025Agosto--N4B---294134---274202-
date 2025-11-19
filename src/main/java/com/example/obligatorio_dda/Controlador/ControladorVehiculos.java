package com.example.obligatorio_dda.Controlador;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.obligatorio_dda.Controlador.DTOs.VehiculoDTO;
import com.example.obligatorio_dda.Controlador.DTOs.VehiculosPropDTO;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Fachada;

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
            return Respuesta.lista(new Respuesta("redirLoginPropietario", "login-propietario.html"));
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
        VehiculosPropDTO vdto = new VehiculosPropDTO(nombreCompleto, vehiculosDTO, propietario.getEstado(), propietario.getSaldoActual());
        return Respuesta.lista(new Respuesta("vehiculosProp", vdto));
    }
}