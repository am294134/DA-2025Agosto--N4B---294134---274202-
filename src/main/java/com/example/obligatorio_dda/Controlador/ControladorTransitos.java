package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio_dda.Controlador.DTOs.TransitoPropietarioDTO;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Transito;

@RestController
@Scope("session")
@RequestMapping("/transitos")
public class ControladorTransitos {

    @Autowired
    private HttpSession sesion;

    @PostMapping("/misTransitos")
    public List<Respuesta> listarMisTransitos() throws Exception {
        Propietario propietario = (Propietario) this.sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            return Respuesta.lista(new Respuesta("redirLoginPropietario", "login-propietario.html"));
        }

        List<TransitoPropietarioDTO> lista = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transito t : propietario.getTransitos()) {
            String puesto = t.getPuestoNombre();
            String matricula = t.getMatricula();
            double montoBase = t.getMontoBase();
            String bonNombre = t.getBonificacionNombreOrEmpty();
            double montoPagado = t.getMontoPagado();
            double descuento = t.getDescuentoAplicado();
            String fecha = t.getFechaHoraFormatted(fmt);

            lista.add(new TransitoPropietarioDTO(puesto, matricula, montoBase, bonNombre, descuento, montoPagado, fecha));
        }

        return Respuesta.lista(new Respuesta("misTransitos", lista));
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada() {
        Propietario propietario = (Propietario) this.sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            return Respuesta.lista(new Respuesta("redirLoginPropietario", "login-propietario.html"));
        }
        this.sesion.setAttribute("vistaConectada", "transitos");
        return Respuesta.lista(new Respuesta("vistaConectada", "OK"));
    }
}
