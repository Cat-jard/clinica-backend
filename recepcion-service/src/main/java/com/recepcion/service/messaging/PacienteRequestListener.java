package com.recepcion.service.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recepcion.service.config.RabbitMQConfig;
import com.recepcion.service.dto.ActualizarEstadoColaCommand;
import com.recepcion.service.dto.ColaTriajeResponse;
import com.recepcion.service.dto.PacienteResponse;
import com.recepcion.service.service.ColaService;
import com.recepcion.service.service.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class PacienteRequestListener {

    private static final Logger log = LoggerFactory.getLogger(PacienteRequestListener.class);

    private final PacienteService pacienteService;
    private final ColaService colaService;
    private final ObjectMapper objectMapper;

    public PacienteRequestListener(PacienteService pacienteService, ColaService colaService, ObjectMapper objectMapper) {
        this.pacienteService = pacienteService;
        this.colaService = colaService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.PACIENTE_REQUEST_QUEUE)
    public String handleObtenerPaciente(UUID id) throws JsonProcessingException {
        log.info("[paciente-listener] Peticion RPC recibida para paciente ID: {}", id);
        try {
            PacienteResponse res = pacienteService.findById(id);
            return objectMapper.writeValueAsString(res);
        } catch (Exception ex) {
            log.error("[paciente-listener] Paciente no encontrado o error: {}", id, ex);
            return "null";
        }
    }

    @RabbitListener(queues = RabbitMQConfig.COLA_TRIAJE_REQUEST_QUEUE)
    public String handleObtenerColaTriaje(String fechaStr) throws JsonProcessingException {
        log.info("[paciente-listener] Peticion RPC recibida para cola de triaje en fecha: {}", fechaStr);
        LocalDate fecha = (fechaStr == null || fechaStr.isBlank()) ? LocalDate.now() : LocalDate.parse(fechaStr);
        List<ColaTriajeResponse> res = colaService.obtenerColaTriaje(fecha);
        return objectMapper.writeValueAsString(res);
    }

    @RabbitListener(queues = RabbitMQConfig.COLA_TRIAJE_COMMAND_QUEUE)
    public void handleActualizarEstadoCola(String commandJson) throws JsonProcessingException {
        log.info("[paciente-listener] Comando recibido para actualizar estado de cola. Payload: {}", commandJson);
        ActualizarEstadoColaCommand command = objectMapper.readValue(commandJson, ActualizarEstadoColaCommand.class);
        colaService.actualizarEstado(command.id(), command.pacienteId(), command.estado());
    }
}
