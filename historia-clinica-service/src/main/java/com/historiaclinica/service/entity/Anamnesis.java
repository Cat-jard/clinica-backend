package com.historiaclinica.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Anamnesis {

    @Column(name = "anamnesis_motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "anamnesis_enfermedad_actual", columnDefinition = "TEXT")
    private String enfermedadActual;

    @Column(name = "anamnesis_antecedentes_patologicos", columnDefinition = "TEXT")
    private String antecedentesPatologicos;

    @Column(name = "anamnesis_antecedentes_quirurgicos", columnDefinition = "TEXT")
    private String antecedentesQuirurgicos;

    @Column(name = "anamnesis_antecedentes_alergicos", columnDefinition = "TEXT")
    private String antecedentesAlergicos;

    @Column(name = "anamnesis_antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "anamnesis_habitos", columnDefinition = "TEXT")
    private String habitos;

    @Column(name = "anamnesis_medicacion_actual", columnDefinition = "TEXT")
    private String medicacionActual;
}
