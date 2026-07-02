package com.hospitalizacion.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "camas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Cama {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(nullable = false, length = 50)
    private String servicio;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "paciente_id")
    private UUID pacienteId;

    @Column(name = "paciente_nombre", length = 300)
    private String pacienteNombre;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "medico_nombre", length = 200)
    private String medicoNombre;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
