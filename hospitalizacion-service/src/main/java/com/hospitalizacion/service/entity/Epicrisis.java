package com.hospitalizacion.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "epicrisis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Epicrisis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospitalizacion_id", nullable = false, unique = true,
                foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_epicrisis_hospitalizacion"))
    private Hospitalizacion hospitalizacion;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "motivo_ingreso", nullable = false, columnDefinition = "TEXT")
    private String motivoIngreso;

    @Column(name = "diagnostico_ingreso", nullable = false, length = 200)
    private String diagnosticoIngreso;

    @Column(name = "diagnostico_final", nullable = false, length = 200)
    private String diagnosticoFinal;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String evolucion;

    @Column(columnDefinition = "TEXT")
    private String procedimientos;

    @Column(columnDefinition = "TEXT")
    private String complicaciones;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String tratamiento;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String recomendaciones;

    @Column(name = "proxima_cita")
    private LocalDate proximaCita;

    @Column(nullable = false)
    private Boolean firmado;

    @Column(name = "firma_base64", columnDefinition = "TEXT")
    private String firmaBase64;

    @Column(name = "medico_id", nullable = false, length = 100)
    private String medicoId;

    @Column(name = "medico_nombre", nullable = false, length = 200)
    private String medicoNombre;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
