package com.triaje.service.dto;

import java.util.UUID;

public record ColaTriajeResponse(
    UUID id,
    UUID pacienteId,
    String pacienteNombre,
    String ticket,
    String horaLlegada,
    String medicoNombre,
    String especialidad,
    String motivo,
    UUID citaId
) {}
