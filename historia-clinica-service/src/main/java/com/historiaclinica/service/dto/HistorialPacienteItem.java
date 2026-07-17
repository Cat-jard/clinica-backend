package com.historiaclinica.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HistorialPacienteItem {

    private UUID id;
    private LocalDate fechaAtencion;
    private String medicoNombre;
    private String especialidad;
    private String estado;
    private String diagnosticoPrincipal;
    private List<String> diagnosticos;
}
