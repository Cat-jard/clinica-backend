package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "clinical_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicalPlan {
    @Id
    private UUID consultationId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
    private String generalIndications;
}
