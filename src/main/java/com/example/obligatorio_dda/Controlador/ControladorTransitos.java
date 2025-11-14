package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio_dda.Controlador.DTOs.TransitoPropietarioDTO;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Transito;
import com.example.obligatorio_dda.Modelo.Bonificacion;
import com.example.obligatorio_dda.Modelo.Asignacion;

@RestController
@RequestMapping("/transitos")
public class ControladorTransitos {

    @PostMapping("/misTransitos")
    public java.util.List<Respuesta> listarMisTransitos(HttpSession sesion) throws Exception {
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            throw new Exception("No hay un propietario logueado");
        }

        List<TransitoPropietarioDTO> lista = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transito t : propietario.getTransitos()) {
            String puesto = t.getPuesto() != null ? t.getPuesto().getNombre() : "";
            String matricula = t.getVehiculo() != null ? t.getVehiculo().getMatricula() : "";
            double montoBase = t.getTarifa() != null ? t.getTarifa().getMonto() : 0.0;
            Bonificacion bon = null;
            // intentar inferir bonificación utilizada (si existe una asignación para el puesto en el propietario)
            if (propietario.getAsignaciones() != null) {
                for (Asignacion a : propietario.getAsignaciones()) {
                    if (a != null && a.getPuesto() != null && t.getPuesto() != null && a.getPuesto().getPeajeString().equals(t.getPuesto().getPeajeString())) {
                        bon = a.getBonificacion();
                        break;
                    }
                }
            }
            double montoPagado = (bon != null) ? bon.calcularDescuento(montoBase) : montoBase;
            double descuento = montoBase - montoPagado;
            String bonNombre = bon != null ? bon.getNombre() : "";
            String fecha = t.getFechaHora() != null ? t.getFechaHora().format(fmt) : "";

            lista.add(new TransitoPropietarioDTO(puesto, matricula, montoBase, bonNombre, descuento, montoPagado, fecha));
        }

        return Respuesta.lista(new Respuesta("misTransitos", lista));
    }
}
