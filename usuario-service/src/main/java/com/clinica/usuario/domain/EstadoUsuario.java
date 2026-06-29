package com.clinica.usuario.domain;

/**
 * Estado de la cuenta. Regla de oro: los usuarios NO se eliminan, solo se
 * desactivan, para conservar su historial de auditoria (Ley 30024 - RENHICE).
 */
public enum EstadoUsuario {

    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String etiqueta;

    EstadoUsuario(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static EstadoUsuario desde(String valor) {
        if (valor == null || valor.isBlank()) {
            return ACTIVO;
        }
        String v = valor.trim().toUpperCase();
        if (v.startsWith("ACT")) return ACTIVO;
        if (v.startsWith("INA")) return INACTIVO;
        throw new IllegalArgumentException("Estado no valido: " + valor);
    }
}
