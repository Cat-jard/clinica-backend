package com.clinica.radiologia.dto;

import java.util.List;

public record EstudioImagenDto(
        String id,
        String nroOrden,
        PacienteRadiologiaDto paciente,
        String medicoSolicitante,
        String especialidadMedico,
        String modalidad,
        String tipoEstudio,
        String regionAnatomica,
        String fechaSolicitud,
        String fechaEstudio,
        String prioridad,
        String estado,
        List<SerieDicomDto> series,
        InformeRadiologicoDto informe,
        String firmadoEn,
        Boolean esCritico,
        String indicaciones
) {
}
