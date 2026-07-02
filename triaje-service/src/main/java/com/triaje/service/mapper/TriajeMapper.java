package com.triaje.service.mapper;

import com.triaje.service.dto.*;
import com.triaje.service.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TriajeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "signosVitales", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RegistroTriaje toEntity(RegistrarTriajeRequest request);

    @Mapping(target = "signos", source = "signosVitales")
    RegistroTriajeResponse toResponse(RegistroTriaje entity);

    @Mapping(target = "signos", source = "signosVitales")
    List<RegistroTriajeResponse> toResponseList(List<RegistroTriaje> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registroTriaje", ignore = true)
    @Mapping(target = "imc", ignore = true)
    SignosVitales toSignosEntity(SignosVitalesDTO dto);

    SignosVitalesDTO toSignosDTO(SignosVitales entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaHora", ignore = true)
    @Mapping(target = "firmado", ignore = true)
    @Mapping(target = "firmadoPor", ignore = true)
    @Mapping(target = "firmaBase64", ignore = true)
    @Mapping(target = "firmaHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "medicamentos", ignore = true)
    EntradaKardex toKardexEntity(CrearKardexRequest request);

    @Mapping(target = "medicamentos", source = "medicamentos")
    EntradaKardexResponse toKardexResponse(EntradaKardex entity);

    List<EntradaKardexResponse> toKardexResponseList(List<EntradaKardex> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entradaKardex", ignore = true)
    MedicamentoKardex toMedicamentoEntity(MedicamentoKardexDTO dto);

    MedicamentoKardexDTO toMedicamentoDTO(MedicamentoKardex entity);

    List<MedicamentoKardex> toMedicamentoEntityList(List<MedicamentoKardexDTO> dtos);

    List<MedicamentoKardexDTO> toMedicamentoDTOList(List<MedicamentoKardex> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ObservacionPaciente toObservacionEntity(ObservacionPacienteResponse dto);

    ObservacionPacienteResponse toObservacionResponse(ObservacionPaciente entity);

    List<ObservacionPacienteResponse> toObservacionResponseList(List<ObservacionPaciente> entities);
}
