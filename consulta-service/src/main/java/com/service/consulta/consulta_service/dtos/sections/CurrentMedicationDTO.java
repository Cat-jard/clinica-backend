package com.service.consulta.consulta_service.dtos.sections;

import java.util.UUID;

public record CurrentMedicationDTO(
        UUID medicineId,
        String dose,
        String frequency
) {
}
