package com.hospitalizacion.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TrasladoRequest {

    @NotNull
    private UUID nuevaCamaId;

    @NotBlank
    private String motivo;
}
