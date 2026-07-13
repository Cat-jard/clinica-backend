package com.service.consulta.consulta_service.dtos;

import com.service.consulta.consulta_service.domain.ConsultationStatus;
import com.service.consulta.consulta_service.domain.ConsultationType;
import com.service.consulta.consulta_service.dtos.sections.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ConsultationRequest(
        @NotNull
        UUID appointmentId,
        @NotNull
        UUID patientId,
        @NotNull
        UUID doctorId,
        @NotNull
        ConsultationType consultationType,
        @NotNull
        ConsultationStatus status,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        AnamnesisDTO anamnesis,
        PhysicalEvaluationDTO physicalEvaluation,
        ClinicalPlanDTO clinicalPlan,
        PrescriptionDTO prescription,
        List<DiagnosisDTO> diagnoses,
        List<CurrentMedicationDTO> currentMedicationList,
        List<ProcedurePerformedDTO> procedures,
        List<InterconsultationRequest> interconsultations
) {
}
