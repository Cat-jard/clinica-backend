package com.service.consulta.consulta_service.dtos.sections;


import java.util.UUID;

public record DiagnosisDTO(
        String cie10code,
        String description,
        Boolean principal
) {
}
