package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.Anamnesis;
import com.service.consulta.consulta_service.dtos.sections.AnamnesisDTO;
import org.springframework.stereotype.Component;

@Component
public class AnamnesisMapper {
    public Anamnesis toEntity(AnamnesisDTO dto) {
        if (dto == null) {
            return null;
        }
        Anamnesis anamnesis = new Anamnesis();
        updateEntity(anamnesis, dto);
        return anamnesis;
    }

    public AnamnesisDTO toDTO(Anamnesis entity) {
        if (entity == null) {
            return null;
        }
        return new AnamnesisDTO(
                entity.getChiefComplaint(),
                entity.getPresentIllness(),
                entity.getPersonalHistory(),
                entity.getSurgicalHistory(),
                entity.getFamilyHistory(),
                entity.getLifestyle()
        );
    }

    public void updateEntity(Anamnesis entity, AnamnesisDTO dto) {
        if (entity == null) {
            return;
        }
        entity.setChiefComplaint(dto.chiefComplaint());
        entity.setPresentIllness(dto.presentIllness());
        entity.setPersonalHistory(dto.personalHistory());
        entity.setSurgicalHistory(dto.surgicalHistory());
        entity.setFamilyHistory(dto.familyHistory());
        entity.setLifestyle(dto.lifestyle());
    }
}
