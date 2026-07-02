package com.triaje.service.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AltaRequest {

    @Pattern(regexp = "DOMICILIARIA|HOSPITALIZACION|TRASLADO",
             message = "Tipo de alta no valida: DOMICILIARIA, HOSPITALIZACION o TRASLADO")
    private String tipoAlta;
}
