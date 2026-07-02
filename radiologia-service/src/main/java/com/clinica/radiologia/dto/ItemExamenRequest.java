package com.clinica.radiologia.dto;

import jakarta.validation.constraints.NotBlank;

public record ItemExamenRequest(
        String id,
        @NotBlank String tipo,
        @NotBlank String nombre,
        String origenMuestra,
        String ayuno,
        Boolean urgente,
        String indicaciones
) {
    public boolean urgenteNormalizado() {
        return Boolean.TRUE.equals(urgente);
    }
}
