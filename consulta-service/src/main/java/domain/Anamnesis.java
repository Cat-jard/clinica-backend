package domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "anamnesis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anamnesis {
    @Id
    private Long consultationId;

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
