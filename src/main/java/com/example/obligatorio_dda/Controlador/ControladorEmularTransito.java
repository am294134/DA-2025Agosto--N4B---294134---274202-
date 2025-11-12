
package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Administrador;


@RestController
@RequestMapping("/emularTransito")
public class ControladorEmularTransito {
    // private String categoria;

    @PostMapping("/agregar")
    public void agregarTransito(HttpSession sesion,
            @RequestParam("puestoId") String puestoId,
            @RequestParam("matricula") String matricula,
            @RequestParam("fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaHora)
            throws PeajeException {
        // obtenemos session
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdministrador");
        
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        // Guardamos el puesto en la sesión del administrador
        sesion.setAttribute("puestoSeleccionado", puestoId);

        Fachada.agregarTransito(puestoId, matricula, fechaHora);
    }
    
    @PostMapping("/tarifasPorPuesto")
    public List<Respuesta> tarifasPorPuestoSesion(HttpSession sesion) throws PeajeException {
    String puestoId = (String) sesion.getAttribute("puestoSeleccionado");
    if (puestoId == null) {
        throw new PeajeException("No hay puesto seleccionado");
    }

    Puesto puesto = Fachada.getInstancia().buscarPuestoPorId(puestoId);
    if (puesto == null) {
        throw new PeajeException("Puesto no encontrado");
    }

    List<Map<String, Object>> lista = new ArrayList<>();
    for (Tarifa t : puesto.getTarifas()) {
        Map<String, Object> fila = new HashMap<>();
        fila.put("categoria", t.getCategoria().getNombre());
        fila.put("monto", t.getMonto());
        lista.add(fila);
    }

    return Respuesta.lista(new Respuesta("tarifasLista", lista));
}
 

} 
