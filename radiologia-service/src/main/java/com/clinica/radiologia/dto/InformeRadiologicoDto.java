package com.clinica.radiologia.dto;

public record InformeRadiologicoDto(
        String hallazgos,
        String impresionDiagnostica,
        String recomendaciones,
        String codigoCIE10,
        String dosisRadiacion
) {
}
