package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prescription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescribedMedication> medication = new ArrayList<>();

    public void addMedication(PrescribedMedication medicine) {
        medication.add(medicine);
        medicine.setPrescription(this);
    }

    public void removeMedication(PrescribedMedication medicine) {
        medication.remove(medicine);
        medicine.setPrescription(null);
    }

    public void replaceMedication(List<PrescribedMedication> medication) {
        this.medication.clear();
        if (medication == null) {
            return;
        }
        medication.forEach(this::addMedication);

    }
}

