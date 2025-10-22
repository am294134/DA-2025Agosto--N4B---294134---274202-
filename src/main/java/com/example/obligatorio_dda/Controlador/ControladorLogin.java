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
//import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/acceso")
public class ControladorLogin {

    @PostMapping("/loginPropietario")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula,
            @RequestParam String contrasenia) throws PeajeException {

        // login al modelo
        Propietario propietario = Fachada.getInstancia().loginPropietario(cedula, contrasenia);

        // guardo el propietario en la sesionHttp
        sesionHttp.setAttribute("usuarioPropietario", propietario);
        return Respuesta.lista(new Respuesta("loginExitoso", "menu-propietario.html"));
    }

    @PostMapping("/loginAdministrador")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String cedula,
            @RequestParam String contrasenia) throws PeajeException {

        Administrador admin = Fachada.getInstancia().loginAdministrador(cedula, contrasenia);
        //guardamos sesion
        sesionHttp.setAttribute("usuarioAdmin", admin);

        return Respuesta.lista(new Respuesta("loginExitoso", "menu-admin.html"));
    }

}
