package com.recepcion.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ColaTriajeResponse(
    UUID id,
    UUID pacienteId,
    String pacienteNombre,
    String pacienteDni,
    String ticket,
    String horaLlegada,
    String medicoNombre,
    String especialidad,
    String motivo,
    String estado,
    UUID citaId,
    LocalDate fecha
) {}
