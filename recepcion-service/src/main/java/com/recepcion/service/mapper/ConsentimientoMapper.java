package com.recepcion.service.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recepcion.service.dto.ConsentimientoResponse;
import com.recepcion.service.dto.CreateConsentimientoRequest;
import com.recepcion.service.entity.ConsentimientoInformado;
import org.mapstruct.*;

import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsentimientoMapper {

    ObjectMapper JSON = new ObjectMapper();

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "textoLegalHash", ignore = true)
    @Mapping(target = "firmaHash", ignore = true)
    @Mapping(target = "fechaExposicion", ignore = true)
    @Mapping(target = "fechaFirma", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "metadata", source = "metadata", qualifiedByName = "metadataToJson")
    ConsentimientoInformado toEntity(CreateConsentimientoRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "metadata", source = "metadata", qualifiedByName = "jsonToMetadata")
    ConsentimientoResponse toResponse(ConsentimientoInformado entity);

    @Named("metadataToJson")
    default String metadataToJson(Map<String, Object> metadata) {
        if (metadata == null || metadata.isEmpty()) return null;
        try {
            return JSON.writeValueAsString(metadata);
        } catch (Exception e) {
            throw new RuntimeException("Error al serializar metadatos", e);
        }
    }

    @Named("jsonToMetadata")
    default Map<String, Object> jsonToMetadata(String metadata) {
        if (metadata == null || metadata.isBlank()) return null;
        try {
            return JSON.readValue(metadata, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar metadatos", e);
        }
    }
}
