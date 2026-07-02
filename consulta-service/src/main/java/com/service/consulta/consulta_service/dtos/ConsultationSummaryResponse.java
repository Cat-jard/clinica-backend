package com.service.consulta.consulta_service.dtos;

import com.service.consulta.consulta_service.domain.ConsultationStatus;
import com.service.consulta.consulta_service.domain.ConsultationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultationSummaryResponse(
        UUID id,
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        ConsultationType consultationType,
        ConsultationStatus status,
        LocalDateTime startedAt,
        LocalDateTime finishedAt
) {
}
