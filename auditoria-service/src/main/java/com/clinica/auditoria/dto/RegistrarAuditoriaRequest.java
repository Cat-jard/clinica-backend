package com.clinica.auditoria.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos para registrar un evento en la bitacora. Lo envian los demas
 * microservicios (o el propio usuario-service tras un login). {@code accion}
 * acepta el nombre del enum ("CREACION") o la etiqueta ("Creación").
 */
public record RegistrarAuditoriaRequest(

        String usuarioEmail,    // opcional: null en eventos del sistema

        String usuarioNombre,

        String rol,

        @NotBlank(message = "La accion es obligatoria")
        String accion,

        @NotBlank(message = "El modulo es obligatorio")
        String modulo,

        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,

        String entidad,         // opcional: recurso afectado

        String ip               // opcional
) {}
