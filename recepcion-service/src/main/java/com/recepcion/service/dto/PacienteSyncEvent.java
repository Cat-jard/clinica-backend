package com.recepcion.service.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteSyncEvent(
        UUID id,
        String tipoDocumento,
        String nroDocumento,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        LocalDate fechaNacimiento,
        String sexo,
        String telefono,
        String email,
        String direccion,
        String nroHistoria
) {}
