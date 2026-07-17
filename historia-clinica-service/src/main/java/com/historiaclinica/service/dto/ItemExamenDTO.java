package com.historiaclinica.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemExamenDTO {

    private String id;

    @NotBlank
    private String tipo;

    @NotBlank
    private String nombre;

    private String origenMuestra;
    private String ayuno;
    private Boolean urgente;
    private String indicaciones;
}
