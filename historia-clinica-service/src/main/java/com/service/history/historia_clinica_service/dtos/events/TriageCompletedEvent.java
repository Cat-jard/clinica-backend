package com.service.history.historia_clinica_service.dtos.events;

import com.service.history.historia_clinica_service.dtos.events_data.TriageEventData;

import java.time.LocalDateTime;
import java.util.UUID;

public record TriageCompletedEvent(
        UUID patientId,
        UUID triageId,
        LocalDateTime occurredAt,
        TriageEventData data
) {
}
