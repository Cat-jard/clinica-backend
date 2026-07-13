package com.clinica.radiologia.dto;

public record SerieDicomDto(
        String id,
        String descripcion,
        Integer numCortes
) {
}
