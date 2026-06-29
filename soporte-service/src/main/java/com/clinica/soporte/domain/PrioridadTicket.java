package com.clinica.soporte.domain;

import java.text.Normalizer;
import java.util.Arrays;

/** Prioridad de atencion de la incidencia. */
public enum PrioridadTicket {

    BAJA   ("Baja"),
    MEDIA  ("Media"),
    ALTA   ("Alta"),
    CRITICA("Crítica");

    private final String etiqueta;

    PrioridadTicket(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static PrioridadTicket desde(String valor) {
        if (valor == null || valor.isBlank()) {
            return MEDIA;
        }
        String objetivo = normalizar(valor);
        return Arrays.stream(values())
                .filter(p -> normalizar(p.name()).equals(objetivo) || normalizar(p.etiqueta).equals(objetivo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Prioridad no valida: " + valor));
    }

    private static String normalizar(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").trim().toUpperCase();
    }
}
