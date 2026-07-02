package com.hospitalizacion.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AltaRequest {

    @NotBlank
    private String tipoAlta;

    @NotBlank
    private String diagnosticoFinal;

    @NotBlank
    private String evolucion;

    private String procedimientos;

    private String complicaciones;

    @NotBlank
    private String tratamiento;

    @NotBlank
    private String recomendaciones;

    private LocalDate proximaCita;

    @NotBlank
    private String medicoId;

    @NotBlank
    private String medicoNombre;

    @NotBlank
    private String firmaBase64;
}
