package com.historiaclinica.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ExamenFisico {

    @Column(name = "examen_general", columnDefinition = "TEXT")
    private String examenGeneral;

    @Column(name = "examen_cabeza_cuello", columnDefinition = "TEXT")
    private String cabezaCuello;

    @Column(name = "examen_torax_pulmones", columnDefinition = "TEXT")
    private String toraxPulmones;

    @Column(name = "examen_corazon", columnDefinition = "TEXT")
    private String corazon;

    @Column(name = "examen_abdomen", columnDefinition = "TEXT")
    private String abdomen;

    @Column(name = "examen_extremidades", columnDefinition = "TEXT")
    private String extremidades;

    @Column(name = "examen_neurologico", columnDefinition = "TEXT")
    private String neurologico;

    @Column(name = "examen_otros", columnDefinition = "TEXT")
    private String otros;
}
