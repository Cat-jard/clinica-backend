package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.PhysicalEvaluation;
import com.service.consulta.consulta_service.dtos.sections.PhysicalEvaluationDTO;
import org.springframework.stereotype.Component;

@Component
public class PhysicalEvaluationMapper {
    public PhysicalEvaluation toEntity(PhysicalEvaluationDTO dto) {
        if (dto == null) {
            return null;
        }
        PhysicalEvaluation physicalEvaluation = new PhysicalEvaluation();
        updateEntity(physicalEvaluation, dto);
        return physicalEvaluation;
    }

    public PhysicalEvaluationDTO toDTO(PhysicalEvaluation entity) {
        if (entity == null) {
            return null;
        }
        return new PhysicalEvaluationDTO(
                entity.getGeneralExam(),
                entity.getHeadNeck(),
                entity.getThoraxLungs(),
                entity.getHeart(),
                entity.getAbdomen(),
                entity.getExtremities(),
                entity.getNeurological(),
                entity.getOtherFindings()
        );
    }

    public void updateEntity(PhysicalEvaluation entity, PhysicalEvaluationDTO dto) {
        if (entity == null) {
            return;
        }
        entity.setGeneralExam(dto.generalExam());
        entity.setHeadNeck(dto.headNeck());
        entity.setThoraxLungs(dto.thoraxLungs());
        entity.setHeart(dto.heart());
        entity.setAbdomen(dto.abdomen());
        entity.setExtremities(dto.extremities());
        entity.setNeurological(dto.neurological());
        entity.setOtherFindings(dto.otherFindings());
    }
}
