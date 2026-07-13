package com.service.history.historia_clinica_service.dtos;

import com.service.history.historia_clinica_service.domain.SexType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PatientSummaryRequest(
        UUID patientId,
        String fullname,
        String dni,
        Short age,
        SexType sex,
        Integer systolicPressure,
        Integer diastolicPressure,
        Integer spo2,
        BigDecimal weight,
        BigDecimal height,
        List<String> allergies
) {
}
