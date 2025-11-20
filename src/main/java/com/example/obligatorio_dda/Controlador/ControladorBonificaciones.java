package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Controlador.DTOs.BonificacionAsignadaDTO;
import com.example.obligatorio_dda.Controlador.DTOs.PropietarioInfoBonDTO;
import com.example.obligatorio_dda.Modelo.Asignacion;
import com.example.obligatorio_dda.Modelo.Bonificacion;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Propietario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Notificacion;
import com.example.obligatorio_dda.Modelo.PeajeException;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@RestController
@Scope("session")
@RequestMapping("/bonificaciones")
public class ControladorBonificaciones {

    @Autowired
    private HttpSession sesion;

    @PostMapping("/porPropietario")
    public List<Respuesta> listarPorPropietario() throws Exception {

        Propietario propietario = (Propietario) this.sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            return Respuesta.lista(new Respuesta("redirLoginPropietario", "login-propietario.html"));
        }

        List<BonificacionAsignadaDTO> bonis = new ArrayList<>();
        List<Asignacion> asignacionesPropietario = Fachada.getInstancia().obtenerAsignacionesPropietario(propietario);
        
        
        
        /*// recorremos las bonificaciones
        for (Bonificacion bonificaciones : Fachada.getInstancia().getBonificaciones()) {
            // recorremos las asignaciones
            for (Asignacion asigns : bonificaciones.getAsignaciones()) {
                // buscamos las que pertenecen al propietario logueado y lo mandamos para el DTO
                if (asigns.getPropietario() != null
                        && asigns.getPropietario().getCedula().equals(propietario.getCedula())) {
*/
                    String nombreBon = bonificaciones.getNombre();

                    String nombrePuesto = "";
                    if (asigns.getPuesto() != null) {
                        nombrePuesto = asigns.getPuesto().getNombre();
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

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada() {
        Propietario propietario = (Propietario) this.sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            return Respuesta.lista(new Respuesta("redirLoginPropietario", "login-propietario.html"));
        }
        this.sesion.setAttribute("vistaConectada", "bonificaciones");
        return Respuesta.lista(new Respuesta("vistaConectada", "OK"));
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
        public List<Respuesta> infoPorCedula(
            @RequestParam(name = "cedula", required = false) String cedula) {

        if (cedula == null || cedula.trim().isEmpty()) {
            return Respuesta.lista(new Respuesta("infoPropietario", null));
        }

        cedula = cedula.trim();
        // buscar propietario en la fachada
        PropietarioInfoBonDTO dto = null;

        for (Propietario p : Fachada.getInstancia().getPropietarios()) {
            if (p != null && p.getCedula() != null && p.getCedula().equals(cedula)) {

                String nombre = p.getNombre() + " " + p.getApellido();
                String estado = p.getEstado().getNombre();
                dto = new PropietarioInfoBonDTO(nombre.trim(), estado);

                // recolectar las bonificaciones asignadas a este propietario
                List<BonificacionAsignadaDTO> asignadas = new ArrayList<>();

                // por cada bon buscamos en las asignaciones
                for (Bonificacion b : Fachada.getInstancia().getBonificaciones()) {
                    for (Asignacion a : b.getAsignaciones()) {
                        if (a != null && a.getPropietario() != null && a.getPropietario().getCedula() != null
                                && a.getPropietario().getCedula().equals(cedula)) {

                            String nombrePuesto = a.getPuesto().getNombre();
                            String fecha = a.getFechaAsignacion().toString();

                            asignadas.add(new BonificacionAsignadaDTO(
                                    b.getNombre(), nombrePuesto, fecha));
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
        public List<Respuesta> asignarBonificacion(
            @RequestParam(name = "cedula") String cedula,
            @RequestParam(name = "puesto") String puestoId,
            @RequestParam(name = "tipo") String tipoBonificacion) throws PeajeException {

        // validar inputs básicos
        Propietario prop = null;
        for (Propietario p : Fachada.getInstancia().getPropietarios()) {
            if (p != null && p.getCedula() != null && p.getCedula().equals(cedula != null ? cedula.trim() : null)) {
                prop = p;
            }
        }

        if (cedula == null || cedula.trim().isEmpty()) {
            throw new PeajeException("Cédula inválida");
        }
        if (tipoBonificacion == null || tipoBonificacion.trim().isEmpty()) {
            throw new PeajeException("Seleccione un tipo de bonificación");
        }

        // buscar propietario
        if (prop == null) {
            throw new PeajeException("No se encontró propietario con esa cédula");
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
            throw new PeajeException("Tipo de bonificación no encontrado");
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
                    throw new PeajeException("Ya hay una bonificación para este propietario en este puesto");
                }
                if (puesto != null && puestoExistente != null) {
                    String p1 = puesto.getPeajeString();
                    String p2 = puestoExistente.getPeajeString();
                    if (p1 != null && p1.equals(p2)) {
                        return Respuesta.lista(new Respuesta("asignacionResultado",
                                "Ya hay una bonificación para este propietario en este puesto"));
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

        // Crear notificación para el propietario informando la nueva bonificación
        List<Puesto> puestos = Fachada.getInstancia().getPuestos();
        int numeroPuesto = -1;

        // Buscamos el número del puesto
        for (int i = 0; i < puestos.size(); i++) {
            if (puestos.get(i) == puesto) {
                numeroPuesto = i + 1; // para empezar por 1
                break;
            }
        }

        String nroTexto = "";
        if (numeroPuesto > 0) {
            nroTexto = " #" + numeroPuesto;
        }

        // Armamos el mensaje (evitar NPE si no hay puesto)
        String puestoNombre = (puesto != null && puesto.getNombre() != null) ? puesto.getNombre() : "sin puesto";
        String mensaje = "Se te asignó la bonificación \"" + bon.getNombre() +
                "\" en el puesto \"" + puestoNombre + "\"" + nroTexto;

        // Creamos y guardamos la notificación
        Notificacion notificacion = new Notificacion(mensaje, prop);
        prop.getNotificaciones().add(notificacion);

        return Respuesta.lista(new Respuesta("asignacionResultado", "Bonificación asignada correctamente"));
    }

}
