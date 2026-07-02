package com.hospitalizacion.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record EpicrisisResponse(
    UUID id,
    UUID hospitalizacionId,
    LocalDateTime fechaIngreso,
    LocalDateTime fechaAlta,
    String motivoIngreso,
    String diagnosticoIngreso,
    String diagnosticoFinal,
    String evolucion,
    String procedimientos,
    String complicaciones,
    String tratamiento,
    String recomendaciones,
    LocalDate proximaCita,
    Boolean firmado,
    String medicoId,
    String medicoNombre,
    LocalDateTime createdAt
) {}
