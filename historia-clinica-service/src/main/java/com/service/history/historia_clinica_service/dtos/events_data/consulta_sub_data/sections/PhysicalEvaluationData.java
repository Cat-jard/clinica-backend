package com.service.history.historia_clinica_service.dtos.events_data.consulta_sub_data.sections;


public record PhysicalEvaluationData(
        String generalExam,
        String headNeck,
        String thoraxLungs,
        String heart,
        String abdomen,
        String extremities,
        String neurological,
        String otherFindings
) {
}
