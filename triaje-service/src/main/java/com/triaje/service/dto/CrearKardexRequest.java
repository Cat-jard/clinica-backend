package com.triaje.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CrearKardexRequest {

    @NotNull
    private UUID pacienteId;

    private SignosVitalesDTO signos;

    @NotNull
    private Integer ingresosHidricos;

    @NotNull
    private Integer egresosHidricos;

    private List<MedicamentoKardexDTO> medicamentos;

    @NotBlank
    private String evolucion;
}
