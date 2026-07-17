package com.historiaclinica.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "interconsultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interconsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_interconsulta_atencion"))
    private AtencionMedica atencion;

    @Column(name = "especialidad_destino", nullable = false, length = 100)
    private String especialidadDestino;

    @Column(name = "medico_destino", length = 200)
    private String medicoDestino;

    @Column(name = "motivo_interconsulta", nullable = false, columnDefinition = "TEXT")
    private String motivoInterconsulta;

    @Column(name = "hallazgos_relevantes", columnDefinition = "TEXT")
    private String hallazgosRelevantes;

    @Column(name = "pregunta_especialista", columnDefinition = "TEXT")
    private String preguntaEspecialista;

    @Column(nullable = false, length = 20)
    private String urgencia = "Normal";

    @Column(nullable = false, length = 20)
    private String estado = "Enviada";
}
