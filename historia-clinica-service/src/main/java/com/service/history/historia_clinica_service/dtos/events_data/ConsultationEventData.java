package com.service.history.historia_clinica_service.dtos.events_data;

import com.service.history.historia_clinica_service.dtos.events_data.consulta_sub_data.sections.*;

import java.util.List;

public record ConsultationEventData(
        ConsultationType type,
        ConsultationStatus status,
        AnamnesisData anamnesis,
        PhysicalEvaluationData physicalEvaluation,
        ClinicalPlanData clinicalPlan,
        List<DiagnosisData> diagnoses,
        List<CurrentMedicationData> currentMedications,
        PrescriptionData prescription,
        List<InterconsultationData> interconsultations
) implements TimeLineEventData {
}
