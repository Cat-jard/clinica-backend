package com.service.history.historia_clinica_service.dtos.events_data.consulta_sub_data.sections;

import java.util.List;

public record PrescriptionData(
        List<PrescribedMedicationData> medication
) {
}
