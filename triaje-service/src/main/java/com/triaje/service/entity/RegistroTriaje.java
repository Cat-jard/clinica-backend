package com.triaje.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "registros_triaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RegistroTriaje {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "paciente_id", nullable = false)
    private UUID pacienteId;

    @Column(name = "paciente_nombre", nullable = false, length = 300)
    private String pacienteNombre;

    @Column(name = "paciente_dni", nullable = false, length = 20)
    private String pacienteDni;

    @Column(name = "medico_id")
    private Long medicoId;

    @Column(name = "medico_nombre", length = 200)
    private String medicoNombre;

    @Column(name = "especialidad_id")
    private UUID especialidadId;

    @Column(name = "especialidad_nombre", length = 100)
    private String especialidadNombre;

    @Column(name = "cita_id")
    private UUID citaId;

    @Column(nullable = false, length = 20)
    private String ticket;

    @Column(name = "hora_llegada", nullable = false)
    private LocalTime horaLlegada;

    @Column(name = "fecha_triaje", nullable = false)
    private LocalDate fechaTriaje;

    @Column(name = "motivo_consulta", nullable = false, columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "nivel_conciencia", nullable = false, length = 20)
    private String nivelConciencia;

    @Column(nullable = false)
    private Integer dolor;

    @Column(nullable = false, length = 20)
    private String prioridad;

    @Column(nullable = false, length = 40)
    private String destino;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String justificacion;

    @Column(name = "enfermera_id", nullable = false, length = 100)
    private String enfermeraId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "con_cita", nullable = false)
    private Boolean conCita;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "registroTriaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private SignosVitales signosVitales;
}
