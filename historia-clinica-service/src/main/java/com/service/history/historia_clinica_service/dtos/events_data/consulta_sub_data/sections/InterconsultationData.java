package com.service.history.historia_clinica_service.dtos.events_data.consulta_sub_data.sections;


import java.time.LocalDateTime;
import java.util.UUID;

public record InterconsultationData(
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
