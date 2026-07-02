package com.service.history.historia_clinica_service.dtos;

import com.service.history.historia_clinica_service.domain.EventType;
import com.service.history.historia_clinica_service.dtos.events_data.TimeLineEventData;

import java.time.LocalDateTime;

public record TimelineEventResponse(
        Long id,
        EventType type,
        LocalDateTime ocurredAt,
        String sourceService,
        TimeLineEventData data
) {
}
