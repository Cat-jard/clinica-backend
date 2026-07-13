package com.service.consulta.consulta_service.dtos.sections;

import java.util.UUID;

public record PrescribedMedicationDTO(
        UUID medicineId,
        String dose,
        String route,
        String frequency,
        String duration,
        String quantity,
        String specialInstructions
) {
}
