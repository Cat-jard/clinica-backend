package com.service.consulta.consulta_service.dtos.sections;

public record CurrentMedicationDTO(
        Long medicineId,
        String dose,
        String frequency
) {
}
