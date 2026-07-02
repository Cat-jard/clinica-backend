package com.citas.service.dto;

public record MedicoResponse(
    String id,
    String dni,
    String nombre,
    String apellidos,
    String email,
    String telefono,
    String especialidad
) {}
