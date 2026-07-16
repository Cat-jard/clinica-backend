package com.clinica.laboratorio.dto;

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
    public boolean esLaboratorio() {
        return "Laboratorio".equalsIgnoreCase(tipo);
    }

    public boolean esImagen() {
        return "Imagenes".equalsIgnoreCase(sinAcentos(tipo)) || "Images".equalsIgnoreCase(tipo);
    }

    public boolean urgenteNormalizado() {
        return Boolean.TRUE.equals(urgente);
    }

    private static String sinAcentos(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("á", "a").replace("Á", "A")
                .replace("é", "e").replace("É", "E")
                .replace("í", "i").replace("Í", "I")
                .replace("ó", "o").replace("Ó", "O")
                .replace("ú", "u").replace("Ú", "U");
    }
}
