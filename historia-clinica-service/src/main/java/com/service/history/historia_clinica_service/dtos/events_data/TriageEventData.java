package com.service.history.historia_clinica_service.dtos.events_data;


import com.service.history.historia_clinica_service.domain.SexType;

import java.math.BigDecimal;

public record TriageEventData(
        String enfermero,
        SexType sex,
        Short systolicPressure,
        Short diastolicPressure,
        Short spo2,
        BigDecimal weight,
        BigDecimal height
) implements TimeLineEventData {
}
