package com.clinica.soporte.domain;

import java.text.Normalizer;
import java.util.Arrays;

/** Categoria de la incidencia. {@code desde} acepta el nombre del enum o la etiqueta. */
public enum CategoriaTicket {

    HARDWARE("Hardware"),
    SOFTWARE("Software"),
    RED     ("Red / Conectividad"),
    ACCESO  ("Accesos / Cuentas"),
    OTRO    ("Otro");

    private final String etiqueta;

    CategoriaTicket(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static CategoriaTicket desde(String valor) {
        if (valor == null || valor.isBlank()) {
            return OTRO;
        }
        String objetivo = normalizar(valor);
        return Arrays.stream(values())
                .filter(c -> normalizar(c.name()).equals(objetivo) || normalizar(c.etiqueta).equals(objetivo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria no valida: " + valor));
    }

    private static String normalizar(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").trim().toUpperCase();
    }
}
