package com.service.consulta.consulta_service.domain.sections;

import com.service.consulta.consulta_service.domain.Consultation;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "anamnesis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anamnesis {
    @Id
    private UUID consultationId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    private String chiefComplaint;
    private String presentIllness;
    private String personalHistory;
    private String surgicalHistory;
    private String familyHistory;
    private String lifestyle;

}
