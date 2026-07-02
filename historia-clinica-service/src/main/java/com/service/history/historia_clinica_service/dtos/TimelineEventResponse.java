package com.service.history.historia_clinica_service.dtos;

import com.service.history.historia_clinica_service.domain.EventType;
import com.service.history.historia_clinica_service.dtos.events_data.TimeLineEventData;

import java.time.LocalDateTime;
import java.util.UUID;

public record TimelineEventResponse(
        UUID id,
        EventType type,
        LocalDateTime occurredAt,
        String sourceService,
        TimeLineEventData data
) {
}
