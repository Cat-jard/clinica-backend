package com.service.consulta.consulta_service.mapper;

import com.service.consulta.consulta_service.domain.Consultation;
import com.service.consulta.consulta_service.dtos.ConsultationRequest;
import com.service.consulta.consulta_service.dtos.ConsultationResponse;
import com.service.consulta.consulta_service.dtos.ConsultationSummaryResponse;
import com.service.consulta.consulta_service.mapper.sections.*;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {
    private final AnamnesisMapper anamnesisMapper;
    private final ClinicalPlanMapper clinicalPlanMapper;
    private final CurrentMedicationMapper currentMedicationMapper;
    private final DiagnosisMapper diagnosisMapper;
    private final InterconsultationMapper interconsultationMapper;
    private final PhysicalEvaluationMapper physicalEvaluationMapper;
    private final PrescriptionMapper prescriptionMapper;
    private final ProcedurePerformedMapper procedurePerformedMapper;

    public ConsultationMapper(AnamnesisMapper anamnesisMapper, ClinicalPlanMapper clinicalPlanMapper, CurrentMedicationMapper currentMedicationMapper, DiagnosisMapper diagnosisMapper, InterconsultationMapper interconsultationMapper, PhysicalEvaluationMapper physicalEvaluationMapper, PrescriptionMapper prescriptionMapper, ProcedurePerformedMapper procedurePerformedMapper) {
        this.anamnesisMapper = anamnesisMapper;
        this.clinicalPlanMapper = clinicalPlanMapper;
        this.currentMedicationMapper = currentMedicationMapper;
        this.diagnosisMapper = diagnosisMapper;
        this.interconsultationMapper = interconsultationMapper;
        this.physicalEvaluationMapper = physicalEvaluationMapper;
        this.prescriptionMapper = prescriptionMapper;
        this.procedurePerformedMapper = procedurePerformedMapper;
    }


    public Consultation toEntity(ConsultationRequest dto) {
        Consultation consultation = new Consultation();
        updateEntity(consultation, dto);
        return consultation;
    }

    public void updateEntity(Consultation consultation, ConsultationRequest dto) {
        consultation.setAppointmentId(dto.appointmentId());
        consultation.setPatientId(dto.patientId());
        consultation.setDoctorId(dto.doctorId());
        consultation.setConsultationType(dto.consultationType());
        consultation.setStatus(dto.status());
        consultation.setStartedAt(dto.startedAt());
        consultation.setFinishedAt(dto.finishedAt());

        //Anamnesis
        if (consultation.getAnamnesis() == null) {
            consultation.setAnamnesis(
                    anamnesisMapper.toEntity(dto.anamnesis())
            );
        } else {
            anamnesisMapper.updateEntity(
                    consultation.getAnamnesis(),
                    dto.anamnesis()
            );
        }
        //Physical evaluation
        if (consultation.getPhysicalEvaluation() == null) {
            consultation.setPhysicalEvaluation(
                    physicalEvaluationMapper.toEntity(dto.physicalEvaluation())
            );
        } else {
            physicalEvaluationMapper.updateEntity(
                    consultation.getPhysicalEvaluation(),
                    dto.physicalEvaluation()
            );
        }
        //Clincal plan
        if (consultation.getClinicalPlan() == null) {
            consultation.setClinicalPlan(
                    clinicalPlanMapper.toEntity(dto.clinicalPlan())
            );
        } else {
            clinicalPlanMapper.updateEntity(
                    consultation.getClinicalPlan(),
                    dto.clinicalPlan()
            );
        }
        //Prescription
        if (consultation.getPrescription() == null) {
            consultation.setPrescription(
                    prescriptionMapper.toEntity(dto.prescription())
            );
        } else {
            prescriptionMapper.updateEntity(
                    consultation.getPrescription(),
                    dto.prescription()
            );
        }


        consultation.replaceDiagnoses(dto.diagnoses().stream().map(diagnosisMapper::toEntity).toList());
        consultation.replaceCurrentMedication(dto.currentMedicationList().stream().map(currentMedicationMapper::toEntity).toList());
        consultation.replaceProcedures(dto.procedures().stream().map(procedurePerformedMapper::toEntity).toList());
        consultation.replaceInteconsultation(dto.interconsultations().stream().map(interconsultationMapper::toEntity).toList());

    }

    public ConsultationResponse toResponse(Consultation consultation) {

        return new ConsultationResponse(

                consultation.getId(),
                consultation.getAppointmentId(),
                consultation.getPatientId(),
                consultation.getDoctorId(),
                consultation.getConsultationType(),
                consultation.getStatus(),
                consultation.getStartedAt(),
                consultation.getFinishedAt(),
                consultation.getCreatedAt(),

                anamnesisMapper.toDTO(consultation.getAnamnesis()),

                physicalEvaluationMapper.toDTO(consultation.getPhysicalEvaluation()),

                clinicalPlanMapper.toDTO(consultation.getClinicalPlan()),

                prescriptionMapper.toDTO(consultation.getPrescription()),

                consultation.getDiagnoses()
                        .stream()
                        .map(diagnosisMapper::toDTO)
                        .toList(),

                consultation.getCurrentMedicationList()
                        .stream()
                        .map(currentMedicationMapper::toDTO)
                        .toList(),

                consultation.getProcedures()
                        .stream()
                        .map(procedurePerformedMapper::toDTO)
                        .toList(),
                consultation.getInterconsultations()
                        .stream()
                        .map(interconsultationMapper::toResponse)
                        .toList()
        );
    }

    public ConsultationSummaryResponse toSummaryResponse(Consultation entity) {
        return new ConsultationSummaryResponse(
                entity.getId(),
                entity.getAppointmentId(),
                entity.getPatientId(),
                entity.getDoctorId(),
                entity.getConsultationType(),
                entity.getStatus(),
                entity.getStartedAt(),
                entity.getFinishedAt()
        );
    }

}
