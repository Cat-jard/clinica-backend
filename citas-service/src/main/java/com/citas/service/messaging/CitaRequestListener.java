package com.citas.service.messaging;

import com.citas.service.config.RabbitMQConfig;
import com.citas.service.dto.CitaResponse;
import com.citas.service.service.CitaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CitaRequestListener {

    private static final Logger log = LoggerFactory.getLogger(CitaRequestListener.class);

    private final CitaService citaService;
    private final ObjectMapper objectMapper;

    public CitaRequestListener(CitaService citaService, ObjectMapper objectMapper) {
        this.citaService = citaService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.CITA_REQUEST_QUEUE)
    public String handleObtenerCita(UUID id) throws JsonProcessingException {
        log.info("[cita-listener] Peticion RPC recibida para cita ID: {}", id);
        try {
            CitaResponse res = citaService.findById(id);
            return objectMapper.writeValueAsString(res);
        } catch (Exception ex) {
            log.error("[cita-listener] Error al obtener cita ID: {}", id, ex);
            return "null";
        }
    }

    @RabbitListener(queues = RabbitMQConfig.CITAS_PACIENTE_REQUEST_QUEUE)
    public String handleObtenerCitasPaciente(UUID pacienteId) throws JsonProcessingException {
        log.info("[cita-listener] Peticion RPC recibida para citas de paciente ID: {}", pacienteId);
        List<CitaResponse> res = citaService.findByPacienteId(pacienteId);
        return objectMapper.writeValueAsString(res);
    }
}
