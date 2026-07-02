package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.Interconsultation;
import com.service.consulta.consulta_service.dtos.sections.InterconsultationRequest;
import com.service.consulta.consulta_service.dtos.sections.InterconsultationResponse;
import org.springframework.stereotype.Component;

@Component
public class InterconsultationMapper {
    public Interconsultation toEntity(InterconsultationRequest dto) {
        Interconsultation interconsultation = new Interconsultation();
        interconsultation.setSpecialtyId(dto.specialtyId());
        interconsultation.setDoctorId(dto.doctorId());
        interconsultation.setReason(dto.reason());
        interconsultation.setRelevantFindings(dto.relevantFindings());
        interconsultation.setClinicalQuestion(dto.clinicalQuestion());
        interconsultation.setPriority(dto.priority());
        return interconsultation;
    }

    public InterconsultationResponse toResponse(Interconsultation entity) {
        if (entity == null) {
            return null;
        }
        return new InterconsultationResponse(
                entity.getId(),
                entity.getSpecialtyId(),
                entity.getDoctorId(),
                entity.getReason(),
                entity.getRelevantFindings(),
                entity.getClinicalQuestion(),
                entity.getPriority(),
                entity.getCreatedAt()
        );
    }
}
