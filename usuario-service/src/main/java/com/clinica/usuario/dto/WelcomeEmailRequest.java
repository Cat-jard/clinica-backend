package com.clinica.usuario.dto;

public record WelcomeEmailRequest(
        String dni,
        String email,
        String nombre,
        String apellidos
) {}
