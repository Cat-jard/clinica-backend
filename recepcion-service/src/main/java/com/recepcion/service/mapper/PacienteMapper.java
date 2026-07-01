package com.recepcion.service.mapper;

import com.recepcion.service.dto.CreatePacienteRequest;
import com.recepcion.service.dto.PacienteResponse;
import com.recepcion.service.dto.PacienteResumenResponse;
import com.recepcion.service.entity.Paciente;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nroHistoria", ignore = true)
    @Mapping(target = "consentimiento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "consentimientos", ignore = true)
    Paciente toEntity(CreatePacienteRequest request);

    @Mapping(target = "consentimiento", source = "consentimiento")
    PacienteResponse toResponse(Paciente paciente);

    @Mapping(target = "consentimiento", source = "consentimiento")
    PacienteResumenResponse toResumenResponse(Paciente paciente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nroHistoria", ignore = true)
    @Mapping(target = "consentimiento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "consentimientos", ignore = true)
    void updateEntity(CreatePacienteRequest request, @MappingTarget Paciente paciente);
}
