package com.clinica.laboratorio.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record SolicitudExamenesRequest(
        String atencionId,
        PacienteOrdenDto paciente,
        String medicoSolicitante,
        String especialidadMedico,
        @Valid @NotEmpty List<ItemExamenRequest> items
) {
}
