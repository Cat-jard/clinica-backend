package com.service.consulta.consulta_service.domain;

import com.service.consulta.consulta_service.domain.sections.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "consultation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID appointmentId;
    private UUID patientId;
    private UUID doctorId;
    @Enumerated(EnumType.STRING)
    private ConsultationType consultationType;

    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    //One to one
    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Anamnesis anamnesis;
    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private PhysicalEvaluation physicalEvaluation;
    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClinicalPlan clinicalPlan;
    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Prescription prescription;
    //One to many
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diagnosis> diagnoses = new ArrayList<>();
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrentMedication> currentMedicationList = new ArrayList<>();
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcedurePerformed> procedures = new ArrayList<>();

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Interconsultation> interconsultations = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }


    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }


    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }


    public void setConsultationType(ConsultationType consultationType) {
        this.consultationType = consultationType;
    }


    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }


    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }


    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAnamnesis(Anamnesis anamnesis) {
        this.anamnesis = anamnesis;
        if (anamnesis != null) {
            anamnesis.setConsultation(this);
        }
    }

    public void setPhysicalEvaluation(PhysicalEvaluation physicalEvaluation) {
        this.physicalEvaluation = physicalEvaluation;
        if (physicalEvaluation != null) {
            physicalEvaluation.setConsultation(this);
        }
    }

    public void setClinicalPlan(ClinicalPlan clinicalPlan) {
        this.clinicalPlan = clinicalPlan;
        if (clinicalPlan != null) {
            clinicalPlan.setConsultation(this);
        }
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
        if (prescription != null) {
            prescription.setConsultation(this);
        }
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
        diagnosis.setConsultation(this);
    }

    public void removeDiagnosis(Diagnosis diagnosis) {
        diagnoses.remove(diagnosis);
        diagnosis.setConsultation(null);
    }

    public void replaceDiagnoses(List<Diagnosis> diagnoses) {
        this.diagnoses.clear();
        if (diagnoses == null) {
            return;
        }
        diagnoses.forEach(this::addDiagnosis);
    }

    public void addCurrentMedication(CurrentMedication medication) {
        currentMedicationList.add(medication);
        medication.setConsultation(this);
    }

    public void removeCurrentMedication(CurrentMedication medication) {
        currentMedicationList.remove(medication);
        medication.setConsultation(null);
    }

    public void replaceCurrentMedication(List<CurrentMedication> currentMedicationList) {
        this.currentMedicationList.clear();
        if (currentMedicationList == null) {
            return;
        }
        currentMedicationList.forEach(this::addCurrentMedication);
    }

    public void addProcedures(ProcedurePerformed procedure) {
        procedures.add(procedure);
        procedure.setConsultation(this);
    }

    public void removeProcedures(ProcedurePerformed procedure) {
        procedures.remove(procedure);
        procedure.setConsultation(null);
    }

    public void replaceProcedures(List<ProcedurePerformed> procedures) {
        this.procedures.clear();
        if (procedures == null) {
            return;
        }
        procedures.forEach(this::addProcedures);
    }

    public void addInterconsultation(Interconsultation interconsultation) {
        interconsultations.add(interconsultation);
        interconsultation.setConsultation(this);
    }

    public void removeInterconsultation(Interconsultation interconsultation) {
        interconsultations.remove(interconsultation);
        interconsultation.setConsultation(null);
    }

    public void replaceInteconsultation(List<Interconsultation> interconsultations) {
        this.interconsultations.clear();
        if (interconsultations == null) {
            return;
        }
        interconsultations.forEach(this::addInterconsultation);
    }
}
