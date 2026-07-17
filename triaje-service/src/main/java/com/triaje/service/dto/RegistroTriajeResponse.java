package com.triaje.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record RegistroTriajeResponse(
    UUID id,
    UUID pacienteId,
    String pacienteNombre,
    String pacienteDni,
    Long medicoId,
    String medicoNombre,
    UUID especialidadId,
    String especialidadNombre,
    UUID citaId,
    String ticket,
    LocalTime horaLlegada,
    LocalDate fechaTriaje,
    String motivoConsulta,
    String nivelConciencia,
    Integer dolor,
    String prioridad,
    String destino,
    String justificacion,
    String enfermeraId,
    Boolean conCita,
    LocalDateTime timestamp,
    String estado,
    SignosVitalesDTO signos
) {}
