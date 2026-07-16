package com.clinica.laboratorio.dto;

import java.util.List;

public record SolicitudExamenesResponse(
        String mensaje,
        List<OrdenLabDto> ordenesLaboratorio,
        List<Object> estudiosRadiologia
) {
}
