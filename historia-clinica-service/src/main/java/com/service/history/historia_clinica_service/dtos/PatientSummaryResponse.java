package com.service.history.historia_clinica_service.dtos;

import com.service.history.historia_clinica_service.domain.SexType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PatientSummaryResponse(
        UUID patientId,
        String fullname,
        String dni,
        Short age,
        SexType sex,
        Short systolicPressure,
        Short diastolicPressure,
        Short spo2,
        BigDecimal weight,
        BigDecimal height,
        List<String> allergies,
        LocalDateTime updatedAt
) {

}
