package com.historiaclinica.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "items_receta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_item_receta"))
    private Receta receta;

    @Column(nullable = false, length = 200)
    private String medicamento;

    @Column(nullable = false, length = 50)
    private String concentracion;

    @Column(nullable = false, length = 100)
    private String presentacion;

    @Column(nullable = false, length = 100)
    private String dosis;

    @Column(nullable = false, length = 20)
    private String via;

    @Column(nullable = false, length = 100)
    private String frecuencia;

    @Column(nullable = false, length = 100)
    private String duracion;

    @Column(nullable = false)
    private Integer cantidad = 1;

    @Column(name = "indicaciones_especiales", columnDefinition = "TEXT")
    private String indicacionesEspeciales;
}
