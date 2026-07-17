package com.historiaclinica.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuardarAtencionRequest {

    private AnamnesisDTO anamnesis;
    private ExamenFisicoDTO examenFisico;
    private List<DiagnosticoDTO> diagnosticos;
    private List<RecetaDTO> recetas;
    private List<SolicitudExamenDTO> solicitudesExamenes;
    private List<InterconsultaDTO> interconsultas;
    private String indicacionesGenerales;
    private String procedimientosRealizados;
}
