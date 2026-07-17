package com.historiaclinica.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "atenciones_medicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AtencionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "paciente_id", nullable = false)
    private UUID pacienteId;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "medico_nombre", length = 200)
    private String medicoNombre;

    @Column(length = 100)
    private String especialidad;

    @Column(length = 100)
    private String consultorio;

    @Column(name = "fecha_atencion", nullable = false)
    private LocalDate fechaAtencion;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(nullable = false, length = 20)
    private String estado = "BORRADOR";

    @Embedded
    private Anamnesis anamnesis;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "examenGeneral", column = @Column(name = "examen_general")),
        @AttributeOverride(name = "cabezaCuello", column = @Column(name = "examen_cabeza_cuello")),
        @AttributeOverride(name = "toraxPulmones", column = @Column(name = "examen_torax_pulmones")),
        @AttributeOverride(name = "corazon", column = @Column(name = "examen_corazon")),
        @AttributeOverride(name = "abdomen", column = @Column(name = "examen_abdomen")),
        @AttributeOverride(name = "extremidades", column = @Column(name = "examen_extremidades")),
        @AttributeOverride(name = "neurologico", column = @Column(name = "examen_neurologico")),
        @AttributeOverride(name = "otros", column = @Column(name = "examen_otros")),
    })
    private ExamenFisico examenFisico;

    @Column(name = "indicaciones_generales", columnDefinition = "TEXT")
    private String indicacionesGenerales;

    @Column(name = "procedimientos_realizados", columnDefinition = "TEXT")
    private String procedimientosRealizados;

    @Column(name = "firmada_en")
    private LocalDateTime firmadaEn;

    @Column(name = "firma_medico_id")
    private Long firmaMedicoId;

    @Column(name = "firma_base64", columnDefinition = "TEXT")
    private String firmaBase64;

    @Column(name = "firma_hash", length = 64)
    private String firmaHash;

    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<Diagnostico> diagnosticos = new ArrayList<>();

    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receta> recetas = new ArrayList<>();

    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudExamen> solicitudesExamenes = new ArrayList<>();

    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interconsulta> interconsultas = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
