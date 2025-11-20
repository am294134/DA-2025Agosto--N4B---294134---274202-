
package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import com.example.obligatorio_dda.Controlador.DTOs.TarifaDTO;
import com.example.obligatorio_dda.Controlador.DTOs.TransitoInfoDTO;
import com.example.obligatorio_dda.Modelo.Tarifa;
import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Administrador;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Modelo.Propietario;

@RestController
@Scope("session")
@RequestMapping("/emularTransito")
public class ControladorEmularTransito {

    @Autowired
    private HttpSession sesion;

    @PostMapping("/agregar")
    public List<Respuesta> agregarTransito(
            @RequestParam("puestoId") String puestoId,
            @RequestParam("matricula") String matricula,
            @RequestParam("fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaHora)
            throws PeajeException {

        Administrador admin = (Administrador) this.sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        this.sesion.setAttribute("puestoSeleccionado", puestoId);

        Fachada.getInstancia().agregarTransito(puestoId, matricula, fechaHora);
        return Respuesta.lista(new Respuesta("emularResultado", "Tránsito registrado correctamente"));
        }


        @PostMapping("/infoMatricula")
        public List<Respuesta> infoMatricula(
            @RequestParam(name = "puestoId", required = false) String puestoId,
            @RequestParam("matricula") String matricula) throws PeajeException {

        Vehiculo vehiculo = null;
        vehiculo = Fachada.getInstancia().buscarVehiculoPorMatricula(matricula);
       
        Propietario prop = vehiculo.getPropietario();
        String propietarioNombre = (prop != null) ? prop.getNombre() + " " + prop.getApellido() : "";
        String categoria = vehiculo.getCategoria().getNombre();
        Puesto puesto = Fachada.getInstancia().buscarPuestoPorId(puestoId);
        Bonificacion bonificacion = prop.getBonificacionEnPuesto(puesto);
        String bonificacionNombre = bonificacion.getNombre();

        if (bonificacionNombre == null) bonificacionNombre = "(ninguna)";

        TransitoInfoDTO dto = new TransitoInfoDTO(propietarioNombre, categoria, bonificacionNombre);
        if (puesto != null) {
                double montoBase = puesto.obtenerTarifaParaCategoria(vehiculo.getCategoria());
                double montoAPagar = (prop != null) ? prop.calcularMontoAPagarParaPuesto(puesto, montoBase) : montoBase;
                Double saldoLuego = prop.calcularSaldoLuegoDePago(montoAPagar);
                dto.setMonto(montoAPagar);
                dto.setSaldoLuegoDelTransito(saldoLuego);
        }
        return Respuesta.lista(new Respuesta("infoMatricula", dto));
    }

    @PostMapping("/tarifasPorPuesto")
    public List<Respuesta> tarifasPorPuestoSesion(
            @RequestParam(name = "puestoId", required = false) String puestoId) throws PeajeException {
        // permitir que el cliente especifique el puesto por parámetro
        if (puestoId == null || puestoId.isEmpty()) {
            puestoId = (String) this.sesion.getAttribute("puestoSeleccionado");
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

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada() throws PeajeException {
        Administrador admin = (Administrador) this.sesion.getAttribute("usuarioAdministrador");
        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }
        this.sesion.setAttribute("vistaConectada", "emularTransito");
        return Respuesta.lista(new Respuesta("vistaConectada", "OK"));
    }

}

