package com.example.obligatorio_dda.Controlador.DTOs;

public class NotificacionDTO {
    private String mensaje;
    private String fechaHora;
    private boolean leida;

    public NotificacionDTO(String mensaje, String fechaHora, boolean leida) {
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
        this.leida = leida;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }
}