package com.hospitalizacion.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class AutorizacionRequest {

    private String representanteNombre;
    private String representanteDni;
    private String firmaBase64;
}
