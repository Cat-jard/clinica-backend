package com.historiaclinica.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AtencionResponse {

    private UUID id;
    private UUID pacienteId;
    private Long medicoId;
    private String medicoNombre;
    private String especialidad;
    private String consultorio;
    private LocalDate fechaAtencion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;
    private AnamnesisDTO anamnesis;
    private ExamenFisicoDTO examenFisico;
    private List<DiagnosticoDTO> diagnosticos;
    private List<RecetaDTO> recetas;
    private List<SolicitudExamenDTO> solicitudesExamenes;
    private List<InterconsultaDTO> interconsultas;
    private String indicacionesGenerales;
    private String procedimientosRealizados;
    private LocalDateTime firmadaEn;
    private Long firmaMedicoId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
