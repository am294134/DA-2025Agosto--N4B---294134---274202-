package com.example.obligatorio_dda.Modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notificacion {
    private String mensaje;
    private Propietario propietario;
    private LocalDateTime fechaHora;
    private boolean leida; //sumamos leida para poder hacer el cambio de estado que ta lindo

    public Notificacion(String mensaje, Propietario propietario) {
        this.mensaje = mensaje;
        this.propietario = propietario;
        this.fechaHora = LocalDateTime.now();
        this.leida = false; 
    }

    public String getMensaje() {
        return mensaje;
    }

    public Propietario getPropietario() {
        return propietario;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public String getFechaHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHora.format(formatter);
    }

    public boolean isLeida() {
        return leida;
    }

    public void marcarComoLeida() {
        this.leida = true;
    }
}