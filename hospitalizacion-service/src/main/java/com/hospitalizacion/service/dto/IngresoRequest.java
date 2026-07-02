package com.hospitalizacion.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class IngresoRequest {

    @NotNull
    private UUID pacienteId;

    @NotNull
    private UUID medicoId;

    @NotBlank
    private String servicio;

    @NotBlank
    private String diagnosticoIngreso;

    @NotBlank
    private String motivoIngreso;

    @NotBlank
    private String userId;

    private UUID camaId;
}
