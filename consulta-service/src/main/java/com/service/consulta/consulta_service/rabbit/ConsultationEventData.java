package com.service.consulta.consulta_service.rabbit;

import com.service.consulta.consulta_service.domain.ConsultationStatus;
import com.service.consulta.consulta_service.domain.ConsultationType;
import com.service.consulta.consulta_service.dtos.sections.*;

import java.util.List;

public record ConsultationEventData(
        ConsultationType type,
        ConsultationStatus status,
        AnamnesisDTO anamnesis,
        PhysicalEvaluationDTO physicalEvaluation,
        ClinicalPlanDTO clinicalPlan,
        List<DiagnosisDTO> diagnoses,
        List<CurrentMedicationDTO> currentMedications,
        PrescriptionDTO prescription,
        List<ProcedurePerformedDTO> procedures,
        List<InterconsultationResponse> interconsultations
) {
}
