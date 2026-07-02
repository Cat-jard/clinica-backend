package com.service.consulta.consulta_service.dtos.sections;

import com.service.consulta.consulta_service.domain.sections.PrescribedMedication;

import java.util.List;

public record PrescriptionDTO(
        List<PrescribedMedicationDTO> medication
) {
}
