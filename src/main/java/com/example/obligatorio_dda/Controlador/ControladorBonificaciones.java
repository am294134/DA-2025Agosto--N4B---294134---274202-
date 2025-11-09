package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Controlador.DTOs.BonificacionAsignadaDTO;
import com.example.obligatorio_dda.Modelo.Asignacion;
import com.example.obligatorio_dda.Modelo.Bonificacion;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Propietario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bonificaciones")
public class ControladorBonificaciones {

    @GetMapping("/porPropietario")
    public List<BonificacionAsignadaDTO> listarPorPropietario(HttpSession sesion) throws Exception {
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            throw new Exception("No hay un propietario logueado");
        }

        List<BonificacionAsignadaDTO> bonis = new ArrayList<>();

        // recorremos las bonificaciones
        for (Bonificacion bonificaciones : Fachada.getInstancia().getBonificaciones()) {
            // recorremos las asignaciones
            for (Asignacion asigns : bonificaciones.getAsignaciones()) {
                // buscamos las que pertenecen al propietario logueado y lo mandamos para el DTO
                if (asigns.getPropietario() != null && asigns.getPropietario().getCedula().equals(propietario.getCedula())) {
                    String nombreBon = bonificaciones.getNombre();
                    String nombrePuesto = asigns.getPuesto() != null ? asigns.getPuesto().getNombre() : "";
                    String fecha = asigns.getFechaAsignacion() != null ? asigns.getFechaAsignacion().toString() : "";
                    bonis.add(new BonificacionAsignadaDTO(nombreBon, nombrePuesto, fecha));
                }
            }
        }

        return bonis;
    }

    @GetMapping("/listarTipos")
    public List<String> listarTipos() {
        List<String> tipos = new ArrayList<>();
        for (Bonificacion b : Fachada.getInstancia().getBonificaciones()) {
            tipos.add(b.getNombre());
        }
        return tipos;
    }

}
