package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Controlador.DTOs.BonificacionAsignadaDTO;
import com.example.obligatorio_dda.Modelo.Asignacion;
import com.example.obligatorio_dda.Modelo.Bonificacion;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Propietario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.PeajeException;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bonificaciones")
public class ControladorBonificaciones {

    @PostMapping("/porPropietario")
    public List<Respuesta> listarPorPropietario(HttpSession sesion) throws Exception {
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
                if (asigns.getPropietario() != null
                        && asigns.getPropietario().getCedula().equals(propietario.getCedula())) {

                    String nombreBon = bonificaciones.getNombre();

                    String nombrePuesto = "";
                    if (asigns.getPuesto() != null) {
                        if (asigns.getPuesto().getNombre() != null) {
                            nombrePuesto = asigns.getPuesto().getNombre();
                        } else {
                            nombrePuesto = "";
                        }
                    }

                    String fecha = "";
                    if (asigns.getFechaAsignacion() != null) {
                        fecha = asigns.getFechaAsignacion().toString(); // pasamos date a string
                    }

                    bonis.add(new BonificacionAsignadaDTO(nombreBon, nombrePuesto, fecha));
                }
            }
        }

        return Respuesta.lista(new Respuesta("bonificacionesAsignadas", bonis));
    }

    @PostMapping("/listarTipos")
    public List<Respuesta> listarTipos() {
        List<String> tipos = new ArrayList<>();
        for (Bonificacion b : Fachada.getInstancia().getBonificaciones()) {
            tipos.add(b.getNombre());
        }
        return Respuesta.lista(new Respuesta("bonificacionesTipos", tipos));
    }

    @PostMapping("/infoPorCedula")
    public List<Respuesta> infoPorCedula(HttpSession sesion,
            @RequestParam(name = "cedula", required = false) String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return Respuesta.lista(new Respuesta("infoPropietario", null));
        }
        cedula = cedula.trim();
        // buscar propietario en la fachada
        com.example.obligatorio_dda.Controlador.DTOs.PropietarioInfoBonDTO dto = null;
        for (Propietario p : Fachada.getInstancia().getPropietarios()) {
            if (p != null && p.getCedula() != null && p.getCedula().equals(cedula)) {
                String nombre = (p.getNombre() != null ? p.getNombre() : "") + " "
                        + (p.getApellido() != null ? p.getApellido() : "");
                String estado = (p.getEstado() != null ? p.getEstado().getNombre() : "");
                dto = new com.example.obligatorio_dda.Controlador.DTOs.PropietarioInfoBonDTO(nombre.trim(), estado);

                // recolectar las bonificaciones asignadas a este propietario
                java.util.List<com.example.obligatorio_dda.Controlador.DTOs.BonificacionAsignadaDTO> asignadas = new java.util.ArrayList<>();
                for (Bonificacion b : Fachada.getInstancia().getBonificaciones()) {
                    for (Asignacion a : b.getAsignaciones()) {
                        if (a != null && a.getPropietario() != null && a.getPropietario().getCedula() != null
                                && a.getPropietario().getCedula().equals(cedula)) {
                            String nombrePuesto = "";
                            if (a.getPuesto() != null && a.getPuesto().getNombre() != null) nombrePuesto = a.getPuesto().getNombre();
                            String fecha = a.getFechaAsignacion() != null ? a.getFechaAsignacion().toString() : "";
                            asignadas.add(new com.example.obligatorio_dda.Controlador.DTOs.BonificacionAsignadaDTO(b.getNombre(), nombrePuesto, fecha));
                        }
                    }
                }
                dto.setAsignadas(asignadas);
                break;
            }
        }
        return Respuesta.lista(new Respuesta("infoPropietario", dto));
    }
    
        @PostMapping("/asignar")
        public List<Respuesta> asignarBonificacion(HttpSession sesion,
                @RequestParam(name = "cedula") String cedula,
                @RequestParam(name = "puesto") String puestoId,
                @RequestParam(name = "tipo") String tipoBonificacion) {
            // validar inputs básicos
            if (cedula == null || cedula.trim().isEmpty()) {
                return Respuesta.lista(new Respuesta("asignacionResultado", "Cédula inválida"));
            }
            if (tipoBonificacion == null || tipoBonificacion.trim().isEmpty()) {
                return Respuesta.lista(new Respuesta("asignacionResultado", "Seleccione un tipo de bonificación"));
            }
            // buscar propietario
            Propietario prop = null;
            for (Propietario p : Fachada.getInstancia().getPropietarios()) {
                if (p != null && p.getCedula() != null && p.getCedula().equals(cedula.trim())) {
                    prop = p;
                    break;
                }
            }
            if (prop == null) {
                return Respuesta.lista(new Respuesta("asignacionResultado", "No se encontró propietario con esa cédula"));
            }
            // buscar bonificacion por nombre
            Bonificacion bon = null;
            for (Bonificacion b : Fachada.getInstancia().getBonificaciones()) {
                if (b != null && b.getNombre() != null && b.getNombre().equals(tipoBonificacion)) {
                    bon = b;
                    break;
                }
            }
            if (bon == null) {
                return Respuesta.lista(new Respuesta("asignacionResultado", "Tipo de bonificación no encontrado"));
            }
            // buscar puesto (se pasa el peajeString como value en selects)
            Puesto puesto = null;
            try {
                puesto = Fachada.getInstancia().buscarPuestoPorId(puestoId);
            } catch (PeajeException ex) {
                puesto = null;
            }
            // Evitar asignaciones duplicadas para el mismo propietario en el mismo puesto
            if (prop.getAsignaciones() != null && prop.getAsignaciones().size() > 0) {
                for (Asignacion existente : prop.getAsignaciones()) {
                    Puesto puestoExistente = existente.getPuesto();
                    if (puesto == null && puestoExistente == null) {
                        return Respuesta.lista(new Respuesta("asignacionResultado", "Ya hay una bonificación para este propietario en este puesto"));
                    }
                    if (puesto != null && puestoExistente != null) {
                        String p1 = puesto.getPeajeString();
                        String p2 = puestoExistente.getPeajeString();
                        if (p1 != null && p1.equals(p2)) {
                            return Respuesta.lista(new Respuesta("asignacionResultado", "Ya hay una bonificación para este propietario en este puesto"));
                        }
                    }
                }
            }
            // crear asignacion
            Asignacion a = new Asignacion(new java.sql.Date(System.currentTimeMillis()), bon, prop, puesto);
            // registrar en listas correspondientes
            prop.getAsignaciones().add(a);
            bon.getAsignaciones().add(a);
            if (puesto != null) {
                puesto.getAsignaciones().add(a);
            }
        
            return Respuesta.lista(new Respuesta("asignacionResultado", "Bonificación asignada correctamente"));
        }

}
