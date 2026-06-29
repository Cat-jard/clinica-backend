package com.clinica.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Datos para abrir un ticket. {@code categoria} y {@code prioridad} aceptan el
 * nombre del enum ("HARDWARE") o la etiqueta ("Hardware"); si se omite la
 * prioridad se asigna "Media".
 */
public record CrearTicketRequest(

        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 120, message = "El titulo no puede superar 120 caracteres")
        String titulo,

        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,

        @Pattern(regexp = "\\d{8}", message = "El DNI del solicitante debe tener 8 digitos")
        String solicitanteDni,   // opcional

        @NotBlank(message = "El nombre del solicitante es obligatorio")
        String solicitanteNombre,

        @NotBlank(message = "La categoria es obligatoria")
        String categoria,

        String prioridad         // opcional: por defecto "Media"
) {}
