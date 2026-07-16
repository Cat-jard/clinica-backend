package com.clinica.laboratorio.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ordenes_laboratorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrdenLaboratorio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nro_orden", nullable = false, unique = true, length = 30)
    private String nroOrden;

    @Column(name = "atencion_id")
    private UUID atencionId;

    @Column(name = "paciente_id")
    private UUID pacienteId;

    @Column(name = "paciente_nombre", nullable = false, length = 120)
    private String pacienteNombre;

    @Column(name = "paciente_apellidos", nullable = false, length = 160)
    private String pacienteApellidos;

    @Column(name = "paciente_dni", nullable = false, length = 20)
    private String pacienteDni;

    @Column(name = "paciente_edad")
    private Integer pacienteEdad;

    @Column(name = "paciente_sexo", length = 10)
    private String pacienteSexo;

    @Column(name = "nro_historia", length = 30)
    private String nroHistoria;

    @Column(name = "medico_solicitante", nullable = false, length = 160)
    private String medicoSolicitante;

    @Column(name = "especialidad_medico", nullable = false, length = 120)
    private String especialidadMedico;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(nullable = false, length = 20)
    private String prioridad;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "origen_muestra", length = 80)
    private String origenMuestra;

    @Column(length = 30)
    private String ayuno;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamenLaboratorio> examenes = new ArrayList<>();

    public void agregarExamen(ExamenLaboratorio examen) {
        examen.setOrden(this);
        examenes.add(examen);
    }
}
