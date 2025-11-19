package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Puesto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/puestos")
public class ControladorPuestos {

    @PostMapping("/listar")
    public List<Respuesta> listarPuestos() {
        List<String> valores = new ArrayList<>();
        for (Puesto p : Fachada.getInstancia().getPuestos()) {
            valores.add(p.getPeajeString());
        }
        return Respuesta.lista(new Respuesta("puestosLista", valores));
    }
}
