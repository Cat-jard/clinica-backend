package com.clinica.laboratorio.dto;

import java.util.List;

public record OrdenLabDto(
        String id,
        String nroOrden,
        PacienteOrdenDto paciente,
        String medicoSolicitante,
        String especialidadMedico,
        String fechaSolicitud,
        List<ExamenSolicitadoDto> examenes,
        String prioridad,
        String estado,
        String origenMuestra,
        String ayuno,
        String indicaciones
) {
}
