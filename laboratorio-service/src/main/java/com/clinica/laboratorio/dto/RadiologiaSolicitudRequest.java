package com.clinica.laboratorio.dto;

public record RadiologiaSolicitudRequest(
        String atencionId,
        PacienteOrdenDto paciente,
        String medicoSolicitante,
        String especialidadMedico,
        ItemExamenRequest item
) {}
