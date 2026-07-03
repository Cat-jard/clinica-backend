package com.service.consulta.consulta_service.dtos.event;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentHistoryProjection(
        String area,
        String doctorName,
        LocalDate date,
        LocalTime time,
        String status,
        String summary
) {
}
