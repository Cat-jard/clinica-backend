package com.citas.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancelacionRequest {

    @NotBlank(message = "El motivo de cancelacion es obligatorio")
    private String motivo;

    @NotBlank(message = "El usuario que cancela es obligatorio")
    private String canceladoPor;
}
