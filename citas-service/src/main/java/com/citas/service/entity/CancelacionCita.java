package com.citas.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cancelaciones_citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CancelacionCita {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false, unique = true,
                foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_cancelacion_cita"))
    private Cita cita;

    @Column(nullable = false, length = 200)
    private String motivo;

    @Column(nullable = false, length = 100)
    private String canceladoPor;

    @Column(name = "fecha_cancelacion", nullable = false)
    private LocalDateTime fechaCancelacion;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
