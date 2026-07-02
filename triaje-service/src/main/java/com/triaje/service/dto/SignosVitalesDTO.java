package com.triaje.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SignosVitalesDTO {

    @NotNull
    @Min(0)
    private Integer pasSistolica;

    @NotNull
    @Min(0)
    private Integer pasDiastolica;

    @NotNull
    @Min(0)
    private Integer frecCardiaca;

    @NotNull
    @Min(0)
    private Integer frecRespiratoria;

    @NotNull
    private BigDecimal temperatura;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer spo2;

    @NotNull
    private BigDecimal peso;

    @NotNull
    @Min(1)
    private Integer talla;
}
