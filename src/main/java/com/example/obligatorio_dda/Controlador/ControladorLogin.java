package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.obligatorio_dda.Modelo.Administrador;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Propietario;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/acceso")
public class ControladorLogin {

    @PostMapping("/loginPropietario")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula,
        @RequestParam String contrasenia) throws PeajeException {

        // login al modelo
        Propietario propietario = Fachada.getInstancia().loginPropietario(cedula, contrasenia);

        // si el propietario esta deshabilitado, no puede ingresar
        if (propietario.getEstado() != null && "Deshabilitado".equalsIgnoreCase(propietario.getEstado().getNombre())) {
            throw new PeajeException("Usuario deshabilitado, no puede ingresar al sistema");
        }

        // se guarda el propietario en la sesionHttp
        sesionHttp.setAttribute("usuarioPropietario", propietario);
        // devolvemos el los datos del usuario
        String nombreCompleto = propietario.getNombre() + " " + propietario.getApellido();
        return Respuesta.lista(
            new Respuesta("datosUsuario", nombreCompleto),
            new Respuesta("loginExitoso", "menu-propietario.html")
        );
    }

    @PostMapping("/loginAdministrador")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String cedula,
            @RequestParam String contrasenia) throws PeajeException {

        Administrador admin = Fachada.getInstancia().loginAdministrador(cedula, contrasenia);
        //guardamos sesion (consistente con el resto del proyecto)
        sesionHttp.setAttribute("usuarioAdministrador", admin);

        return Respuesta.lista(new Respuesta("loginExitoso", "menu-admin.html"));
    }
}
