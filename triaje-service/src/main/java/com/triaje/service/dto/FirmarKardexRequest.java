package com.triaje.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FirmarKardexRequest {

    @NotBlank
    private String firmaBase64;
}
