package com.recepcion.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

@Data
public class CreateConsentimientoRequest {

    @NotBlank(message = "El tipo de consentimiento es obligatorio")
    @Pattern(regexp = "TRATAMIENTO_DATOS|PROCEDIMIENTO|CIRUGIA", message = "Tipo de consentimiento no válido")
    private String tipo;

    @NotBlank(message = "El texto legal es obligatorio")
    private String textoLegal;

    @NotBlank(message = "La versión del texto es obligatoria")
    private String versionTexto;

    private String firmaBase64;

    private Boolean aceptado = false;

    @NotBlank(message = "La IP de origen es obligatoria")
    private String ipOrigen;

    @NotBlank(message = "El ID del usuario es obligatorio")
    private String userId;

    private Map<String, Object> metadata;
}
