package com.hospitalizacion.service.mapper;

import com.hospitalizacion.service.dto.HospitalizacionResponse;
import com.hospitalizacion.service.entity.Hospitalizacion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HospitalizacionMapper {

    HospitalizacionResponse toResponse(Hospitalizacion entity);
}
