package com.historiaclinica.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "items_examen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_item_examen"))
    private SolicitudExamen solicitud;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(name = "origen_muestra", length = 100)
    private String origenMuestra;

    @Column(length = 100)
    private String ayuno;

    @Column(nullable = false)
    private Boolean urgente = false;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;
}
