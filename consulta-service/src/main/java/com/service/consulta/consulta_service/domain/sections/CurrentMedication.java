package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "current_medication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentMedication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID medicineId;
    private String dose;
    private String frequency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
}
