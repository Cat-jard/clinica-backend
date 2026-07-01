package com.citas.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record CitaResponse(
    UUID id,
    UUID pacienteId,
    Long medicoId,
    LocalDate fechaCita,
    LocalTime horaInicio,
    LocalTime horaFin,
    String estado,
    String motivo,
    String observaciones,
    String tipoSeguro,
    String numeroHistoria,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
