package com.historiaclinica.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "diagnosticos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_diagnostico_atencion"))
    private AtencionMedica atencion;

    @Column(nullable = false, length = 10)
    private String codigo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false)
    private Short orden = 0;
}
