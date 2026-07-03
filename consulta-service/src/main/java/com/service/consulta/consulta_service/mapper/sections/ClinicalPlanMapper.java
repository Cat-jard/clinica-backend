package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.ClinicalPlan;
import com.service.consulta.consulta_service.dtos.sections.ClinicalPlanDTO;
import org.springframework.stereotype.Component;

@Component
public class ClinicalPlanMapper {
    public ClinicalPlan toEntity(ClinicalPlanDTO dto) {
        if (dto == null) {
            return null;
        }
        ClinicalPlan clinicalPlan = new ClinicalPlan();
        updateEntity(clinicalPlan, dto);
        return clinicalPlan;
    }

    public ClinicalPlanDTO toDTO(ClinicalPlan entity) {
        if (entity == null) {
            return null;
        }
        return new ClinicalPlanDTO(
                entity.getGeneralIndications()
        );
    }

    public void updateEntity(ClinicalPlan entity, ClinicalPlanDTO dto) {
        if (entity == null) {
            return;
        }
        entity.setGeneralIndications(dto.generalIndications());
    }
}
