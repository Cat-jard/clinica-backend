package com.clinica.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos para actualizar un ticket existente (lo usa el equipo de Soporte).
 * Permite reasignar, recategorizar, repriorizar y cambiar el estado.
 */
public record ActualizarTicketRequest(

        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 120, message = "El titulo no puede superar 120 caracteres")
        String titulo,

        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,

        @NotBlank(message = "La categoria es obligatoria")
        String categoria,

        @NotBlank(message = "La prioridad es obligatoria")
        String prioridad,

        String estado,      // opcional: si se omite se conserva el actual

        String asignadoA    // opcional: tecnico responsable
) {}
