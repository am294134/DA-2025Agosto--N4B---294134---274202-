
package com.example.obligatorio_dda.Controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/emularTransito")
public class ControladorEmularTransito {
    // private String categoria;

    @PostMapping("/agregar")
    public List<Respuesta> agregarTransito(HttpSession sesion,
            @RequestParam("puestoId") String puestoId,
            @RequestParam("matricula") String matricula,
            @RequestParam("fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaHora)
            throws PeajeException {
        // obtenemos session
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdministrador");

        if (admin == null) {
            throw new PeajeException("No hay un administrador logueado");
        }

        // Guardamos el puesto en la sesión del administrador
        sesion.setAttribute("puestoSeleccionado", puestoId);

        // Delegamos la creación del tránsito a la fachada. Fachada acepta fechaHora como String
        String fechaStr = null;
        if (fechaHora != null) {
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            fechaStr = fechaHora.format(fmt);
        }
        try {
            Fachada.getInstancia().agregarTransito(puestoId, matricula, fechaStr);
            return Respuesta.lista(new Respuesta("emularResultado", "Tránsito registrado correctamente"));
        } catch (PeajeException ex) {
            return Respuesta.lista(new Respuesta("emularResultado", ex.getMessage()));
        }
    }

    @PostMapping("/infoMatricula")
    public List<Respuesta> infoMatricula(HttpSession sesion,
            @RequestParam(name = "puestoId", required = false) String puestoId,
            @RequestParam("matricula") String matricula) throws PeajeException {

        // buscar vehículo en la fachada (comparación tolerante: quitar no-alfa-numéricos y comparar uppercase)
        Vehiculo vehiculo = null;
        String buscada = matricula == null ? "" : matricula.replaceAll("[^A-Za-z0-9]", "").toUpperCase(java.util.Locale.ROOT);
        for (Vehiculo v : Fachada.getInstancia().getVehiculos()) {
            if (v == null || v.getMatricula() == null) continue;
            String actual = v.getMatricula().replaceAll("[^A-Za-z0-9]", "").toUpperCase(java.util.Locale.ROOT);
            if (actual.equals(buscada)) {
                vehiculo = v;
                break;
            }
        }

        if (vehiculo == null) {
            // sin info
            return Respuesta.lista(new Respuesta("infoMatricula", null));
        }

        Propietario prop = vehiculo.getPropietario();
        String propietarioNombre = (prop != null) ? prop.getNombre() + " " + prop.getApellido() : "";
        String categoria = (vehiculo.getCategoria() != null) ? vehiculo.getCategoria().getNombre() : "";

        // Buscar si el propietario tiene alguna asignación (bonificación) para el puesto (si se proporcionó)
        Puesto puesto = null;
        if (puestoId != null && !"".equals(puestoId)) {
            try {
                puesto = Fachada.getInstancia().buscarPuestoPorId(puestoId);
            } catch (PeajeException ex) {
                puesto = null;
            }
        }
        String bonificacionNombre = null;
        if (prop != null) {
            com.example.obligatorio_dda.Modelo.Bonificacion b = prop.getBonificacionForPuesto(puesto);
            if (b != null) bonificacionNombre = b.getNombre();
        }
        if (bonificacionNombre == null) bonificacionNombre = "(ninguna)";

        TransitoInfoDTO dto = new TransitoInfoDTO(propietarioNombre, categoria, bonificacionNombre);
        // Si se proporcionó un puesto intentamos calcular el costo y saldo luego del tránsito
        if (puesto != null) {
            try {
                double montoBase = puesto.obtenerTarifaParaCategoria(vehiculo.getCategoria());
                double montoAPagar = (prop != null) ? prop.calcularMontoAPagarParaPuesto(puesto, montoBase) : montoBase;
                Double saldoLuego = null;
                if (prop != null) {
                    try {
                        saldoLuego = prop.calcularSaldoLuegoDePago(montoAPagar);
                    } catch (Exception ex) {
                        saldoLuego = null;
                    }
                }
                dto.setMonto(montoAPagar);
                dto.setSaldoLuegoDelTransito(saldoLuego);
            } catch (PeajeException ex) {
                // no hay tarifa definida para la categoría en ese puesto; dejamos monto/saldo en null
            }
        }

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
            String nombreCategoria = t.getCategoria() != null ? t.getCategoria().getNombre() : null;
            lista.add(new TarifaDTO(t.getMonto(), nombreCategoria));
        }

        return Respuesta.lista(new Respuesta("tarifasLista", lista));
    }
}
