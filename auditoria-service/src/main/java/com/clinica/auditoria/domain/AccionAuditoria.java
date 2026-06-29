package com.clinica.auditoria.domain;

import java.text.Normalizer;
import java.util.Arrays;

/**
 * Tipo de evento registrado en la bitacora. {@code desde} resuelve tanto el
 * nombre del enum ("CREACION") como la etiqueta del frontend ("Creación").
 */
public enum AccionAuditoria {

    LOGIN          ("Inicio de sesión"),
    LOGOUT         ("Cierre de sesión"),
    CREACION       ("Creación"),
    ACTUALIZACION  ("Actualización"),
    ELIMINACION    ("Eliminación"),
    CONSULTA       ("Consulta"),
    ACCESO_DENEGADO("Acceso denegado");

    private final String etiqueta;

    AccionAuditoria(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static AccionAuditoria desde(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La accion es obligatoria");
        }
        String objetivo = normalizar(valor);
        return Arrays.stream(values())
                .filter(a -> normalizar(a.name()).equals(objetivo)
                        || normalizar(a.etiqueta).equals(objetivo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Accion no valida: " + valor));
    }

    private static String normalizar(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")   // quita tildes
                .trim()
                .toUpperCase();
    }
}
