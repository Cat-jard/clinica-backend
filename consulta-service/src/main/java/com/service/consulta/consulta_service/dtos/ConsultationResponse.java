package com.service.consulta.consulta_service.dtos;

import com.service.consulta.consulta_service.domain.ConsultationStatus;
import com.service.consulta.consulta_service.domain.ConsultationType;
import com.service.consulta.consulta_service.domain.sections.*;
import com.service.consulta.consulta_service.dtos.sections.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public record ConsultationResponse(
        Long id,
        Long appointmentId,
        Long patientId,
        Long doctorId,
        ConsultationType consultationType,
        ConsultationStatus status,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        LocalDateTime createdAt,
        AnamnesisDTO anamnesis,
        PhysicalEvaluationDTO physicalEvaluation,
        ClinicalPlanDTO clinicalPlan,
        PrescriptionDTO prescription,
        List<DiagnosisDTO> diagnoses,
        List<CurrentMedicationDTO> currentMedicationList,
        List<ProcedurePerformedDTO> procedures
) {
}
