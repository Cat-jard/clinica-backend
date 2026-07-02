package com.triaje.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MedicamentoKardexDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String dosis;

    @NotBlank
    private String via;

    @NotBlank
    private String hora;
}
