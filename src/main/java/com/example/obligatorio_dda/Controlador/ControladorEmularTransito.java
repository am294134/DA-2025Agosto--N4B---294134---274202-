
package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;

import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Transito;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Categoria;
import com.example.obligatorio_dda.Modelo.Tarifa;
import com.example.obligatorio_dda.Modelo.Administrador;

@RestController
@RequestMapping("/emularTransito")
public class ControladorEmularTransito {
//private String categoria;

    @PostMapping("/agregar")
    public void agregarTransito(HttpSession sesion,
            @RequestParam("puestoId") String puestoId,
            @RequestParam("matricula") String matricula,
            @RequestParam("fechaHora") String fechaHora) throws PeajeException {
        // obtenemos session
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        Fachada.getInstancia().agregarTransito(puestoId, matricula, fechaHora);
    }
/* 
    @GetMapping("/mostrar")
    public List<TransitoEmuladoDTO> mostrarTransito(HttpSession sesion) throws PeajeException {
        // obtenemos session
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        List<TransitoEmuladoDTO> transitosDTO = new ArrayList<>();
        for (Transito t : Fachada.getInstancia().getTransitos
        ) {
            transitosDTO.add(new TransitoEmuladoDTO(
                t.getPuesto(),
                t.getVehiculo(),
                t.getPropietario()
            ));
            */
}
