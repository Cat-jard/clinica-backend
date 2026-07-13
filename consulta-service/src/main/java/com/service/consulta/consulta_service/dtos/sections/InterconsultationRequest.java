package com.service.consulta.consulta_service.dtos.sections;

import com.service.consulta.consulta_service.domain.InterconsultationPriority;

import java.util.UUID;

public record InterconsultationRequest(
        UUID specialtyId,
        UUID doctorId,
        String reason,
        String relevantFindings,
        String clinicalQuestion,
        InterconsultationPriority priority
) {
}
