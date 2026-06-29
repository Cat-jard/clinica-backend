package com.clinica.soporte.dto;

/**
 * Representacion de un ticket expuesta por la API
 * (categoria, prioridad y estado como etiquetas en espanol).
 */
public record TicketResponse(
        String id,
        String codigo,             // "TCK-0001"
        String titulo,
        String descripcion,
        String solicitanteDni,     // null si no aplica
        String solicitanteNombre,
        String categoria,          // etiqueta: "Hardware"
        String prioridad,          // etiqueta: "Alta"
        String estado,             // etiqueta: "Abierto"
        String asignadoA,          // null si aun no se asigna
        String fechaCreacion,      // "dd/MM/yyyy - HH:mm"
        String fechaActualizacion  // "dd/MM/yyyy - HH:mm" o "—"
) {}
