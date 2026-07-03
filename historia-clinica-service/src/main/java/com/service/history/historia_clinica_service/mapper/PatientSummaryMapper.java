package com.service.history.historia_clinica_service.mapper;

import com.service.history.historia_clinica_service.domain.PatientSummary;
import com.service.history.historia_clinica_service.dtos.PatientSummaryRequest;
import com.service.history.historia_clinica_service.dtos.PatientSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class PatientSummaryMapper {
    public PatientSummary toEntity(PatientSummaryRequest dto) {
        if (dto == null)
            return null;
        PatientSummary patient = new PatientSummary();
        updateEntity(patient, dto);
        return patient;
    }

    public void updateEntity(PatientSummary entity, PatientSummaryRequest dto) {
        entity.setPatientId(dto.patientId());
        entity.setFullName(dto.fullname());
        entity.setDni(dto.dni());
        entity.setAge(dto.age());
        entity.setSex(dto.sex());

        entity.setSystolicPressure(dto.systolicPressure());
        entity.setDiastolicPressure(dto.diastolicPressure());
        entity.setSpo2(dto.spo2());
        entity.setWeight(dto.weight());
        entity.setHeight(dto.height());

        entity.setAllergies(dto.allergies());
    }

    public PatientSummaryResponse toResponse(PatientSummary entity) {
        return new PatientSummaryResponse(
                entity.getPatientId(),
                entity.getFullName(),
                entity.getDni(),
                entity.getAge(),
                entity.getSex(),
                entity.getSystolicPressure(),
                entity.getDiastolicPressure(),
                entity.getSpo2(),
                entity.getWeight(),
                entity.getHeight(),
                entity.getAllergies(),
                entity.getUpdatedAt()
        );
    }
}
