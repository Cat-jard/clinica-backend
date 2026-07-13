package com.triaje.service.rabbit;

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
) {
}
