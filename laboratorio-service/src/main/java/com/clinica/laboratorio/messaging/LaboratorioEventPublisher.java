package com.clinica.laboratorio.messaging;

import com.clinica.laboratorio.config.RabbitMQConfig;
import com.clinica.laboratorio.dto.RadiologiaSolicitudRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class LaboratorioEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(LaboratorioEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public LaboratorioEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishRadiologyStudyRequest(RadiologiaSolicitudRequest request) {
        try {
            log.info("[laboratorio-publisher] Publicando solicitud de estudio radiologico para paciente: {}", 
                    request.paciente() != null ? request.paciente().nombre() : "Desconocido");
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLINIC_EXCHANGE,
                    RabbitMQConfig.RADIOLOGY_STUDY_REQUEST_ROUTING_KEY,
                    request
            );
        } catch (Exception ex) {
            log.error("[laboratorio-publisher] Error al publicar solicitud de estudio radiologico", ex);
        }
    }
}
