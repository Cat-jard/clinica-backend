package com.citas.service.mapper;

import com.citas.service.dto.CitaRequest;
import com.citas.service.dto.CitaResponse;
import com.citas.service.entity.Cita;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CitaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroHistoria", ignore = true)
    @Mapping(target = "pacienteNombre", ignore = true)
    @Mapping(target = "medicoNombre", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cita toEntity(CitaRequest request);

    CitaResponse toResponse(Cita cita);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroHistoria", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(CitaRequest request, @MappingTarget Cita cita);
}
