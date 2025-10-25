package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Puesto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/puestos")
public class ControladorPuestos {

    @GetMapping("/listar")
    public List<String> listarPuestos() {
        List<String> nombres = new ArrayList<>();
        for (Puesto p : Fachada.getInstancia().getPuestos()) {
            nombres.add(p.getNombre());
        }
        return nombres;
    }
}
