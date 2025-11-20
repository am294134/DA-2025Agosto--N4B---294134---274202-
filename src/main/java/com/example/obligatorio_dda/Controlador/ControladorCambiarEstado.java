package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.example.obligatorio_dda.Controlador.DTOs.PropietarioEstadoDTO;
import com.example.obligatorio_dda.Modelo.Administrador;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.PeajeException;

@RestController
@Scope("session")
@RequestMapping("/cambiarEstado")
public class ControladorCambiarEstado {

    @Autowired
    private HttpSession sesion;

    @PostMapping("/buscar")
    public List<Respuesta> buscarPropietario(
            @RequestParam("cedula") String cedula) throws PeajeException {

        // Verificar que hay un administrador logueado
        Administrador admin = (Administrador) this.sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        Propietario propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);

        String nombre = propietario.getNombre() + " " + propietario.getApellido();
        String estado = propietario.getEstado().getNombre();

        PropietarioEstadoDTO dto = new PropietarioEstadoDTO(nombre, estado);
        return Respuesta.lista(new Respuesta("propietarioEncontrado", dto));
    }

    @PostMapping("/cambiar")
    public List<Respuesta> cambiarEstado(
            @RequestParam("cedula") String cedula,
            @RequestParam("estado") String estado) throws PeajeException {

        // Verificar que hay un administrador logueado
        Administrador admin = (Administrador) this.sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        Fachada.getInstancia().cambiarEstado(cedula, estado);
        
         return Respuesta.lista(new Respuesta("cambioEstadoResultado","Estado cambiado correctamente a: " + estado));
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada() throws PeajeException {
        Administrador admin = (Administrador) this.sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }
        this.sesion.setAttribute("vistaConectada", "cambiarEstado");
        return Respuesta.lista(new Respuesta("vistaConectada", "OK"));
    }
}