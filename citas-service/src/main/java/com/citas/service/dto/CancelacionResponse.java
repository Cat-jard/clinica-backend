package com.citas.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CancelacionResponse(
    UUID id,
    UUID citaId,
    String motivo,
    String canceladoPor,
    LocalDateTime fechaCancelacion,
    LocalDateTime createdAt
) {}
