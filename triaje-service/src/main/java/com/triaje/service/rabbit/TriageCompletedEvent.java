package com.triaje.service.rabbit;

import java.time.LocalDateTime;
import java.util.UUID;

public record TriageCompletedEvent(
        UUID patientID,
        UUID triageID,
        LocalDateTime ocurredAt,
        TriageEventData data
) {
}
