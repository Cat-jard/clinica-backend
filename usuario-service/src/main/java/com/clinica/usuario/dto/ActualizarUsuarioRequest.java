package com.clinica.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Datos para actualizar un usuario existente. {@code password} es opcional:
 * solo se cambia cuando llega un valor.
 */
public record ActualizarUsuarioRequest(

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 digitos")
        String dni,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "Los apellidos son obligatorios")
        String apellidos,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo no valido")
        String email,

        @NotBlank(message = "El telefono es obligatorio")
        @Pattern(regexp = "\\d{9}", message = "El telefono debe tener 9 digitos")
        String telefono,

        @NotBlank(message = "El rol es obligatorio")
        String rol,

        String especialidad,

        String estado,

        @Size(min = 6, message = "La contrasena debe tener al menos 6 caracteres")
        String password
) {}
