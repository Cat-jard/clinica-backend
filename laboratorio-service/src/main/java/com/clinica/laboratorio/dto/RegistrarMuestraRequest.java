package com.clinica.laboratorio.dto;

public record RegistrarMuestraRequest(
        String origenMuestra,
        String condicion,
        String observaciones
) {
}
