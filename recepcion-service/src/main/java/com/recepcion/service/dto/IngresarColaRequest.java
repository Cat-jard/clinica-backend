package com.recepcion.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record IngresarColaRequest(
    @NotNull UUID pacienteId,
    @NotBlank String pacienteNombre,
    @NotBlank String pacienteDni,
    String medicoNombre,
    String especialidad,
    String motivo,
    UUID citaId,
    LocalTime horaLlegada
) {}
