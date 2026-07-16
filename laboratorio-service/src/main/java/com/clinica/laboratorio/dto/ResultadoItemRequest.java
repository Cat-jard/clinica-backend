package com.clinica.laboratorio.dto;

public record ResultadoItemRequest(
        String examenId,
        String resultado,
        String unidad,
        String valorRef,
        Boolean critico,
        String observaciones
) {
}
