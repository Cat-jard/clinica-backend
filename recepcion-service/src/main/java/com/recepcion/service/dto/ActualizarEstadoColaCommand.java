package com.recepcion.service.dto;

import java.util.UUID;

public record ActualizarEstadoColaCommand(
        UUID id,
        UUID pacienteId,
        String estado
) {}
