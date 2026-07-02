package com.hospitalizacion.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AutorizacionResponse(
    UUID id,
    UUID hospitalizacionId,
    String representanteNombre,
    String representanteDni,
    String textoLegal,
    Boolean firmado,
    LocalDateTime createdAt
) {}
