package com.hospitalizacion.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CamaResponse(
    UUID id,
    String numero,
    String servicio,
    String estado,
    UUID pacienteId,
    String pacienteNombre,
    LocalDateTime fechaIngreso,
    String diagnostico,
    String medicoNombre,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
