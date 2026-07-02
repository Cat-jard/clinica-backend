package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.Diagnosis;
import com.service.consulta.consulta_service.dtos.sections.DiagnosisDTO;
import org.springframework.stereotype.Component;

@Component
public class DiagnosisMapper {
    public Diagnosis toEntity(DiagnosisDTO dto) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setCie10code(dto.cie10code());
        diagnosis.setDescription(dto.description());
        diagnosis.setPrincipal(dto.principal());
        return diagnosis;
    }

    public DiagnosisDTO toDTO(Diagnosis entity) {
        if (entity == null) {
            return null;
        }
        return new DiagnosisDTO(
                entity.getCie10code(),
                entity.getDescription(),
                entity.getPrincipal()
        );
    }
}
