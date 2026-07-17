package com.historiaclinica.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosticoDTO {

    private String id;

    @NotBlank
    private String codigo;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String tipo;

    private Short orden;
}
