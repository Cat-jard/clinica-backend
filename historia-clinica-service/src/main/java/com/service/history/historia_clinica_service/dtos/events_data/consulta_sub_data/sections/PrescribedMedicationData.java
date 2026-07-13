package com.service.history.historia_clinica_service.dtos.events_data.consulta_sub_data.sections;

import java.util.UUID;

public record PrescribedMedicationData(
        UUID medicineId,
        String dose,
        String route,
        String frequency,
        String duration,
        String quantity,
        String specialInstructions
) {
}
