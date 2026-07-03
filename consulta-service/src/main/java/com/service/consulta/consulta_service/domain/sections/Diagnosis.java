package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "diagnosis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String cie10code;
    private String description;
    private Boolean principal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
}
