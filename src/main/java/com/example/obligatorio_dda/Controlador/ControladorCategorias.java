package com.example.obligatorio_dda.Controlador;

import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Categoria;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class ControladorCategorias {

    @PostMapping("/listar")
    public List<Respuesta> listarCategorias() {
        List<String> nombres = new ArrayList<>();
        for (Categoria c : Fachada.getInstancia().getCategorias()) {
            nombres.add(c.getNombre());
        }
        return Respuesta.lista(new Respuesta("categoriasLista", nombres));
    }
}
