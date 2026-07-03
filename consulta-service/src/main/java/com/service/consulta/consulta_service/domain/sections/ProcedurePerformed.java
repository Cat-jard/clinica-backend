package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "prescription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcedurePerformed {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
}
