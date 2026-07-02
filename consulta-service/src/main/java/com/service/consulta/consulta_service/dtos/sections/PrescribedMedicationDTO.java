package com.service.consulta.consulta_service.dtos.sections;

public record PrescribedMedicationDTO(
        Long medicineId,
        String dose,
        String route,
        String frequency,
        String duration,
        String quantity,
        String specialInstructions
) {
}
