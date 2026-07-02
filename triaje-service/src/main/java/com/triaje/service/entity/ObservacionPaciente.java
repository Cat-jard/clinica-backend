package com.triaje.service.entity;

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
@Table(name = "observaciones_pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ObservacionPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "paciente_id", nullable = false)
    private UUID pacienteId;

    @Column(name = "paciente_nombre", nullable = false, length = 300)
    private String pacienteNombre;

    @Column(name = "medico_id")
    private Long medicoId;

    @Column(name = "medico_nombre", length = 200)
    private String medicoNombre;

    @Column(length = 100)
    private String especialidad;

    @Column(name = "hora_ingreso", nullable = false)
    private LocalDateTime horaIngreso;

    @Column(nullable = false, length = 10)
    private String prioridad;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Column(nullable = false, length = 30)
    private String estado;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
