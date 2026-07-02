package com.service.history.historia_clinica_service.dtos;

import com.service.history.historia_clinica_service.domain.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public record MedicalHistoryEventRequest<T>(
        UUID patientId,
        EventType type,
        LocalDateTime occurredAt,
        String sourceService,
        UUID referenceId,
        //Using the generic to map the JSON
        //What this "object" projection looks like is determined by each service
        T projection
) {
}
