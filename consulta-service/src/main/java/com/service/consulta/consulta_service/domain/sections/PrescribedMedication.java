package com.service.consulta.consulta_service.domain.sections;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "prescribed_medication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescribedMedication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID medicineId;
    private String dose;
    private String route;
    private String frequency;
    private String duration;
    private String quantity;
    private String specialInstructions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

}
