package com.service.history.historia_clinica_service.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_history_event")
public class HistoryEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Reference to the id of another microservice, this id is used by the frontend to
    // then call said microservices when more details want to be shown
    // since not all personal data is handled by this service
    private Long patientId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private LocalDateTime ocurredAt;
    private String sourceService;
    private Long referenceId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode projection;

    private LocalDateTime createdAt;
}
