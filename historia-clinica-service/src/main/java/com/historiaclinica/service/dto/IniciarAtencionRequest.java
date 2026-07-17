package com.historiaclinica.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IniciarAtencionRequest {

    @NotNull
    private UUID pacienteId;

    @NotNull
    private Long medicoId;

    private String medicoNombre;

    private String especialidad;

    private String consultorio;
}
