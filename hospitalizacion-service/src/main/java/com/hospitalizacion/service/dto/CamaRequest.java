package com.hospitalizacion.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CamaRequest {

    @NotBlank(message = "El numero de cama es obligatorio")
    private String numero;

    @NotBlank(message = "El servicio es obligatorio")
    private String servicio;
}
