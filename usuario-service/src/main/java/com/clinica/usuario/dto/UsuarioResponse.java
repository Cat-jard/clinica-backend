package com.clinica.usuario.dto;

/**
 * Representacion de un usuario expuesta por la API. Coincide con la interfaz
 * {@code Usuario} del frontend (rol y estado como etiquetas en espanol).
 */
public record UsuarioResponse(
        String id,
        String dni,
        String nombre,
        String apellidos,
        String email,
        String telefono,
        String rol,            // etiqueta: "Médico"
        String especialidad,   // null si no aplica
        String estado,         // "Activo" / "Inactivo"
        String ultimoAcceso,   // "dd/MM/yyyy - HH:mm" o "—"
        String ruta            // ruta de inicio del modulo en el frontend
) {}
