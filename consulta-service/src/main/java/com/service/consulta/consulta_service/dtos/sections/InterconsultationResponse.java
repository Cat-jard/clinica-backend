package com.service.consulta.consulta_service.dtos.sections;

import com.service.consulta.consulta_service.domain.InterconsultationPriority;

import java.time.LocalDateTime;
import java.util.UUID;

public record InterconsultationResponse(
        UUID id,
        UUID specialtyId,
        UUID doctorId,
        String reason,
        String relevantFindings,
        String clinicalQuestion,
        InterconsultationPriority priority,
        LocalDateTime createdAt
) {
}
