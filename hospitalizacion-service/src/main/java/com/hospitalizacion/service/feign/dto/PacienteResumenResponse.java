package com.hospitalizacion.service.feign.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class PacienteResumenResponse {
    private UUID id;
    private String tipoDocumento;
    private String nroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String nroHistoria;
}
