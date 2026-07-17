package com.historiaclinica.service.mapper;

import com.historiaclinica.service.dto.*;
import com.historiaclinica.service.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AtencionMapper {

    Anamnesis toAnamnesisEntity(AnamnesisDTO dto);

    AnamnesisDTO toAnamnesisDTO(Anamnesis entity);

    ExamenFisico toExamenFisicoEntity(ExamenFisicoDTO dto);

    ExamenFisicoDTO toExamenFisicoDTO(ExamenFisico entity);

    DiagnosticoDTO toDiagnosticoDTO(Diagnostico entity);

    List<DiagnosticoDTO> toDiagnosticoDTOList(List<Diagnostico> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "atencion", ignore = true)
    @Mapping(target = "orden", ignore = true)
    Diagnostico toDiagnosticoEntity(DiagnosticoDTO dto);

    ItemRecetaDTO toItemRecetaDTO(ItemReceta entity);

    List<ItemRecetaDTO> toItemRecetaDTOList(List<ItemReceta> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receta", ignore = true)
    ItemReceta toItemRecetaEntity(ItemRecetaDTO dto);

    RecetaDTO toRecetaDTO(Receta entity);

    List<RecetaDTO> toRecetaDTOList(List<Receta> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "atencion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "firmadaEn", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    Receta toRecetaEntity(RecetaDTO dto);

    ItemExamenDTO toItemExamenDTO(ItemExamen entity);

    List<ItemExamenDTO> toItemExamenDTOList(List<ItemExamen> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "solicitud", ignore = true)
    ItemExamen toItemExamenEntity(ItemExamenDTO dto);

    SolicitudExamenDTO toSolicitudExamenDTO(SolicitudExamen entity);

    List<SolicitudExamenDTO> toSolicitudExamenDTOList(List<SolicitudExamen> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "atencion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    SolicitudExamen toSolicitudExamenEntity(SolicitudExamenDTO dto);

    InterconsultaDTO toInterconsultaDTO(Interconsulta entity);

    List<InterconsultaDTO> toInterconsultaDTOList(List<Interconsulta> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "atencion", ignore = true)
    Interconsulta toInterconsultaEntity(InterconsultaDTO dto);

    AtencionResponse toAtencionResponse(AtencionMedica entity);

    List<AtencionResponse> toAtencionResponseList(List<AtencionMedica> entities);

    @Mapping(target = "diagnosticos", ignore = true)
    @Mapping(target = "diagnosticoPrincipal", expression = "java(extractDiagnosticoPrincipal(entity))")
    HistorialPacienteItem toHistorialItem(AtencionMedica entity);

    List<HistorialPacienteItem> toHistorialItemList(List<AtencionMedica> entities);

    default String extractDiagnosticoPrincipal(AtencionMedica entity) {
        if (entity.getDiagnosticos() == null || entity.getDiagnosticos().isEmpty()) return null;
        return entity.getDiagnosticos().stream()
                .filter(d -> "Principal".equals(d.getTipo()))
                .findFirst()
                .map(d -> d.getCodigo() + " — " + d.getDescripcion())
                .orElse(null);
    }

    default String mapListToString(List<String> list) {
        if (list == null) return null;
        return String.join(",", list);
    }

    default List<String> mapStringToList(String str) {
        if (str == null || str.trim().isEmpty()) return java.util.Collections.emptyList();
        return java.util.Arrays.asList(str.split(","));
    }
}
