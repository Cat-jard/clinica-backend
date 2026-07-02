package domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "physica_evaluation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicalEvaluation {
    @Id
    private Long consultationId;

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
