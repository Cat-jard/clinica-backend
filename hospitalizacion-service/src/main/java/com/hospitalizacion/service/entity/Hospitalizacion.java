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
@Table(name = "hospitalizaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Hospitalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cama_id", nullable = false)
    private UUID camaId;

    @Column(name = "cama_numero", nullable = false, length = 10)
    private String camaNumero;

    @Column(nullable = false, length = 50)
    private String servicio;

    @Column(name = "paciente_id", nullable = false)
    private UUID pacienteId;

    @Column(name = "paciente_nombre", nullable = false, length = 300)
    private String pacienteNombre;

    @Column(name = "paciente_dni", nullable = false, length = 20)
    private String pacienteDni;

    @Column(name = "medico_id", nullable = false)
    private UUID medicoId;

    @Column(name = "medico_nombre", nullable = false, length = 200)
    private String medicoNombre;

    @Column(name = "motivo_ingreso", nullable = false, columnDefinition = "TEXT")
    private String motivoIngreso;

    @Column(name = "diagnostico_ingreso", nullable = false, length = 200)
    private String diagnosticoIngreso;

    @Column(name = "diagnostico_alta", length = 200)
    private String diagnosticoAlta;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "tipo_alta", length = 30)
    private String tipoAlta;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "user_id_ingreso", nullable = false, length = 100)
    private String userIdIngreso;

    @Column(name = "user_id_alta", length = 100)
    private String userIdAlta;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
