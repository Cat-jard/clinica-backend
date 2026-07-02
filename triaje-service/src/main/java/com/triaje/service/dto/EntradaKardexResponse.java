package com.triaje.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EntradaKardexResponse(
    UUID id,
    UUID pacienteId,
    String pacienteNombre,
    UUID citaId,
    LocalDateTime fechaHora,
    Integer pasSistolica,
    Integer pasDiastolica,
    Integer frecCardiaca,
    Integer frecRespiratoria,
    BigDecimal temperatura,
    Integer spo2,
    Integer ingresosHidricos,
    Integer egresosHidricos,
    String evolucion,
    Boolean firmado,
    String firmadoPor,
    LocalDateTime createdAt,
    List<MedicamentoKardexDTO> medicamentos
) {}
