package com.service.consulta.consulta_service.mapper.sections;

import com.service.consulta.consulta_service.domain.sections.PrescribedMedication;
import com.service.consulta.consulta_service.domain.sections.Prescription;
import com.service.consulta.consulta_service.dtos.sections.PrescribedMedicationDTO;
import com.service.consulta.consulta_service.dtos.sections.PrescriptionDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrescriptionMapper {
    private final PrescribedMedicationMapper mapper;

    public PrescriptionMapper(PrescribedMedicationMapper mapper) {
        this.mapper = mapper;
    }

    public Prescription toEntity(PrescriptionDTO dto) {
        if (dto == null) {
            return null;
        }
        Prescription prescription = new Prescription();
        for (PrescribedMedicationDTO medicationDTO : dto.medication()) {
            prescription.addMedication(mapper.toEntity(medicationDTO));
        }
        return prescription;

    }

    public void updateEntity(Prescription entity, PrescriptionDTO dto) {
        entity.replaceMedication(dto.medication().stream().map(mapper::toEntity).toList());
    }

    public PrescriptionDTO toDTO(Prescription entity) {
        if (entity == null) {
            return null;
        }
        return new PrescriptionDTO(
                entity.getMedication().stream().map(mapper::toDTO).toList()
        );
    }
}
