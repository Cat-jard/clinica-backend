package com.clinica.radiologia.dto;

public record PacienteRadiologiaDto(
        String id,
        String nombre,
        String apellidos,
        String dni,
        Integer edad,
        String sexo,
        String nroHistoria
) {
    public PacienteRadiologiaDto normalizado() {
        return new PacienteRadiologiaDto(
                id,
                valor(nombre, "Paciente"),
                valor(apellidos, "Sin datos"),
                valor(dni, "00000000"),
                edad == null ? 0 : edad,
                valor(sexo, "M"),
                valor(nroHistoria, "HC-PENDIENTE")
        );
    }

    private static String valor(String actual, String fallback) {
        return actual == null || actual.isBlank() ? fallback : actual;
    }
}
