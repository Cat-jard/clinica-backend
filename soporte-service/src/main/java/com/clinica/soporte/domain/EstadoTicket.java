package com.clinica.soporte.domain;

import java.text.Normalizer;
import java.util.Arrays;

/** Estado del ciclo de vida del ticket. */
public enum EstadoTicket {

    ABIERTO   ("Abierto"),
    EN_PROCESO("En proceso"),
    RESUELTO  ("Resuelto"),
    CERRADO   ("Cerrado");

    private final String etiqueta;

    EstadoTicket(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static EstadoTicket desde(String valor) {
        if (valor == null || valor.isBlank()) {
            return ABIERTO;
        }
        String objetivo = normalizar(valor);
        return Arrays.stream(values())
                .filter(e -> normalizar(e.name()).equals(objetivo) || normalizar(e.etiqueta).equals(objetivo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estado no valido: " + valor));
    }

    private static String normalizar(String s) {
        // Une "En proceso" / "EN_PROCESO" tratando espacios y guiones bajos por igual
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace('_', ' ')
                .replaceAll("\\s+", " ")
                .trim()
                .toUpperCase();
    }
}
