package com.service.consulta.consulta_service.dtos;

import com.service.consulta.consulta_service.domain.ConsultationStatus;
import com.service.consulta.consulta_service.domain.ConsultationType;

import java.time.LocalDateTime;

public record ConsultationSummaryResponse(
        Long id,
        Long appointmentId,
        Long patientId,
        Long doctorId,
        ConsultationType consultationType,
        ConsultationStatus status,
        LocalDateTime startedAt,
        LocalDateTime finishedAt
) {
}
