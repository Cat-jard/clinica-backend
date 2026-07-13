package com.service.consulta.consulta_service.rabbit;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultationCompletedEvent(
        UUID patientId,
        UUID consultationId,
        UUID appointmentId,
        UUID doctorId,
        LocalDateTime ocurredAt,
        ConsultationEventData data
) {
}
