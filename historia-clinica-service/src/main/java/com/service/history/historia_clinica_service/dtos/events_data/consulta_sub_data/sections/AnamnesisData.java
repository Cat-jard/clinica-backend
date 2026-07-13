package com.service.history.historia_clinica_service.dtos.events_data.consulta_sub_data.sections;

public record AnamnesisData(
        String chiefComplaint,
        String presentIllness,
        String personalHistory,
        String surgicalHistory,
        String familyHistory,
        String lifestyle
) {
}
