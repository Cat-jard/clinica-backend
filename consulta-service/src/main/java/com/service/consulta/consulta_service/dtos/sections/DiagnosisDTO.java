package com.service.consulta.consulta_service.dtos.sections;


public record DiagnosisDTO(
        Long id,
        String cie10code,
        String description,
        Boolean principal
) {
}
