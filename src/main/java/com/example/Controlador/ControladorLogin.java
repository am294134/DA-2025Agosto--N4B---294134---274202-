package com.example.Controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Respuesta;
import com.example.Modelo.Fachada;
import com.example.Modelo.PeajeException;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/acceso")
public class ControladorLogin {

    //AGREGAR SESSION 
    
    @PostMapping("loginPropietario")
    public List<Respuesta> loginPropietario(@RequestBody String username, String password) throws PeajeException {
        Fachada.getInstancia().loginPropietario(username, password);
        return Respuesta.lista(new Respuesta("loginExitoso", "menu-agenda.html"));
    }

    @PostMapping("loginAdministrador")
    public List<Respuesta> loginAdministrador(@RequestBody String username, String password) throws PeajeException {
        Fachada.getInstancia().loginAdministrador(username, password);
        return Respuesta.lista(new Respuesta("loginExitoso", "menu-administrador.html"));
    }
}
