package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Controlador.DTOs.NotificacionDTO;
import com.example.obligatorio_dda.Modelo.Notificacion;
import com.example.obligatorio_dda.Modelo.Propietario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class ControladorNotificaciones {

    @PostMapping("/listar")
    public List<Respuesta> listarNotificaciones(HttpSession sesion,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "pageSize", required = false) Integer pageSize) throws Exception {
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            return Respuesta.lista(new Respuesta("redirLoginPropietario", "login-propietario.html"));
        }

        int p = (page == null || page < 1) ? 1 : page;
        int ps = (pageSize == null || pageSize < 1) ? Integer.MAX_VALUE : pageSize;

        // Use Propietario to obtain paged/ordered notifications
        List<Notificacion> pageItems;
        if (ps == Integer.MAX_VALUE) {
            pageItems = propietario.listarNotificacionesOrdenadasDesc();
        } else {
            pageItems = propietario.obtenerNotificacionesPagina(p, ps);
        }

        List<NotificacionDTO> notificacionesDTO = new ArrayList<>();
        for (Notificacion notif : pageItems) {
            notificacionesDTO.add(new NotificacionDTO(
                    notif.getMensaje(),
                    notif.getFechaHoraFormateada(),
                    notif.isLeida()));
        }

        int totalItems = propietario.getTotalNotificaciones();
        int totalPages = (ps == Integer.MAX_VALUE) ? 1 : propietario.getTotalPagesFor(ps);

        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("items", notificacionesDTO);
        result.put("page", p);
        result.put("pageSize", ps == Integer.MAX_VALUE ? totalItems : ps);
        result.put("totalPages", totalPages);
        result.put("totalItems", totalItems);
        int totalUnread = propietario.getTotalNotificacionesNoLeidas();
        result.put("totalUnread", totalUnread);

        return Respuesta.lista(new Respuesta("notificaciones", result));
    }

    @PostMapping("/marcarLeidas")
    public List<Respuesta> marcarNotificacionesComoLeidas(HttpSession sesion) throws Exception {
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            return Respuesta.lista(new Respuesta("redirLoginPropietario", "login-propietario.html"));
        }

        // acá marcamos todas como leídas
        for (Notificacion notif : propietario.getNotificaciones()) {
            notif.marcarComoLeida();
        }
        return Respuesta.lista(new Respuesta("notificacionesMarcadas", "OK"));
    }
}