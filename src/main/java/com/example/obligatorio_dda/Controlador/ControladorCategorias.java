package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class ControladorCategorias {

    @GetMapping("/listar")
    public List<String> listarCategorias() {
        List<String> nombres = new ArrayList<>();
        for (Categoria c : Fachada.getInstancia().getCategorias()) {
            nombres.add(c.getNombre());
        }
        return nombres;
    }
}
