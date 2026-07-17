package com.historiaclinica.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirmarAtencionRequest {

    @NotNull
    private Long medicoId;

    @NotBlank
    private String firmaBase64;
}
