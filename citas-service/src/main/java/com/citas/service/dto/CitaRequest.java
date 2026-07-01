package com.citas.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class CitaRequest {

    @NotNull(message = "El ID del paciente es obligatorio")
    private UUID pacienteId;

    @NotNull(message = "El ID del medico es obligatorio")
    private Long medicoId;

    @NotNull(message = "La fecha de la cita es obligatoria")
    private LocalDate fechaCita;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    private String observaciones;

    private String tipoSeguro;
}
