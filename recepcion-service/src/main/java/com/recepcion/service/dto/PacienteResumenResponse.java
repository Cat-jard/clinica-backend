package com.recepcion.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PacienteResumenResponse(
    UUID id,
    String tipoDocumento,
    String nroDocumento,
    String apellidoPaterno,
    String apellidoMaterno,
    String nombres,
    LocalDate fechaNacimiento,
    String telefono,
    String nroHistoria,
    String aseguradora,
    String consentimiento,
    LocalDateTime createdAt
) {}
