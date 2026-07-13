package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.PrescribedMedication;
import com.service.consulta.consulta_service.dtos.sections.PrescribedMedicationDTO;
import org.springframework.stereotype.Component;

@Component
public class PrescribedMedicationMapper {
    public PrescribedMedication toEntity(PrescribedMedicationDTO dto) {
        if (dto == null) {
            return null;
        }
        PrescribedMedication prescribedMedication = new PrescribedMedication();
        updateEntity(prescribedMedication, dto);
        return prescribedMedication;
    }

    public void updateEntity(PrescribedMedication entity, PrescribedMedicationDTO dto) {
        entity.setMedicineId(dto.medicineId());
        entity.setDose(dto.dose());
        entity.setRoute(dto.route());
        entity.setFrequency(dto.frequency());
        entity.setDuration(dto.duration());
        entity.setQuantity(dto.quantity());
        entity.setSpecialInstructions(dto.specialInstructions());
    }

    public PrescribedMedicationDTO toDTO(PrescribedMedication entity) {
        if (entity == null) {
            return null;
        }
        return new PrescribedMedicationDTO(
                entity.getMedicineId(),
                entity.getDose(),
                entity.getRoute(),
                entity.getFrequency(),
                entity.getDuration(),
                entity.getQuantity(),
                entity.getSpecialInstructions()
        );
    }
}
