package com.example.obligatorio_dda.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.obligatorio_dda.ConexionNavegador;
import com.example.obligatorio_dda.Modelo.Administrador;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Usuario;
import com.example.obligatorio_dda.Observador.Observable;
import com.example.obligatorio_dda.Observador.Observador;

import jakarta.servlet.http.HttpSession;

public class ControladorPropietario {

    @Autowired
    private HttpSession sesion;
    private final ConexionNavegador conexionNavegador;

    public ControladorPropietario(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        Fachada.getInstancia().agregarObservador((Observador) this);
        return conexionNavegador.getConexionSSE();
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

    @PostMapping("/vistaCerrada")
    public void salir(HttpSession sesionHttp) {
        Fachada.getInstancia().quitarObservador((Observador) this);
        sesionHttp.removeAttribute("usuarioAdministrador");
    }

    public void actualizar(Object evento, Observable origen) {
        if (evento == Usuario.Eventos.listaNot) {
            conexionNavegador.enviarJSON("");
        }
    }
}
