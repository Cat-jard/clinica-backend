package com.clinica.auditoria.dto;

/**
 * Representacion de un evento de auditoria expuesta por la API
 * (accion como etiqueta en espanol y fecha ya formateada).
 */
public record AuditoriaResponse(
        long id,
        String usuarioEmail,
        String usuarioNombre,
        String rol,
        String accion,         // etiqueta: "Creación"
        String modulo,
        String descripcion,
        String entidad,        // null si no aplica
        String ip,
        String fecha           // "dd/MM/yyyy - HH:mm"
) {}
