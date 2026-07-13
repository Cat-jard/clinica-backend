package com.clinica.radiologia.entity;

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
@Table(name = "estudios_radiologia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EstudioRadiologia {
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

    @Column(nullable = false, length = 80)
    private String modalidad;

    @Column(name = "tipo_estudio", nullable = false, length = 160)
    private String tipoEstudio;

    @Column(name = "region_anatomica", nullable = false, length = 80)
    private String regionAnatomica;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_estudio")
    private LocalDateTime fechaEstudio;

    @Column(nullable = false, length = 20)
    private String prioridad;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "hallazgos", columnDefinition = "TEXT")
    private String hallazgos;

    @Column(name = "impresion_diagnostica", columnDefinition = "TEXT")
    private String impresionDiagnostica;

    @Column(columnDefinition = "TEXT")
    private String recomendaciones;

    @Column(name = "codigo_cie10", length = 20)
    private String codigoCie10;

    @Column(name = "dosis_radiacion", length = 80)
    private String dosisRadiacion;

    @Column(name = "firmado_en")
    private LocalDateTime firmadoEn;

    @Column(name = "es_critico", nullable = false)
    private boolean esCritico;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "estudio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SerieRadiologia> series = new ArrayList<>();

    public void agregarSerie(SerieRadiologia serie) {
        serie.setEstudio(this);
        series.add(serie);
    }
}
