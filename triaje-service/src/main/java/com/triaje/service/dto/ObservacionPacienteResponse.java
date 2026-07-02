package com.triaje.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ObservacionPacienteResponse(
    UUID id,
    UUID pacienteId,
    String pacienteNombre,
    Long medicoId,
    String medicoNombre,
    String especialidad,
    LocalDateTime horaIngreso,
    String prioridad,
    String motivo,
    String estado,
    LocalDateTime createdAt
) {}
