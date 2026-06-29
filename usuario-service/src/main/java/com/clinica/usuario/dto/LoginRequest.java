package com.clinica.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo no valido")
        String email,

        @NotBlank(message = "La contrasena es obligatoria")
        String password
) {}
