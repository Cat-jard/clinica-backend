package com.service.history.historia_clinica_service.dtos.events_data;


import com.service.history.historia_clinica_service.domain.SexType;

import java.math.BigDecimal;

public record TriageEventData(
        String nurse,
        String reason,
        String priority,
        String consciousness,
        Integer pain,
        Integer systolicPressure,
        Integer diastolicPressure,
        Integer heartRate,
        Integer respiratoryRate,
        BigDecimal temperature,
        Integer spo2,
        BigDecimal weight,
        BigDecimal height
) implements TimeLineEventData {
}
