package com.clinica.soporte.dto;

import jakarta.validation.constraints.NotBlank;

/** Cambio rapido de estado de un ticket (Abierto / En proceso / Resuelto / Cerrado). */
public record CambiarEstadoRequest(

        @NotBlank(message = "El estado es obligatorio")
        String estado,

        String asignadoA    // opcional: tecnico que toma el ticket
) {}
