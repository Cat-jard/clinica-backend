package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "physical_evaluation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicalEvaluation {
    @Id
    private UUID consultationId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    private String generalExam;
    private String headNeck;
    private String thoraxLungs;
    private String heart;
    private String abdomen;
    private String extremities;
    private String neurological;
    private String otherFindings;
}
