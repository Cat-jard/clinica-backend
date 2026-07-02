package com.service.history.historia_clinica_service.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_summary")
public class PatientSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @Column(nullable = false, length = 200)
    private String fullname;

    @Column(nullable = false, length = 8)
    private String dni;

    private Short age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SexType sex;

    private Short systolicPressure;
    private Short diastolicPressure;
    private Short spo2;
    private BigDecimal weight;
    private BigDecimal height;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> allergies;

    private LocalDateTime updatedAt;
}
