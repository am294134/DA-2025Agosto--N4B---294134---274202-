
package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import com.example.obligatorio_dda.Controlador.DTOs.TarifaDTO;
import com.example.obligatorio_dda.Controlador.DTOs.TransitoInfoDTO;
import com.example.obligatorio_dda.Modelo.Tarifa;
import com.example.obligatorio_dda.Modelo.Bonificacion;
import com.example.obligatorio_dda.Modelo.Asignacion;
import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Administrador;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Propietario;

@RestController
@RequestMapping("/emularTransito")
public class ControladorEmularTransito {

    @PostMapping("/agregar")
    public List<Respuesta> agregarTransito(HttpSession sesion,
            @RequestParam("puestoId") String puestoId,
            @RequestParam("matricula") String matricula,
            @RequestParam("fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaHora)
            throws PeajeException {

        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        sesion.setAttribute("puestoSeleccionado", puestoId);

        Fachada.getInstancia().agregarTransito(puestoId, matricula, fechaHora);
        return Respuesta.lista(new Respuesta("emularResultado", "Tránsito registrado correctamente"));
        }


    @PostMapping("/infoMatricula")
    public List<Respuesta> infoMatricula(HttpSession sesion,
            @RequestParam(name = "puestoId", required = false) String puestoId,
            @RequestParam("matricula") String matricula) throws PeajeException {
        
        
        
        Vehiculo vehiculo = Fachada.getInstancia().buscarVehiculoPorMatricula(matricula);
        Propietario prop = vehiculo.getPropietario();
        String propietarioNombre = (prop != null) ? prop.getNombre() + " " + prop.getApellido() : "";
        String categoria = vehiculo.getCategoria().getNombre();
        Puesto puesto = Fachada.getInstancia().buscarPuestoPorId(puestoId);;
        String bonificacionNombre = Fachada.getInstancia().buscarBonificacionNombreEnPuesto(prop, puesto);
        if (bonificacionNombre == null) bonificacionNombre = "(ninguna)";

        TransitoInfoDTO dto = new TransitoInfoDTO(propietarioNombre, categoria, bonificacionNombre);
        
                double montoBase = puesto.obtenerTarifaParaCategoria(vehiculo.getCategoria());
                double montoAPagar = (prop != null) ? prop.calcularMontoAPagarParaPuesto(puesto, montoBase) : montoBase;
                Double saldoLuego = prop.calcularSaldoLuegoDePago(montoAPagar);
                dto.setMonto(montoAPagar);
                dto.setSaldoLuegoDelTransito(saldoLuego);
            return Respuesta.lista(new Respuesta("infoMatricula", dto));
    }

    @PostMapping("/tarifasPorPuesto")
    public List<Respuesta> tarifasPorPuestoSesion(HttpSession sesion,
            @RequestParam(name = "puestoId", required = false) String puestoId) throws PeajeException {
        
        // permitir que el cliente especifique el puesto por parámetro
        if (puestoId == null || puestoId.isEmpty()) {
            puestoId = (String) sesion.getAttribute("puestoSeleccionado");
        }
        if (puestoId == null) {
            throw new PeajeException("No hay puesto seleccionado");
        }

        Puesto puesto = Fachada.getInstancia().buscarPuestoPorId(puestoId);

        List<TarifaDTO> lista = new ArrayList<>();
        for (Tarifa t : puesto.getTarifas()) {
            String nombreCategoria = t.getCategoria().getNombre();
            lista.add(new TarifaDTO(t.getMonto(), nombreCategoria));
        }

        return Respuesta.lista(new Respuesta("tarifasLista", lista));
    }

}

