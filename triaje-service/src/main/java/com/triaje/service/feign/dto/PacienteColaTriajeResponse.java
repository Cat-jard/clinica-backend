package com.triaje.service.feign.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PacienteColaTriajeResponse {
    private UUID id;
    private UUID pacienteId;
    private String pacienteNombre;
    private String pacienteDni;
    private String ticket;
    private String horaLlegada;
    private String medicoNombre;
    private String especialidad;
    private String motivo;
    private String estado;
    private UUID citaId;
}
