package com.clinica.usuario.dto;

public record MedicoSyncEvent(
        Long id,
        String dni,
        String nombre,
        String apellidos,
        String email,
        String telefono,
        String especialidad
) {}
