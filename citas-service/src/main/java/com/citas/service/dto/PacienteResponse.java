package com.citas.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PacienteResponse(
    UUID id,
    String tipoDocumento,
    String nroDocumento,
    String apellidoPaterno,
    String apellidoMaterno,
    String nombres,
    LocalDate fechaNacimiento,
    String sexo,
    String telefono,
    String email,
    String direccion,
    String aseguradora,
    String nroHistoria,
    String alergias,
    LocalDateTime createdAt
) {}
