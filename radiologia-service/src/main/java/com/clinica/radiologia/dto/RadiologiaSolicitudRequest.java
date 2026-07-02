package com.clinica.radiologia.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RadiologiaSolicitudRequest(
        String atencionId,
        @Valid PacienteRadiologiaDto paciente,
        String medicoSolicitante,
        String especialidadMedico,
        @Valid @NotNull ItemExamenRequest item
) {
}
