package com.triaje.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class RegistrarTriajeRequest {

    @NotNull
    private UUID colaId;

    @NotNull
    private UUID pacienteId;

    private UUID citaId;

    @NotBlank
    private String enfermeraId;

    @NotNull
    private SignosVitalesDTO signos;

    @NotBlank
    private String motivoConsulta;

    @NotBlank
    @Pattern(regexp = "Alerta|Verbal|Dolor|Inconsciente")
    private String nivelConciencia;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer dolor;

    @NotBlank
    @Pattern(regexp = "I-ROJO|II-NARANJA|III-AMARILLO|IV-VERDE|V-AZUL")
    private String prioridad;

    @NotBlank
    @Size(min = 10, message = "La justificacion debe tener al menos 10 caracteres")
    private String justificacion;
}
