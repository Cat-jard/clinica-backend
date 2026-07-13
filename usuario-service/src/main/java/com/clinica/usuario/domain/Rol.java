package com.clinica.usuario.domain;

import java.text.Normalizer;
import java.util.Arrays;

/**
 * Roles del sistema (RBAC). Cada rol corresponde a uno de los modulos del SIHCE
 * y conoce su ruta de inicio en el frontend para redirigir tras el login.
 */
public enum Rol {

    ADMIN       ("Admin",       "/admin",         "Administracion"),
    RECEPCION   ("Recepción",   "/recepcionista", "Recepción / Admisión"),
    ENFERMERIA  ("Enfermería",  "/triaje",        "Enfermería / Triaje"),
    MEDICO      ("Médico",      "/medico",        "Médico"),
    LABORATORIO ("Laboratorio", "/laboratorio",   "Laboratorio Clínico"),
    RADIOLOGO   ("Radiólogo",   "/radiologo",     "Médico Radiólogo"),
    FARMACIA    ("Farmacia",    "/farmacia",       "Farmacia"),
    SOPORTE     ("Soporte",     "/soporte",       "Soporte Técnico / TI"),
    PACIENTE    ("Paciente",    "/portal",        "Portal del Paciente");

    private final String etiqueta;     // texto que muestra/envia el frontend
    private final String ruta;         // ruta de inicio del modulo en el frontend
    private final String descripcion;  // nombre largo del modulo

    Rol(String etiqueta, String ruta, String descripcion) {
        this.etiqueta = etiqueta;
        this.ruta = ruta;
        this.descripcion = descripcion;
    }

    public String getEtiqueta()    { return etiqueta; }
    public String getRuta()        { return ruta; }
    public String getDescripcion() { return descripcion; }

    /** Authority usada por Spring Security (formato ROLE_XXX). */
    public String getAuthority() {
        return "ROLE_" + name();
    }

    /**
     * Resuelve un Rol a partir del nombre del enum ("MEDICO") o de la etiqueta
     * del frontend ("Médico"), ignorando mayusculas y tildes.
     */
    public static Rol desde(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        String objetivo = normalizar(valor);
        return Arrays.stream(values())
                .filter(r -> normalizar(r.name()).equals(objetivo)
                        || normalizar(r.etiqueta).equals(objetivo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rol no valido: " + valor));
    }

    private static String normalizar(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")   // quita tildes
                .trim()
                .toUpperCase();
    }
}
