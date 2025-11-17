package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

import com.example.obligatorio_dda.Controlador.DTOs.PropietarioEstadoDTO;
import com.example.obligatorio_dda.Modelo.Administrador;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.PeajeException;

@RestController
@RequestMapping("/cambiarEstado")
public class ControladorCambiarEstado {
    
    @PostMapping("/buscar")
    public List<Respuesta> buscarPropietario(HttpSession sesion,
            @RequestParam("cedula") String cedula) throws PeajeException {
        
        // Verificar que hay un administrador logueado
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }
        
        try {
            Propietario propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
            
            String nombreCompleto = propietario.getNombre() + " " + propietario.getApellido();
            String estadoActual = propietario.getEstado() != null ? 
                propietario.getEstado().getNombre() : "Sin estado";
            
            PropietarioEstadoDTO dto = new PropietarioEstadoDTO(nombreCompleto, estadoActual);
            
            return Respuesta.lista(new Respuesta("propietarioEncontrado", dto));
        } catch (PeajeException ex) {
            return Respuesta.lista(new Respuesta("propietarioEncontrado", null),
                                  new Respuesta("mensaje", ex.getMessage()));
        }
    }
    
    @PostMapping("/cambiar")
    public List<Respuesta> cambiarEstado(HttpSession sesion,
            @RequestParam("cedula") String cedula,
            @RequestParam("estado") String estado) throws PeajeException {
        
        // Verificar que hay un administrador logueado
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }
        
        try {
            Fachada.getInstancia().cambiarEstado(cedula, estado);
            return Respuesta.lista(new Respuesta("cambioEstadoResultado", 
                "Estado cambiado correctamente a: " + estado));
        } catch (PeajeException ex) {
            return Respuesta.lista(new Respuesta("cambioEstadoResultado", ex.getMessage()));
        }
    }
}