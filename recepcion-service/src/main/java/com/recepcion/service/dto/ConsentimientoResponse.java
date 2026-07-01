package com.recepcion.service.dto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record ConsentimientoResponse(
    UUID id,
    UUID pacienteId,
    String tipo,
    String textoLegal,
    String textoLegalHash,
    String versionTexto,
    String firmaHash,
    Boolean aceptado,
    LocalDateTime fechaExposicion,
    LocalDateTime fechaFirma,
    String ipOrigen,
    String userId,
    Map<String, Object> metadata,
    LocalDateTime createdAt
) {}
