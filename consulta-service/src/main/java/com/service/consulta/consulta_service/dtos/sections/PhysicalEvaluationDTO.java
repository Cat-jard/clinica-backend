package com.service.consulta.consulta_service.dtos.sections;


public record PhysicalEvaluationDTO(
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
