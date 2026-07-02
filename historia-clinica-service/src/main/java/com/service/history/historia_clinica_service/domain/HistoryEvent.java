package com.service.history.historia_clinica_service.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_history_event")
public class HistoryEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //Reference to the id of another microservice, this id is used by the frontend to
    // then call said microservice when more details want to be shown
    // since not all personal data is handled by this service
    private UUID patientId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private LocalDateTime occurredAt;
    private String sourceService;
    private UUID referenceId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode projection;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
