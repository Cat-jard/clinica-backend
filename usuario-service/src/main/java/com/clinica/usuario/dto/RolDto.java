package com.clinica.usuario.dto;

import com.clinica.usuario.domain.Rol;

/** Catalogo de roles para poblar selects del frontend. */
public record RolDto(
        String nombre,        // "MEDICO"
        String etiqueta,      // "Médico"
        String ruta,          // "/medico"
        String descripcion    // "Médico"
) {
    public static RolDto de(Rol rol) {
        return new RolDto(rol.name(), rol.getEtiqueta(), rol.getRuta(), rol.getDescripcion());
    }
}
