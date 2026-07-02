package com.triaje.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "signos_vitales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignosVitales {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_triaje_id", nullable = false, unique = true,
                foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_signos_registro_triaje"))
    private RegistroTriaje registroTriaje;

    @Column(name = "pas_sistolica", nullable = false)
    private Integer pasSistolica;

    @Column(name = "pas_diastolica", nullable = false)
    private Integer pasDiastolica;

    @Column(name = "frec_cardiaca", nullable = false)
    private Integer frecCardiaca;

    @Column(name = "frec_respiratoria", nullable = false)
    private Integer frecRespiratoria;

    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal temperatura;

    @Column(nullable = false)
    private Integer spo2;

    @Column(nullable = false, precision = 5, scale = 1)
    private BigDecimal peso;

    @Column(nullable = false)
    private Integer talla;

    @Column(precision = 4, scale = 1)
    private BigDecimal imc;
}
