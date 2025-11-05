package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Controlador.DTOs.NotificacionDTO;
import com.example.obligatorio_dda.Modelo.Notificacion;
import com.example.obligatorio_dda.Modelo.Propietario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class ControladorNotificaciones {

    @GetMapping("/listar")
    public List<NotificacionDTO> listarNotificaciones(HttpSession sesion) throws Exception {
        Propietario propietario = (Propietario) sesion.getAttribute("usuarioPropietario");
        if (propietario == null) {
            throw new Exception("No hay un propietario logueado");
        }

        // pasamos a dtoy devolvemos todas las notificaciones
        List<NotificacionDTO> notificacionesDTO = new ArrayList<>();
        for (Notificacion notif : propietario.getNotificaciones()) {
            notificacionesDTO.add(new NotificacionDTO(
                notif.getMensaje(),
                notif.getFechaHoraFormateada(),
                notif.isLeida()
            ));
        }

        return notificacionesDTO;
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