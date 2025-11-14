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
            throw new Exception("No hay un propietario logueado");
        }
        // default pagination values
        int p = (page == null || page < 1) ? 1 : page;
        int ps = (pageSize == null || pageSize < 1) ? Integer.MAX_VALUE : pageSize;

        // copiar y ordenar notificaciones por fecha descendente (recientes primero)
        List<Notificacion> todas = new ArrayList<>(propietario.getNotificaciones());
        todas.sort((a, b) -> b.getFechaHora().compareTo(a.getFechaHora()));

        int totalItems = todas.size();
        int totalPages = (ps == Integer.MAX_VALUE) ? 1 : (int) Math.max(1, Math.ceil((double) totalItems / ps));
        int fromIndex = (ps == Integer.MAX_VALUE) ? 0 : Math.min(totalItems, (p - 1) * ps);
        int toIndex = (ps == Integer.MAX_VALUE) ? totalItems : Math.min(totalItems, fromIndex + ps);

        List<NotificacionDTO> notificacionesDTO = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            Notificacion notif = todas.get(i);
            notificacionesDTO.add(new NotificacionDTO(
                notif.getMensaje(),
                notif.getFechaHoraFormateada(),
                notif.isLeida()
            ));
        }

        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("items", notificacionesDTO);
        result.put("page", p);
        result.put("pageSize", ps == Integer.MAX_VALUE ? totalItems : ps);
        result.put("totalPages", totalPages);
        result.put("totalItems", totalItems);
        // contar no leídas
        int totalUnread = 0;
        for (Notificacion n : todas) {
            if (!n.isLeida()) totalUnread++;
        }
        result.put("totalUnread", totalUnread);

        return Respuesta.lista(new Respuesta("notificaciones", result));
    }
    
    @PostMapping("/marcarLeidas")
    public void marcarNotificacionesComoLeidas(HttpSession sesion) throws Exception {
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            throw new Exception("No hay un propietario logueado");
        }

        // acá marcamos todas como leídas
        for (Notificacion notif : propietario.getNotificaciones()) {
            notif.marcarComoLeida();
        }
    }
}