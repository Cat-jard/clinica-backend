package com.clinica.radiologia.dto;

import jakarta.validation.Valid;

public record FirmarInformeRequest(
        @Valid InformeRadiologicoDto informe,
        Boolean urgente
) {
}
