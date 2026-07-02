package com.service.consulta.consulta_service.dtos.sections;

public record AnamnesisDTO(
        String chiefComplaint,
        String presentIllness,
        String personalHistory,
        String surgicalHistory,
        String familyHistory,
        String lifestyle
) {
}
