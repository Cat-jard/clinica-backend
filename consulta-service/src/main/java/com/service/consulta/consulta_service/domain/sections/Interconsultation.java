package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import com.service.consulta.consulta_service.domain.InterconsultationPriority;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "interconsultation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interconsultation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "specialty_id", nullable = false)
    private UUID specialtyId;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(nullable = false)
    private String reason;

    @Column(name = "relevant_findings")
    private String relevantFindings;

    @Column(name = "clinical_question", nullable = false)
    private String clinicalQuestion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterconsultationPriority priority;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
