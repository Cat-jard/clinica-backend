package com.clinica.laboratorio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "examenes_laboratorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamenLaboratorio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenLaboratorio orden;

    @Column(nullable = false, length = 160)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String area;

    @Column(length = 120)
    private String analizador;

    @Column(length = 80)
    private String resultado;

    @Column(length = 40)
    private String unidad;

    @Column(name = "valor_ref", length = 120)
    private String valorRef;

    @Column(nullable = false)
    private boolean critico;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}
