package com.historiaclinica.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRecetaDTO {

    private String id;

    @NotBlank
    private String medicamento;

    @NotBlank
    private String concentracion;

    @NotBlank
    private String presentacion;

    @NotBlank
    private String dosis;

    @NotBlank
    private String via;

    @NotBlank
    private String frecuencia;

    @NotBlank
    private String duracion;

    @NotNull
    private Integer cantidad;

    private String indicacionesEspeciales;
}
