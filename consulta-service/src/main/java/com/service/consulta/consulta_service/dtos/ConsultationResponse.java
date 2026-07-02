package com.service.consulta.consulta_service.dtos;

import com.service.consulta.consulta_service.domain.ConsultationStatus;
import com.service.consulta.consulta_service.domain.ConsultationType;
import com.service.consulta.consulta_service.domain.sections.*;
import com.service.consulta.consulta_service.dtos.sections.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ConsultationResponse(
        UUID id,
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
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
