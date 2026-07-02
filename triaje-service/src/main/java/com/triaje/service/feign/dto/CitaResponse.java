package com.triaje.service.feign.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CitaResponse {
    private UUID id;
    private UUID pacienteId;
    private Long medicoId;
    private String fecha;
    private String horaInicio;
    private String estado;
    private String motivo;
    private String tipoSeguro;
    private String numeroHistoria;
}
