package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.ProcedurePerformed;
import com.service.consulta.consulta_service.dtos.sections.ProcedurePerformedDTO;
import org.springframework.stereotype.Component;

@Component
public class ProcedurePerformedMapper {
    public ProcedurePerformed toEntity(ProcedurePerformedDTO dto) {
        ProcedurePerformed procedure = new ProcedurePerformed();
        procedure.setDescription(dto.description());
        return procedure;
    }

    public ProcedurePerformedDTO toDTO(ProcedurePerformed entity) {
        if (entity == null) {
            return null;
        }
        return new ProcedurePerformedDTO(
                entity.getDescription()
        );
    }
}
