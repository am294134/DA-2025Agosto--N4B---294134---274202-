
package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;

import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Transito;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Propietario;
import com.example.obligatorio_dda.Modelo.Categoria;
import com.example.obligatorio_dda.Modelo.Tarifa;

@RestController
@RequestMapping("/emularTransito")
public class ControladorEmularTransito {

	// Controlador vacío por ahora; los imports necesarios ya están añadidos.

}
