package com.historiaclinica.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterconsultaDTO {

    private String id;

    @NotBlank
    private String especialidadDestino;

    private String medicoDestino;

    @NotBlank
    private String motivoInterconsulta;

    private String hallazgosRelevantes;
    private String preguntaEspecialista;
    private String urgencia;
    private String estado;
}
