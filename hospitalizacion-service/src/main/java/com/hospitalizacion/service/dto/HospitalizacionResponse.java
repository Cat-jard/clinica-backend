package com.hospitalizacion.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record HospitalizacionResponse(
    UUID id,
    UUID camaId,
    String camaNumero,
    String servicio,
    UUID pacienteId,
    String pacienteNombre,
    String pacienteDni,
    UUID medicoId,
    String medicoNombre,
    String motivoIngreso,
    String diagnosticoIngreso,
    String diagnosticoAlta,
    LocalDateTime fechaIngreso,
    LocalDateTime fechaAlta,
    String tipoAlta,
    String estado,
    String observaciones,
    String userIdIngreso,
    String userIdAlta,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
