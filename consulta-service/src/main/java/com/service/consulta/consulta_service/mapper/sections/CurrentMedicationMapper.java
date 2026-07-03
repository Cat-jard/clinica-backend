package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.CurrentMedication;
import com.service.consulta.consulta_service.dtos.sections.CurrentMedicationDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrentMedicationMapper {
    public CurrentMedication toEntity(CurrentMedicationDTO dto) {
        CurrentMedication currentMedication = new CurrentMedication();
        currentMedication.setMedicineId(dto.medicineId());
        currentMedication.setDose(dto.dose());
        currentMedication.setFrequency(dto.frequency());
        return currentMedication;
    }

    public CurrentMedicationDTO toDTO(CurrentMedication entity) {
        if (entity == null) {
            return null;
        }
        return new CurrentMedicationDTO(
                entity.getMedicineId(),
                entity.getDose(),
                entity.getFrequency()
        );
    }
}
