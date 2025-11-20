
package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

import com.example.obligatorio_dda.ConexionNavegador;
import com.example.obligatorio_dda.Controlador.DTOs.TarifaDTO;
import com.example.obligatorio_dda.Controlador.DTOs.TransitoInfoDTO;
import com.example.obligatorio_dda.Modelo.Bonificacion;
import com.example.obligatorio_dda.Modelo.PeajeException;
import com.example.obligatorio_dda.Modelo.Puesto;
import com.example.obligatorio_dda.Modelo.Administrador;
import com.example.obligatorio_dda.Modelo.Fachada;
import com.example.obligatorio_dda.Modelo.Vehiculo;
import com.example.obligatorio_dda.Observador.Observable;
import com.example.obligatorio_dda.Observador.Observador;
import com.example.obligatorio_dda.Modelo.Propietario;

@RestController
@Scope("session")
@RequestMapping("/emularTransito")
public class ControladorEmularTransito extends Observable {

    @Autowired
    private HttpSession sesion;

    private final ConexionNavegador conexionNavegador;

    public ControladorEmularTransito(@Autowired ConexionNavegador conexionNavegador) {
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
        this.sesion.setAttribute("vistaConectada", "emularTransito");
        return Respuesta.lista(new Respuesta("vistaConectada", "OK"));
    }

    @PostMapping("/vistaCerrada")
    public void salir(HttpSession sesionHttp) {
        Fachada.getInstancia().quitarObservador((Observador) this);
        sesionHttp.removeAttribute("usuarioAdministrador");
    }

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

        Vehiculo vehiculo = Fachada.getInstancia().buscarVehiculoPorMatricula(matricula);

        Propietario prop = vehiculo.getPropietario();
        String propietarioNombre = (prop != null) ? prop.getNombre() + " " + prop.getApellido() : "";
        String categoria = (vehiculo.getCategoria() != null) ? vehiculo.getCategoria().getNombre() : "";
        Puesto puesto = null;
        String bonificacionNombre = "(ninguna)";
        try {
            if (puestoId != null && !puestoId.trim().isEmpty()) {
                puesto = Fachada.getInstancia().buscarPuestoPorId(puestoId);
            }
        } catch (Exception e) {
            // si no se encuentra el puesto, lo dejamos en null y seguimos (no es crítico
            // para mostrar info)
            puesto = null;
        }
        Bonificacion bonificacion = null;
        if (prop != null && puesto != null) {
            bonificacion = prop.getBonificacionEnPuesto(puesto);
        }
        if (bonificacion != null && bonificacion.getNombre() != null) {
            bonificacionNombre = bonificacion.getNombre();
        }

        TransitoInfoDTO dto = new TransitoInfoDTO(propietarioNombre, categoria, bonificacionNombre);
        if (puesto != null) {
            double montoBase = puesto.obtenerTarifaParaCategoria(vehiculo.getCategoria()).getMonto();
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
        List<TarifaDTO> lista = puesto.obtenerTarifasPorPuesto();

        return Respuesta.lista(new Respuesta("tarifasLista", lista));
    }

}
